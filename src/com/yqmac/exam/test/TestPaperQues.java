package com.yqmac.exam.test;

import com.yqmac.exam.dao.IPaperQuesDao;
import com.yqmac.exam.service.IPaperQuesService;
import com.yqmac.exam.vo.TPaperQues;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/5/12 0012.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestPaperQues {
    @Resource(name = "paperQuesService")
    private IPaperQuesService paperQuesService;

    @Resource(name = "paperQuesDao")
    private IPaperQuesDao paperQuesDao;
    @Test
    public void test01(){
        List<TPaperQues> queses = paperQuesService.list();

        for (TPaperQues quese : queses) {
            System.out.println(quese.getQuesContent());
        }
    }

    @Test
    public  void test02(){
        //System.out.println(paperQuesDao.get(1).getQuesContent());

        System.out.println(paperQuesService.load(57).getQuesContent());

    }
}
