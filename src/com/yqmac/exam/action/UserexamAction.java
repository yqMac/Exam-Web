package com.yqmac.exam.action;

import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.service.IExamService;
import com.yqmac.exam.vo.TUser;
import com.yqmac.exam.vo.TExam;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 考生可以参加考试
 * Created by yqmac on 2016/5/5 0005.
 */
@Controller("userexamAction")
@RequestMapping(value = {"/userexam"})
@AuthClass
public class UserexamAction {

    /**
     * 跳转到列表页
     *
     * @return
     */
    private String redirect() {
        return "redirect:/userexam/exams";
    }

    @AuthMethod(right = EnumAuth.Logined)
    @RequestMapping(value = {"/exams", "/list"}, method = RequestMethod.GET)
    public String list(Model model, HttpServletRequest request, HttpServletResponse respone) {
        TUser user = (TUser) request.getSession().getAttribute("loginUser");
        if (user == null) {
            throw new ExamException("用户未登录，无法使用该功能");
        }
        List<TExam> exams = examService.listByUserId(user.getId());
       model.addAttribute("exams", exams);
        return "/userexam/list";
    }

    @AuthMethod(right = EnumAuth.ExamM)
    @RequestMapping(value = {"/{id}/delete"}, method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        examService.delete(id);
        return redirect();
    }

    @AuthMethod(right = EnumAuth.Logined)
    @RequestMapping(value = {"/{id}/detail"},method = RequestMethod.GET)
    public String detail(@PathVariable int id, Model model  ) {
        TExam e = examService.load(id );
        model.addAttribute("exam", e);
        return "/userexam/detail";
    }

    private IExamService examService;
    @Resource
    public void setExamService(IExamService examService) {
        this.examService = examService;
    }

}
