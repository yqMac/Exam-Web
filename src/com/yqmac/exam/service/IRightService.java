package com.yqmac.exam.service;

import com.yqmac.exam.vo.TRight;

import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
public interface IRightService {
    public TRight add(TRight right, int parentId);

    public void delete(int rightId);

    public void deleteByRoleId(int roleId);
    public void deleteHaveRightId(int rightId);

    public void update(TRight right, int parentId);

    public TRight loadById(int rightId);

    public List<TRight> list();

    public List<TRight> listByRoleId(int roleId);

    public List<TRight> listBigByRoleId(int roleId);
    public List<TRight> listBigByUserId(int userId);
    public List<TRight> listBig();
    public List<TRight> listBigAll();

    public List<TRight> listSmallByRBId(int roleId, int BigId);
    public List<TRight> listSmallByCBId(int userId, int BigId);
    public List<TRight> listSmallByBigId(int BigId);


    public String getMenuJSonString(int userId);

    public void addRight2Role(int roleId, int rightId);
    public void addRights2Role(int roleId, List<Integer> rightIds);
    public void setRights4Role(int roleId, List<Integer> rightIds);
    public void deleteRight4Role(int roleId, int rightId);
    
}
