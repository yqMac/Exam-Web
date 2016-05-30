package com.yqmac.exam.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yqmac.exam.service.*;
import com.yqmac.exam.util.StringUtil;
import com.yqmac.exam.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yqmac on 2016/4/26 0026.
 */
@Service("ajaxService")
public class AjaxService implements IAjaxService {

    private IRightService rightService;
    private IQuesPointService quesPointService;
    private IQuesBankService quesBankService;
    private IQuesLibService quesLibService;
    private IQuesService quesService;
    private IQuesTypeService quesTypeService;
    private IPaperStrategyServcie paperStrategyServcie;
    private IUserService userService;
    private IPaperUserService paperUserService;
    private IUserAnswerService userAnswerService;
    private IUserGradeService userGradeService;

    @Resource
    public void setUserGradeService(IUserGradeService userGradeService) {
        this.userGradeService = userGradeService;
    }

    @Resource
    public void setUserAnswerService(IUserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    @Resource
    public void setPaperUserService(IPaperUserService paperUserService) {
        this.paperUserService = paperUserService;
    }

    @Resource
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Resource
    public void setQuesBankService(IQuesBankService quesBankService) {
        this.quesBankService = quesBankService;
    }

    @Resource
    public void setPaperStrategyServcie(IPaperStrategyServcie paperStrategyServcie) {
        this.paperStrategyServcie = paperStrategyServcie;
    }

    @Resource
    public void setQuesTypeService(IQuesTypeService quesTypeService) {
        this.quesTypeService = quesTypeService;
    }

    @Resource
    public void setQuesService(IQuesService quesService) {
        this.quesService = quesService;
    }

    @Resource
    public void setQuesLibService(IQuesLibService quesLibService) {
        this.quesLibService = quesLibService;
    }

    @Resource
    public void setRightService(IRightService rightService) {
        this.rightService = rightService;
    }

    @Resource
    public void setQuesPointService(IQuesPointService quesPointService) {
        this.quesPointService = quesPointService;
    }


    @Override
    public String getRightCheckboxByBigIds(String bigRights) {
        String[] strs = bigRights.split(",");
        StringBuffer sbuf = new StringBuffer();
        //<input type="checkbox"  name ="sRs" value="1" /><br/>
        for (String str : strs) {
            int bID = Integer.valueOf(str);
            List<TRight> sRs = rightService.listSmallByBigId(bID);
            for (TRight r : sRs) {
                sbuf.append("<input type=\"checkbox\"  name =\"smalRs\" value=\"" + r.getId() + "\" />" + StringUtil.getFixedLenString(r.getRightName(), 10, ' ') + "&nbsp;");
            }
            sbuf.append("<br/>");
        }
        return sbuf.toString();
    }

    @Override
    public String getRightCheckboxByBigIds4Update(int roleId, String bigRights) {
        List<TRight> rs = rightService.listByRoleId(roleId);
        List<Integer> rint = new ArrayList<>();
        for (TRight r : rs) {
            rint.add(r.getId());
        }

        String[] strs = bigRights.split(",");
        StringBuffer sbuf = new StringBuffer();
        //<input type="checkbox"  name ="sRs" value="1" /><br/>
        for (String str : strs) {
            int bID = Integer.valueOf(str);
            List<TRight> sRs = rightService.listSmallByBigId(bID);
            for (TRight r : sRs) {
                sbuf.append("<input type=\"checkbox\"  name =\"smalRs\" value=\"" + r.getId() + "\" " + (rint.contains(r.getId()) ? "checked" : "") + " />" + StringUtil.getFixedLenString(r.getRightName(), 10, ' ') + "&nbsp;");
            }

            sbuf.append("<br/>");
        }
        return sbuf.toString();
    }

    @Override
    public String getPointOptionByLibId(int libId) {
        StringBuffer sbuf = new StringBuffer();
        //<option value="">==请选题库集==</option>
        List<TQuesPoint> points = quesPointService.listByLibId(libId);
        for (TQuesPoint p : points) {
            sbuf.append(String.format("<option value=\"%d\">%s</option>",
                    p.getId(), p.getPointName()));
        }
        return sbuf.toString();
    }

    @Override
    public String getPointOptionHeaderByLibId(int libId) {
        StringBuffer sbuf = new StringBuffer(String.format("<option value=\"%d\">%s</option>",
                -1, "所有"));
        //<option value="">==请选题库集==</option>
        List<TQuesPoint> points = quesPointService.listByLibId(libId);
        for (TQuesPoint p : points) {
            sbuf.append(String.format("<option value=\"%d\">%s</option>",
                    p.getId(), p.getPointName()));
        }
        return sbuf.toString();
    }

    @Override
    public String getLibOptionByBankId(int bankId) {
        StringBuffer sbuf = new StringBuffer();
        //<option value="">==请选题库集==</option>
        List<TQuesLib> libs = quesLibService.listByBankId(bankId);
        for (TQuesLib lib : libs) {
            sbuf.append(String.format("<option value=\"%d\">%s</option>",
                    lib.getId(), lib.getLibName()));
        }
        return sbuf.toString();
    }

    @Override
    public String getLibOptionHeaderByBankId(int bankId) {
        StringBuffer sbuf = new StringBuffer(String.format("<option value=\"%d\">%s</option>",
                -1, "所有"));
        //<option value="">==请选题库集==</option>
        List<TQuesLib> libs = quesLibService.listByBankId(bankId);
        for (TQuesLib lib : libs) {
            sbuf.append(String.format("<option value=\"%d\">%s</option>",
                    lib.getId(), lib.getLibName()));
        }
        return sbuf.toString();
    }


    @Override
    public String getTypeQuesJsonByBLPTs(int bankId, int libId, int pointId, String types) {
        String[] strs = types.split(",");
        List<TypeCount> tc = new ArrayList<>();
        int total = 0;
        for (String str : strs) {
            int typeId = Integer.valueOf(str);
            int count = 0;
            if (pointId != -1) {
                count = quesService.getQuesCountPointType(pointId, typeId);
            } else if (libId != -1) {
                count = quesService.getQuesCountLibType(libId, typeId);
            } else if (bankId != -1) {
                count = quesService.getQuesCountBankType(bankId, typeId);
            }
            total += count;
            tc.add(new TypeCount(typeId, count));
        }
        Map<String, Object> maps = new HashMap<>();
        maps.put("total", total);
        maps.put("list", tc);
        String reText = JSONObject.toJSONString(maps);

        return reText;
    }


    @Override
    public String getStrategyTypesByExamId(int examId) {

        List<TQuesType> types = paperStrategyServcie.getTypesByExamId(examId);
        List<Map<String, Object>> typedata = new ArrayList<>();
        List<Map<String, Object>> strategydata = new ArrayList<>();

        Map<String, Object> tmp = null;
        int[] typeIds = new int[types.size()];
        int index = 0;

        //获取类型信息
        for (TQuesType type : types) {
            tmp = new HashMap<>();
            tmp.put("id", type.getId());
            tmp.put("typeName", type.getTypeName());
            typeIds[index++] = type.getId();
            typedata.add(tmp);
        }
        //获取数量信息
        List<Object[]> sss = paperStrategyServcie.getStrategyDetailByExamId(examId, typeIds);

        for (Object[] ss : sss) {
            tmp = new HashMap<>();
            tmp.put("bankName", quesBankService.loadById((Integer) ss[0]).getBankName());
            tmp.put("libName", quesLibService.load((Integer) ss[1]).getLibName());
            tmp.put("pointName", quesPointService.load((Integer) ss[2]).getPointName());

            for (int i = 3; i < ss.length; i++) {
                tmp.put("type_" + String.valueOf(typeIds[i - 3]), ss[i]);
            }
            strategydata.add(tmp);
        }

        tmp = new HashMap<>();
        tmp.put("typedata", typedata);
        tmp.put("strategydata", strategydata);
        String str = JSONObject.toJSONString(tmp);
        System.out.println(sss.size() + "--" + str);
        return str;
    }

    @Override
    public String getQuesByExamId(int examId) {
        List<TQues> queses = quesService.listByExamId(examId);
        List<QuesBean> beans = new ArrayList<>();
        for (TQues quese : queses) {
            QuesBean qb = new QuesBean(quese);
            beans.add(qb);
        }

        String str = JSONObject.toJSONString(beans);
        System.out.println(str);
        str = JSON.toJSONString(beans);
        System.out.println(str);
        return str;
    }

    @Override
    public String getUserOptionsByClassId(int classId) {

        List<TUser> users = userService.listByOragan(classId);

        StringBuffer sbuf = new StringBuffer(String.format("<option value=\"%d\">%s</option>",
                0, "所有"));
        for (TUser c : users) {
            sbuf.append(String.format("<option value=\"%d\">%s</option>",
                    c.getId(), c.getUsername()));
        }
        return sbuf.toString();
    }


    @Override
    public String delExamUsers(int examId, int classId, int userId) {

        if (userId != 0) {
            paperUserService.delete(examId, userId);
        } else if (classId != 0) {
            paperUserService.deleteClass(examId, classId);
        } else {
            paperUserService.deleteByExamId(examId);
        }
        int count = paperUserService.getUserCountByExamId(examId);
        return String.valueOf(count);
    }

    @Override
    public String addExamUsers(int examId, int classId, int userId) {
        if (userId != 0) {
            paperUserService.add(examId, userId);
        } else if (classId != 0) {
            paperUserService.addClass(examId, classId);
        } else {
            paperUserService.addAllUser(examId);
        }
        int count = paperUserService.getUserCountByExamId(examId);
        return String.valueOf(count);
    }

    @Override
    public String loadOne(int examId, int typeId) {
        TUserAnswer answer = userAnswerService.loadOne(examId, typeId);
        System.out.println(answer);
        if(answer==null){
            return null;
        }

        AnswerBean bean = new AnswerBean();
        bean.setAnswerId(answer.getId());
        bean.setExamId(answer.getTExam().getId());
        bean.setExamName(answer.getTExam().getExamName());
        bean.setTypeId(answer.getTPaperQues().getTypeId());

        bean.setQuesContent(answer.getTPaperQues().getQuesContent());
        bean.setQuesAnswer(answer.getTPaperQues().getAnswer());
        bean.setQuesScore(answer.getTPaperQues().getScore());

        bean.setUserUserName(answer.getTUserGrade().getTUser().getUsername());
        bean.setUserNickName(answer.getTUserGrade().getTUser().getNickname());
        bean.setUserAnswer(answer.getAnswer());


        return JSONObject.toJSONString(bean);
        //return userAnswerDao.loadOne(examId,typeId);
    }

    @Override
    public String markOne(int answerId, float score) {
        TUserAnswer ans = userAnswerService.load(answerId);
        if (ans == null) return "{msg:\"问题不存在\"}";

        System.out.println(answerId + "-->" + ans.getId());

        TUserGrade grade = ans.getTUserGrade();
        grade.setGrade(grade.getGrade()-ans.getGradeAuto()-ans.getGradeHandle()+score);

        ans.setGradeAuto(0f);
        ans.setGradeHandle(score);
        ans.setNeedhandle(0);

        userGradeService.update(grade);
        userAnswerService.update(ans);

        return "{msg:\"更新成功\"}";
    }

    class AnswerBean {
        private int examId;
        private int typeId;
        private int answerId;
        private String examName;

        private String quesContent;
        private String quesAnswer;
        private float quesScore;

        private String userUserName;
        private String userNickName;
        private String userAnswer;


        public int getExamId() {
            return examId;
        }

        public void setExamId(int examId) {
            this.examId = examId;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getAnswerId() {
            return answerId;
        }

        public void setAnswerId(int answerId) {
            this.answerId = answerId;
        }

        public String getExamName() {
            return examName;
        }

        public void setExamName(String examName) {
            this.examName = examName;
        }

        public String getQuesContent() {
            return quesContent;
        }

        public void setQuesContent(String quesContent) {
            this.quesContent = quesContent;
        }

        public String getQuesAnswer() {
            return quesAnswer;
        }

        public void setQuesAnswer(String quesAnswer) {
            this.quesAnswer = quesAnswer;
        }

        public float getQuesScore() {
            return quesScore;
        }

        public void setQuesScore(float quesScore) {
            this.quesScore = quesScore;
        }

        public String getUserUserName() {
            return userUserName;
        }

        public void setUserUserName(String userUserName) {
            this.userUserName = userUserName;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getUserAnswer() {
            return userAnswer;
        }

        public void setUserAnswer(String userAnswer) {
            this.userAnswer = userAnswer;
        }
    }

    class QuesBean {
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getPointId() {
            return pointId;
        }

        public void setPointId(int pointId) {
            this.pointId = pointId;
        }

        public String getQuesContent() {
            return quesContent;
        }

        public void setQuesContent(String quesContent) {
            this.quesContent = quesContent;
        }

        public Float getScore() {
            return score;
        }

        public void setScore(Float score) {
            this.score = score;
        }

        public Integer getDiffDegr() {
            return diffDegr;
        }

        public void setDiffDegr(Integer diffDegr) {
            this.diffDegr = diffDegr;
        }

        public String getOptions() {
            return options;
        }

        public void setOptions(String options) {
            this.options = options;
        }

        public Integer getOptionNum() {
            return optionNum;
        }

        public void setOptionNum(Integer optionNum) {
            this.optionNum = optionNum;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Timestamp getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Timestamp createTime) {
            this.createTime = createTime;
        }

        private Integer id;
        private int typeId;
        private int pointId;
        private String quesContent;
        private Float score;
        private Integer diffDegr;
        private String options;
        private Integer optionNum;
        private String answer;
        private Timestamp createTime;

        public QuesBean() {

        }

        public QuesBean(TQues q) {
            this.id = q.getId();
            this.typeId = q.getTQuesType().getId();
            this.pointId = q.getTQuesPoint().getId();
            this.quesContent = q.getQuesContent();
            this.score = q.getScore();
            this.diffDegr = q.getDiffDegr();
            this.options = q.getOptions();
            this.optionNum = q.getOptionNum();
            this.answer = q.getAnswer();
            this.createTime = q.getCreateTime();
        }

    }

    class TypeCount {
        private int typeId;
        private int count;

        public TypeCount() {
        }

        public int getTypeId() {
            return typeId;
        }

        public void setTypeId(int typeId) {
            this.typeId = typeId;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public TypeCount(int typeId, int count) {
            this.typeId = typeId;
            this.count = count;
        }
    }
}
