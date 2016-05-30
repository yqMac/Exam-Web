package com.yqmac.exam.service;

import com.yqmac.exam.vo.TExam;
import com.yqmac.exam.vo.TUserAnswer;

import java.util.List;

/**
 * Created by yqmac on 2016/5/13 0013.
 */
public interface IUserAnswerService extends IBaseService<TUserAnswer> {

    public List<TExam> listExam();

    public List<TUserAnswer> listByExamId(int examId,int typeId);

    public List<TUserAnswer> listByExamId(int examId);

    public TUserAnswer loadOne(int examId, int typeId);

    public int hanldAnsCount(int gradeId);


}
