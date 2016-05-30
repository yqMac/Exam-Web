package com.yqmac.exam.test;

import com.yqmac.exam.dao.IRightDao;
import com.yqmac.exam.service.IRightService;
import com.yqmac.exam.vo.TRight;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestRightDao {

    @Resource(name = "rightDao")
    private IRightDao rightDao;

    @Resource(name = "rightService")
    private IRightService rightService;

    @Test
    public void testList(){
        List<TRight> rights = rightService.list();
        System.out.println(rights.size());
        for (TRight right : rights) {
            System.out.println(right);
        }
    }
}
