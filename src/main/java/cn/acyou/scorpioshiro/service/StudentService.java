package cn.acyou.scorpioshiro.service;

import cn.acyou.scorpioshiro.entity.Student;
import cn.acyou.scorpioshiro.util.RandomUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author youfang
 * @version [1.0.0, 2020-6-26 下午 09:25]
 **/
@Service
public class StudentService {

    public List<Student> selectAll() {
        List<Student> studentList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            studentList.add(randomStudent());
        }
        return studentList;
    }

    public Student selectByPrimaryKey(Integer id) {
        Student student = randomStudent();
        student.setId(id);
        return student;
    }

    public Student randomStudent(){
        Student student = new Student();
        student.setId(Integer.valueOf(RandomUtil.createRandom(true, 5)));
        student.setName(RandomUtil.randomUserName());
        student.setAge(RandomUtil.randomAge());
        return student;
    }
}
