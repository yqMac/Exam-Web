package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.IQuesBankService;
import com.yqmac.exam.vo.TQuesBank;
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
@Controller("quesBankAction")
@RequestMapping(value = {"/bank"})
@AuthClass
public class QuesBankAction  {

    private String redirect(){
        return "redirect:/bank/banks";
    }

    @AuthMethod(right = EnumAuth.QuesBankM)
    @RequestMapping(value = {"/add"},method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new TQuesBank());
        return "/bank/add";
    }
    @AuthMethod(right = EnumAuth.Logined)
    @RequestMapping(value = {"/banks"},method = RequestMethod.GET)
    public String list(Model model) {
        List<TQuesBank> banks = quesBankService.listAll();
        model.addAttribute("banks", banks);
        return "/bank/list";
    }


    @AuthMethod(right = EnumAuth.QuesBankM)
    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public String add(TQuesBank bank) {
        quesBankService.add(bank);
        return redirect();
    }
    @AuthMethod(right = EnumAuth.QuesBankM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.GET)
    public String update(@PathVariable int id,Model model ) {
        TQuesBank b = quesBankService.loadById(id);
        model.addAttribute(b);
        return "/bank/update";
    }
    @AuthMethod(right = EnumAuth.QuesBankM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.POST)
    public String update(@PathVariable int id,TQuesBank bank ) {
        quesBankService.update(bank);
        return redirect();
    }
    @AuthMethod(right = EnumAuth.QuesBankM)
    @RequestMapping(value = {"/{id}/delete"},method = RequestMethod.GET)
    public String delete(@PathVariable int id ) {
        quesBankService.delete(id);
        return redirect();
    }

    //基本
    private IQuesBankService quesBankService;

    public IQuesBankService getQuesBankService() {
        return quesBankService;
    }

    @Resource
    public void setQuesBankService(IQuesBankService quesBankService) {
        this.quesBankService = quesBankService;
    }

}
