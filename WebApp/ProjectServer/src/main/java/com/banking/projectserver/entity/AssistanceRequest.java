package com.banking.projectserver.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 16:34:50
 */
public class AssistanceRequest {
    public String getAssistanceID() {
        return assistanceID;
    }

    public void setAssistanceID(String assistanceID) {
        this.assistanceID = assistanceID;
    }

    private String assistanceID;
    private String userID;
    private String accountID;

    public String getPayeeAccountID() {
        return payeeAccountID;
    }

    public void setPayeeAccountID(String payeeAccountID) {
        this.payeeAccountID = payeeAccountID;
    }

    private String payeeAccountID;
    private int functionID;
    private BigDecimal amount;

    public Date getAssistanceDate() {
        return assistanceDate;
    }

    public void setAssistanceDate(Date assistanceDate) {
        this.assistanceDate = assistanceDate;
    }

    private Date assistanceDate;
    private int isFinished;

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getAccountID() {
        return accountID;
    }
    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
    public int getFunctionID() {
        return functionID;
    }
    public void setFunctionID(int functionID) {
        this.functionID = functionID;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public int getIsFinished() {
        return isFinished;
    }
    public void setIsFinished(int isFinished) {
        this.isFinished = isFinished;
    }

}