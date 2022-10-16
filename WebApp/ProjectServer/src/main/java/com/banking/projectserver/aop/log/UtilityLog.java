package com.banking.projectserver.aop.log;

import java.lang.annotation.*;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-19 22:51:15
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UtilityLog {
    String desc() default "";
}