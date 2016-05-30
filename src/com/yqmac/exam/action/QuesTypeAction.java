package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.IQuesTypeService;
import com.yqmac.exam.vo.TQuesType;
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
@Controller("quesTypeAction")
@RequestMapping(value = {"/type"})
@AuthClass
public class QuesTypeAction  {

    @AuthMethod(right = EnumAuth.QuesTypeM)
   @RequestMapping(value = {"/add"},method = RequestMethod.GET)
   public String add(Model model) {
       model.addAttribute(new TQuesType());
        return "/type/add";
    }


    @AuthMethod(right = EnumAuth.QuesTypeM)
    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public String add(TQuesType type ) {
        quesTypeService.add(type);
        return "redirect:/type/types";
    }

    @AuthMethod(right = EnumAuth.QuesTypeM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.GET)
    public String update(@PathVariable int id ,Model model) {
        TQuesType t = quesTypeService.load(id);
        model.addAttribute(t);
        return "/type/update";
    }

    @AuthMethod(right = EnumAuth.QuesTypeM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.POST)
    public String update(@PathVariable int id, TQuesType type ) {
        quesTypeService.update(type);
        return "redirect:/type/types";
    }

    @AuthMethod(right = EnumAuth.QuesTypeM)
    @RequestMapping(value = {"/{id}/delete"},method = RequestMethod.GET)
    public String delete(@PathVariable int id ) {
        quesTypeService.delete(id );
        return "redirect:/type/types";
    }

    @AuthMethod(right = EnumAuth.QuesTypeM)
    @RequestMapping(value = {"/types","/list"},method = RequestMethod.GET)
    public String list(Model model) {
        List<TQuesType> types = quesTypeService.list();
        model.addAttribute("types", types);
        //ActionContext.getContext().put("types",types);
        return "/type/list";
    }

    //基本
    private TQuesType qtype;
    private int qtypeId;
    private IQuesTypeService quesTypeService;

    public int getQtypeId() {
        return qtypeId;
    }

    public void setQtypeId(int qtypeId) {
        this.qtypeId = qtypeId;
    }

    public IQuesTypeService getQuesTypeService() {
        return quesTypeService;
    }

    @Resource
    public void setQuesTypeService(IQuesTypeService quesTypeService) {
        this.quesTypeService = quesTypeService;
    }

    private static final String SUCCESS = "success";
    private void copypro(TQuesType o){
        qtype.setId(o.getId());
        qtype.setTypeName(o.getTypeName());

    }
}
