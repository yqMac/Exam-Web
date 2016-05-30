package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IPaperQuesDao;
import com.yqmac.exam.dao.IPaperStrategyDao;
import com.yqmac.exam.dao.IQuesTypeDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IExamService;
import com.yqmac.exam.service.IPaperStrategyServcie;
import com.yqmac.exam.service.IQuesService;
import com.yqmac.exam.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yqmac on 2016/4/28 0028.
 */
@Service("paperStrategyService")
public class PaperStrategyService implements IPaperStrategyServcie {

    private IPaperStrategyDao paperStrategyDao;
    private IExamService examService;
    private IQuesTypeDao quesTypeDao;
    private IPaperQuesDao paperQuesDao;
    private IQuesService quesService;


    @Resource
    public void setQuesService(IQuesService quesService) {
        this.quesService = quesService;
    }

    @Resource
    public void setPaperQuesDao(IPaperQuesDao paperQuesDao) {
        this.paperQuesDao = paperQuesDao;
    }

    @Resource
    public void setQuesTypeDao(IQuesTypeDao quesTypeDao) {
        this.quesTypeDao = quesTypeDao;
    }

    @Resource
    public void setPaperStrategyDao(IPaperStrategyDao paperStrategyDao) {
        this.paperStrategyDao = paperStrategyDao;
    }

    @Resource
    public void setExamService(IExamService examService) {
        this.examService = examService;
    }

    @Override
    public TPaperStrategy add(TPaperStrategy strategy, int examId) {
        TExam exam = examService.load(examId);
        if (exam == null) {
            throw new ExamException("考试不存在,策略无法正常添加");
        }
        return add(strategy, exam);
    }

    private void addPaperQues(TExam exam, int quesId) {
        TQues ques = quesService.load(quesId);
        TPaperQues tq = new TPaperQues();
        tq.setExamId(exam.getId());
        tq.setBankId(ques.getTQuesBank().getId());
        tq.setLibId(ques.getTQuesLib().getId());
        tq.setPointId(ques.getTQuesPoint().getId());
        tq.setTypeId(ques.getTQuesType().getId());
        tq.setQuesId(ques.getId());
        tq.setQuesContent(ques.getQuesContent());
        tq.setAnswer(ques.getAnswer());
        tq.setCreateTime(ques.getCreateTime());
        tq.setDiffDegr(ques.getDiffDegr()==null?0:ques.getDiffDegr());
        tq.setOptions(ques.getOptions());
        tq.setOptionNum(ques.getOptionNum());
        tq.setScore(ques.getScore());
        tq.setId(null);
        paperQuesDao.add(tq);
    }

    private TPaperStrategy add(TPaperStrategy strategy, TExam exam) {
        strategy.setTExam(exam);
        List<Integer> lis = null;
        int bankId = strategy.getTQuesBank().getId();
        int libId = strategy.getTQuesLib().getId();
        int pointId = strategy.getTQuesPoint().getId();
        int typeId = strategy.getTQuesType().getId();
        int count = strategy.getQuesCount();

        if (count == 0) return strategy;

        if (pointId != 0) {
            lis = quesService.getIdsByPointType(pointId, typeId, count);
        } else if (libId != 0) {
            lis = quesService.getIdsByLibType(libId, typeId, count);
        } else {
            lis = quesService.getIdsByBankType(bankId, typeId, count);
        }

        for (Integer li : lis) {
            addPaperQues(exam, li);
        }
        return paperStrategyDao.add(strategy);
    }

    @Override
    public void add(int examId, List<TPaperStrategy> strategies) {
        TExam exam = examService.load(examId);
        if (exam == null) {
            throw new ExamException("考试不存在,策略无法正常添加");
        }
        for (TPaperStrategy strategy : strategies) {
            add(strategy, exam);
        }
    }

    @Override
    public void update(TPaperStrategy strategy, int examId) {
        TExam exam = examService.load(examId);
        if (exam == null) {
            throw new ExamException("考试不存在,策略无法正常更新");
        }
        strategy.setTExam(exam);
        paperStrategyDao.update(strategy);
    }

    @Override
    public void delete(int strategyId) {
        paperStrategyDao.delete(strategyId);
    }

    @Override
    public void deleteByExamId(int examId) {
        paperStrategyDao.updateByHql("Delete from TPaperStrategy ps where ps.TExam.id = ?", examId);
    }

    @Override
    public TPaperStrategy loadById(int strategyId) {
        return paperStrategyDao.load(strategyId);
    }

    @Override
    public List<TPaperStrategy> listByExamId(int examId) {
        return paperStrategyDao.list("From TPaperStrategy ps where ps.TExam.id = ?", examId);
    }

    @Override
    public List<TPaperStrategy> listAll() {
        return paperStrategyDao.list("From TPaperStrategy");
    }


    @Override
    public List<TQuesType> getTypesByExamId(int examId) {

        List<TQuesType> types =
                quesTypeDao.list("select distinct TQuesType from TPaperStrategy ps where  ps.TExam.id = ?", examId);
        return types;
    }

    @Override
    public List<Object[]> getStrategyDetailByExamId(int examId, int[] typeIds) {

        String str = "select t.bank_id,t.lib_id,t.point_id ";

        List<Object> objs = new ArrayList<>();
        for (int type : typeIds) {
            str += ", sum(case when t.type_id = ? then t.quesCount end) ";
            objs.add(type);
        }
        str += " from t_paper_strategy t  where exam_id= ? group by t.bank_id,t.lib_id,t.point_id";
        objs.add(examId);
        List<Object[]> sss = paperStrategyDao.listObjectsBySql(str, objs.toArray());
        return sss;
    }

}
