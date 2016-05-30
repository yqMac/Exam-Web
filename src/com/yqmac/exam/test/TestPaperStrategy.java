package com.yqmac.exam.test;

import com.yqmac.exam.dao.IQuesDao;
import com.yqmac.exam.service.IPaperStrategyServcie;
import com.yqmac.exam.vo.TPaperStrategy;
import com.yqmac.exam.vo.TQuesPoint;
import com.yqmac.exam.vo.TQuesType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by yqmac on 2016/5/5 0005.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext.xml")
public class TestPaperStrategy {

    @Resource(name = "paperStrategyService")
    private IPaperStrategyServcie paperStrategyService;


    @Resource(name = "quesDao")
    private IQuesDao quesDao;


    @Test
    public void test01(){

        TPaperStrategy ps = new TPaperStrategy();

        TQuesPoint t = new TQuesPoint();
        TQuesType ty = new TQuesType();

        t.setId(9);
        ty.setId(1);

        ps.setTQuesPoint(t);
        ps.setTQuesType(ty);
    ps.setQuesCount(2);
        paperStrategyService.add(ps, 18);
        //SELECT * FROM   users WHERE userId >=
        // (
        // ( SELECT MAX(userId) FROM users)-
        // (SELECT    MIN(userId) FROM users)
        // )
        // * RAND()
        // +
        // (SELECT MIN(userId) FROM users)
        // LIMIT 1
        //String sql ="select id ,rand() as ind from t_ques where  point_id = ? and type_id = ?  order by  ind  LIMIT ?;";
        //List<Object[]> los = quesDao.listObjectsBySql(sql, new Object[]{9, 1, 2});
        //
        //for (Object[] lo : los) {
        //    System.out.println(lo);
        //}
    }


}
