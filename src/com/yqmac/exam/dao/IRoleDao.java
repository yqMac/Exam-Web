package com.yqmac.exam.dao;

import com.yqmac.exam.base.IBaseDao;
import com.yqmac.exam.vo.TRole;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
public interface IRoleDao extends IBaseDao<TRole> {

    public TRole loadByName(String roleName);
}
