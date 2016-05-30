package com.yqmac.exam.service;

import com.yqmac.exam.vo.TQuesBank;

import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
public interface IQuesBankService {
    public TQuesBank add(TQuesBank bank);

    public void delete(int bankId);

    public void update(TQuesBank bank);

    public TQuesBank loadById(int bankId);

    public List<TQuesBank> listAll();
}
