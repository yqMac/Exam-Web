package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IQuesDao;
import com.yqmac.exam.dao.IQuesLibDao;
import com.yqmac.exam.dao.IQuesPointDao;
import com.yqmac.exam.dao.IQuesTypeDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IQuesService;
import com.yqmac.exam.util.Pager;
import com.yqmac.exam.vo.TQues;
import com.yqmac.exam.vo.TQuesPoint;
import com.yqmac.exam.vo.TQuesType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Service("quesService")
public class QuesService implements IQuesService {
    private IQuesDao quesDao;
    private IQuesLibDao quesLibDao;
    private IQuesTypeDao quesTypeDao;
    private IQuesPointDao quesPointDao;

    @Resource
    public void setQuesPointDao(IQuesPointDao quesPointDao) {
        this.quesPointDao = quesPointDao;
    }

    @Resource
    public void setQuesTypeDao(IQuesTypeDao quesTypeDao) {
        this.quesTypeDao = quesTypeDao;
    }

    @Resource
    public void setQuesDao(IQuesDao quesDao) {
        this.quesDao = quesDao;
    }

    @Resource
    public void setQuesLibDao(IQuesLibDao quesLibDao) {
        this.quesLibDao = quesLibDao;
    }

    @Override
    public TQues add(TQues ques,int pointId, int typeId) {
        TQuesPoint point = quesPointDao.load(pointId);
        if (point == null) {
            throw new ExamException("要添加的问题的知识点不存在");
        }
        TQuesType qt = quesTypeDao.load(typeId);
        if (qt == null) {
            throw new ExamException("要添加的问题的类型不存在");
        }

        ques.setTQuesBank(point.getTQuesLib().getTQuesBank());
        ques.setTQuesLib(point.getTQuesLib());
        ques.setTQuesPoint(point);
        ques.setTQuesType(qt);
        ques.setCreateTime(new Timestamp(new Date().getTime()));
        return quesDao.add(ques);
    }

    @Override
    public void delete(int quesId) {
        quesDao.delete(quesId);
    }

    @Override
    public void update(TQues ques, int pointId, int typeId) {
        TQuesPoint  point  = quesPointDao.load(pointId);
        if (point == null) {
            throw new ExamException("要修改的问题的知识点不存在");
        }
        TQuesType qt = quesTypeDao.load(typeId);
        if (qt == null) {
            throw new ExamException("要修改的问题的类型不存在");
        }
        ques.setTQuesBank(point.getTQuesLib().getTQuesBank());
        ques.setTQuesLib(point.getTQuesLib());
        ques.setTQuesPoint(point);
        ques.setTQuesType(qt);
        quesDao.update(ques);
    }

    @Override
    public TQues load(int quesId) {
        return quesDao.load(quesId);
    }

    @Override
    public List<TQues> listByExamId(int examId) {
        return quesDao.list("select tq.TQues From TPaperQues  as tq where tq.TExam.id = ? ", examId);
    }

    @Override
    public List<TQues> listByTypeId(int typeId) {
        return quesDao.list("From TQues as tq where tq.TQuesType.id = ?", typeId);
    }

    @Override
    public List<TQues> listByLibTypeId(int libId, int typeId) {
        return quesDao.list("From TQues as tq where tq.TQuesLib.id = ? and tq.TQuesType.id = ? ", new Object[]{libId, typeId});
    }

    @Override
    public List<TQues> listByBankTypeId(int bankId, int typeId) {
        return quesDao.list("From TQues as tq where tq.quesBank.id = ? and tq.TQuesType.id = ? ", new Object[]{bankId, typeId});
    }

   private String InitTypeFuzz(String hql,List<Object> objs ,int typeId,String fuzz){
       if(typeId!=0){
           hql+= " and tq.TQuesType.id = ? ";
           objs.add(typeId);
           //return quesDao.find("From TQues as tq where tq.TQuesBank.id = ? ", new Object[]{bankId});
       }
       if(fuzz!=null && !"".equals(fuzz.trim())){
           hql+= "  and tq.quesContent  like  ? ";
           objs.add("%"+fuzz+"%");
       }
       return  hql;
   }

    @Override
    public Pager<TQues> find(int bankId, int libId, int pointId, int typeId, String fuzz) {
        if(pointId!=0){
            return findByPointId(pointId, typeId, fuzz);
        }else if(libId!=0){
            return findByLibId(libId, typeId, fuzz);
        }else if (bankId!=0){
            return findByBankId(bankId, typeId, fuzz);
        }else if(typeId!=0){
            return findByTypeId(typeId, fuzz);
        }else {
            return findAll(fuzz);
        }
    }

    @Override
    public Pager<TQues> findByBankId(int bankId, int typeId,String fuzz) {
        List<Object> objs = new ArrayList<>();
        String hql = "From TQues as tq where tq.TQuesBank.id = ? ";
        objs.add(bankId);

        hql = InitTypeFuzz(hql,objs,typeId,fuzz);
        return quesDao.find(hql, objs.toArray());

        //return quesDao.find("From TQues as tq where tq.TQuesBank.id = ? and tq.TQuesType.id = ? ", new Object[]{bankId, typeId});
    }

    @Override
    public Pager<TQues> findByLibId(int libId, int typeId,String fuzz) {
        List<Object> objs = new ArrayList<>();
        String hql = "From TQues as tq where tq.TQuesLib.id = ? ";
        objs.add(libId);

        hql=InitTypeFuzz(hql,objs,typeId,fuzz);
        return quesDao.find(hql, objs.toArray());

    }

    @Override
    public Pager<TQues> findByPointId(int pointId, int typeId,String fuzz) {
        List<Object> objs = new ArrayList<>();
        String hql = "From TQues as tq where tq.TQuesPoint.id = ? ";
        objs.add(pointId);

        hql = InitTypeFuzz(hql,objs,typeId,fuzz);
        return quesDao.find(hql, objs.toArray());
    }
    @Override
    public Pager<TQues> findByTypeId(int typeId, String fuzz) {
        List<Object> objs = new ArrayList<>();
        String hql = "From TQues as tq where tq.TQuesType.id = ? ";
        objs.add(typeId);

        hql =  InitTypeFuzz(hql,objs,0,fuzz);
        return quesDao.find(hql, objs.toArray());
    }
    @Override
    public List<TQues> listByBankId(int bankId) {
        return quesDao.list("from From TQues as tq where tq.TQuesBank.id = ? ",bankId);
    }

    @Override
    public List<TQues> listByLibId(int libId) {
        return quesDao.list("from From TQues as tq where tq.TQuesLib.id = ? ",libId);
    }

    @Override
    public List<TQues> listAll() {
        return quesDao.list("From TQues");
    }

    @Override
    public Pager<TQues> findAll(String fuzz) {
        if(fuzz!=null && !"".equals(fuzz.trim()))
        return quesDao.find("From TQues tq where tq.quesContent like ?",("%"+fuzz+"%"));
       else
        return quesDao.find("From TQues ");
    }

    @Override
    public int getQuesCountPointType(int pointId, int typeId) {
        Long count = (Long) quesDao.queryObject("select count(q.id) from TQues q where q.TQuesPoint.id = ? and q.TQuesType.id = ? ", new Object[]{pointId, typeId});
        return count.intValue();
    }



    @Override
    public List<Integer> getIdsByPointType(int pointId, int type_id, int count) {
        String sql = "select id ,rand() as ind from t_ques where  point_id = ? and type_id = ?  order by  ind  LIMIT ?;";
        List<Object[]> los = quesDao.listObjectsBySql(sql, new Object[]{pointId, type_id, count});


        List<Integer> lis = new ArrayList<>();
        for (Object[] lo : los) {
            lis.add((Integer) lo[0]);
        }
        return lis;
    }

    @Override
    public List<Integer> getIdsByLibType(int libId, int type_id, int count) {
        String sql = "select id ,rand() as ind from t_ques where  lib_id = ? and type_id = ?  order by  ind  LIMIT ?;";
        List<Object[]> los = quesDao.listObjectsBySql(sql, new Object[]{libId, type_id, count});


        List<Integer> lis = new ArrayList<>();
        for (Object[] lo : los) {
            lis.add((Integer) lo[0]);
        }
        return lis;
    }

    @Override
    public List<Integer> getIdsByBankType(int bankId, int type_id, int count) {
        String sql = "select id ,rand() as ind from t_ques where  bank_id = ? and type_id = ?  order by  ind  LIMIT ?;";
        List<Object[]> los = quesDao.listObjectsBySql(sql, new Object[]{bankId, type_id, count});


        List<Integer> lis = new ArrayList<>();
        for (Object[] lo : los) {
            lis.add((Integer) lo[0]);
        }
        return lis;
    }

    @Override
    public int getQuesCountLibType(int libId, int typeId) {
        Long count = (Long) quesDao.queryObject("select count(q.id) from TQues q where q.TQuesLib.id = ? and q.TQuesType.id = ? ", new Object[]{libId, typeId});
        return count.intValue();
    }

    @Override
    public int getQuesCountBankType(int bankId, int typeId) {
        Long count = (Long) quesDao.queryObject("select count(q.id) from TQues q where q.TQuesBank.id = ? and q.TQuesType.id = ? ", new Object[]{bankId, typeId});
        return count.intValue();
    }
}
