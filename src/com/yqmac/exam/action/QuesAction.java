package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.*;
import com.yqmac.exam.util.Pager;
import com.yqmac.exam.util.StringUtil;
import com.yqmac.exam.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Controller("quesAction")
@RequestMapping(value = {"/ques"})
@AuthClass
public class QuesAction {
    private String redirect() {
        return "redirect:/ques/queses";
    }

    @AuthMethod(right = EnumAuth.QuesM)
    @RequestMapping(value = {"/queses"}, method = RequestMethod.GET)
    public String list(Model model, Integer bankId, Integer libId, Integer pointId, Integer typeId,String fuzz) {

        System.out.println(" fuzz ----> "+fuzz);

        //System.out.println(bankId + "-" + libId + "-" + pointId + "-" + typeId + "-" + fuzz);
        bankId = bankId == null ? 0 : bankId;
        libId = libId == null ? 0 : libId;
        pointId = pointId == null ? 0 : pointId;
        typeId = typeId == null ? 0 : typeId;
        fuzz = fuzz == null ? "" : fuzz;

        model.addAttribute("bankId", bankId);
        model.addAttribute("libId", libId);
        model.addAttribute("pointId", pointId);
        model.addAttribute("typeId", typeId);
        model.addAttribute("fuzz", fuzz);

        List<TQuesType> types = quesTypeService.list();
        model.addAttribute("types", types);

        List<TQuesBank> banks = quesBankService.listAll();
        model.addAttribute("banks", banks);

        if (bankId != 0) {
            List<TQuesLib> libs = new ArrayList<>();
            libs.addAll(quesLibService.listByBankId(bankId));
            model.addAttribute("libs", libs);
        }

        if (libId != 0) {
            List<TQuesPoint> points = new ArrayList<>();
            points.addAll(quesPointService.listByLibId(libId));
            model.addAttribute("points", points);
        }

        Pager<TQues> datas = quesService.find(bankId, libId, pointId, typeId, fuzz);
        model.addAttribute("datas", datas);
        return "/ques/list";
    }

    @AuthMethod(right = EnumAuth.QuesM)
    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new TQues());

        List<TQuesBank> banks = quesBankService.listAll();
        model.addAttribute("banks", banks);

        List<TQuesPoint> points = quesPointService.list();
        model.addAttribute("points", points);

        List<TQuesType> qtypes = quesTypeService.list();
        model.addAttribute("qtypes", qtypes);

        return "/ques/add";
    }

    @AuthMethod(right = EnumAuth.QuesM)
    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String add(TQues ques, int pointId, int typeId, String[] optiontexts) {
        String options = StringUtil.options2String(optiontexts);

        ques.setOptions(options);
        ques.setOptionNum(optiontexts == null ? 0 : optiontexts.length);

        quesService.add(ques, pointId, typeId);
        return redirect();
    }

    @AuthMethod(right = EnumAuth.QuesM)
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model) {
        TQues q = quesService.load(id);

        model.addAttribute(q);

        List<String> optiontexts = StringUtil.string2Options(q.getOptions());
        model.addAttribute("optiontexts", optiontexts);

        List<TQuesBank> banks = quesBankService.listAll();
        model.addAttribute("banks", banks);

        List<TQuesLib> libs = quesLibService.listByBankId(q.getTQuesBank().getId());
        model.addAttribute("libs", libs);

        List<TQuesPoint> points = quesPointService.listByLibId(q.getTQuesLib().getId());
        model.addAttribute("points", points);

        List<TQuesType> qtypes = quesTypeService.list();
        model.addAttribute("qtypes", qtypes);

        return "/ques/update";
    }

    @AuthMethod(right = EnumAuth.QuesM)
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.POST)
    public String update(@PathVariable int id, TQues ques, String[] optiontexts) {
        String options = StringUtil.options2String(optiontexts);
        ques.setOptions(options);
        ques.setOptionNum(optiontexts.length);
        //System.out.println(pointId+"======="+typeId);
        quesService.update(ques, ques.getTQuesPoint().getId(), ques.getTQuesType().getId());
        return redirect();
    }

    @AuthMethod(right = EnumAuth.QuesM)
    @RequestMapping(value = {"/{id}/delete"}, method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        quesService.delete(id);
        return redirect();
    }

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/ajaxGetLibOptionByBankId"}, method = RequestMethod.POST)
    public String ajaxGetLibOptionByBankId(int bankId, HttpServletResponse response) {
        String str = quesLibService.ajaxGetLibOptionByBankId(bankId);
        StringUtil.printStrRespone(response, str);

        return null;
    }

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/ajaxGetPointOptionByLibId"}, method = RequestMethod.POST)
    public String ajaxGetPointOptionByLibId(int libId, HttpServletResponse response) {
        String str = quesPointService.ajaxGetPointOptionByLibId(libId);
        StringUtil.printStrRespone(response, str);
        return null;
    }


    //基本
    private IQuesPointService quesPointService;
    private IQuesTypeService quesTypeService;
    private IQuesService quesService;
    private IQuesLibService quesLibService;
    private IQuesBankService quesBankService;

    @Resource
    public void setQuesPointService(IQuesPointService quesPointService) {
        this.quesPointService = quesPointService;
    }

    @Resource
    public void setQuesTypeService(IQuesTypeService quesTypeService) {
        this.quesTypeService = quesTypeService;
    }

    @Resource
    public void setQuesService(IQuesService quesService) {
        this.quesService = quesService;
    }

    public IQuesLibService getQuesLibService() {
        return quesLibService;
    }

    @Resource
    public void setQuesLibService(IQuesLibService quesLibService) {
        this.quesLibService = quesLibService;
    }

    public IQuesBankService getQuesBankService() {
        return quesBankService;
    }

    @Resource
    public void setQuesBankService(IQuesBankService quesBankService) {
        this.quesBankService = quesBankService;
    }


}
