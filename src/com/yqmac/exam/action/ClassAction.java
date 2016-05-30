package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.IClassService;
import com.yqmac.exam.vo.TClass;
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
@Controller("classAction")
@RequestMapping(value = "/class")
@AuthClass
public class ClassAction  {


    /**
     * 添加用户组
     * @param model
     * @return
     */
    @AuthMethod(right = EnumAuth.ClassM)
    @RequestMapping(value = {"/add"},method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new TClass());
        return "/class/add";
    }
    @AuthMethod(right = EnumAuth.ClassM)
    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public String add(TClass tclass) {
        classService.add(tclass);
        return "redirect:/class/classes";
    }
    @AuthMethod(right = EnumAuth.ClassM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.GET)
    public String update(@PathVariable int id,Model model  ) {
        TClass o = classService.load(id);
        model.addAttribute(o);
        return "/class/update";
    }

    @AuthMethod(right = EnumAuth.ClassM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.POST)
    public String update(@PathVariable int id ,TClass tclass) {
        classService.update(tclass);
        return "redirect:/class/classes";
    }

    @AuthMethod(right = EnumAuth.ClassM)
    @RequestMapping(value = {"/{id}/delete"} ,method = RequestMethod.GET)
    public String delete(@PathVariable int id ) {
        classService.delete(id );
        return "redirect:/class/classes";
    }

    @AuthMethod(right = EnumAuth.Logined)
    @RequestMapping(value = {"/classes","/list"},method = RequestMethod.GET)
    public String list(Model model ) {
        List<TClass> classes = classService.list();
        model.addAttribute("classes",classes);
        System.out.println(classes.size());

        return "/class/list";
    }

    //基本
    private IClassService classService;
    public IClassService getClassService() {
        return classService;
    }
    @Resource
    public void setClassService(IClassService classService) {
        this.classService = classService;
    }
}
