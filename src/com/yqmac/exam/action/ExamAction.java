package com.yqmac.exam.action;

import com.yqmac.exam.Beans.PaperStrategies;
import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.*;
import com.yqmac.exam.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Controller("examAction")
@RequestMapping(value = {"/exam"})
@AuthClass
public class ExamAction {

    /**
     * 跳转到列表页
     *
     * @return
     */
    private String redirect() {
        return "redirect:/exam/exams";
    }

    @AuthMethod(right = EnumAuth.ExamM)
    @RequestMapping(value = {"/exams", "/list"}, method = RequestMethod.GET)
    public String list(Model model) {
        List<TExam> exams = examService.list();
        model.addAttribute("exams", exams);
        return "/exam/list";
    }

    @AuthMethod(right = EnumAuth.ExamM)
    @RequestMapping(value = {"/{id}/delete"}, method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        examService.delete(id);
        return redirect();
    }
    @AuthMethod(right = EnumAuth.ExamM)
    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String add(Model model) {

        //添加空的model
        model.addAttribute(new TExam());

        //全部的题库集用于下拉框
        List<TQuesBank> banks = quesBankService.listAll();
        model.addAttribute("banks", banks);

        //全部的题型,用于下拉框
        List<TQuesType> types = quesTypeService.list();
        model.addAttribute("types", types);

        return "/exam/add";
    }
    @AuthMethod(right = EnumAuth.ExamM)
    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String add(TExam exam, PaperStrategies paperStrate) {
        //兼用Struts2的Service,转换数组为List
        List<TPaperStrategy> strategies = new ArrayList<>();
        //PaperStrategies为中间类,用于存储TPaperStrategy的数组,这里将数组取出
        if (paperStrate != null && paperStrate.getStrategies() != null) {
            TPaperStrategy[] s = paperStrate.getStrategies();
            //取出全部可用策略
            for (TPaperStrategy tPaperStrategy : s) {
                if (tPaperStrategy != null) {
                    strategies.add(tPaperStrategy);
                }
            }
        }
        //添加考试
        examService.add(exam, strategies);
        return redirect();
    }
    @AuthMethod(right = EnumAuth.ExamM)
    @RequestMapping(value = {"/{id}/detail"}, method = RequestMethod.GET)
    public String detail(@PathVariable int id, Model model) {
        TExam e = examService.load(id);
        model.addAttribute("exam", e);
        return "/exam/detail";
    }

    @AuthMethod(right = EnumAuth.ExamM)
    @RequestMapping(value = {"/{id}/examusers"},method = RequestMethod.GET)
    public String examusers(@PathVariable int id,Model model){

        //List<TUser> examusers = paperUserService.listUserByExamId(id);

        int examusers = examService.getExamUserCount(id);

        List<TClass> classs = classService.list();
        List<TUser> users = userService.list();


        model.addAttribute("examId", id);
        model.addAttribute("examusers", examusers);
        model.addAttribute("classs", classs);
        model.addAttribute("users", users);

        return "/exam/modusers";
    }

    @RequestMapping(value = {"/{id}/examhandle"},method = RequestMethod.GET)
    public String examhandle(@PathVariable int id , Model model){


        return "/exam/examhandle";
    }


    //
    public String updatePre() {
        //TExam e = examService.load(exam.getId());
        ////copypro(e);
        //
        //System.out.println(exam.getExamName());
        //
        //List<TQuesBank> banks = quesBankService.listAll();
        //ActionContext.getContext().put("banks", banks);
        //
        //List<TQuesType> types = quesTypeService.list();
        //ActionContext.getContext().put("types", types);
        //
        //String selectedtx = "";
        //List<TQuesType> typesss = paperStrategyServcie.getTypesByExamId(exam.getId());
        //for (TQuesType typess : typesss) {
        //    selectedtx += typess.getId() + ",";
        //}
        //if (selectedtx.length() > 0)
        //    selectedtx = selectedtx.substring(0, selectedtx.length() - 1);
        //ActionContext.getContext().put("selectedTX", selectedtx);
        //bankId = 0;
        //pointId = 0;
        //
        //System.out.println(exam.getExamName());
        return "";
    }

    //
    //public String update() {
    //    System.out.println(class);
    //    classService.update(class);
    //    ActionContext.getContext().put("url","class_list");
    //    return "redirect";
    //}
    //

    //基本
    private IExamService examService;
    private IQuesBankService quesBankService;
    private IQuesLibService quesLibService;
    private IQuesPointService quesPointService;
    private IQuesTypeService quesTypeService;
    private IPaperStrategyServcie paperStrategyServcie;
    private IPaperUserService paperUserService;
    private IClassService classService;
    private IUserService userService;

    @Resource
    public void setClassService(IClassService classService) {
        this.classService = classService;
    }

    @Resource
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Resource
    public void setPaperUserService(IPaperUserService paperUserService) {
        this.paperUserService = paperUserService;
    }

    @Resource
    public void setPaperStrategyServcie(IPaperStrategyServcie paperStrategyServcie) {
        this.paperStrategyServcie = paperStrategyServcie;
    }

    @Resource
    public void setQuesBankService(IQuesBankService quesBankService) {
        this.quesBankService = quesBankService;
    }

    @Resource
    public void setQuesLibService(IQuesLibService quesLibService) {
        this.quesLibService = quesLibService;
    }

    @Resource
    public void setQuesPointService(IQuesPointService quesPointService) {
        this.quesPointService = quesPointService;
    }

    @Resource
    public void setQuesTypeService(IQuesTypeService quesTypeService) {
        this.quesTypeService = quesTypeService;
    }

    @Resource(name = "examService")
    public void setExamService(IExamService examService) {
        this.examService = examService;
    }

}
