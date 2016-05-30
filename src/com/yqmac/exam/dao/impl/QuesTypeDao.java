package com.yqmac.exam.dao.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IQuesTypeDao;
import com.yqmac.exam.vo.TQuesType;
import org.springframework.stereotype.Repository;

/**
 * Created by yqmac on 2016/4/21 0021.
 */
@Repository("quesTypeDao")
public class QuesTypeDao extends BaseDao<TQuesType> implements IQuesTypeDao {

}
