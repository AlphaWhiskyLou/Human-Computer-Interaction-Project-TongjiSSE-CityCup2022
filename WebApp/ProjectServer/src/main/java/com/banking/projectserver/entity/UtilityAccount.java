package com.banking.projectserver.entity;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 16:44:15
 */
public class UtilityAccount {
    private String accountID;
    private int utilityID;

    public int getUtilityID() {
        return utilityID;
    }

    public void setUtilityID(int utilityID) {
        this.utilityID = utilityID;
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