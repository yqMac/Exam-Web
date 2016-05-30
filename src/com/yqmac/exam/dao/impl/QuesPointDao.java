package com.yqmac.exam.dao.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IQuesPointDao;
import com.yqmac.exam.vo.TQuesPoint;
import org.springframework.stereotype.Repository;

/**
 * Created by yqmac on 2016/4/25 0025.
 */
@Repository("quesPointDao")
public class QuesPointDao  extends BaseDao<TQuesPoint> implements IQuesPointDao{
}
