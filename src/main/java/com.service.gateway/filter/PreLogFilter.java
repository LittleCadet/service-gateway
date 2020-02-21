package com.service.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**z
 * 设置一个zuul的自定义的过滤器
 * @Author LittleCadet
 * @Date 2020/2/21
 */
@Slf4j
@Component //@Component,@Service, @Controller,@Repostory将该类扫描到spring的容器中去，相当于xml中<bean id = "" class  ="">
public class PreLogFilter extends ZuulFilter
{
    /**
     * 何时执行
     * @return
     */
    @Override
    public String filterType()
    {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 继承ZuulFilter的子类的执行顺序
     * @return
     */
    @Override
    public int filterOrder()
    {
        //在PreDecorationFilter之前执行
        return FilterConstants.PRE_DECORATION_FILTER_ORDER -1;
    }

    /**
     * 是否执行
     * @return
     */
    @Override
    public boolean shouldFilter()
    {
        return Boolean.TRUE;
    }

    /**
     * 执行什么
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run()
        throws ZuulException
    {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        log.info("===============正在执行自定义的zuul的过滤器send {} request to {}================" ,request.getMethod(), request.getRequestURI());
        return null;
    }
}
