package com.yqmac.exam.test;

import com.yqmac.exam.service.IUserAnswerService;
import com.yqmac.exam.vo.TUserAnswer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yqmac on 2016/5/14 0014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestUserAnswer {

    @Resource(name = "userAnswerService")
    private IUserAnswerService userAnswerService;

    @Test
    public void test01(){
        TUserAnswer ans = userAnswerService.list().get(0);

        System.out.println(ans.getTUserGrade().getId());
    }

    @Test
    public void test02(){

    }
}
