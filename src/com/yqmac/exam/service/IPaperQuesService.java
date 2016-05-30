package com.yqmac.exam.service;

import com.yqmac.exam.vo.TPaperQues;

import java.util.List;

/**
 * Created by yqmac on 2016/5/5 0005.
 */
public interface IPaperQuesService  extends  IBaseService<TPaperQues>{


    public void deleteByExamId(int examId);

    public List<TPaperQues> listByExamId(int examId);

    public List<TPaperQues> listByExamTypeId(int examId, int TypeId);

    public TPaperQues loadById(int id);
}
