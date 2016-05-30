package com.yqmac.exam.service.impl;

import com.yqmac.exam.Beans.Ques;
import com.yqmac.exam.dao.IUserAnswerDao;
import com.yqmac.exam.dao.IUserGradeDao;
import com.yqmac.exam.service.*;
import com.yqmac.exam.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by yqmac on 2016/5/25 0025.
 */
@Service
public class AndroidService implements IAndroidService {


    @Resource(name = "paperStrategyService")
    private IPaperStrategyServcie paperStrategyServcie;
    private IPaperQuesService paperQuesService;
    private IUserGradeService userGradeService;
    private IUserAnswerService userAnswerService;
    private IQuesTypeService quesTypeService;
    private IUserAnswerDao userAnswerDao;
    private IUserGradeDao userGradeDao;

    @Resource
    public void setUserAnswerDao(IUserAnswerDao userAnswerDao) {
        this.userAnswerDao = userAnswerDao;
    }

    @Resource
    public void setUserGradeDao(IUserGradeDao userGradeDao) {
        this.userGradeDao = userGradeDao;
    }

    @Resource
    public void setPaperStrategyServcie(IPaperStrategyServcie paperStrategyServcie) {
        this.paperStrategyServcie = paperStrategyServcie;
    }

    @Resource
    public void setQuesTypeService(IQuesTypeService quesTypeService) {
        this.quesTypeService = quesTypeService;
    }

    @Resource
    public void setPaperQuesService(IPaperQuesService paperQuesService) {
        this.paperQuesService = paperQuesService;
    }

    @Resource
    public void setUserGradeService(IUserGradeService userGradeService) {
        this.userGradeService = userGradeService;
    }

    @Resource
    public void setUserAnswerService(IUserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @Override
    public List<TQuesType> getTypesByExamId(int examId) {
        List<TQuesType> types = paperStrategyServcie.getTypesByExamId(examId);
        return types;
    }

    @Override
    public boolean subAns(int examId, int userId, List<Ques> queses) {

        TUserAnswer userAns;
        TUserGrade grade = userGradeService.loadByUE(userId, examId);
        grade.setEndtime(new Timestamp(System.currentTimeMillis()));

        if (grade == null) {
            return false;
        }
        for (Ques quese : queses) {
            TPaperQues q = paperQuesService.load(quese.getId());

            if(q==null ) continue;

            userAns = new TUserAnswer();
            userAns.setTExam(new TExam(examId));
            userAns.setTPaperQues(q);
            userAns.setTUserGrade(grade);
            userAns.setAnswer(quese.getUserAnswer());
            userAns.setGradeAuto(0f);
            userAns.setGradeHandle(0f);

            String typeName = quesTypeService.load(q.getTypeId()).getTypeName();
            //答案正确，完全不需要判断，直接给分
            if (q.getAnswer().equals(quese.getUserAnswer())) {
                userAns.setNeedhandle(0);
                userAns.setGradeAuto(q.getScore());
            } else {
                //选择题，错了
                if (typeName.contains("选")) {
                    userAns.setNeedhandle(0);
                    userAns.setGradeAuto(0.0f);
                }
                //简答题，需要评分
                else {
                    userAns.setGradeAuto(0f);
                    userAns.setNeedhandle(1);
                }
            }
            grade.setGrade(grade.getGrade() + userAns.getGradeAuto());
            userAnswerDao.add(userAns);
        }
        userGradeDao.update(grade);
        return true;
    }
}
