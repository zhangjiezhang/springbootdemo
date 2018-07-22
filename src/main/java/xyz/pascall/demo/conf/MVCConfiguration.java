package xyz.pascall.demo.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class MVCConfiguration implements WebMvcConfigurer {

    /**
     * 拦截器(判断用户是否登录)
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将加一个拦截器，检查会话，URL以/**开头的都使用此拦截器
        registry.addInterceptor(new SessionHandlerInterceptor())
                .addPathPatterns("/**");
    }
}


/**
 * 用于侦测请求
 */
class SessionHandlerInterceptor implements HandlerInterceptor {
    /**
     * 到达Controller前执行
     * （登录、编码等）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // TODO Auto-generated method stub
        //return HandlerInterceptor.super.preHandle(request, response, handler);
        /*User user = (User) request.getSession().getAttribute("user");
        if(user == null) {
            System.err.println("error: session not have user, user is null");
            return true;
        }*/

        System.err.println("执行用户校验Interceptor,用于验证session中的数据");
        return true;
    }

    /**
     * 执行完Controller后执行
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    /**
     * 页面渲染完毕后调用此方法，通常用来清除某些资源，类似Java语法的finally
     * （关闭资源）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}

