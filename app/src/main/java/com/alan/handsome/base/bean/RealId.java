package com.alan.handsome.base.bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 类说明：greendao 注解数据表的真正意义的主键
 * 作者：qiujialiu
 * 时间：2018/12/21
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RealId {
}
