package cn.acyou.scorpioshiro.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-6-27 下午 02:16]
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired(required = false)
    private List<HandlerInterceptor> interceptorList = Collections.emptyList();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (HandlerInterceptor handlerInterceptor : interceptorList) {
            registry.addInterceptor(handlerInterceptor);
        }
    }


}
