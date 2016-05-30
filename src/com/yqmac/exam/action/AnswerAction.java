package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.IUserAnswerService;
import com.yqmac.exam.service.IQuesTypeService;
import com.yqmac.exam.vo.TUserAnswer;
import com.yqmac.exam.vo.TExam;
import com.yqmac.exam.vo.TQuesType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * 对考试打分
 * Created by yqmac on 2016/5/13 0013.
 */
@Controller
@RequestMapping(value = {"/answer"})
@AuthClass
public class AnswerAction {

    /**
     * 显示需要打分的考试
     * @return
     */
    @AuthMethod(right = EnumAuth.HandleAnswer)
    @RequestMapping(value = {"/exams"},method = RequestMethod.GET)
    public  String exams(Model model ){

        List<TExam> exams = userAnswerService.listExam();

        model.addAttribute("exams", exams);
        return "/answer/list";
    }

    @AuthMethod(right = EnumAuth.HandleAnswer)
    @RequestMapping(value = {"/{id}/mark"},method = RequestMethod.GET)
    public String mark(@PathVariable int id ,Model model){
        TUserAnswer answer = userAnswerService.loadOne(id,0);
        List<TQuesType> types = quesTypeService.list();

        model.addAttribute("types", types);
        model.addAttribute("answer", answer);
        return "/answer/mark";
    }




    private IUserAnswerService userAnswerService;
    private IQuesTypeService quesTypeService;

    @Resource
    public void setQuesTypeService(IQuesTypeService quesTypeService) {
        this.quesTypeService = quesTypeService;
    }

    @Resource
    public void setUserAnswerService(IUserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }
}
