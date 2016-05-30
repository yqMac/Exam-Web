package com.yqmac.exam.service;

import com.yqmac.exam.vo.TUser;
import com.yqmac.exam.vo.TPaperUser;

import java.util.List;

/**
 * Created by yqmac on 2016/5/5 0005.
 */
public interface IPaperUserService extends IBaseService<TPaperUser>  {

    public TPaperUser add(int examId, int userId);

    public void add(int examId, List<Integer> users);

    public void addAllUser(int examId);

    public void addClass(int examId, int ClassId);

    public void deleteClass(int examId,int ClassId);

    public int  getUserCountByExamId(int examId);

    public int getExamCountByUserId(int userId);

    public void delete(int examId,int userId);

    public void deleteByUserId(int userId);

    public void deleteByExamId(int examId);

    public List<TPaperUser> listAll();

    public List<TUser> listUserByExamId(int examId);
}
