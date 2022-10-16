package com.banking.projectserver.entity;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-29 15:59:42
 */
public class UtilityAccountDisp {
    private String accountID;
    private String utilityName;

    public String getUtilityName() {
        return utilityName;
    }

    public void setUtilityID(String utilityName) {
        this.utilityName = utilityName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public float getUtilityBalance() {
        return utilityBalance;
    }

    public void setUtilityBalance(float utilityBalance) {
        this.utilityBalance = utilityBalance;
    }

    private String userID;
    private float utilityBalance;

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }
}