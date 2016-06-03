package com.yqmac.exam.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by yqmac on 2016/4/21 0021.
 */
public class StringUtil {

    public static Random ran = new Random();

    /**
     * 封装选项数组
     * @param options
     * @return
     */
    public static String options2String(String []options) {
        if (options == null) {
            return "";
        }
        StringBuffer optionD = new StringBuffer();
        StringBuffer numD = new StringBuffer();
        numD.append("|" + options.length+ "|");
        for (String optiontext : options) {
            optionD.append(optiontext);
            numD.append(optiontext.length() + "|");
        }
        String optotal=optionD.length()+numD.toString()+optionD.toString();
        return optotal;
    }

    /**
     * 拆分选项为数组
     * @param optionStr
     * @return
     */
    public static List<String > string2Options(String optionStr){
        List<String > options = null;
        String[] oparr = optionStr.split("\\|");
        if(oparr.length<3) return null;
        int contentLength=Integer.valueOf(oparr[0]);
        String optionsContent = optionStr.substring(optionStr.length() - contentLength);

        int optionsCount = Integer.valueOf(oparr[1]);
        options = new ArrayList<>();
        int lll=0;
        for (int i = 0; i < optionsCount; i++) {
            int tmp = Integer.valueOf(oparr[2+ i]);
            options.add(optionsContent.substring(lll,lll+tmp));
            lll += tmp;
        }
        return options;
    }

    /**
     * 打印Json字符串到页面
     * @param response
     * @param str
     */
    public static  void printStrRespone(HttpServletResponse response, String str ){
        PrintWriter out =null;
        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");
            out = response.getWriter();
            out.print(str);
            out.flush();
        }catch (IOException ex ){

        }finally {
            if(out!=null)out.close();
        }
    }

    /**
     * 使用指定字符填充字符串到指定长度
     * @param str
     * @param len
     * @param c
     * @return
     */
    public static  String getFixedLenString(String str, int len, char c) {
        if (str == null || str.length() == 0) {
            str = "";
        }
        if (str.length() == len) {
            return str;
        }
        if (str.length() > len) {
             return  str.substring(0, len);
        }
        StringBuilder sb = new StringBuilder(str);
        while (sb.length() < len) {
            sb.append(c);
        }
        return sb.toString();
    }

    public static String toUtf8String(String s){
        StringBuffer sb = new StringBuffer();
        for (int i=0;i<s.length();i++){
            char c = s.charAt(i);
            if (c >= 0 && c <= 255){sb.append(c);}
            else{
                byte[] b;
                try { b = Character.toString(c).getBytes("utf-8");}
                catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
    public static  Date getSysDate(){
        return new Date();
    }
    public static  String getSysDateString(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
}
