package com.yqmac.exam.service.impl;

import com.yqmac.exam.dao.IUserDao;
import com.yqmac.exam.dao.IPaperUserDao;
import com.yqmac.exam.service.IUserService;
import com.yqmac.exam.service.IExamService;
import com.yqmac.exam.service.IClassService;
import com.yqmac.exam.service.IPaperUserService;
import com.yqmac.exam.vo.TUser;
import com.yqmac.exam.vo.TExam;
import com.yqmac.exam.vo.TPaperUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/5/5 0005.
 */
@Service
public class PaperUserService extends BaseService<TPaperUser> implements IPaperUserService {

    private IPaperUserDao paperUserDao;
    private IUserDao userDao;
    private IExamService examService;
    private IUserService userService;
    private IClassService classService;

    @Resource
    public void setClassService(IClassService classService) {
        this.classService = classService;
    }

    @Resource
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Resource
    public void setExamService(IExamService examService) {
        this.examService = examService;
    }

    @Resource
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Resource
    public void setPaperUserDao(IPaperUserDao paperUserDao) {
        this.paperUserDao = paperUserDao;
    }


    @Override
    public TPaperUser add(int examId, int userId) {
        if (load(examId, userId) != null) {
            return null;
        }
        TExam exam = examService.load(examId);
        if (exam == null) {
            //throw new ExamException("要添加的考试信息有误");
            return null;
        }
        TUser user = userService.load(userId);
        if (user == null) {
            //throw new ExamException("要添加的员工信息有误");
            return null;
        }
        return paperUserDao.add(new TPaperUser(user, exam));
    }

    @Override
    public void add(int examId, List<Integer> users) {

        TExam exam = examService.load(examId);
        if (exam == null) {
            //throw new ExamException("要添加的考试信息有误");
            return;
        }
        for (Integer userId : users) {
            if (load(examId, userId) != null) {
                continue;
            }
            TUser user = userService.load(userId);
            if (user != null) {
                paperUserDao.add(new TPaperUser(user, exam));
            }
        }
    }

    @Override
    public void addAllUser(int examId) {
        TExam exam = examService.load(examId);
        if (exam == null) {
            //throw new ExamException("要添加的考试信息有误");
            return;
        }
        deleteByExamId(examId);

        String sql = "insert into t_paper_user (exam_id,user_id) " +
                "select ?,id from t_user  ;";
        paperUserDao.insertBySql(sql, new Object[]{examId});

    }

    private TPaperUser load(int examId, int userId) {
        List<TPaperUser> pcs = paperUserDao.list("from TPaperUser tpc where tpc.TExam.id = ? and tpc.TUser.id = ? ", new Object[]{examId, userId});
        if (pcs == null || pcs.size() == 0) {
            return null;
        } else {
            return pcs.get(0);
        }
    }

    @Override
    public void addClass(int examId, int ClassId) {
        TExam exam = examService.load(examId);
        if (exam == null) {
            //throw new ExamException("要添加的考试信息有误");
            return;
        }
        deleteClass(examId, ClassId);

        String sql = "insert into t_paper_user (exam_id,user_id) " +
                "select ?,id from t_user tc where tc.class_id = ? ;";
        paperUserDao.insertBySql(sql, new Object[]{examId, ClassId});
    }

    @Override
    public void deleteClass(int examId, int ClassId) {
        String sql = "delete from  t_paper_user  " +
                "where exam_id = ? and user_id in (select id from t_user tc where tc.class_id = ? ) ;";
        paperUserDao.insertBySql(sql, new Object[]{examId, ClassId});
    }

    @Override
    public void delete(int examId, int userId) {
        paperUserDao.updateByHql("delete from TPaperUser p where p.TExam.id = ? and  p.TUser.id= ?", new Object[]{examId, userId});
    }

    @Override
    public void deleteByUserId(int userId) {
        paperUserDao.updateByHql("delete from TPaperUser p where p.TUser.id= ?", userId);
    }

    @Override
    public void deleteByExamId(int examId) {
        paperUserDao.updateByHql("delete from TPaperUser p where p.TExam.id= ?", examId);
    }

    @Override
    public List<TPaperUser> listAll() {
        return paperUserDao.list("From TPaperUser ");
    }

    @Override
    public List<TUser> listUserByExamId(int examId) {
        String hql = "select p.TUser From TPaperUser p where p.TExam.id= ?";
        return userDao.list(hql, examId);
    }


    @Override
    public int getUserCountByExamId(int examId) {
        String hql = "select count(*) from TPaperUser tpc where tpc.TExam.id = ? ";
        Long count = (Long) paperUserDao.queryObject(hql, examId);
        return count.intValue();
    }

    @Override
    public int getExamCountByUserId(int userId) {
        String hql = "select count(*) from TPaperUser tpc where tpc.TUser.id = ? ";
        Long count = (Long) paperUserDao.queryObject(hql, userId);
        return count.intValue();
    }
}
