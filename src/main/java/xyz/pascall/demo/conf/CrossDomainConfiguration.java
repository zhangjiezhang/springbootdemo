package xyz.pascall.demo.conf;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class CrossDomainConfiguration {

    /**
     * 解决跨域请求问题（使用过滤器实现CrossFilter）
     * @return
     */
    @Bean
    public FilterRegistrationBean<CrossFilter> registerFilter() {

        FilterRegistrationBean<CrossFilter> bean = new FilterRegistrationBean<CrossFilter>();
        bean.addUrlPatterns("/*");
        bean.setFilter(new CrossFilter());

        return bean ;
    }
}



/**
 * 这个过滤器：解决跨域请求问题
 */
class CrossFilter implements Filter {
    /**
     * web容器初始化后执行
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * 当每次请求时执行
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String origin = req.getHeader("Origin");
        if( StringUtils.isEmpty(origin) ) {
            origin = "*";
        }
        String header = req.getHeader("Access-Control-Request-Headers");
        if( StringUtils.isEmpty(header) ) {
            header = "*";
        }

        resp.addHeader("Access-Control-Allow-Origin", origin);
        resp.addHeader("Access-Control-Allow-Headers", header);
        resp.addHeader("Access-Control-Allow-Method", "*");
        resp.addHeader("Access-Control-Max-Age", "3600");

        System.err.println("跨域请求filter执行了");

        filterChain.doFilter(req, resp);
    }

    /**
     * web容器销毁之前执行
     */
    @Override
    public void destroy() {}
}
