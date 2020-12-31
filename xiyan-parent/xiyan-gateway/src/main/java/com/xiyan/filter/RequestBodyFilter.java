package com.xiyan.filter;

import com.alibaba.fastjson.JSONObject;
import com.xiyan.constants.Constant;
import com.xiyan.util.AESUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.CachedBodyOutputMessage;
import org.springframework.cloud.gateway.support.BodyInserterContext;
import org.springframework.cloud.gateway.support.DefaultServerRequest;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


/**
 * 处理前端请求体解密和
 *
 * @author bright
 * @date 2020-09-18
 */
@Component
@Slf4j
public class RequestBodyFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取aes密钥
        String keypair = redisTemplate.opsForValue().get(Constant.AES_KEY);
        log.info("redis-key [{}]", keypair);
        //获取ServerHttpRequest对象
        ServerHttpRequest request = exchange.getRequest();

        //根据请求头的x-aes 判断请求参数是否需要加密
        HttpHeaders head = request.getHeaders();
        String encryptionFlag = head.getFirst("x-aes");
        if (encryptionFlag != null) {
            if (encryptionFlag.equals(Constant.ENCRYPTION_FLAG)) {
                return chain.filter(exchange);
            }
        }

        //获取请求类型
        String methodValue = request.getMethodValue();
        //获取Content-type
        String contentType = head.getFirst("Content-type");
        log.info("requestUrl [{}], Content-type [{}],  method [{}]", request.getURI(), contentType, methodValue);
        //获取请求体
        Object requestBody = exchange.getAttribute("cachedRequestBodyObject");
        String requestData = "";
        //PUT请求Content-type可以是application/json,application/json;charset=utf-8,application/x-www-form-urlencoded
        if (methodValue.equalsIgnoreCase(HttpMethod.PUT.toString())) {
            requestData = InterceptFormData(requestBody, request);
        } else {
            //如果是post请求的看 Content-type 为application/x-www-form-urlencoded还是application/json类型
            if (null != contentType) {
                //Content-type不同数据解密方式不同，需要做特殊处理
                // 兼容ie和苹果手机的浏览器，默认是不支持application/json方式
                if (contentType.equalsIgnoreCase(MediaType.APPLICATION_FORM_URLENCODED_VALUE)) {
                    requestData = InterceptFormData(requestBody, request);
                } else {
                    requestData = InterceptPost(requestBody, request);
                }
            }
        }
        if (!requestData.equals("")) {
            //获取解密后的内容
            String content = AESUtil.aesDecrypt(requestData, keypair);
            log.info("\n 解密后》requestBody[{}]", content);
            // mediaType
            MediaType mediaType = exchange.getRequest().getHeaders().getContentType();
            // read & modify body
            ServerRequest serverRequest = new DefaultServerRequest(exchange);
            Mono<String> modifiedBody = serverRequest.bodyToMono(String.class)
                    .flatMap(body -> {
                        if (MediaType.APPLICATION_JSON.isCompatibleWith(mediaType) || MediaType.APPLICATION_FORM_URLENCODED.isCompatibleWith(mediaType)) {
                            // 对原先的body进行修改操作
                            return Mono.just(content);
                        }
                        return Mono.empty();
                    });
            BodyInserter bodyInserter = BodyInserters.fromPublisher(modifiedBody, String.class);
            HttpHeaders headers = new HttpHeaders();
            headers.putAll(exchange.getRequest().getHeaders());
            headers.remove(HttpHeaders.CONTENT_LENGTH);
            /*由于Content-type为application/x-www-form-urlencoded的时候 后端@requestBody获取不到参数
            所以这里需要修改CONTENT_TYPE为 application/json
                    《删除 CONTENT_TYPE》
            */
            headers.remove(HttpHeaders.CONTENT_TYPE);
            CachedBodyOutputMessage outputMessage = new CachedBodyOutputMessage(exchange, headers);
            return bodyInserter.insert(outputMessage, new BodyInserterContext())
                    .then(Mono.defer(() -> {
                        ServerHttpRequestDecorator decorator = new ServerHttpRequestDecorator(
                                exchange.getRequest()) {
                            @Override
                            public HttpHeaders getHeaders() {
                                long contentLength = headers.getContentLength();
                                HttpHeaders httpHeaders = new HttpHeaders();
                                httpHeaders.putAll(super.getHeaders());
                                if (contentLength > 0) {
                                    httpHeaders.setContentLength(contentLength);
                                } else {
                                    httpHeaders.set(HttpHeaders.TRANSFER_ENCODING, "chunked");
                                }
                                //重新设置CONTENT_TYPE为 application/json
                                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                                return httpHeaders;
                            }

                            @Override
                            public Flux<DataBuffer> getBody() {
                                return outputMessage.getBody();
                            }
                        };
                        return chain.filter(exchange.mutate().request(decorator).build());
                    }));
        }
        return chain.filter(exchange);
    }


    /**
     * 如果是post请求的只能是 Content-type 为application/x-www-form-urlencoded
     * 如果是PUT请求可以是application/json,application/json;charset=utf-8,application/x-www-form-urlencoded
     * 三种类型
     *
     * @param requestData
     * @return
     */
    public String InterceptFormData(Object requestData, ServerHttpRequest request) {
        //过滤一些请求,因为支付的回调里面的参数是没有加密的所以转换会出现问题,所以需要过滤下支付的回调地址
        if (request.getURI().toString().indexOf("/cms/user/qq/back") != -1
                || request.getURI().toString().indexOf("/cms/user/weibo/back") != -1
        ) {
            return "";
        }
        if (requestData != null && !requestData.equals("")) {
            String s = "{\"requestData\":";
            if (!requestData.toString().startsWith(s)) {
                throw new RuntimeException("参数【requestData】缺失异常！");
            } else {
                int closeLen = requestData.toString().length() - 1;
                int openLen = "{\"requestData\":".length();
                String substring = StringUtils.substring(requestData.toString(), openLen, closeLen);
                return substring;
            }
        }
        return "";
    }

    /**
     * 截取POST请求body的加密数据
     * 这是  Content-type 为 application/json或者
     * application/json;charset=utf-8类型 才可以
     *
     * @param requestBody
     * @param request
     * @return
     */
    public String InterceptPost(Object requestBody, ServerHttpRequest request) {
        //过滤一些请求,因为支付的回调里面的参数是没有加密的所以转换会出现问题,所以需要过滤下支付的回调地址
        if (request.getURI().toString().indexOf("/cms/user/qq/back") != -1
                || request.getURI().toString().indexOf("/cms/user/weibo/back") != -1
        ) {
            return "";
        }
        //将请求体json对象转换为JSONObject
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(requestBody);
        //获取加密串
        String requestData = "";
        if (requestBody != null) {
            requestData = jsonObject.get("requestData").toString();
        }
        return requestData;
    }

    @Override
    public int getOrder() {
        return -2;
    }
}