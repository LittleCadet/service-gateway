package com.service.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author LittleCadet
 * @Date 2020/2/19
 */
@EnableZuulProxy
@SpringBootApplication
public class ServiceGatewayApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(ServiceGatewayApplication.class,args);
    }
}
