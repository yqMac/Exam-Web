package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IUserDao;
import com.yqmac.exam.dao.IClassDao;
import com.yqmac.exam.dao.IRoleDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IUserService;
import com.yqmac.exam.util.Pager;
import com.yqmac.exam.vo.TUser;
import com.yqmac.exam.vo.TClass;
import com.yqmac.exam.vo.TRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yqmac on 2016/4/18 0018.
 */

@Service("userService")
public class UserService extends BaseService<TUser> implements IUserService {

    private IUserDao userDao;
    private IClassDao classDao;
    private IRoleDao roleDao;


    @Resource(name = "userDao")
    public void setBaseDao(IUserDao userDao){
        super.setBaseDao(userDao);
    }


    @Resource
    public void setRoleDao(IRoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Resource
    public void setClassDao(IClassDao classDao) {
        this.classDao = classDao;
    }

    @Resource
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public TUser login(String username, String password) {
        System.out.println(username + "-" + password);
        TUser c = userDao.login(username, password);
        return c;
    }

    @Override
    public TUser add(TUser user, int classId, int roleId) {
        TUser c = loadByName(user.getUsername());
        if (c != null) throw new ExamException("用户名已存在");

        TClass o = classDao.load(classId);
        if (o == null) throw new ExamException("要添加的员工的机构不存在");

        TRole role = roleDao.load(roleId);
        if (role == null) {
            throw new ExamException("要添加的员工的角色不存在");
        }

        user.setTClass(o);
        user.setTRole(role);
        return userDao.add(user);
    }


    @Override
    public void delete(int id) {
        userDao.delete(id);
    }

    @Override
    public void update(TUser user, int classId, int roleId) {
        TClass o = classDao.load(classId);
        if (o == null) throw new ExamException("要修改的员工的机构不存在");
        TRole r = roleDao.load(roleId);
        if (r == null) {
            throw new ExamException("要修改的员工的角色不存在");
        }
        user.setTRole(r);
        user.setTClass(o);
        userDao.update(user);
    }

    @Override
    public TUser load(int userId) {
        return userDao.load(userId);
    }

    @Override
    public TUser loadByName(String username) {
        List<TUser> list = userDao.list("From TUser c where c.username = ?", username);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<TUser> list() {
        List<TUser> list = userDao.list("From TUser");
        return list;
    }

    @Override
    public List<TUser> listByOragan(int classId) {
        List<TUser> list = userDao.list("From TUser c where c.TClass.id = ?", classId);
        return list;
    }

    @Override
    public List<TUser> listFuzzy(String userName) {
        return userDao.list("From TUser c where c.username like ?", "%" + userName + "%");
    }

    @Override
    public Pager<TUser> find() {
        return userDao.find("From TUser");
    }

    @Override
    public Pager<TUser> find(int classId, String fuzz) {
        String hql = "from TUser tc where tc.id =tc.id ";
        List<Object> objs = new ArrayList<>();
        if (classId != 0) {
            hql += " and  tc.TClass.id = ? ";
            objs.add(classId);
        }
        if (fuzz != null && !"".equals(fuzz.trim())) {
            hql += " and tc.username like ? ";
            objs.add("%"+fuzz+"%");
        }
        return userDao.find(hql,objs.toArray());
    }

    @Override
    public Pager<TUser> findByClass(int classId) {
        return userDao.find("From TUser c where c.TClass.id = ?", classId);
    }

    @Override
    public Pager<TUser> findFuzzy(String username) {
        return userDao.find("From TUser c where c.username like ?", "%" + username + "%");
    }

    @Override
    public int getUserCount() {
        Long  count = (Long) userDao.queryObject("select count(*) from TUser");
        return count.intValue();
    }
}
