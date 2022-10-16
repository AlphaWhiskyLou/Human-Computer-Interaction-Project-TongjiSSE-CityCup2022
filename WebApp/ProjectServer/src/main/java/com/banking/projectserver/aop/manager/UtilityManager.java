package com.banking.projectserver.aop.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-19 22:52:17
 */
@Service
@Slf4j
public class UtilityManager {
    public static void doAbnormal() {
        log.warn("Request with Empty Argument");
    }
    public static void doNormal() { log.info("State: Normal Utility Service");
    }
}