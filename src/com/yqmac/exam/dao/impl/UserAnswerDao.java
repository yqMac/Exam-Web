package com.yqmac.exam.dao.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IUserAnswerDao;
import com.yqmac.exam.util.StringUtil;
import com.yqmac.exam.vo.TUserAnswer;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yqmac on 2016/5/13 0013.
 */
@Repository
public class UserAnswerDao extends BaseDao<TUserAnswer> implements IUserAnswerDao {

    /**
     * @param examId
     * @param typeId
     * @return
     */
    @Override
    public TUserAnswer loadOne(int examId, int typeId) {
        String hql =" from TUserAnswer tca where tca.needhandle = 1 " +
                " and  tca.TExam.id = ? ";
        String hqlCount = "select count(tca.id) "+hql;

        Query query=null;
        Query queryCount = null;

        if(typeId>0){
            hql+= " and tca.TPaperQues.typeId = ? ";
            query= this.getSession().createQuery(hql);
            query.setParameter(0, examId);
            query.setParameter(1, typeId);

            hqlCount += " and tca.TPaperQues.typeId = ? ";
            queryCount = this.getSession().createQuery(hqlCount);
            queryCount.setParameter(0, examId);
            queryCount.setParameter(1, typeId);


        }else {
            query= this.getSession().createQuery(hql);
            query.setParameter(0, examId);

            queryCount = this.getSession().createQuery(hqlCount);
            queryCount.setParameter(0, examId);
        }

        Long count = (Long) queryCount.uniqueResult();

        if (count == 0) {
            return null;
        }

        query.setFirstResult(StringUtil.ran.nextInt(count.intValue())).setMaxResults(1);
        List<TUserAnswer> list = (List<TUserAnswer>)query.list();
        if (list == null || list.size() == 0) {
            return null ;
        }else {
            return list.get(0);
        }
    }
}
