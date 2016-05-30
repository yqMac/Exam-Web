package com.yqmac.exam.test;

import com.yqmac.exam.action.UserAction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestUserAction {

    private UserAction userAction;

    @Resource(name = "userAction")
    public void setUserAction(UserAction userAction) {
        this.userAction = userAction;
    }

    @Test
    public void testLogin(){
         //userAction.login();
    }



}
