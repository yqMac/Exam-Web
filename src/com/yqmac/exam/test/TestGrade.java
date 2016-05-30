package com.yqmac.exam.test;

import com.yqmac.exam.service.IUserGradeService;
import com.yqmac.exam.vo.TExam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/5/16 0016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestGrade {

    @Resource(name = "userGradeService")
    private IUserGradeService userGradeService;



    @Test
    public void test01(){
        List<TExam> exams = userGradeService.listExam();

        for (TExam exam : exams) {
            System.out.println(exam.getExamName());

        }
    }
}
