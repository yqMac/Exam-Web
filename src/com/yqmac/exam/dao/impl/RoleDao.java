package com.yqmac.exam.dao.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IRoleDao;
import com.yqmac.exam.vo.TRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@Repository("roleDao")
public class RoleDao extends BaseDao<TRole> implements IRoleDao {

    @Override
    public TRole loadByName(String roleName) {
        List<TRole> list = this.list("From TRole r where r.roleName = ?" ,new Object[]{roleName});
        if(list!=null && list.size()>0)
            return list.get(0);
        return null;
    }
}
