package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IQuesBankDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IQuesBankService;
import com.yqmac.exam.vo.TQuesBank;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@Service("quesBankService")
public class QuesBankService implements IQuesBankService {

    private IQuesBankDao quesBankDao;

    @Resource
    public void setQuesBankDao(IQuesBankDao quesBankDao) {
        this.quesBankDao = quesBankDao;
    }

    @Override
    public TQuesBank add(TQuesBank bank) {

       return quesBankDao.add(bank);
    }

    @Override
    public void delete(int bankId) {
        if (loadById(bankId).getTQuesLibs().size() > 0) {
            throw new ExamException("题库集下还有题库,不能直接删除");
        }
        quesBankDao.delete(bankId);
    }

    @Override
    public void update(TQuesBank bank) {
        quesBankDao.update(bank);
    }


    @Override
    public TQuesBank loadById(int bankId) {
        TQuesBank bank = quesBankDao.load(bankId);
        return bank;
    }

    @Override
    public List<TQuesBank> listAll() {
        List<TQuesBank> banks = quesBankDao.list("From TQuesBank");
        return banks;
    }
}
