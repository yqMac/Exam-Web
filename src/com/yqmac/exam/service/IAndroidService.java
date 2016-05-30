package com.yqmac.exam.service;

import com.yqmac.exam.Beans.Ques;
import com.yqmac.exam.vo.TQuesType;

import java.util.List;

/**
 * Created by yqmac on 2016/5/25 0025.
 */
public interface IAndroidService {

    public List<TQuesType> getTypesByExamId(int examId);


    public boolean subAns(int examId, int userId, List<Ques> queses);


}
