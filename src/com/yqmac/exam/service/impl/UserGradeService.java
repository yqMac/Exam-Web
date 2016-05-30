package com.yqmac.exam.service.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IUserAnswerDao;
import com.yqmac.exam.dao.IUserGradeDao;
import com.yqmac.exam.dao.IExamDao;
import com.yqmac.exam.service.IUserGradeService;
import com.yqmac.exam.vo.TUserGrade;
import com.yqmac.exam.vo.TExam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by yqmac on 2016/5/14 0014.
 */
@Service
public class UserGradeService extends BaseService<TUserGrade> implements IUserGradeService {

    @Resource(name = "userGradeDao")
    public void  setBaseDao(BaseDao<TUserGrade> baseDao){
        super.setBaseDao(baseDao);
    }


    private IUserGradeDao userGradeDao;
    private IUserAnswerDao userAnswerDao;
    private IExamDao examDao;


    @Resource
    public void setExamDao(IExamDao examDao) {
        this.examDao = examDao;
    }

    @Resource
    public void setUserAnswerDao(IUserAnswerDao userAnswerDao) {
        this.userAnswerDao = userAnswerDao;
    }

    @Resource
    public void setUserGradeDao(IUserGradeDao userGradeDao) {
        this.userGradeDao = userGradeDao;
    }

    @Override
    public List<TUserGrade> listByUserId(int userId) {
        String  hql ="From TUserGrade tcg where tcg.TUser.id = ? ";
        List<TUserGrade> grades = userGradeDao.list(hql, userId);

        return grades;
    }

    @Override
    public List<TExam> listExam() {
        //"select distinct tca.TExam  from TUserAnswer tca " +
         //       " where tca.TExam.begaintime < ? and tca.needhandle = 1";
        String hql="select distinct tcg.TExam from TUserGrade tcg " +
                "where tcg.TExam.endtime < ? ";
        List<TExam> exams = examDao.list(hql, new Date());

        return exams ;
    }

    @Override
    public List<TUserGrade> listByExamId(int examId) {
        String  hql ="From TUserGrade tcg where tcg.TExam.id = ? ";
        List<TUserGrade> grades = userGradeDao.list(hql, examId);

        return grades;
    }

    @Override
    public TUserGrade loadByUE(int userId, int examId) {
        String  hql ="From TUserGrade tcg where tcg.TExam.id = ? and tcg.TUser.id = ?";
        List<TUserGrade> grades = userGradeDao.list(hql, new Object[]{examId, userId});

        if (grades != null && grades.size() > 0) {
            return grades.get(0);
        }
        else
            return null;
    }
}
