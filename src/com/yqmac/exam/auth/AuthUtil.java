package com.yqmac.exam.auth;

import com.yqmac.exam.service.IRightService;
import com.yqmac.exam.vo.TRight;
import com.yqmac.exam.vo.TUser;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FilenameFilter;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by yqmac on 2016/5/20 0020.
 */
public class AuthUtil {

    /**
     * 获取每个权限所能访问的方法结合
     * @param pname
     * @return
     */
    public static Map<EnumAuth,Set<String >> initAuthRightMethod(String pname){
        //方法结果存储
        Map<EnumAuth, Set<String>> auths = new HashMap<>();
        //获取包名下类名
        String[] ps = getClassByPackage(pname);
        //遍历每个类
        for (String p : ps) {
            //package + Class 完整路径名称
            String pc = pname + "." + p.substring(0, p.lastIndexOf(".class"));
            try {
                //根据完整路径名称反射生成类结构
                Class clz = Class.forName(pc);
                //类是否需要验证【根据是否有AuthClass的Annotation来区分】
                if(!clz.isAnnotationPresent(AuthClass.class))continue;
                //获取类下的所有自己定义的方法
                Method[] ms = clz.getDeclaredMethods();
                for (Method m : ms) {
                    if(!m.isAnnotationPresent(AuthMethod.class)) continue;
                    AuthMethod am = m.getAnnotation(AuthMethod.class);
                    Set<String> methods = auths.get(am.right());
                    if (methods == null) {
                        methods = new HashSet<>();
                        auths.put(am.right(), methods);
                    }
                    methods.add(pc + "." + m.getName());
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return auths;
    }

    /**
     * 遍历包名下的所有文件，返回以‘.class’结尾的文件的文件名数组
     * @param pname
     * @return
     */
    private static String [] getClassByPackage(String pname){
        String pr = pname.replace(".", "/");
        String pp = AuthUtil.class.getClassLoader().getResource(pr).getPath();

        File file = new File(pp);
        String [] fs =file.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if(name.endsWith(".class"))return true;
                return false;
            }
        });
        return fs ;
    }

    /**
     * 获取用户可以访问的所有方法
     * @param c
     * @param rightService
     * @param session
     * @return
     */
    public static  Set<String> getAllActions(TUser c, IRightService rightService, HttpSession session) {
        Set<String> actions = new HashSet<>();
        Map<EnumAuth, Set<String>> allAuths = (Map<EnumAuth, Set<String>>) session.getServletContext().getAttribute("allAuths");
        Set<String> tmp = null;
        tmp = allAuths.get(EnumAuth.Base);
        if (tmp != null)
            actions.addAll(tmp);
        tmp = allAuths.get(EnumAuth.Logined);
        if (tmp != null)
            actions.addAll(tmp);
        List<TRight> rights = rightService.listByRoleId(c.getTRole().getId());
        for (TRight right : rights) {
            EnumAuth ea = EnumAuth.valueof(right.getId());
            if (ea != null) {
                tmp = allAuths.get(ea);
                if (tmp != null)
                    actions.addAll(tmp);
            }
        }
        return actions;
    }
}
