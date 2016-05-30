package com.yqmac.exam.auth;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 有该Annotation的Controller，都需要验证
 * Created by yqmac on 2016/5/20 0020.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthClass {

}
