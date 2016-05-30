package com.yqmac.exam.service;

import com.yqmac.exam.vo.TQuesLib;

import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
public interface IQuesLibService {
    public TQuesLib add(TQuesLib lib, int bankId);

    public void delete(int libId);

    public void update(TQuesLib lib, int bankId);

    public TQuesLib load(int libId);

    public List<TQuesLib> list();

    public List <TQuesLib>listByBankId(int bankId);


    public String ajaxGetLibOptionByBankId(int bankId);
}
