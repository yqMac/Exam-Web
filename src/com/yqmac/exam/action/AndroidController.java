package com.yqmac.exam.action;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yqmac.exam.Beans.Exam;
import com.yqmac.exam.Beans.Grade;
import com.yqmac.exam.Beans.Ques;
import com.yqmac.exam.Beans.QuesType;
import com.yqmac.exam.auth.AuthClass;
import com.yqmac.exam.auth.AuthMethod;
import com.yqmac.exam.auth.AuthUtil;
import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.service.*;
import com.yqmac.exam.util.StringUtil;
import com.yqmac.exam.vo.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yqmac on 2016/5/24 0024.
 */
@Controller()
@RequestMapping(value = {"/android"})
@AuthClass
public class AndroidController {
    /**
     * 用于Andoird端的登录
     *
     * @param username
     * @param password
     * @param session
     * @param response
     * @return
     */
    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(String username, String password, HttpSession session, HttpServletResponse response) {
        Map<String, String> result = new HashMap<>();
        TUser c = userService.login(username, password);
        if (c == null) {
            result.put("code", "201601");
            result.put("msg", "用户名或者密码不正确");
        } else {
            session.setAttribute("loginUser", c);
            session.setAttribute("isAdmin", ("超级管理员".equals(c.getTRole().getRoleName())));
            session.setAttribute("allAction", AuthUtil.getAllActions(c, rightService, session));

            result.put("code", "201600");
            result.put("msg", "登录成功");
            result.put("userId", c.getId().toString());
        }
        String str = JSONObject.toJSONString(result);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    /**
     * 用于Android端获取考试列表
     *
     * @param session
     * @param response
     * @return
     */
    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/exams"}, method = RequestMethod.GET)
    public String exams(HttpSession session, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        TUser loginUser = null;

        if ((loginUser = prehandle(result, session)) != null) {

            List<TExam> exams = examService.listByUserId(loginUser.getId());

            List<Exam> redata = new ArrayList<>();
            for (TExam exam : exams) {
                Exam e = new Exam();
                e.id = exam.getId();
                e.name = exam.getExamName();
                e.begainTime = exam.getBegaintime().toString();
                e.endTime = exam.getEndtime().toString();
                e.detail = "详细信息";


                TUserGrade grade = userGradeService.loadByUE(loginUser.getId(), exam.getId());
                if (grade == null) {
                    e.status = 1;
                } else {
                    e.status = 2;
                }
                redata.add(e);
            }
            result.put("data", redata);
        }
        String str = JSONObject.toJSONString(result);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    /**
     * 用于Andorid端查看某id考试的详情
     *
     * @param id
     * @param session
     * @param response
     * @return
     */
    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/exam/{id}/detail"}, method = RequestMethod.GET)
    public String detail(@PathVariable int id, HttpSession session, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        TUser loginUser = null;
        if ((loginUser = prehandle(result, session)) != null) {
            List<Map<String, String>> redata = new ArrayList<>();
            TExam exam = examService.load(id);
            add(redata, "考试名称：", exam.getExamName());
            add(redata, "开始时间：", exam.getBegaintime().toString());
            add(redata, "结束时间：", exam.getEndtime().toString());
            add(redata, "考试时长：", exam.getExamTime() == 0 ? "不限时间" : (exam.getExamTime().toString() + " 分钟"));
            add(redata, "查看答案：", ((exam.getSeepaper() == null) || (exam.getSeepaper() == 0) ? "不" : "") + "允许查看试卷");
            add(redata, "保存试卷：", exam.getSavepaper() == 0 ? "不保存试卷" : (exam.getSavepaper() == 1 ? "交卷自动保存" : "随时手动保存"));
            add(redata, "考试次数：", "最多 " + exam.getEntermaxtimes() + " 次");
            add(redata, "题目乱序：", exam.getIsorder() == 0 ? "不乱序" : "乱序");
            add(redata, "移出限制：", exam.getMoveouttimes() == -1 ? "不限次数" : ("最多 " + exam.getEntermaxtimes() + " 次"));
            add(redata, "显示方式：", exam.getShowtype() == 2 ? "一屏一题" : (exam.getShowtype() == 1 ? "一屏一题型" : "一屏所有题"));
            add(redata, "考试规模：", exam.getIspartuser() == 1 ? "部分用户" : "全部用户");
            result.put("data", redata);
        }
        String str = JSONObject.toJSONString(result);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    private void add(List<Map<String, String>> data, String key, String value) {
        Map<String, String> edata = new HashMap<>();
        edata.put("key", key);
        edata.put("value", value);
        data.add(edata);
    }


    /**
     * 用户信息的基本验证
     *
     * @param result
     * @param session
     * @return
     */
    private TUser prehandle(Map<String, Object> result, HttpSession session) {
        TUser loginUser = (TUser) session.getAttribute("loginUser");
        if (loginUser == null) {
            result.put("code", "201601");
            result.put("msg", "用户尚未登录,请重新登录后重试");
            return null;
        } else {
            result.put("code", "201600");
            result.put("msg", "操作成功");
            return loginUser;
        }
    }


    /**
     * 开始考试后首先获取有哪些类型的题目
     *
     * @param id
     * @param session
     * @param response
     * @return
     */
    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/exam/{id}/init"}, method = RequestMethod.GET)
    public String initexam(@PathVariable int id, HttpSession session, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        TUser loginUser = null;

        if ((loginUser = prehandle(result, session)) != null) {
            List<TQuesType> types = paperStrategyServcie.getTypesByExamId(id);
            List<QuesType> data = new ArrayList<>();
            for (TQuesType type : types) {
                data.add(new QuesType(type.getId(), type.getTypeName()));
            }
            result.put("data", data);
        }
        //初始化成绩
        TUserGrade grade = userGradeService.loadByUE(loginUser.getId(), id);
        if (grade == null) {
            grade = new TUserGrade();
            grade.setTExam(examService.load(id));
            grade.setBegaintime(new Timestamp(System.currentTimeMillis()));
            grade.setTUser(loginUser);
            grade.setGrade(0f);
            grade.setGradeObjective(0f);
            grade.setGradeSubjective(0f);
            grade = userGradeService.add(grade);
        }
        String str = JSONObject.toJSONString(result);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"{examId}/{typeId}/queses"}, method = RequestMethod.GET)
    public String queses(@PathVariable int examId, @PathVariable int typeId, HttpSession session, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        TUser loginUser = null;
        if ((loginUser = prehandle(result, session)) != null) {
            List<Ques> data = new ArrayList<>();
            List<TPaperQues> queses = paperQuesService.listByExamTypeId(examId, typeId);
            for (TPaperQues quese : queses) {
                data.add(new Ques(quese));
            }
            result.put("data", data);
        }
        String str = JSONObject.toJSONString(result);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/exam/{id}/submit"}, method = RequestMethod.POST)
    public String submitexam(@PathVariable int id, String datas, HttpSession session, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        TUser loginUser = null;


        if ((loginUser = prehandle(result, session)) != null) {

            List<Ques> queses = new ArrayList<>();

            JSONArray arr = (JSONArray) JSONArray.parse(datas);
            for (int i = 0; i < arr.size(); i++) {
                String s = (String) arr.get(i);
                JSONObject obj = JSONObject.parseObject(s);
                queses.add(new Ques(obj.getIntValue("id"), obj.getString("ans"), obj.getInteger("typeId")));
            }
            if (!androidService.subAns(id, loginUser.getId(), queses)) {
                result.put("msg", "提交失败");
            }
        }
        String str = JSONObject.toJSONString(result);
        StringUtil.printStrRespone(response, str);
        return null;
    }

    @AuthMethod(right = EnumAuth.Base)
    @RequestMapping(value = {"/grades"}, method = RequestMethod.GET)
    public String grades(HttpSession session, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        TUser loginUser = null;

        if ((loginUser = prehandle(result, session)) != null) {
            List<Grade> data = new ArrayList<>();
            List<TUserGrade> grades = userGradeService.listByUserId(loginUser.getId());

            for (TUserGrade grade : grades) {
                Grade g = new Grade();
                g.setExamName(grade.getTExam().getExamName());
                g.setGrade(String.valueOf(grade.getGrade()));
                g.setBegainTime(String.valueOf(grade.getBegaintime()));
                g.setEndTime(String.valueOf(grade.getEndtime()));
                int count = userAnswerService.hanldAnsCount(grade.getId());
                if (count > 0) {
                    g.setStatus(2);

                } else g.setStatus(1);
                data.add(g);
            }
            result.put("data", data);
        }
        String str = JSONObject.toJSONString(result);
        StringUtil.printStrRespone(response, str);
        return null;
    }


    @Resource
    private IUserService userService;
    @Resource
    private IRightService rightService;
    @Resource
    private IExamService examService;
    @Resource
    private IAndroidService androidService;
    @Resource
    private IPaperStrategyServcie paperStrategyServcie;
    @Resource
    private IPaperQuesService paperQuesService;
    @Resource
    private IUserGradeService userGradeService;
    @Resource
    private IUserAnswerService userAnswerService;


    //@AuthMethod(right = EnumAuth.Base)
    //@RequestMapping(value = {"/exam/{id}/init"},method = RequestMethod.GET)
    //public String initexam(@PathVariable int id, HttpSession session, HttpServletResponse response) {
    //    Map<String, Object> result = new HashMap<>();
    //    TUser loginUser =null;
    //
    //    if((loginUser=prehandle(result,session))!=null){
    //        List<Exam> data = new ArrayList<>();
    //
    //        result.put("data", data);
    //    }
    //    String str = JSONObject.toJSONString(result);
    //    StringUtil.printStrRespone(response,str );
    //    return null;
    //}

}
