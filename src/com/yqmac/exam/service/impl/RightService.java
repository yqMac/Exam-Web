package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IRightDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IRightService;
import com.yqmac.exam.service.IRoleRightRelationService;
import com.yqmac.exam.service.IRoleService;
import com.yqmac.exam.vo.TRight;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@Service("rightService")
public class RightService implements IRightService {

    private IRightDao rightDao;
    private IRoleService roleService;
    private IRoleRightRelationService roleRightRelationService;

    @Resource
    public void setRoleRightRelationService(IRoleRightRelationService roleRightRelationService) {
        this.roleRightRelationService = roleRightRelationService;
    }

    @Resource
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Resource
    public void setRightDao(IRightDao rightDao) {
        this.rightDao = rightDao;
    }

    @Override
    public TRight add(TRight right ,int parentId) {

        TRight r = loadById(parentId);
        if (r == null) {
            throw new ExamException("要添加的权限的父权限不存在,不能更新");
        }
        right.setParent(r);
      return   rightDao.add(right);
    }

    @Override
    public void delete(int rightId) {
        deleteHaveRightId(rightId);
        rightDao.delete(rightId);
    }

    @Override
    public void update(TRight right,int parentId) {
        TRight r = loadById(parentId);
        if (r == null) {
            throw new ExamException("要更新的权限的父权限不存在,不能更新");
        }
        right.setParent(r);
        rightDao.update(right);
    }

    @Override
    public TRight loadById(int rightId) {
        return rightDao.load(rightId);
    }

    @Override
    public List<TRight> list() {
        List<TRight> rights = rightDao.list("From TRight");
        return rights;
    }

    @Override
    public List<TRight> listByRoleId(int roleId) {
        List<TRight> rights = rightDao.list("select r From TRolerightrelation as trr Left join trr.TRight as r  where trr.TRole.id = ?", roleId);
        return rights;
    }

    @Override
    public List<TRight> listBigByRoleId(int roleId) {
        List<TRight> rights = rightDao.list("select r From TRolerightrelation as trr Left join trr.TRight as r  where trr.TRole.id = ? and r.parent.id = -1 ", roleId);
        return rights;
    }

    @Override
    public List<TRight> listBigByUserId(int userId) {
        int roleId = roleService.loadByUserId(userId).getId();
        return listBigByRoleId(roleId);
    }

    @Override
    public List<TRight> listBig() {
        List<TRight> rights = rightDao.list("From TRight as r where  r.parent.id = -1 and r.id !=-1");
        return rights;
    }

    @Override
    public List<TRight> listBigAll() {
      List<TRight> rights = rightDao.list("From TRight as r where  r.parent.id = -1");
        return rights;
    }

    @Override
    public List<TRight> listSmallByBigId(int BigId) {
        TRight par = loadById(BigId);
        List<TRight> rights = rightDao.list("From TRight r where r.parent.id = ? and r.id != -1",BigId);
        return rights;
    }

    @Override
    public List<TRight> listSmallByRBId(int roleId, int BigId) {
        TRight par = loadById(BigId);
        List<TRight> rights = rightDao.list("select r From TRolerightrelation as trr Left join trr.TRight as r  where trr.TRole.id = ? and r.parent.id= ? and r.id != -1", new Object[]{roleId, BigId});
        //for (TRight right : rights) {
        //    right.setParentName(par.getRightName());
        //}
        return rights;
    }


    @Override
    public List<TRight> listSmallByCBId(int userId, int BigId) {
        return listSmallByRBId(roleService.loadByUserId(userId).getId(), BigId);
    }

    @Override
    public String getMenuJSonString(int userId) {
        StringBuffer sbuf = new StringBuffer();
        int bigIndex = 0, smallIndex = 0;
        List<TRight> bigRs = null, smalRs = null;
        int roleId = roleService.loadByUserId(userId).getId();

        //初始化
        sbuf.append("var _menus = {\"menus\":[");

        bigRs = listBigByRoleId(roleId);
        for (TRight bigR : bigRs) {
            smallIndex = 0;
            bigIndex++;
            //权限分组数据开始
            sbuf.append("{\"menuid\":\"" + String.valueOf(bigIndex) + "\",\"icon\":\"icon-sys1\",\"menuname\":\"" + bigR.getRightName() + "\"," +
                    "\"menus\":[");

            smalRs = listSmallByRBId(roleId, bigR.getId());
            for (TRight smalR : smalRs) {
                smallIndex++;
                sbuf.append("{\"menuid\":\"" + String.valueOf(bigIndex) + String.valueOf(smallIndex) + "\",\"menuname\":\"" + smalR.getRightName() + "\",\"icon\":\"icon-notice\",\"url\":\"" + (smalR.getRightUrl() == null ? "###" : smalR.getRightUrl()) + "\"}");
                if (smallIndex < smalRs.size()) {
                    sbuf.append(",");
                }
            }
            //权限分组数据结束
            sbuf.append("]}");
            if (bigIndex < bigRs.size()) {
                sbuf.append(",");
            }
        }
        //完成
        sbuf.append("]};");
        return sbuf.toString();
    }


    @Override
    public void deleteByRoleId(int roleId) {
        rightDao.updateByHql("delete from TRolerightrelation as trrr where trrr.TRole.id = ?", new Object[]{roleId});
    }

    @Override
    public void deleteHaveRightId(int rightId) {
        rightDao.updateByHql("delete from TRolerightrelation as trrr where trrr.TRight.id = ?", new Object[]{rightId});

    }

    @Override
    public void addRight2Role(int roleId, int rightId) {
        roleRightRelationService.addRight2Role(roleId,rightId);
    }

    @Override
    public void deleteRight4Role(int roleId, int rightId) {
        roleRightRelationService.deleteRight4Role(roleId, rightId);
    }


    @Override
    public void addRights2Role(int roleId, List<Integer> rightIds) {
        for (Integer rightId : rightIds) {
            addRight2Role(roleId,rightId);
        }
    }

    @Override
    public void setRights4Role(int roleId, List<Integer> rightIds) {
        deleteByRoleId(roleId);
        addRights2Role(roleId,rightIds);
    }
}
