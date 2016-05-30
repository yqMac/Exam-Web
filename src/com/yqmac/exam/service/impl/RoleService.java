package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IUserDao;
import com.yqmac.exam.dao.IRoleDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IRightService;
import com.yqmac.exam.service.IRoleRightRelationService;
import com.yqmac.exam.service.IRoleService;
import com.yqmac.exam.vo.TRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@Service("roleService")
public class RoleService implements IRoleService {

    private IRoleDao roleDao;
    private IUserDao userDao;
    private IRoleRightRelationService roleRightRelationService;
    private IRightService rightService;

    @Resource
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
    }

    @Resource
    public void setRoleRightRelationService(IRoleRightRelationService roleRightRelationService) {
        this.roleRightRelationService = roleRightRelationService;
    }

    @Resource
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Resource
    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public TRole add(TRole role) {
        TRole r = roleDao.loadByName(role.getRoleName());
        if (r != null) {
            throw new ExamException("该角色已经存在");
        }
       return  roleDao.add(role);
    }

    @Override
    public TRole add(TRole role, List<Integer> rightIds) {
        role = add(role);
        rightService.setRights4Role(role.getId(),rightIds);
        return role;
    }

    @Override
    public void delete(int roleId) {
        TRole role = loadById(roleId);
        if(role.getTUsers().size()>0){
            throw new ExamException("该角色下还有人员，不能直接删除");
        }
        rightService.deleteByRoleId(roleId);
        roleDao.delete(roleId);
    }

    @Override
    public void update(TRole role) {
        roleDao.update(role);
    }

    @Override
    public void update(TRole role, List<Integer> rightIds) {
        roleDao.update(role);
        rightService.setRights4Role(role.getId(),rightIds);
    }

    @Override
    public TRole loadById(int roleId) {
        return roleDao.load(roleId);
    }

    @Override
    public List<TRole> list() {
        return roleDao.list("From TRole");
    }


    @Override
    public TRole loadByUserId(int UserId) {
        return userDao.load(UserId).getTRole();
    }
}
