package com.banking.projectserver.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * @ program: Bank
 * @ description: the info of concierge
 * @ author: Ling Wang
 * @ date: 2021-12-28 22:25:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Concierge {
    private String conciergeID;
    private String conciergeName;
    private String gender;
    private int age;
    private String ConciergeLanguage;
    private String personalStatement;

}
