package com.banking.projectserver.service;

import com.banking.projectserver.entity.User;


public interface UserService {

    User getUserById(String userId);
    boolean insertUser(String userId, String userPassword,String name,String gender,int age);
    boolean deleteUser(String userId);
}
