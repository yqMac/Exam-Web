package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.IRightService;
import com.yqmac.exam.vo.TRight;
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
@Controller("rightAction")
@RequestMapping(value = "/right")
@AuthClass
public class RightAction {

    @AuthMethod(right = EnumAuth.RightM)
    @RequestMapping(value = {"/add"},method = RequestMethod.GET)
    public String add(Model model) {
        List<TRight> rights =rightService.listBigAll();
        model.addAttribute(new TRight());
        model.addAttribute("rights",rights);

        return "/right/add";
    }

    @AuthMethod(right = EnumAuth.RightM)
    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public String add(TRight right ) {
        rightService.add(right,right.getParent().getId());
        return "redirect:/right/rights";
    }

    @AuthMethod(right = EnumAuth.RightM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.GET)
    public String update(@PathVariable int id, Model model) {
        System.out.println("right的更新请求 "+id);
        TRight r = rightService.loadById(id);
        model.addAttribute("right", r);
        model.addAttribute("parentId", r.getParent().getId());
        List<TRight> rights =rightService.listBigAll();
        model.addAttribute("rights", rights);
        return "/right/update";
    }

    @AuthMethod(right = EnumAuth.RightM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.POST)
    public String update(TRight right, @PathVariable int id) {
        System.out.println(right.getRightName());
        rightService.update(right, right.getParent().getId());
        return "redirect:/right/rights";
    }

    @AuthMethod(right = EnumAuth.RightM)
    @RequestMapping(value = {"/{id}/delete"},method = RequestMethod.GET)
    public String delete(@PathVariable int id ) {
        rightService.delete(id);
        return "redirect:/right/rights";
    }

    @AuthMethod(right = EnumAuth.RightM)
    @RequestMapping(value = {"/rights","/list"},method = RequestMethod.GET)
    public String list(Model model) {
        System.out.println("right-->rights");
        List<TRight>rights = rightService.list();
        model.addAttribute("rights",rights);
        return "/right/list";
    }


    private IRightService rightService;

    @Resource
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
    }


}
