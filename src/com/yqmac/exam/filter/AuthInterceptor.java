package com.yqmac.exam.filter;

import com.yqmac.exam.auth.EnumAuth;
import com.yqmac.exam.ex.ExamException;
import com.yqmac.exam.vo.TUser;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yqmac on 2016/5/20 0020.
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod hm = (HandlerMethod) handler;
        String target = hm.getBean().getClass().getName()+"."+hm.getMethod().getName();

        HttpSession session = request.getSession();
        Map<EnumAuth,Set<String >> authMap= (Map<EnumAuth, Set<String>>) session.getServletContext().getAttribute("allAuths");

        TUser c = (TUser) session.getAttribute("loginUser");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (c == null) {
            Set<String> tmp = new HashSet<>();
            if(authMap.get(EnumAuth.Base)!=null) tmp.addAll(authMap.get(EnumAuth.Base));
            if(authMap.get(EnumAuth.UnLogined)!=null) tmp.addAll(authMap.get(EnumAuth.UnLogined));
            if (!tmp.contains(target)) {
                response.sendRedirect(request.getContextPath() + "/user/login");
                return super.preHandle(request, response, handler);
            }
        }else if( isAdmin==null || !isAdmin) {
            Set<String> allAction = (Set<String>) session.getAttribute("allAction");
            if(allAction==null  || !allAction.contains(target)){
                throw new ExamException("权限不足，不能使用该操作");
            }
        }
        return super.preHandle(request, response, handler);
    }

}
