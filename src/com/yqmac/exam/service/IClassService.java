package com.yqmac.exam.service;

import com.yqmac.exam.util.Pager;
import com.yqmac.exam.vo.TClass;

import java.util.List;

/**
 * Created by yqmac on 2016/4/18 0018.
 */
public interface IClassService {
    public TClass add(TClass tClass);
    public void delete(int id);
    public void  update(TClass tClass);

    public TClass load(int classId);
    public TClass loadByName(String className);

    public List<TClass> list();
    public List<TClass> listFuzzy(String className);

    public Pager<TClass> find();
    public Pager<TClass> findFuzzy(String className);
}
