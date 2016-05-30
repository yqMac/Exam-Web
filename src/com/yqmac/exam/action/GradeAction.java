package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IUserGradeService;
import com.yqmac.exam.vo.TUserGrade;
import com.yqmac.exam.vo.TExam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/5/16 0016.
 */
@Controller
@RequestMapping(value = {"/grade"})
@AuthClass
public class GradeAction {

    /**
     * 分数统计-->考试列表
     *
     * @param model
     * @return
     */
    @AuthMethod(right = EnumAuth.Statistics)
    @RequestMapping(value = {"/exams"}, method = RequestMethod.GET)
    public String exams(Model model) {

        List<TExam> exams = userGradeService.listExam();
        model.addAttribute("exams", exams);
        return "/grade/list";
    }

    /**
     * 考试成绩详情
     *
     * @param id
     * @param model
     * @return
     */
    @AuthMethod(right = EnumAuth.Statistics)
    @RequestMapping(value = {"/{id}/grades"})
    public String grades(@PathVariable int id, Model model) {

        List<TUserGrade> grades = userGradeService.listByExamId(id);
        model.addAttribute("grades", grades);

        return "/grade/grade";
    }

    /**
     * 考试成绩统计信息
     *
     * @param id
     * @param model
     * @return
     */
    @AuthMethod(right = EnumAuth.Statistics)
    @RequestMapping(value = {"/{id}/statistics"}, method = RequestMethod.GET)
    public String statistics(@PathVariable int id, Model model) {
        if (true)
            throw new ExamException("buzhidao");
        /**
         * 统计信息有什么
         * 1、平均分
         * 2、及格人数
         * 3、不及格人数
         * 4、及格率
         * 5、及格饼状图
         * 6、分数段折线图
         *
         */

        return "/grade/statistics";
    }


    private IUserGradeService userGradeService;

    @Resource
    public void setUserGradeService(IUserGradeService userGradeService) {
        this.userGradeService = userGradeService;
    }
}
