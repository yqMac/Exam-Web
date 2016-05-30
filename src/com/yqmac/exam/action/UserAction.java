package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.AuthUtil;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IClassService;
import com.yqmac.exam.service.IRightService;
import com.yqmac.exam.service.IRoleService;
import com.yqmac.exam.service.IUserService;
import com.yqmac.exam.util.Captcha;
import com.yqmac.exam.util.Pager;
import com.yqmac.exam.vo.TClass;
import com.yqmac.exam.vo.TRole;
import com.yqmac.exam.vo.TUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by yqmac on 2016/4/19 0019.
 */
@Controller("userAction")
@RequestMapping(value ={"/user","/"})
@AuthClass
public class UserAction {

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/index"}, method = RequestMethod.GET)
    public String index(HttpSession session, Model model) {
        TUser c = (TUser) session.getAttribute("loginUser");
        if (c == null) {
            return "redirect:/user/login";
        }
        model.addAttribute(c);
        model.addAttribute("menus", rightService.getMenuJSonString(c.getId()));
        //System.out.println(rightService.getMenuJSonString(c.getId()));
        return "/index";
    }

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/login","/"}, method = RequestMethod.GET)
    public String login() {
        System.out.println("login->get");
        return "/user/login";
    }

    @AuthMethod(right = EnumAuth.Logined)
    @RequestMapping(value = {"/detail"}, method = RequestMethod.GET)
    public String detail(Model model, HttpSession session) {
        TUser c = (TUser) session.getAttribute("loginUser");
        if (c == null) {
            return "/user/login";
        }
        TUser u = userService.load(c.getId());
        model.addAttribute("loginUser",u);
        return "/user/detail";
    }
    @AuthMethod(right = EnumAuth.Logined)
    @RequestMapping(value = {"/detail"}, method = RequestMethod.POST)
    public String detail(TUser user ,Model model, HttpSession session) {
        TUser c = (TUser) session.getAttribute("loginUser");
        if (c == null) {
            return "redirect:/user/login";
        }
        TUser u = userService.load(c.getId());
        u.setPassword(user.getPassword());
        u.setNickname(user.getNickname());

        userService.update(u);
        model.addAttribute("loginUser",u);
        return "/user/detail";
    }

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/verifycode"}, method = RequestMethod.GET)
    public void checkCode(HttpServletResponse res, HttpSession session) throws IOException {
        res.setContentType("image/jpg");
        int width = 90;
        int height = 30;
        Captcha c = Captcha.getInstance();
        c.set(width, height);
        String code = c.generateCheckcode();
        BufferedImage img = c.generateCheckImg(code);
        OutputStream os = res.getOutputStream();
        session.setAttribute("checkcode", code);
        ImageIO.write(img, "jpg", os);
    }

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/login","/"}, method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, HttpSession session, String username, String password, String checkcode, Model model) {
        String s = (String) session.getAttribute("checkcode");
        if (s == null || !s.equals(checkcode.trim())) {
            throw new ExamException("验证码不正确");
        }
        session.setAttribute("checkcode", null);
        TUser c = userService.login(username, password);
        if (c != null) {
            session.setAttribute("loginUser", c);

            session.setAttribute("isAdmin", ("超级管理员".equals(c.getTRole().getRoleName())));

            session.setAttribute("allAction", AuthUtil.getAllActions(c,rightService, session));
            System.out.println("登录成功 ");
            return "redirect:/user/index";
        } else {
            throw new ExamException("登录失败");
        }
    }


    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/user/login";
    }

    @AuthMethod(right = EnumAuth.UserM)
    @RequestMapping(value = {"/users"}, method = RequestMethod.GET)
    public String list(Model model, Integer classId, String fuzz) {
        classId = classId == null ? 0 : classId;
        fuzz = fuzz == null ? "" : fuzz;

        Pager<TUser> datas = userService.find(classId, fuzz);
        List<TClass> classs = classService.list();


        model.addAttribute("classId", classId);
        model.addAttribute("fuzz", fuzz);
        model.addAttribute("classes", classs);
        model.addAttribute("datas", datas);
        return "/user/list";
    }

    @AuthMethod(right = EnumAuth.UserM)
    @RequestMapping(value = {"/add"}, method = RequestMethod.GET)
    public String add(Model model) {
        List<TRole> roles = roleService.list();
        List<TClass> classs = classService.list();

        model.addAttribute(new TUser());
        model.addAttribute("roles", roles);
        model.addAttribute("classs", classs);
        return "/user/add";
    }

    @AuthMethod(right = EnumAuth.UserM)
    @RequestMapping(value = {"/add"}, method = RequestMethod.POST)
    public String add(TUser user, int classId, int roleId) {
        userService.add(user, classId, roleId);
        return "redirect:/user/users";
    }

    @AuthMethod(right = EnumAuth.UserM)
    @RequestMapping(value = {"/{id}/delete"}, method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        userService.delete(id);
        return "redirect:/user/users";
    }

    @AuthMethod(right = EnumAuth.UserM)
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.GET)
    public String update(Model model, @PathVariable int id) {
        TUser c = userService.load(id);
        int classId = c.getTClass().getId();
        int roleId = c.getTRole().getId();
        List<TRole> roles = roleService.list();
        List<TClass> classs = classService.list();

        model.addAttribute(c);
        model.addAttribute("roles", roles);
        model.addAttribute("classs", classs);

        return "/user/update";
    }

    @AuthMethod(right = EnumAuth.UserM)
    @RequestMapping(value = {"/{id}/update"}, method = RequestMethod.POST)
    public String update(TUser user, @PathVariable int id) {
        System.out.println("员工更新:" + user);
        userService.update(user, user.getTClass().getId(), user.getTRole().getId());
        System.out.println("更新成功");
        return "redirect:/user/users";
    }


    //下面是基本IOC
    private IUserService userService;
    private IRightService rightService;
    private IClassService classService;
    private IRoleService roleService;

    @Resource
    public void setClassService(IClassService classService) {
        this.classService = classService;
    }

    @Resource
    public void setRoleService(IRoleService roleService) {
        this.roleService = roleService;
    }

    @Resource
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
    }

    @Resource
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

}
