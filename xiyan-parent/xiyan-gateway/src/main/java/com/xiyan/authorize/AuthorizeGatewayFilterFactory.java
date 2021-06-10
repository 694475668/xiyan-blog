package com.xiyan.authorize;

import com.alibaba.fastjson.JSONArray;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.xiyan.constants.Constant;
import com.xiyan.util.JWTUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 身份验证
 *
 * @author bright
 */
@Component
@Slf4j
@ConfigurationProperties(prefix = "exclude")
@Data
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {


    /**
     * 需要放行的授权接口
     */
    private String[] path;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 授权token
     */
    private static final String AUTHORIZE_TOKEN = "x-access-token";

    /**
     *
     */
    private static final String AUTHORIZE_IP = "x-access-ip";

    /**
     * 用来标记是否需要授权校验
     */
    private static final String AUTHORIZE_RESOURCE = "x-access-resource";

    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
        log.info("Loaded GatewayFilterFactory [Authorize]");
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList("enabled");
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            //是否需要token校验
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }
            //获取ServerHttpRequest对象
            ServerHttpRequest request = exchange.getRequest();
            //获取ServerHttpResponse对象
            ServerHttpResponse response = exchange.getResponse();

            // 获取HttpHeaders对象
            HttpHeaders headers = request.getHeaders();
            ServerWebExchange build = null;

            //从header头信息中token信息
            String token = headers.getFirst(AUTHORIZE_TOKEN);
            //获取用户ip
            String ip = headers.getFirst(AUTHORIZE_IP);
            log.info(request.getURI().toString());
            if (token == null) {
                //设置状态码
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            log.info(" 请求的token: {}", token);

            if (token != null && !"".equals(token)) {
                try {
                    //调用token解析方法
                    User user = getUser(token);
                    final String userId = user.getUserId();
                    final String username = user.getUsername();
                    //设置请求头
                    ServerHttpRequest host = exchange.getRequest().mutate().headers(httpHeaders -> {
                        httpHeaders.add("User-ID", userId);
                        httpHeaders.add("User-NAME", username);
                    }).build();
                    build = exchange.mutate().request(host).build();
                    //判断是否需要接口授权校验
                    if (StringUtils.isNotBlank(headers.getFirst(AUTHORIZE_RESOURCE))) {
                        Boolean authority = getAuthority(request.getPath().toString(), userId);
                        if (authority) {
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            return response.setComplete();
                        }
                    }
                    //黑名单校验
                    if (checkBlackList(ip, username)) {
                        response.setStatusCode(HttpStatus.FORBIDDEN);
                        return response.setComplete();
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                    //token无效
                    log.error("出现异常【{}】", exception.getMessage());
                    //设置状态码
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }
            }
            //认证通过，将用户ID进行传递到下游服务器
            return chain.filter(build);
        };
    }

    /**
     * 接口权限校验
     *
     * @param url
     * @param userId
     * @return
     */
    private Boolean getAuthority(String url, String userId) {
        //放行接口不需要做授权校验，直接放行
        for (String path : path) {
            if (url.indexOf(path) != -1) {
                return false;
            }
        }
        //权限校验
        String resource = redisTemplate.opsForValue().get(Constant.AUTH_USER_KEY + userId);
        List<String> list = JSONArray.parseArray(resource, String.class);
        for (String res : list) {
            if (url.indexOf(res) != -1) {
                return false;
            }
        }
        return true;
    }

    /**
     * 校验黑名单
     *
     * @param ip
     * @param username
     * @return
     */
    private Boolean checkBlackList(String ip, String username) {
        String blackList = redisTemplate.opsForValue().get(Constant.BLACK_LIST_KEY);
        if (null == blackList) {
            return false;
        }
        List<String> list = JSONArray.parseArray(blackList, String.class);
        if (list.contains(ip) || list.contains(username)) {
            return true;
        }
        return false;
    }

    /**
     * /解析token
     *
     * @param token
     * @return
     */
    private User getUser(String token) {
        Map<String, Claim> map;
        User user = new User();
        try {
            map = JWTUtil.verifyToken(token);
            //遍历
            for (Map.Entry<String, Claim> entry : map.entrySet()) {
                if ("userId".equals(entry.getKey())) {
                    log.info("userId============{}", entry.getValue().asString());
                    user.setUserId(entry.getValue().asString());
                }
                if ("username".equals(entry.getKey())) {
                    log.info("username============{}", entry.getValue().asString());
                    user.setUsername(entry.getValue().asString());
                }
            }
            return user;
        } catch (TokenExpiredException e1) {
            log.error("token已过期:[{}]", e1.getMessage());
        } catch (SignatureVerificationException e2) {
            log.error("Token无效:[{}]", e2.getMessage());
        } catch (JWTDecodeException e3) {
            log.error("令牌应该包含3个部分:[{}]", e3.getMessage());
        }
        return null;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Config {
        /**
         * 控制是否开启认证
         */
        private boolean enabled;
    }
}
