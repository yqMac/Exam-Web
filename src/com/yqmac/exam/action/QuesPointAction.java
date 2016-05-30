package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.IQuesBankService;
import com.yqmac.exam.service.IQuesLibService;
import com.yqmac.exam.service.IQuesPointService;
import com.yqmac.exam.vo.TQuesBank;
import com.yqmac.exam.vo.TQuesLib;
import com.yqmac.exam.vo.TQuesPoint;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Controller("quesPointAction")
@RequestMapping(value = {"/point"})
@AuthClass
public class QuesPointAction {

    private String redirect() {
        return "redirect:/point/points";
    }

    @AuthMethod(right = EnumAuth.QuesPoint)
    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String add(Model model) {
        List<TQuesBank> banks = quesBankService.listAll();
        model.addAttribute(new TQuesPoint());
        model.addAttribute("banks", banks);
        return "/point/add";
    }

    @AuthMethod(right = EnumAuth.Logined)
    @RequestMapping(value = {"/points"}, method = RequestMethod.GET)
    public String list(Model model) {
        System.out.println("知识点->list");
        List<TQuesPoint> points = quesPointService.list();
        System.out.println("知识点数量:" + points.size());
        model.addAttribute("points", points);
        return "/point/list";
    }
    @AuthMethod(right = EnumAuth.QuesPoint)
    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String add(TQuesPoint point, int libId) {
        quesPointService.add(point, libId);
        return redirect();
    }
    @AuthMethod(right = EnumAuth.QuesPoint)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.GET)
    public String update(@PathVariable int id,Model model  ) {
        TQuesPoint p = quesPointService.load(id );
        int libId = p.getTQuesLib().getId();
        int bankId = p.getTQuesBank().getId();

        model.addAttribute(p);
        model.addAttribute("libId", libId);
        model.addAttribute("bankId", bankId);

        List<TQuesBank> banks = quesBankService.listAll();
        model.addAttribute("banks", banks);

        List<TQuesLib> libs = quesLibService.listByBankId(bankId);
        model.addAttribute("libs", libs);
        return "/point/update";
    }
    @AuthMethod(right = EnumAuth.QuesPoint)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.POST)
    public String update(@Validated int id,int libId,TQuesPoint point ) {
        quesPointService.update(point, libId);
        return redirect();
    }
    @AuthMethod(right = EnumAuth.QuesPoint)
    @RequestMapping(value = {"/{id}/delete"},method = RequestMethod.GET)
    public String delete(@PathVariable int id ) {
        quesPointService.delete(id);
        return redirect();
    }


    //基本
    private IQuesPointService quesPointService;
    private IQuesBankService quesBankService;
    private IQuesLibService quesLibService;

    @Resource
    public void setQuesPointService(IQuesPointService quesPointService) {
        this.quesPointService = quesPointService;
    }

    @Resource
    public void setQuesBankService(IQuesBankService quesBankService) {
        this.quesBankService = quesBankService;
    }

    @Resource
    public void setQuesLibService(IQuesLibService quesLibService) {
        this.quesLibService = quesLibService;
    }


}
