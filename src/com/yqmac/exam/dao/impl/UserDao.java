package com.yqmac.exam.dao.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IUserDao;
import com.yqmac.exam.vo.TUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yqmac on 2016/4/18 0018.
 */

@Repository("userDao")
public class UserDao extends BaseDao<TUser> implements IUserDao{

    @Override
    public TUser login(String username, String password) {
        List<TUser> list = this.list("From TUser c where c.username = ? and c.password = ? " ,new Object[]{username,password});

        if(list!=null && list.size()>0)
            return list.get(0);
        return null;
    }
}
