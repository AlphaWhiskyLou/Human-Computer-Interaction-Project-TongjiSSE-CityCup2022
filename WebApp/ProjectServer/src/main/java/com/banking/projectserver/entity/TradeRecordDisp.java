package com.banking.projectserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-29 00:05:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TradeRecordDisp {
    private String recordID;
    private String accountID;
    private Integer functionID;
    private BigDecimal tradeAmount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date tradeTime;
    private String payeeAccountID;
    private String functionName;
}