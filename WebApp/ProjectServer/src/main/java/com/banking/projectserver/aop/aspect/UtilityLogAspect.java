package com.banking.projectserver.aop.aspect;

import com.banking.projectserver.aop.manager.AssistanceManager;
import com.banking.projectserver.aop.manager.UtilityManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.weaver.bcel.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-19 22:54:53
 */
@Aspect
@Component
@Slf4j
public class UtilityLogAspect {
    private ObjectMapper objectMapper;


    @Autowired
    public UtilityLogAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController) && @annotation(com.banking.projectserver.aop.log.UtilityLog)")
    public void utilityLog() {
    }

    @Before("utilityLog()")
    public void doBefore(JoinPoint joinPoint) throws JsonProcessingException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        log.info("Request URL: {}, Method: {}, IP:{}, Args:{}",
                request.getRequestURI(), request.getMethod(), request.getRemoteAddr(), objectMapper.writeValueAsString(joinPoint.getArgs()));

        if (joinPoint.getArgs()[0].toString().contains(" ")) {
            UtilityManager.doAbnormal();
        } else {
            UtilityManager.doNormal();
        }
    }

    @Around("utilityLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();

        Object result = null;
        if (joinPoint.getArgs()[0].toString().contains(" ")) {
            UtilityManager.doAbnormal();
        } else {
            UtilityManager.doAbnormal();
            result = joinPoint.proceed();
        }
        log.info("Time Elapsed: {} ms", stopwatch.elapsed(TimeUnit.MILLISECONDS));
        return result;
    }
}