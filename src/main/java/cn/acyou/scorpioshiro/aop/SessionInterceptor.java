package cn.acyou.scorpioshiro.aop;

import cn.acyou.scorpioshiro.entity.Student;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author youfang
 * @version [1.0.0, 2020-6-27 下午 02:13]
 **/
@Component
public class SessionInterceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("---SessionInterceptor preHandle---");
        System.out.println(request.getContextPath());
        Subject currentUser = SecurityUtils.getSubject();
        //判断用户是通过记住我功能自动登录,此时session失效
        if(!currentUser.isAuthenticated() && currentUser.isRemembered()){
            try {
                Object principal = currentUser.getPrincipal();
                System.out.println(principal);
                //对密码进行加密后验证
                UsernamePasswordToken token = new UsernamePasswordToken(String.valueOf(principal), "123",currentUser.isRemembered());
                //把当前用户放入session
                currentUser.login(token);
                Session session = currentUser.getSession();
                session.setAttribute("currentUser", new Student().random());
                //设置会话的过期时间--ms,默认是30分钟，设置负数表示永不过期
                session.setTimeout(1000*60*1);
            }catch (Exception e){
                //自动登录失败,跳转到登录页面
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
            }
            if(!currentUser.isAuthenticated()){
                //自动登录失败,跳转到登录页面
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
