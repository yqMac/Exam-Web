package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IExamDao;
import com.yqmac.exam.service.*;
import com.yqmac.exam.vo.TExam;
import com.yqmac.exam.vo.TPaperQues;
import com.yqmac.exam.vo.TPaperStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yqmac on 2016/4/26 0026.
 */
@Service("examService")
public class ExamService implements IExamService {

    private IExamDao examDao;

    private IPaperStrategyServcie paperStrategyServcie;
    private IPaperQuesService paperQuesService;
    private IPaperUserService paperUserService;
    private IUserService userService;

    @Resource
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Resource
    public void setPaperUserService(IPaperUserService paperUserService) {
        this.paperUserService = paperUserService;
    }

    @Resource
    public void setPaperQuesService(IPaperQuesService paperQuesService) {
        this.paperQuesService = paperQuesService;
    }

    @Resource
    public void setPaperStrategyServcie(IPaperStrategyServcie paperStrategyServcie) {
        this.paperStrategyServcie = paperStrategyServcie;
    }

    @Resource(name = "examDao")
    public void setExamDao(IExamDao examDao) {
        this.examDao = examDao;
    }


    @Override
    public TExam add(TExam exam, List<TPaperStrategy> strategies) {
        exam = examDao.add(exam);
        paperStrategyServcie.add(exam.getId(), strategies);
        return exam;
    }

    @Override
    public void update(TExam exam) {
        examDao.update(exam);
    }

    @Override
    public void update(TExam exam, List<TPaperStrategy> strategies) {
        paperStrategyServcie.deleteByExamId(exam.getId());
        update(exam);
        paperStrategyServcie.add(exam.getId(), strategies);
    }

    @Override
    public void delete(int examId) {
        paperStrategyServcie.deleteByExamId(examId);
        paperQuesService.deleteByExamId(examId);
        examDao.delete(examId);
    }

    @Override
    public TExam load(int examId) {
        return examDao.load(examId);
    }

    @Override
    public List<TExam> list() {
        return examDao.list("from TExam");
    }


    @Override
    public List<List<String>> listStrateData(int examId) {
        List<List<String>> datas = new ArrayList<>();
        return datas;
    }

    @Override
    public List<TExam> listByUserId(int userId) {
        List<TExam> exams = null;

        String hql = "from TExam e where e.ispartuser = 0 and e.endtime > ?";
        exams = examDao.list(hql,new Date());
        hql = "select p.TExam From TPaperUser p where p.TUser.id= ? and p.TExam.ispartuser = 1  and p.TExam.endtime > ?";
        List<TExam> es2 = examDao.list(hql, new Object[]{userId,new Date()});
        if (es2 != null) {
            if (exams == null) {
                exams = new ArrayList<>();
            }
            exams.addAll(es2);
        }
        return exams;
    }

    @Override
    public int getExamUserCount(int examId) {
        TExam exam = examDao.load(examId);
        if (exam == null) {
            return -1;
        }
        if(exam.getIspartuser()==0){
            return  userService.getUserCount();
        }
        else {
            return paperUserService.getUserCountByExamId(examId);
        }
    }

    @Override
    public List<TPaperQues> listques(int examId) {
        return paperQuesService.listByExamId(examId);
    }
}
