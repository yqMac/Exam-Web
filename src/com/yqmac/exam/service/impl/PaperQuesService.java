package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IPaperQuesDao;
import com.yqmac.exam.dao.IUserAnswerDao;
import com.yqmac.exam.dao.IUserGradeDao;
import com.yqmac.exam.service.IPaperQuesService;
import com.yqmac.exam.vo.TPaperQues;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/5/5 0005.
 */
@Service
public class PaperQuesService extends BaseService<TPaperQues> implements IPaperQuesService {

    private IPaperQuesDao paperQuesDao;
    private IUserGradeDao userGradeDao;
    private IUserAnswerDao userAnswerDao;

    @Resource(name = "paperQuesDao")
    public void setBaseDao(IPaperQuesDao paperQuesDao){
        super.setBaseDao(paperQuesDao);
    }


    @Resource
    public void setUserGradeDao(IUserGradeDao userGradeDao) {
        this.userGradeDao = userGradeDao;
    }
    @Resource
    public void setUserAnswerDao(IUserAnswerDao userAnswerDao) {
        this.userAnswerDao = userAnswerDao;
    }
    @Resource
    public void setPaperQuesDao(IPaperQuesDao paperQuesDao) {
        this.paperQuesDao = paperQuesDao;
    }


    @Override
    public List<TPaperQues> listByExamId(int examId) {
        return paperQuesDao.list("From TPaperQues tpq where tpq.examId = ? ", examId);
    }

    @Override
    public void deleteByExamId(int examId) {
        userAnswerDao.updateByHql("delete from TUserAnswer ua where ua.TExam.id=?",examId);
        userGradeDao.updateByHql("delete from TUserGrade ug where ug.TExam.id=?",examId);
        paperQuesDao.updateByHql("delete from TPaperQues q where q.examId = ?", examId);
    }

    @Override
    public List<TPaperQues> listByExamTypeId(int examId, int typeId) {

        String hql = "From TPaperQues tpq where tpq.examId = ?  and tpq.typeId = ? ";

        return paperQuesDao.list(hql, new Object[]{examId, typeId});
    }

    @Override
    public TPaperQues loadById(int id) {
        return paperQuesDao.load(id);
    }

}
