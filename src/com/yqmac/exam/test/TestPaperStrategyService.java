package com.yqmac.exam.test;

import com.yqmac.exam.service.IPaperStrategyServcie;
import com.yqmac.exam.vo.TQuesType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/28 0028.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestPaperStrategyService {

    @Resource(name = "paperStrategyService")
    private IPaperStrategyServcie paperStrategyServcie;



    @Test
    public void testtype(){
        List<TQuesType> types = paperStrategyServcie.getTypesByExamId(1);
        for (TQuesType type : types) {
            System.out.println(type.getTypeName());
        }
    }

    @Test
    public void testaaa(){

        int [] typeIds = new int[]{1,3};
        List<Object[]> sss = paperStrategyServcie.getStrategyDetailByExamId(4, typeIds);

        for (Object[] ss : sss) {
            for (Object s : ss) {
                System.out.print(s+"  ");
            }
            System.out.println("");
        }
    }

}
