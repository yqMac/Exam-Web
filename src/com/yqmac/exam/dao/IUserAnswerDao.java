package com.yqmac.exam.dao;

import com.yqmac.exam.base.IBaseDao;
import com.yqmac.exam.vo.TUserAnswer;

/**
 * Created by yqmac on 2016/5/13 0013.
 */
public interface IUserAnswerDao extends IBaseDao<TUserAnswer> {

    public TUserAnswer loadOne(int examId, int typeId);

}
