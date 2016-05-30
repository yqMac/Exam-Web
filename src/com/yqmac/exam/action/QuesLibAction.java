package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.IQuesBankService;
import com.yqmac.exam.service.IQuesLibService;
import com.yqmac.exam.vo.TQuesBank;
import com.yqmac.exam.vo.TQuesLib;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Controller("quesLibAction")
@RequestMapping(value = {"/lib"})
@AuthClass
public class QuesLibAction  {

    private String redirect(){
        return "redirect:/lib/libs";
    }

    @AuthMethod(right = EnumAuth.QuesLibM)
    @RequestMapping(value = {"/add"},method = RequestMethod.GET)
    public String add(Model model) {
        List<TQuesBank> banks = quesBankService.listAll();

        model.addAttribute(new TQuesLib());
        model.addAttribute("banks", banks);
        return "/lib/add";
    }
    @AuthMethod(right = EnumAuth.QuesLibM)
    @RequestMapping(value = {"/libs"},method = RequestMethod.GET)
    public String list(Model model) {
        List<TQuesLib> libs = quesLibService.list();
        model.addAttribute("libs", libs);
        return "/lib/list";
    }
    @AuthMethod(right = EnumAuth.QuesLibM)
    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public String add(TQuesLib lib,int bankId) {
        quesLibService.add(lib, bankId);
        return redirect();
    }
    @AuthMethod(right = EnumAuth.QuesLibM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.GET)
    public String update(@PathVariable int id,Model model) {
        TQuesLib b = quesLibService.load(id);
        List<TQuesBank> banks = quesBankService.listAll();

        model.addAttribute(b);
        model.addAttribute("bankId", b.getTQuesBank().getId());
        model.addAttribute("banks", banks);
        return "/lib/update";
    }
    @AuthMethod(right = EnumAuth.QuesLibM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.POST)
    public String update(@PathVariable int id ,TQuesLib lib,int bankId) {
        quesLibService.update(lib,bankId);
        return redirect();
    }
    @AuthMethod(right = EnumAuth.QuesLibM)
    @RequestMapping(value = {"/{id}/delete"},method = RequestMethod.GET)
    public String delete(@PathVariable int id ) {
        quesLibService.delete(id );
        return redirect();
    }

    //基本
    private IQuesLibService quesLibService;
    private IQuesBankService quesBankService;

    @Resource
    public void setQuesBankService(IQuesBankService quesBankService) {
        this.quesBankService = quesBankService;
    }

    public IQuesLibService getQuesLibService() {
        return quesLibService;
    }

    @Resource(name = "quesLibService")
    public void setQuesLibService(IQuesLibService quesLibService) {
        this.quesLibService = quesLibService;
    }

}
