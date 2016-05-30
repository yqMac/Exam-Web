package com.yqmac.exam.service;

import com.yqmac.exam.vo.TQuesPoint;

import java.util.List;

/**
 * Created by yqmac on 2016/4/25 0025.
 */
public interface IQuesPointService {
    public TQuesPoint add(TQuesPoint point, int libId);

    public void update(TQuesPoint point, int libId);

    public TQuesPoint load(int pointId);

    public void delete(int pointId);

    public List<TQuesPoint> list();

    public List<TQuesPoint> listByLibId(int libId);

    public List<TQuesPoint> listByBankId(int bankId);

    public String ajaxGetPointOptionByLibId(int libId);
}
