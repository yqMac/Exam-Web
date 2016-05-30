package com.yqmac.exam.service;

import com.yqmac.exam.vo.TPaperStrategy;
import com.yqmac.exam.vo.TQuesType;

import java.util.List;

/**
 * Created by yqmac on 2016/4/28 0028.
 */
public interface IPaperStrategyServcie {

    public TPaperStrategy add(TPaperStrategy strategy, int examId);

    public void add(int examId, List<TPaperStrategy> strategies);

    public void update(TPaperStrategy strategy, int examId);

    public void delete(int strategyId);

    public void deleteByExamId(int examId);

    public TPaperStrategy loadById(int strategyId);

    public List<TPaperStrategy> listByExamId(int examId);

    public List<TPaperStrategy> listAll();


    public List<TQuesType> getTypesByExamId(int examId);

    public List<Object[] > getStrategyDetailByExamId(int examId, int[] typeIds);


}
