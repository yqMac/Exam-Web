package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IQuesTypeDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IQuesTypeService;
import com.yqmac.exam.vo.TQuesType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/21 0021.
 */
@Service("quesTypeService")
public class QuesTypeService implements IQuesTypeService {

    private IQuesTypeDao quesTypeDao;

    @Resource
    public void setQuesTypeDao(IQuesTypeDao quesTypeDao) {
        this.quesTypeDao = quesTypeDao;
    }

    @Override
    public TQuesType add(TQuesType quesType) {
        return quesTypeDao.add(quesType);
    }

    @Override
    public void delete(int qTypeId) {

        if (load(qTypeId).getTQueses().size() > 0) {
            throw new ExamException("该题型下还有题目,不能直接删除");
        }
        quesTypeDao.delete(qTypeId);

    }

    @Override
    public void update(TQuesType quesType) {
        quesTypeDao.update(quesType);

    }

    @Override
    public TQuesType load(int typeId) {

        return quesTypeDao.load(typeId);

    }

    @Override
    public List<TQuesType> list() {

        return quesTypeDao.list("From TQuesType");
    }
}
