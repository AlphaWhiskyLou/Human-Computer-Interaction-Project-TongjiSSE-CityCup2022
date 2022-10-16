package com.banking.projectserver.aop.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-18 18:16:04
 */
@Service
@Slf4j
public class MessageManager {
    public static void doAbnormal() {
        log.warn("Request with Empty Argument");
    }
    public static void doNormal() { log.info("State: Normal Message Service");
    }
}