package com.yqmac.exam.service.impl;

import com.yqmac.exam.base.IBaseDao;
import com.yqmac.exam.service.IBaseService;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * Created by yqmac on 2016/5/10 0010.
 */
@Service
public class BaseService<T> implements IBaseService<T> {

    private IBaseDao<T> baseDao;

    /**
     * 用于实例化BaseDao
     *在实现类中，利用下面方法为BaseService实例化BaseDao
        @Resource(name = "classDao")
        public void  setBaseDao(BaseDao<TClass> baseDao){
            super.setBaseDao(baseDao);
        }
     * @param baseDao
     */
    public void setBaseDao(IBaseDao<T> baseDao) {
        this.baseDao = baseDao;
    }

    /**
     * 用于获取泛型的真实类型
     */
    private Class<?> clz;

    public Class<?> getClz() {
        if (clz == null) {
            //获取泛型的Class对象
            clz = ((Class<?>)
                    (((ParameterizedType) (this.getClass().getGenericSuperclass())).getActualTypeArguments()[0]));
        }
        return clz;
    }


    @Override
    public T add(T t) {
        return baseDao.add(t);
    }

    @Override
    public void update(T t) {
        baseDao.update(t);
    }

    @Override
    public void delete(int id) {
        baseDao.delete(id);
    }

    @Override
    public T load(int id) {
        return baseDao.load(id);
    }

    @Override
    public List<T> list() {
        return baseDao.list("From " + getClz().getSimpleName());
    }
}
