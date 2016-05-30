package com.yqmac.exam.test;

import com.yqmac.exam.service.IPaperUserService;
import com.yqmac.exam.vo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/5/5 0005.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestPaperUser {
    @Resource(name = "paperUserService")
    private IPaperUserService paperUserService;

    @Test
    public void test01(){
        List<TUser> users = paperUserService.listUserByExamId(49);

        for (TUser user : users) {

            System.out.println(user.getNickname());
        }
    }

    @Test
    public void test02(){
        paperUserService.addClass(49,1);
        test01();

    }

}
