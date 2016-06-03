package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.IRightService;
import com.yqmac.exam.service.IRoleService;
import com.yqmac.exam.vo.TRight;
import com.yqmac.exam.vo.TRole;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yqmac on 2016/4/20 0020.
 */
@Controller("roleAction")
@RequestMapping(value = {"/role"})
@AuthClass
public class RoleAction {

    @AuthMethod(right = EnumAuth.RoleM)
    @RequestMapping(value = {"/add"},method = RequestMethod.GET)
    public String add(Model model) {
        List<TRight> rights =rightService.listBig();
        model.addAttribute("rightsBig", rights);
        model.addAttribute(new TRole());

        return "/role/add";
    }

    @AuthMethod(right = EnumAuth.RoleM)
    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public String add(TRole role ,String smalRs, String selectedValues) {
        List<Integer> rightIds = new ArrayList<>();

        String[] smls = selectedValues.split(",");
        for (String sml : smls) {
            rightIds.add(Integer.valueOf(sml.trim()));
        }
        smls = smalRs.split(",");
        for (String sml : smls) {
            rightIds.add(Integer.valueOf(sml.trim()));
        }

        roleService.add(role,rightIds);
        return "redirect:/role/roles";
    }
    @AuthMethod(right = EnumAuth.RoleM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.GET)
    public String update(Model model,@PathVariable int id , HttpServletResponse response, HttpServletRequest request) {

        //要修改的用户
        TRole r = roleService.loadById(id);

        //用户的权限信息
        List<Integer> selectedValues = new ArrayList<>();
        List<TRight> se = rightService.listBigByRoleId(id);

        for (TRight tRight : se) {
            selectedValues.add(tRight.getId());
        }
        List<TRight> rights =rightService.listBig();


        model.addAttribute("role",r);
        model.addAttribute("rights", rights);
        model.addAttribute("selectedValues", selectedValues);

        return "/role/update";
    }
    @AuthMethod(right = EnumAuth.RoleM)
    @RequestMapping(value = {"/{id}/update"},method = RequestMethod.POST)
    public String update(TRole role ,@PathVariable int id ,String smalRs, String selectedValues ) {
        List<Integer> rightIds = new ArrayList<>();

        String[] smls = selectedValues.split(",");
        for (String sml : smls) {
            rightIds.add(Integer.valueOf(sml.trim()));
        }
        smls = smalRs.split(",");
        for (String sml : smls) {
            rightIds.add(Integer.valueOf(sml.trim()));
        }

        roleService.update(role,rightIds);
        return "redirect:/role/roles";
    }
    @AuthMethod(right = EnumAuth.RoleM)
    @RequestMapping(value = {"/{id}/delete"},method = RequestMethod.GET)
    public String delete(@PathVariable int id ) {
        roleService.delete(id);
        return "redirect:/role/roles";
    }
    @AuthMethod(right = EnumAuth.RoleM)
    @RequestMapping(value = {"/roles"},method = RequestMethod.GET)
    public String list(Model model) {
        List<TRole> roles = roleService.list();
        model.addAttribute("roles", roles);
        return "/role/list";
    }

    //基本

    private IRightService rightService;
    private IRoleService roleService;

    @Resource
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }
    @Resource
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
    }
}
