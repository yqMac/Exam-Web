package com.yqmac.exam.service;

import com.yqmac.exam.util.Pager;
import com.yqmac.exam.vo.TQues;

import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
public interface IQuesService {
    public TQues add(TQues ques, int pointId, int typeId);

    public void delete(int quesId);

    public void update(TQues ques, int pointId, int typeId);

    public TQues load(int quesId);

    public List<TQues> listByExamId(int examId);

    public List<TQues> listAll();
    public List<TQues> listByBankId(int bankId);
    public List<TQues> listByLibId(int libId);
    public List<TQues> listByTypeId(int typeId);

    public List<TQues> listByBankTypeId(int bankId, int typeId);
    public List<TQues> listByLibTypeId(int libId, int typeId);


    public Pager<TQues> find(int bankId,int libId,int pointId,int typeId,String fuzz);
    public Pager<TQues> findAll(String fuzz);
    public Pager<TQues> findByBankId(int bankId,int typeId,String fuzz);
    public Pager<TQues> findByLibId(int libId,int typeId,String fuzz);
    public Pager<TQues> findByPointId(int pointId,int typeId,String fuzz);
    public Pager<TQues> findByTypeId(int typeId,String fuzz);


    public int getQuesCountPointType(int pointId, int typeId);
    public int getQuesCountLibType(int libId, int typeId);
    public int getQuesCountBankType(int bankId, int typeId);
    public List<Integer> getIdsByPointType(int pointId, int type_id, int count);
    public List<Integer> getIdsByLibType(int libId, int type_id, int count);
    public List<Integer> getIdsByBankType(int bankId, int type_id, int count);

}
