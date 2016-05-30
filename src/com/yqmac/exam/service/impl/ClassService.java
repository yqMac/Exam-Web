package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IClassDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IClassService;
import com.yqmac.exam.util.Pager;
import com.yqmac.exam.vo.TClass;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/18 0018.
 */

@Service("classService")
public class ClassService implements IClassService {

    private IClassDao classDao;

    @Resource
    public void setClassDao(IClassDao classDao) {
        this.classDao = classDao;
    }

    @Override
    public TClass add(TClass tclass) {

        return  classDao.add(tclass);
    }

    @Override
    public void delete(int id) {

        if (getCountById(id)>0) {
            throw new ExamException("该用户组尚有人,不能直接删除");
        }
        classDao.delete(id);
    }

    @Override
    public void update(TClass tclass) {
        classDao.update(tclass);
    }

    @Override
    public TClass load(int classId) {
        return classDao.load(classId);
    }

    @Override
    public List<TClass> list() {
        return classDao.list("From TClass");
    }

    private int getCountById(int classId){

        return ((Long) classDao.queryObject("select count(*) From TUser as c where c.TClass.id = ?", new Object[]{classId})).intValue();
    }


    @Override
    public TClass loadByName(String className) {

        List<TClass> ors = classDao.list("From TClass r where r.className = ?", className);
        if (ors != null && ors.size() > 0) {
            return ors.get(0);
        } else {
            return null;
        }
    }


    @Override
    public List<TClass> listFuzzy(String className) {
        List<TClass> ors = classDao.list("From TClass r where r.className like ?", "%" + className + "%");
        return ors;
    }

    @Override
    public Pager<TClass> find() {
        return classDao.find("From TClass");
    }

    @Override
    public Pager<TClass> findFuzzy(String className) {
        return classDao.find("From TClass or where or.className like ?", "%" + className + "%");
    }
}
