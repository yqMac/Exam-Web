package com.yqmac.exam.service;

import com.yqmac.exam.vo.TRolerightrelation;

import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
public interface IRoleRightRelationService {

    public List<TRolerightrelation> listByRoleId(int roleId);
    public List<TRolerightrelation> listByRightId(int rightId);
    public TRolerightrelation loadByRR(int roleId, int rightId);
    public void addRight2Role(int roleId, int rightId);
    public void deleteRight4Role(int roleId, int rightId);
    public int getRoleRightCount(int roleId);
}
