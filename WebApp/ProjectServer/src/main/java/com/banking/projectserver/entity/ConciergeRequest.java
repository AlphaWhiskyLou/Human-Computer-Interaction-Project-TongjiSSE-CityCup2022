package com.banking.projectserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.util.Date;

/**
 * @ program: Bank
 * @ description: Concierge Request
 * @ author: Ling Wang
 * @ date: 2021-11-29 20:16:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConciergeRequest {
    private String conciergeID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date booktime;
    private String conciergeName;
    private String gender;
    private int age;
    private String ConciergeLanguage;
    private String personalStatement;
}
