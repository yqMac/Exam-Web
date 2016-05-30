package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IRightDao;
import com.yqmac.exam.dao.IRoleDao;
import com.yqmac.exam.dao.IRoleRightRelationDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IRoleRightRelationService;
import com.yqmac.exam.vo.TRight;
import com.yqmac.exam.vo.TRole;
import com.yqmac.exam.vo.TRolerightrelation;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Service
public class RoleRightRelationService implements IRoleRightRelationService {

    private IRoleRightRelationDao roleRightRelationDao;
    private IRoleDao roleDao;
    private IRightDao rightDao;

    @Resource
    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Resource
    public void setRightDao(IRightDao rightDao) {
        this.rightDao = rightDao;
    }

    @Resource
    public void setRoleRightRelationDao(IRoleRightRelationDao roleRightRelationDao) {
        this.roleRightRelationDao = roleRightRelationDao;
    }

    @Override
    public List<TRolerightrelation> listByRoleId(int roleId) {
        return roleRightRelationDao.list("From TRolerightrelation as trr where trr.TRole.id = ?", roleId);
    }

    @Override
    public List<TRolerightrelation> listByRightId(int rightId) {
        return roleRightRelationDao.list("From TRolerightrelation as trr where trr.TRight.id = ?", rightId);
    }

    @Override
    public TRolerightrelation loadByRR(int roleId, int rightId) {
        List<TRolerightrelation> lists = roleRightRelationDao.list("From TRolerightrelation as trr where trr.TRight.id = ? and trr.TRole.id =?", new Object[]{rightId, roleId});
        if (lists != null && lists.size() > 0)
            return lists.get(0);
        else
            return null;
    }

    @Override
    public void addRight2Role(int roleId, int rightId) {
        TRole trole = roleDao.load(roleId);
        if (trole == null) {
            throw new ExamException("要添加权限的用户不存在");
        }
        TRight tright = rightDao.load(rightId);
        if (tright == null) {
            throw new ExamException("要添加的权限不存在");
        }
        TRolerightrelation trrr = new TRolerightrelation();
        trrr.setTRight(tright);
        trrr.setTRole(trole);

        roleRightRelationDao.add(trrr);
    }

    @Override
    public void deleteRight4Role(int roleId, int rightId) {
        TRolerightrelation trrr = loadByRR(roleId, rightId);
        if (trrr != null) {
            roleRightRelationDao.delete(trrr.getId());
        }
    }

    @Override
    public int getRoleRightCount(int roleId) {
        return (Integer) roleRightRelationDao.queryObject("Select count(rr.id) From TRoleRightRelation rr where rr.TRole.id = ?", roleId);
    }
}
