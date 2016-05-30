package com.yqmac.exam.base;


import com.yqmac.exam.util.Pager;

import java.util.List;
import java.util.Map;

/**
 * 公共的DAO处理对象，这个对象中包含了Hibernate的所有基本操作和对SQL的操作
 *
 * @param <T>
 * @author Administrator
 */
public interface IBaseDao<T> {
    /**
     * 添加对象
     *
     * @param t
     * @return
     */
    public T add(T t);

    public void insertBySql(String sql,Object[]args);


    public Object queryBySql(String sql, Object[] args);

    public int  updateBySql(String sql ,Object[] args );
    /**
     * 更新对象
     *
     * @param t
     */
    public void update(T t);

    /**
     * 根据id删除对象
     *
     * @param id
     */
    public void delete(int id);

    /**
     * 根据id加载对象
     *
     * @param id
     * @return
     */
    public T load(int id);

    public List<T> list(String hql, Object[] args);

    public List<T> list(String hql, Object arg);


    public List<T> list(String hql);


    public List<T> list(String hql, Object[] args, Map<String, Object> alias);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#list(java.lang.String, java.util.Map)
     */
    public List<T> listByAlias(String hql, Map<String, Object> alias);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.lang.Object[])
     */
    public Pager<T> find(String hql, Object[] args);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.lang.Object)
     */
    public Pager<T> find(String hql, Object arg);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String)
     */
    public Pager<T> find(String hql);


    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.lang.Object[], java.util.Map)
     */
    public Pager<T> find(String hql, Object[] args, Map<String, Object> alias);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#find(java.lang.String, java.util.Map)
     */
    public Pager<T> findByAlias(String hql, Map<String, Object> alias);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#queryObject(java.lang.String, java.lang.Object[])
     */
    public Object queryObject(String hql, Object[] args);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#queryObject(java.lang.String, java.lang.Object)
     */
    public Object queryObject(String hql, Object arg);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#queryObject(java.lang.String)
     */
    public Object queryObject(String hql);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#updateByHql(java.lang.String, java.lang.Object[])
     */
    public void updateByHql(String hql, Object[] args);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#updateByHql(java.lang.String, java.lang.Object)
     */
    public void updateByHql(String hql, Object arg);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#updateByHql(java.lang.String)
     */
    public void updateByHql(String hql);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#listBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
     */
    public <N extends Object> List<N> listBySql(String sql, Object[] args, Class<?> clz,
                                                boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#listBySql(java.lang.String, java.lang.Object, java.lang.Class, boolean)
     */
    public <N extends Object> List<N> listBySql(String sql, Object arg, Class<?> clz,
                                                boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#listBySql(java.lang.String, java.lang.Class, boolean)
     */
    public <N extends Object> List<N> listBySql(String sql, Class<?> clz, boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#listBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
     */
    public <N extends Object> List<N> listBySql(String sql, Object[] args,
                                                Map<String, Object> alias, Class<?> clz, boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#listBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
     */
    public <N extends Object> List<N> listByAliasSql(String sql, Map<String, Object> alias,
                                                     Class<?> clz, boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#findBySql(java.lang.String, java.lang.Object[], java.lang.Class, boolean)
     */
    public <N extends Object> Pager<N> findBySql(String sql, Object[] args, Class<?> clz,
                                                 boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#findBySql(java.lang.String, java.lang.Object, java.lang.Class, boolean)
     */
    public <N extends Object> Pager<N> findBySql(String sql, Object arg, Class<?> clz,
                                                 boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#findBySql(java.lang.String, java.lang.Class, boolean)
     */
    public <N extends Object> Pager<N> findBySql(String sql, Class<?> clz, boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#findBySql(java.lang.String, java.lang.Object[], java.util.Map, java.lang.Class, boolean)
     */
    public <N extends Object> Pager<N> findBySql(String sql, Object[] args,
                                                 Map<String, Object> alias, Class<?> clz, boolean hasEntity);

    /* (non-Javadoc)
     * @see org.konghao.baisc.dao.IBaseDao#findBySql(java.lang.String, java.util.Map, java.lang.Class, boolean)
     */
    public <N extends Object> Pager<N> findByAliasSql(String sql, Map<String, Object> alias,
                                                      Class<?> clz, boolean hasEntity);

    public Object queryObject(String hql, Object[] args,
                              Map<String, Object> alias);

    public Object queryObjectByAlias(String hql, Map<String, Object> alias);


    public List<Object[]> listObjectsBySql(String sql, Object[] objs);
}

