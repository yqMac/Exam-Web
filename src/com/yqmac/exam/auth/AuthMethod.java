package com.yqmac.exam.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 该添加了该Annotation的方法都需要验证权限
 * Created by yqmac on 2016/5/20 0020.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthMethod {

    public EnumAuth right() default EnumAuth.Logined;
}
