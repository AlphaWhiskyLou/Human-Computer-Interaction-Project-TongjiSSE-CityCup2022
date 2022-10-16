package com.banking.projectserver.entity;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-12-27 18:00:36
 */
public class unityUser {
    public String getUnityID() {
        return unityID;
    }
    public void setUnityID(String unityID) {
        this.unityID = unityID;
    }
    private String unityID;

    public int getAuthorization() {
        return authorization;
    }

    public void setAuthorization(int authorization) {
        this.authorization = authorization;
    }

    private int authorization;
}