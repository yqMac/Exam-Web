package com.yqmac.exam.service;

import com.yqmac.exam.vo.TExam;
import com.yqmac.exam.vo.TPaperQues;
import com.yqmac.exam.vo.TPaperStrategy;

import java.util.List;

/**
 * Created by yqmac on 2016/4/25 0025.
 */

public interface IExamService {
    public TExam add(TExam exam, List<TPaperStrategy> strategies);


    public void update(TExam exam);

    public void update(TExam exam, List<TPaperStrategy> strategies);


    public void delete(int examId);

    public TExam load(int examId);

    public List<TExam> list();

    public List<TExam>listByUserId(int userId);

    public int getExamUserCount(int examId);

    public List<List<String>> listStrateData(int examId);


    public List<TPaperQues> listques(int examId);

}
