package com.banking.projectserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ program: Bank
 * @ description:
 * @ author: Ling Wang
 * @ date: 2021-12-30 18:05:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserConcierge {
    private String userID;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getConciergeName() {
        return conciergeName;
    }

    public void setConciergeName(String conciergeName) {
        this.conciergeName = conciergeName;
    }

    public Date getBookTime() {
        return booktime;
    }

    public void setBookTime(Date bookTime) {
        this.booktime = bookTime;
    }

    private String conciergeName;
    private Date booktime;


}
