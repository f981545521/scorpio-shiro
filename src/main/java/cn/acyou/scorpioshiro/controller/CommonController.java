package cn.acyou.scorpioshiro.controller;

import cn.acyou.scorpioshiro.common.Result;
import cn.acyou.scorpioshiro.entity.Student;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * @author youfang
 * @version [1.0.0, 2020-6-26 下午 11:59]
 **/
@RestController
@RequestMapping("/common")
public class CommonController {
    @RequestMapping(value = "/pleaseLogin")
    public Result<String> pleaseLogin() {
        return Result.success("请先登录！");
    }

    @RequestMapping(value = "/successLogin")
    public Result<String> successLogin() {
        return Result.success("登录成功！");
    }

    @RequestMapping(value = "/unauthorized")
    public Result<String> unauthorized() {
        return Result.success("unauthorized！");
    }

    @RequestMapping(value = "/login")
    public Result<String> login(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("rememberMe") boolean rememberMe) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        try {
            if (rememberMe){
                token.setRememberMe(rememberMe);
            }
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return Result.error("未知账户");
        } catch (IncorrectCredentialsException ice) {
            return Result.error("密码不正确");
        } catch (LockedAccountException lae) {
            return Result.error("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return Result.error("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确！");
        }
        //登录成功
        if (subject.isAuthenticated()) {//表示用户进行了身份验证登录的
            //1分钟的过期
            Session session = subject.getSession();
            session.setAttribute("currentUser", new Student().random());
            session.setTimeout(1*60*1000);
            Serializable id = subject.getSession().getId();
            return Result.success("登录成功！SessionId:" + id);
        } else {
            token.clear();
            return Result.error("登录失败");
        }
    }

}
