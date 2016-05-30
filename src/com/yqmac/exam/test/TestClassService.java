package com.yqmac.exam.test;

import com.yqmac.exam.service.IClassService;
import com.yqmac.exam.vo.TClass;
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
public class TestClassService {

    @Resource
    private IClassService classService;


    @Test
    public void testList() {
        List<TClass> list = classService.list();
        for (TClass tClass : list) {
            System.out.println(tClass);
        }
    }

    @Test
    public void testLoad2() {

        classService.update(null);

    }


    @Test
    public void testAdd() {
        TClass o = new TClass();
        o.setClassName("销售部");

        o = classService.add(o);
        testList();
        System.out.println("----" + o.getId());
    }

    @Test
    public void testDelete() {
        classService.delete(3);
        testList();
    }

    @Test
    public void testUpdate() {
        TClass tClass = classService.load(2);
        tClass.setClassName("计算机科学与信息技术学院");
        classService.update(tClass);
        testList();
    }

    @Test
    public void testListFuzzy() {
        System.out.println(classService.listFuzzy("计算机"));
    }


}
