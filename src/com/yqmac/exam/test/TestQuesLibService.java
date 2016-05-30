package com.yqmac.exam.test;

import com.yqmac.exam.service.IQuesLibService;
import com.yqmac.exam.vo.TQuesLib;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/21 0021.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestQuesLibService {

    @Resource(name = "quesLibService")
    private IQuesLibService quesLibService;

    @Test
    public void testList() {
        List<TQuesLib> libs = quesLibService.list();
        System.out.println(libs.size());
    }
}
