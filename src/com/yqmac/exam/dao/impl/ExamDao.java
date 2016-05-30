package com.yqmac.exam.dao.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IExamDao;
import com.yqmac.exam.vo.TExam;
import org.springframework.stereotype.Repository;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@Repository("examDao")
public class ExamDao extends BaseDao<TExam> implements IExamDao {
}
