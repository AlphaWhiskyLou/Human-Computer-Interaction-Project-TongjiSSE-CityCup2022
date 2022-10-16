package com.banking.projectserver.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户实体类
 *
 * @author 孟繁霖
 * @date 2021/11/22 23:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    //属性与数据库字段一一对应，这里仅作为演示
    private String userId;
    private String userPassword;
    private String userName;
    private String gender;
    private int age;
    private int isValid;

}


