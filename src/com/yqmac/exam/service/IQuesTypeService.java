package com.yqmac.exam.service;

import com.yqmac.exam.vo.TQuesType;

import java.util.List;

/**
 * Created by yqmac on 2016/4/21 0021.
 */
public interface IQuesTypeService {

    public TQuesType add(TQuesType quesType);

    public void delete(int qTypeId);

    public void update(TQuesType quesType);

    public TQuesType load(int typeId);

    public List<TQuesType> list();
}
