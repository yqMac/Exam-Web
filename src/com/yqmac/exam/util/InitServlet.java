package com.yqmac.exam.util;

import com.yqmac.exam.auth.AuthUtil;
import com.yqmac.exam.auth.EnumAuth;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yqmac on 2016/5/20 0020.
 */
public class InitServlet extends HttpServlet {


    public static WebApplicationContext wc ;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        //初始化Spring工程
        wc = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        //初始化权限信息

        Map<EnumAuth, Set<String>> auths = AuthUtil.initAuthRightMethod("com.yqmac.exam.action");
        this.getServletContext().setAttribute("allAuths", auths);

        System.out.println("--------系统初始化成功 ["+auths+"] -------------------");

    }
}
