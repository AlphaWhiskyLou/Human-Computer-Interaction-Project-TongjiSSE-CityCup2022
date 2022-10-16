package com.banking.projectserver.aop.log;
import java.lang.annotation.*;
/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-18 18:15:02
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageLog {
    String desc() default "";
}