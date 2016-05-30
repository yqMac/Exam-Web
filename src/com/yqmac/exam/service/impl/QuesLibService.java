package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IQuesBankDao;
import com.yqmac.exam.dao.IQuesLibDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IQuesLibService;
import com.yqmac.exam.vo.TQuesBank;
import com.yqmac.exam.vo.TQuesLib;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@Service("quesLibService")
public class QuesLibService implements IQuesLibService {

    private IQuesLibDao quesLibDao;
    private IQuesBankDao quesBankDao;

    @Resource
    public void setQuesBankDao(IQuesBankDao quesBankDao) {
        this.quesBankDao = quesBankDao;
    }

    @Resource
    public void setQuesLibDao(IQuesLibDao quesLibDao) {
        this.quesLibDao = quesLibDao;
    }

    @Override
    public TQuesLib add(TQuesLib lib, int bankId) {
        TQuesBank bank = quesBankDao.load(bankId);
        if (bank == null) {
            throw new ExamException("要添加的题库的题库集不存在");
        }
        lib.setTQuesBank(bank);

       return quesLibDao.add(lib);

    }

    @Override
    public void delete(int libId) {
        if (load(libId).getTQueses().size() > 0) {
            throw new ExamException("该题库下还有题目,不能直接删除");
        }
        quesLibDao.delete(libId);
    }

    @Override
    public void update(TQuesLib lib, int bankId) {
        TQuesBank bank = quesBankDao.load(bankId);
        if (bank == null) {
            throw new ExamException("要修改的题库的题库集不存在");
        }
        lib.setTQuesBank(bank);
        quesLibDao.update(lib);
    }

    @Override
    public TQuesLib load(int libId) {

        return quesLibDao.load(libId);
    }

    @Override
    public List<TQuesLib> list() {
        List<TQuesLib> libs = quesLibDao.list("From TQuesLib");
        return libs;
    }

    @Override
    public List<TQuesLib> listByBankId(int bankId) {
        List<TQuesLib> libs = quesLibDao.list("From TQuesLib as lib where lib.TQuesBank.id= ?", bankId);
        return libs;
    }


    @Override
    public String ajaxGetLibOptionByBankId(int bankId) {
        StringBuffer sbuf = new StringBuffer();
        //<option value="">==请选题库集==</option>
        List<TQuesLib> libs = listByBankId(bankId);
        for (TQuesLib lib : libs) {
            sbuf.append( String.format("<option value=\"%d\">%s</option>",
                    lib.getId(),lib.getLibName()));
        }
        return sbuf.toString();
    }
}
