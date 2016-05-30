package com.yqmac.exam.service;

import com.yqmac.exam.util.Pager;
import com.yqmac.exam.vo.TUser;

import java.util.List;

/**
 * Created by yqmac on 2016/4/18 0018.
 */
public interface IUserService extends IBaseService<TUser> {

    public TUser add(TUser user, int classId, int roleId);
    public void delete(int id);
    public void  update(TUser user, int classId, int roleId);


    public TUser load(int userId);
    public TUser loadByName(String username);

    public TUser login(String username, String password);

    public List<TUser>list();
    public List<TUser>listFuzzy(String userName);
    public List<TUser>listByOragan(int classId);

    public Pager<TUser> find(int classId,String fuzz);

    public Pager<TUser> find();
    public Pager<TUser> findByClass(int classId);
    public Pager<TUser> findFuzzy(String username);

    public int getUserCount();

}
