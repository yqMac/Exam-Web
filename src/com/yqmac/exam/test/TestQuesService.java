package com.yqmac.exam.test;

import com.yqmac.exam.service.IQuesService;
import com.yqmac.exam.vo.TQues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/27 0027.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestQuesService {

    @Resource(name = "quesService")
    private IQuesService quesService;

    @Test
    public void testget(){
        List<TQues> ss = quesService.listAll();
        System.out.println("list数量"+ss.size());

        int a =  quesService.getQuesCountPointType(1, 1);
        System.out.println(a);

    }

}
