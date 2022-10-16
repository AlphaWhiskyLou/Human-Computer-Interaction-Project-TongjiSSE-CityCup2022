package com.banking.projectserver.aop.log;

import java.lang.annotation.*;
/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-12 17:10:40
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebLog {
    String desc() default "";
}