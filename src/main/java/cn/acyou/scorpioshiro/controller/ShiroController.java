package cn.acyou.scorpioshiro.controller;

import cn.acyou.scorpioshiro.common.Result;
import cn.acyou.scorpioshiro.entity.Student;
import cn.acyou.scorpioshiro.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-4-20 下午 09:05]
 **/
@RestController
@RequestMapping("/shiro")
public class ShiroController {

    @Autowired
    private StudentService studentService;


    @RequiresPermissions("student:list")
    @RequestMapping("/listStudents")
    public Result<List<Student>> listStudents() {
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Object currentUser = session.getAttribute("currentUser");
        System.out.println(currentUser);
        long timeout = session.getTimeout();
        System.out.println("Subject 超时时间：" + timeout);
        System.out.println("Subject isRemembered：" + subject.isRemembered());
        List<Student> students = studentService.selectAll();
        return Result.success(students);
    }

    @RequiresPermissions("student:get")
    @RequestMapping("/getStudent")
    public Result<Student> getStudent(Integer id) {
        Student student = studentService.selectByPrimaryKey(id);
        return Result.success(student);
    }

    @RequiresRoles("developer")
    @RequestMapping("/listStudentsForDeveloper")
    public Result<List<Student>> listStudentsForDeveloper() {
        List<Student> students = studentService.selectAll();
        return Result.success(students);
    }


}
