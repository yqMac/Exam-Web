package com.yqmac.exam.action;

import com.yqmac.exam.service.IAjaxService;
import com.yqmac.exam.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yqmac on 2016/4/26 0026.
 */
@Controller("ajaxAction")
@RequestMapping(value = "/ajax")
public class AjaxAction {

    //ajax
    @RequestMapping(value = {"/getLibOptionByBankId"}, method = RequestMethod.POST)
    public String getLibOptionByBankId(int bankId, HttpServletResponse response) {
        String str = ajaxService.getLibOptionByBankId(bankId);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @RequestMapping(value = {"/getLibOptionHeaderAllByBankId"}, method = RequestMethod.POST)
    public String getLibOptionHeaderAllByBankId(int bankId, HttpServletResponse response) {

        String str = ajaxService.getLibOptionByBankId(bankId);
        //str=String.format("<option value=\"%d\">%s</option>",
        //        -1, "所有")+str;
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @RequestMapping(value = {"/getPointOptionByLibId"}, method = RequestMethod.POST)
    public String getPointOptionByLibId(int libId, HttpServletResponse response) {
        String str = ajaxService.getPointOptionByLibId(libId);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @RequestMapping(value = {"/getPointOptionHeaderAllByLibId"}, method = RequestMethod.POST)
    public String getPointOptionHeaderAllByLibId(int libId, HttpServletResponse response) {
        String str = ajaxService.getPointOptionByLibId(libId);
        //str=String.format("<option value=\"%d\">%s</option>",
        //        -1, "所有")+str;
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @RequestMapping(value = {"/getRightCheckboxByBigIds"}, method = RequestMethod.POST)
    public String getRightCheckboxByBigIds(String strparam, HttpServletResponse response) {
        String str = ajaxService.getRightCheckboxByBigIds(strparam);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @RequestMapping(value = {"/ajaxRightCheckboxByBigIds4Update"}, method = RequestMethod.POST)
    public String ajaxRightCheckboxByBigIds4Update(int id, String strparam, HttpServletResponse response) {
        String str = ajaxService.getRightCheckboxByBigIds4Update(id, strparam);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @RequestMapping(value = {"/getTypeQuesJsonByBLPTs"}, method = RequestMethod.POST)
    public String getTypeQuesJsonByBLPTs(int bankId, int libId, int pointId, String strparam1, HttpServletResponse response) {
        System.out.println(bankId + "-" + libId + "-" + pointId + "-" + strparam1);
        String str = ajaxService.getTypeQuesJsonByBLPTs(bankId, libId, pointId, strparam1);
        System.out.println(str);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @RequestMapping(value = {"/getStrategyTypesByExamId"}, method = RequestMethod.POST)
    public String getStrategyTypesByExamId(int examId, HttpServletResponse response) {
        System.out.println("ajax-->" + examId);
        String str = ajaxService.getStrategyTypesByExamId(examId);
        //System.out.println(str);
        StringUtil.printStrRespone(response, str);

        return null;
    }

    @RequestMapping(value = {"/getUserOptionsByClassId"}, method = RequestMethod.POST)
    public String getUserOptionsByClassId(int classId, HttpServletResponse response) {

        String str = ajaxService.getUserOptionsByClassId(classId);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @RequestMapping(value = {"/delExamUsers"}, method = RequestMethod.POST)
    public void delExamUsers(Integer examId, Integer classId, Integer userId, HttpServletResponse response) {
        System.out.println("删除： " + examId + "--" + classId + "--" + userId);
        String str = ajaxService.delExamUsers(examId, classId, userId);
        StringUtil.printStrRespone(response, str);
    }

    @RequestMapping(value = {"/addExamUsers"}, method = RequestMethod.POST)
    public void addExamUsers(Integer examId, Integer classId, Integer userId, HttpServletResponse response) {
        System.out.println("增加： " + examId + "--" + classId + "--" + userId);
        String str = ajaxService.addExamUsers(examId, classId, userId);
        StringUtil.printStrRespone(response, str);
    }

    @RequestMapping(value = {"/getNextUserAnswer"}, method = RequestMethod.POST)
    public void getNextUserAnswer(Integer examId, Integer typeId,HttpServletResponse response) {
        if (examId == null || examId <= 0) return;

        String str = ajaxService.loadOne(examId, typeId);
        StringUtil.printStrRespone(response,str);

    }

    @RequestMapping(value = {"/markOneAnswer"},method = RequestMethod.POST)
    public void markOneAnswer(Integer answerId, Float score, HttpServletResponse response) {
        if(answerId==null || score<0)return ;

        String str = ajaxService.markOne(answerId, score);
        StringUtil.printStrRespone(response,str);
    }

    private IAjaxService ajaxService;

    @Resource
    public void setAaxService(IAjaxService ajaxService) {
        this.ajaxService = ajaxService;
    }
}
