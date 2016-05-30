package com.yqmac.exam.service;

import java.util.List;

/**
 * Created by yqmac on 2016/5/10 0010.
 */
public interface IBaseService<T> {

    public T add(T t);
    public void update(T t);
    public void delete(int id);
    public T load(int id);
    public List<T> list();
}
