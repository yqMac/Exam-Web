package com.yqmac.exam.service;

import com.yqmac.exam.vo.TRole;

import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
public interface IRoleService {

    public TRole add(TRole role);
    public TRole add(TRole role, List<Integer> rightIds);

    public void delete(int roleId);

    public void update(TRole role);
    public void update(TRole role, List<Integer> rightIds);
    public TRole loadById(int roleId);

    public List<TRole> list();

    public TRole loadByUserId(int UserId);


}
