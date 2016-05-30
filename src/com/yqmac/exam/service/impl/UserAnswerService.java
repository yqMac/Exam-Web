package com.yqmac.exam.service.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IUserAnswerDao;
import com.yqmac.exam.dao.IExamDao;
import com.yqmac.exam.service.IUserAnswerService;
import com.yqmac.exam.vo.TUserAnswer;
import com.yqmac.exam.vo.TExam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yqmac on 2016/5/13 0013.
 */
@Service
public class UserAnswerService extends BaseService<TUserAnswer> implements IUserAnswerService {
    @Resource(name = "userAnswerDao")
    public void  setBaseDao(BaseDao<TUserAnswer> baseDao){
        super.setBaseDao(baseDao);
    }



    private IUserAnswerDao userAnswerDao;
    private IExamDao examDao;


    @Resource
    public void setUserAnswerDao(IUserAnswerDao userAnswerDao) {
        this.userAnswerDao = userAnswerDao;
    }

    @Resource
    public void setExamDao(IExamDao examDao) {
        this.examDao = examDao;
    }

    /**
     * 需要手动打分的考试
     * 1、考试已经开始，Exam
     * 2、还有学生答案需要手动打分，
     *
     * @return
     */
    @Override
    public List<TExam> listExam() {
        String hql = "select distinct tca.TExam  from TUserAnswer tca " +
                " where tca.TExam.begaintime < ? and tca.needhandle = 1";
        List<TExam> exams = examDao.list(hql, new Date());
        return exams;
    }

    @Override
    public List<TUserAnswer> listByExamId(int examId, int typeId) {
        String hql = "from TUserAnswer tca where tca.TExam.id = ? and tca.needhandle =1 ";
        if (examId == 0) {
            return userAnswerDao.list(hql, examId);
        } else {
            hql += " tca.TPaperQues.typeId = ? ";
            return userAnswerDao.list(hql, new Object[]{examId, typeId});
        }
    }

    @Override
    public List<TUserAnswer> listByExamId(int examId) {
        return listByExamId(examId, 0);
    }

    @Override
    public TUserAnswer loadOne(int examId, int typeId) {
        return userAnswerDao.loadOne(examId,typeId);
    }

    @Override
    public int hanldAnsCount(int gradeId) {
        String hql = "From TUserAnswer tua where tua.TUserGrade.id =? and tua.needhandle=1";
        List<TUserAnswer> anss = userAnswerDao.list(hql,gradeId);
        if(anss==null )
        return 0;
        else return anss.size();
    }
}
