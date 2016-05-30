package com.yqmac.exam.dao.impl;

import com.yqmac.exam.base.BaseDao;
import com.yqmac.exam.dao.IPaperQuesDao;
import com.yqmac.exam.vo.TPaperQues;
import org.springframework.stereotype.Repository;

/**
 * Created by yqmac on 2016/5/5 0005.
 */
@Repository("paperQuesDao")
public class PaperQuesDao extends BaseDao<TPaperQues> implements IPaperQuesDao {
}
