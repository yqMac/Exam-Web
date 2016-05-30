package com.yqmac.exam.Beans;


import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.yqmac.exam.util.StringUtil;
import com.yqmac.exam.vo.TPaperQues;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yqmac on 2016/5/27 0027.
 */
public class Ques {

    private int id;
    private int examId;

    private String content;
    private String answer;


    private String optionStr;
    private int optionNum;
    private String[] options;

    private String userAnswer;
    private List<Integer> userAnswerIndex = new ArrayList<>();

    private int typeId;
    private String typeName;

    public Ques() {

    }
    public Ques(int id ,String ans,int typeId) {
        this.id = id;
        this.userAnswer = ans;
        this.typeId = typeId;
    }
    public Ques(TPaperQues ques ){
        this.id = ques.getId();
        this.content = ques.getQuesContent();
        this.optionNum = ques.getOptionNum();
        this.optionStr = ques.getOptions();
        this.answer = ques.getAnswer();
        this.typeId = ques.getTypeId();
        this.typeName = "";
    }
    public Ques(JSONObject json) {
        try {
            this.id = json.getIntValue("id");
            this.content = json.getString("content");
            this.optionNum = json.getIntValue("optionNum");
            this.optionStr = json.getString("options");
            if (optionStr.length() > 0) {
                this.options = (String[]) StringUtil.string2Options(optionStr).toArray();
            }
            this.answer = json.getString("answer");
            this.typeId = json.getIntValue("typeId");
            this.typeName = json.getString("typeName");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<Integer> getUserAnswerIndex() {
        return userAnswerIndex;
    }

    public void setUserAnswerIndex(List<Integer> userAnswerIndex) {
        this.userAnswerIndex = userAnswerIndex;
    }

    public String getOptionStr() {
        return optionStr;
    }

    public void setOptionStr(String optionStr) {
        this.optionStr = optionStr;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getOptionNum() {
        return optionNum;
    }

    public void setOptionNum(int optionNum) {
        this.optionNum = optionNum;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
