package com.yqmac.exam.service;

import com.yqmac.exam.vo.TUserGrade;
import com.yqmac.exam.vo.TExam;

import java.util.List;

/**
 * Created by yqmac on 2016/5/14 0014.
 */
public interface IUserGradeService extends IBaseService<TUserGrade> {

    public List<TExam> listExam();


    public List<TUserGrade>listByExamId(int examId);

    public List<TUserGrade> listByUserId(int userId);

    public TUserGrade loadByUE(int userId, int ExamId);
}
