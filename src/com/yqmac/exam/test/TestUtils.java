package com.yqmac.exam.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yqmac on 2016/4/21 0021.
 */
public class TestUtils {

    @Test
    public void testSFormat(){
        System.out.println(String.format("123%s%d","123",2));

    }

    @Test
    public void test03(){
        String str ="9|3|3|3|3|选项以选项2选项3";
        List<String > options = null;
        String[] oparr = str.split("\\|");
        if(oparr.length<3)return ;
        int le=Integer.valueOf(oparr[0]);
        String opstr = str.substring(str.length() - le);
        System.out.println(opstr);

        int count = Integer.valueOf(oparr[1]);
        options = new ArrayList<>();
        int lll=0;
        for (int i = 0; i < count; i++) {
            int tmp = Integer.valueOf(oparr[2+ i]);
            options.add(opstr.substring(lll,lll+tmp));

            lll += tmp;
        }
        System.out.println("===============");
        for (String option : options) {
            System.out.println(option);
        }
    }


    @Test
    public void testJson(){
        String a ="990123:4sdjkf";
        Map<String, String> ds = new HashMap<>();
        ds.put("ddd", a);
        List<String> strs = new ArrayList<>();
        strs.add("12,3");
        strs.add("324");
        System.out.println(JSON.toJSONString(strs));
    }
}
