package com.banking.projectserver.aop.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-12 17:12:28
 */
@Service
@Slf4j
public class SecurityManager {
    public static void doA() {
        log.warn("Request with Empty Argument");
    }
    public static void doB() {
        log.info("State: Normal");
    }
}

