package com.yqmac.exam.dao.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IClassDao;
import com.yqmac.exam.vo.TClass;
import org.springframework.stereotype.Service;

/**
 * Created by yqmac on 2016/4/18 0018.
 */
@Service("classDao")
public class ClassDao extends BaseDao<TClass> implements IClassDao {

}
