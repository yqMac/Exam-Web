package com.yqmac.exam.test;

import com.yqmac.exam.service.IAjaxService;
import com.yqmac.exam.service.IUserAnswerService;
import com.yqmac.exam.service.IUserGradeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yqmac on 2016/4/28 0028.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestAjax {

    @Resource(name = "ajaxService")
    private IAjaxService ajaxService;


    @Resource(name = "userAnswerService")
    private IUserAnswerService userAnswerService;

    @Resource(name = "userGradeService")
    private IUserGradeService userGradeService;

    @Test
    public void test01(){
        ajaxService.markOne(2, 23.2f);
        //System.out.println(ajaxService.getStrategyTypesByExamId(4));
        System.out.println(userGradeService.list().get(0).getGrade());

    }

    @Test
    public void test02(){
        ajaxService.markOne(1,20.3f);

    }

}
