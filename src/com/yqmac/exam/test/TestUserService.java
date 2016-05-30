package com.yqmac.exam.test;

import com.yqmac.exam.service.IUserService;
import com.yqmac.exam.vo.TUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/18 0018.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestUserService {

    @Resource(name = "userService")
    private IUserService userService;

    @Test
    public void testLogin(){
        TUser c = userService.login("admin","admin");
        System.out.println(c);
    }
    @Test
    public void testAdd(){

        TUser c = new TUser();
        c.setUsername("yqmac4");
        c.setPassword("yqmac4");
        c.setNickname("雨强4");
        //c.setRoleId(1);
        //userService.add(c, 2);
    }

    @Test
    public void testList(){
        List<TUser> cs = userService.list();
        System.out.println("员工总数:"+cs.size());
        for (TUser c : cs) {
            System.out.println(c);
        }
    }

    @Test
    public void testDelete(){
        userService.delete(3);
        testList();
    }

    @Test
    public void  testUpdate(){

        TUser c = userService.load(6);
        c.setNickname("雨强1");
        c.setPassword("yqmac1");
        c.setUsername("yqmac1");
        //userService.update(c,2);

        testList();
    }

    @Test
    public void testListFuzzy(){
        List<TUser> cs = userService.listFuzzy("yqmac");
        System.out.println("员工总数:"+cs.size());
        for (TUser c : cs) {
            System.out.println(c);
        }
    }

}
