package com.yqmac.exam.test;

import com.yqmac.exam.service.IRoleService;
import com.yqmac.exam.vo.TRole;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestRoleService {

    @Resource(name = "roleService")
    private IRoleService roleService;

    @Test
    public void testList(){
        List<TRole> roles = roleService.list();

        for (TRole role : roles) {
            System.out.println(role);
        }
    }

    @Test
    public void testaddright(){
        //roleService.addRight2Role(4, 1);
        testList();
    }

    @Test
    public void testDeleteR(){
        //roleService.deleteRight4Role(4, 1);
        testList();
    }

}
