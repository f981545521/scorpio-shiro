package cn.acyou.scorpioshiro.conf.shiro;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * [Shiro的authc过滤器的执行流程](https://blog.csdn.net/qq_28000789/article/details/86555184)
 *
 * //1.还没有登陆成功的情况下:get请求我们在配置文件中设置的loginUrl，authc会放行请求到这里
 * //2.还没有登陆成功的情况下:post请求我们在配置文件中设置的loginUrl，authc在认证失败后会让浏览器重定向到这里
 * //3.还没有登陆成功的情况下:请求(不管post还是get)的页面不是loginUrl且需要authc过滤,那么authc也会让浏览器重定向到这里
 * //4.登陆成功后，浏览器再次访问loginUrl(不管post或get)，authc也会放行到这里。
 *
 * 解决未登录时。authc 过滤器会自动执行登录问题！
 * @author youfang
 * @version [1.0.0, 2020-6-26 下午 11:56]
 **/
public class CustomAuthcFilter extends FormAuthenticationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        //如果请求的是loginUrl 并且是POST请求，那么肯定是要验证密码的，这里直接返回false 就会执行onAcessDenied()方法
        if (isLoginRequest(request, response) && isLoginSubmission(request, response)) {
            return false;
        }
        //如果是其他请求 则执行父类的方法
        return super.isAccessAllowed(request, response, mappedValue);
    }
}
