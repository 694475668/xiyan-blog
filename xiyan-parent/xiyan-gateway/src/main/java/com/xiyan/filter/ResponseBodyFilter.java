package com.xiyan.filter;

import com.alibaba.fastjson.JSONArray;
import com.xiyan.constants.Constant;
import com.xiyan.util.AESUtil;
import com.xiyan.vo.ResponseBodyVO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * 响应体加密的操作
 *
 * @author bright
 * @date 2020-09-18
 */
@Component
@Slf4j
public class ResponseBodyFilter implements GlobalFilter, Ordered {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取ServerHttpRequest对象
        ServerHttpRequest request = exchange.getRequest();

        //根据请求头的x-aes 判断响应数据是否需要加密
        HttpHeaders head = request.getHeaders();
        String encryptionFlag = head.getFirst("x-aes");
        if (encryptionFlag != null) {
            if (encryptionFlag.equals(Constant.ENCRYPTION_FLAG)) {
                return chain.filter(exchange);
            }
        }

        //获取aes密钥
        String aesKeypair = redisTemplate.opsForValue().get(Constant.AES_KEY);
        //获取请求的url
        String url = request.getURI().toString();
        //获取response的 返回数据
        ServerHttpResponse originalResponse = exchange.getResponse();
        DataBufferFactory bufferFactory = originalResponse.bufferFactory();
        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                //处理一些不需要响应的接口比如下载
                if (url.indexOf("/pic/code") == -1 && url.indexOf("/export") == -1 && url.indexOf("/xlsx") == -1) {
                    if (getStatusCode().equals(HttpStatus.OK) && body instanceof Flux) {
                        Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                        return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                            //解决返回体分段传输
                            List<byte[]> list = new ArrayList<>();
                            dataBuffers.forEach(dataBuffer -> {
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer);
                                list.add(content);
                            });
                            byte[] both = null;
                            Iterator<byte[]> iterator = list.iterator();
                            while (iterator.hasNext()) {
                                both = ArrayUtils.addAll(both, iterator.next());
                            }
                            String responseData = new String(both, Charset.forName("UTF-8"));
                            log.info("\n 加密之前数据的内容：[{}]", responseData);
                            byte[] uppedContent = null;
                            //过滤获取aes密钥接口不需要加密
                            if (url.indexOf("/cms/aes/key") == -1) {
                                try {
                                    responseData = AESUtil.aesEncrypt(responseData, aesKeypair).toString();
                                    log.info("\n 数据加密后==========={}", responseData);
                                    ResponseBodyVO responseBodyVO = new ResponseBodyVO(responseData);
                                    uppedContent = new String(JSONArray.toJSONString(responseBodyVO).getBytes(), Charset.forName("UTF-8")).getBytes();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                //不加密数据
                                uppedContent = new String(responseData.getBytes(), Charset.forName("UTF-8")).getBytes();
                            }
                            log.info("\n requestHeaders=[{}], \n requestUrl=[{}], \n responseBody:[{}] \n", request.getHeaders(), url, responseData);
                            originalResponse.getHeaders().setContentLength(uppedContent.length);
                            return bufferFactory.wrap(uppedContent);
                        }));
                    } else {
                        log.error("\n 响应code异常:{} \n", getStatusCode());
                    }
                } else {
                    log.info("\n responseBodyrequestHeaders=[{}], \n requestUrl=[{}], \n responseBody:[{}] \n", request.getHeaders(), url, "文件流");
                }
                return super.writeWith(body);
            }
        };
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -1;
    }
}