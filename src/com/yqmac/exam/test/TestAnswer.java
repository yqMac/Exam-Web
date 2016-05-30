package com.yqmac.exam.test;

import com.yqmac.exam.service.IUserAnswerService;
import com.yqmac.exam.vo.TUserAnswer;
import com.yqmac.exam.vo.TExam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/5/13 0013.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestAnswer {

    @Resource(name = "userAnswerService")
    private IUserAnswerService userAnswerService;


    @Test
    public void test01(){
        List<TExam> exams = userAnswerService.listExam();
        for (TExam exam : exams) {
            System.out.println(exam);
        }
    }


    @Test
    public void test02(){
        List<TUserAnswer> answers = userAnswerService.listByExamId(49);
        for (TUserAnswer answer : answers) {
            System.out.println( answer.getTPaperQues().getQuesContent()+" : ----> "+answer.getAnswer());
        }
    }
}
