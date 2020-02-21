package com.service.gateway.fallback;

import com.netflix.hystrix.exception.HystrixTimeoutException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * zuul 整合hystrix的回退机制
 * @Author LittleCadet
 * @Date 2020/2/21
 */
@Slf4j
@Component
public class CustomZuulFallBackProvider implements FallbackProvider
{
    /**
     * 指定为那个服务提供回退    “*”：为所有的网关代理的服务
     * @return
     */
    @Override
    public String getRoute()
    {
        return "*";
    }

    /**
     * 提供了造成回退的原因
     * 备注：E版本以前：用于继承的类是ZuulFallBackProvider,但是不提供回退原因
     * @param route
     * @param cause
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse(String route, Throwable cause)
    {
        if(cause instanceof HystrixTimeoutException)
            return this.response(HttpStatus.GATEWAY_TIMEOUT);
        else
            return this.response(HttpStatus.valueOf(cause.getMessage()));
    }

    /**
     * 设置http的response
     * @param status
     * @return
     */
    private ClientHttpResponse response(final HttpStatus status){
        return new ClientHttpResponse()
        {
            @Override
            public HttpStatus getStatusCode()
                throws IOException
            {
                return status;
            }

            @Override
            public int getRawStatusCode()
                throws IOException
            {
                return status.value();
            }

            @Override
            public String getStatusText()
                throws IOException
            {
                return status.getReasonPhrase();
            }

            @Override
            public void close()
            {

            }

            @Override
            public InputStream getBody()
                throws IOException
            {
                log.error("message:{}" ,status);
                return new ByteArrayInputStream("服务不可用，请稍后重试".getBytes());
            }

            @Override
            public HttpHeaders getHeaders()
            {
                HttpHeaders headers = new HttpHeaders();
                MediaType mediaType = new MediaType("application","json", Charset.forName("UTF-8"));
                headers.setContentType(mediaType);
                return headers;
            }
        };
    }
}
