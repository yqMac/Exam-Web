package com.yqmac.exam.test;

import com.yqmac.exam.service.IRoleRightRelationService;
import com.yqmac.exam.vo.TRolerightrelation;
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
public class TestTRRService {

    @Resource(name = "roleRightRelationService")
    private IRoleRightRelationService roleRightRelationService;


    @Test
    public void testListByRoleId(){
        List<TRolerightrelation> lists = roleRightRelationService.listByRoleId(1);
        for (TRolerightrelation list : lists) {
            System.out.println(list);
        }
    }


}
