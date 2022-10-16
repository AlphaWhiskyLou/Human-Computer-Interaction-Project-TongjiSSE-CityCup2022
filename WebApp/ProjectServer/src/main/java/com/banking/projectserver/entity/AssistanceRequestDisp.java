package com.banking.projectserver.entity;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-30 20:47:41
 */
public class AssistanceRequestDisp {

    public String getAssistanceID() {
        return assistanceID;
    }

    public void setAssistanceID(String assistanceID) {
        this.assistanceID = assistanceID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAssistanceDate() {
        return assistanceDate;
    }

    public void setAssistanceDate(String assistanceDate) {
        this.assistanceDate = assistanceDate;
    }

    private String assistanceDate;
    private String assistanceID;
    private String userName;
    private String accountID;
    private String functionName;
    private Double amount;
}