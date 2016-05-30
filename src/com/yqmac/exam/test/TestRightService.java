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
 * Created by yqmac on 2016/4/19 0019.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestRightService {


    @Resource(name = "rightService")
    private IRightService rightService;

    @Resource(name = "rightDao")
    private IRightDao rightDao;

    @Test
    public void testLoad(){
        System.out.println(rightService.loadById(1));
    }

    @Test
    public void testList(){
        List<TRight> rights =rightService.list();
        for (TRight right : rights) {
            System.out.println(right);
        }
    }

    @Test
    public void testAdd(){
        TRight r = new TRight();
        //r.setParentId(-1);
        r.setRightName("测试");
        //rightService.add(r);

        testList();
    }
    @Test
    public  void testDelete(){
        rightService.delete(16);
        testList();
    }

    @Test
    public void testUpdate(){
        TRight r = rightService.loadById(17);
        r.setRightName("测试权利");
        //rightService.update(r);
        testList();
    }

    @Test
    public void testLoadByRoleId(){
        List<TRight> rs =rightService.listByRoleId(4);
        System.out.println(rs.size());
        for (TRight r : rs) {
            System.out.println(r);
        }
    }

    @Test
    public void testDeleteByRoleId(){
        rightService.deleteByRoleId(4);
        testLoadByRoleId();
    }


    @Test
    public void testList1(){
        List<TRight> rs = rightService.listByRoleId(1);
        for (TRight r : rs) {
            System.out.println(r);
        }
    }

    @Test
    public void testListBSright(){
        StringBuffer sbuf = new StringBuffer();
        String s ="";

        List<TRight> bs = rightService.listBigByUserId(1);
        for (TRight b : bs) {
           sbuf.append(s+b+"\n");
            List<TRight> ss = rightService.listSmallByCBId(1, b.getId());
            s="     ";
            for (TRight tRight : ss) {
               sbuf.append(s + tRight+"\n");
            }
            s="";
        }
        System.out.println(sbuf.toString());
    }

    @Test
    public void testGetJSon(){
        System.out.println(rightService.getMenuJSonString(1));
    }




}
