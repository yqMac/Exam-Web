package com.yqmac.exam.dao;

import com.yqmac.exam.base.IBaseDao;
import com.yqmac.exam.vo.TUser;

/**
 * Created by yqmac on 2016/4/18 0018.
 */
public interface IUserDao  extends IBaseDao<TUser>{

    public TUser login(String username, String password);
}
