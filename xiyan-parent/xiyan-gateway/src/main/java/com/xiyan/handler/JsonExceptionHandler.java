package com.xiyan.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义异常处理
 *
 * @author: bright
 * @date:Created in 2020-08-09 17:35
 */
@Slf4j
public class JsonExceptionHandler extends DefaultErrorWebExceptionHandler {

    /**
     * Create a new {@code DefaultErrorWebExceptionHandler} instance.
     *
     * @param errorAttributes    the error attributes
     * @param resourceProperties the resources configuration properties
     * @param errorProperties    the error configuration properties
     * @param applicationContext the current application context
     */
    public JsonExceptionHandler(ErrorAttributes errorAttributes, ResourceProperties resourceProperties,
                                ErrorProperties errorProperties, ApplicationContext applicationContext) {
        super(errorAttributes, resourceProperties, errorProperties, applicationContext);
    }

    /**
     * 获取异常属性
     */

    @Override
    protected Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
        int code = 500;
        Throwable error = super.getError(request);
        if (error instanceof org.springframework.cloud.gateway.support.NotFoundException) {
            code = 404;
        }
        //aes密钥错误
        if (error instanceof javax.crypto.BadPaddingException) {
            code = 678;
        }
        //限流
        if (error instanceof com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException) {
            code = 789;
        }
        log.error(response(code, this.buildMessage(request, error)).toString());

        return response(code, this.buildMessage(request, error));
    }

    /**
     * 指定响应处理方法为JSON处理的方法
     *
     * @param errorAttributes
     */

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {

        //json显示错误信息
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);

        //HTML示错误信息

        // return RouterFunctions.route(acceptsTextHtml(),
        // this::renderErrorView).andRoute(RequestPredicates.all(),
        // this::renderErrorResponse);
    }

    /**
     * 根据code获取对应的HttpStatus 原始的方法是通过status来获取对应的HttpStatus的，
     * 如果我们定义的格式中没有status字段的话，这么就会报错，找不到对应的响应码，
     * 要么返回数据格式中增加status子段，要么重写，我这边返回的是code，所以要重写
     *
     * @param errorAttributes
     */
    @Override
    protected int getHttpStatus(Map<String, Object> errorAttributes) {
        int statusCode = (int) errorAttributes.get("errorCode");
        return statusCode;
    }


    /**
     * 构建异常信息
     *
     * @param request
     * @param ex
     * @return
     */
    private String buildMessage(ServerRequest request, Throwable ex) {
        StringBuilder message = new StringBuilder("Failed to handle request [");
        message.append(request.methodName());
        message.append(" ");
        message.append(request.uri());
        message.append("]");
        if (ex != null) {
            message.append(": ");
            message.append(ex.getMessage());
        }
        return message.toString();
    }

    /**
     * 构建返回的JSON数据格式
     *
     * @param errorCode    状态码
     * @param errorMessage 异常信息
     * @return
     */
    public static Map<String, Object> response(int errorCode, String errorMessage) {
        Map<String, Object> map = new HashMap<>();
        //处理限流后响应规则
        map.put("success", false);
        map.put("errorCode", errorCode);
        if (errorCode == 789) {
            map.put("errorMsg", "客官,您操作的有点快哦！！！");
            return map;
        }
        //除了限流其它的异常都显示如下提示信息
        map.put("errorMsg", "客官，您访问的页面已经飞走了！！！");
        return map;
    }
}
