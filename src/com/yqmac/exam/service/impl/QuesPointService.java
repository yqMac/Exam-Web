package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IQuesBankDao;
import com.yqmac.exam.dao.IQuesLibDao;
import com.yqmac.exam.dao.IQuesPointDao;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IQuesPointService;
import com.yqmac.exam.vo.TQuesLib;
import com.yqmac.exam.vo.TQuesPoint;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/25 0025.
 */
@Service("quesPointService")
public class QuesPointService implements IQuesPointService {
    private IQuesPointDao quesPointDao;
    private IQuesLibDao quesLibDao;
    private IQuesBankDao quesBankDao;

    @Resource(name = "quesLibDao")
    public void setQuesLibDao(IQuesLibDao quesLibDao) {
        this.quesLibDao = quesLibDao;
    }

    @Resource(name = "quesBankDao")
    public void setQuesBankDao(IQuesBankDao quesBankDao) {
        this.quesBankDao = quesBankDao;
    }

    @Resource(name = "quesPointDao")
    public void setQuesPointDao(IQuesPointDao quesPointDao) {
        this.quesPointDao = quesPointDao;
    }

    @Override
    public TQuesPoint add(TQuesPoint point, int libId) {
        TQuesLib lib = quesLibDao.load(libId);
        if (lib == null) {
            throw new ExamException("要添加的知识点的题库为空,不能添加");
        }

        point.setTQuesLib(lib);
        point.setTQuesBank(lib.getTQuesBank());
       return quesPointDao.add(point);
    }

    @Override
    public void update(TQuesPoint point, int libId) {
        TQuesLib lib = quesLibDao.load(libId);
        if (lib == null) {
            throw new ExamException("要更新的知识点的题库为空,不能更新");
        }

        point.setTQuesLib(lib);
        point.setTQuesBank(lib.getTQuesBank());
        quesPointDao.update(point);
    }

    @Override
    public TQuesPoint load(int pointId) {

        return quesPointDao.load(pointId);
    }

    @Override
    public void delete(int pointId) {
        quesPointDao.delete(pointId);
    }

    @Override
    public List<TQuesPoint> list() {
        return quesPointDao.list("From TQuesPoint");
    }

    @Override
    public List<TQuesPoint> listByLibId(int libId) {
        return quesPointDao.list("from TQuesPoint p where p.TQuesLib.id = ?" ,libId);
    }

    @Override
    public List<TQuesPoint> listByBankId(int bankId) {
         return quesPointDao.list("from TQuesPoint p where p.TQuesBank.id =?" ,bankId);
    }


    @Override
    public String ajaxGetPointOptionByLibId(int libId) {
        StringBuffer sbuf = new StringBuffer();
        //<option value="">==请选题库集==</option>
        List<TQuesPoint> points  = listByLibId(libId);
        for (TQuesPoint p : points) {
            sbuf.append( String.format("<option value=\"%d\">%s</option>",
                    p.getId(),p.getPointName()));
        }
        return sbuf.toString();
    }
}
