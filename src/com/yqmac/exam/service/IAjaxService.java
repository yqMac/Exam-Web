package com.yqmac.exam.service;

/**
 * Created by yqmac on 2016/4/26 0026.
 */
public interface IAjaxService {

    public String getRightCheckboxByBigIds(String bigRights);

    public String getRightCheckboxByBigIds4Update(int roleId, String bigRights);

    public String getPointOptionByLibId(int libId);


    public String getLibOptionByBankId(int bankId);

    public String getPointOptionHeaderByLibId(int libId);


    public String getLibOptionHeaderByBankId(int bankId);



    public String getTypeQuesJsonByBLPTs(int bankId, int libId, int pointId, String types);


    public String  getStrategyTypesByExamId(int examId);

    public String getQuesByExamId(int examId);


    public String getUserOptionsByClassId(int classId);


    public String delExamUsers(int examId,int classId, int userId);

    public String addExamUsers(int exmaId,int classId, int userId);

    public String  loadOne(int examId, int typeId);

    public String markOne(int answerId, float score);
}
