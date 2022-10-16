package com.banking.projectserver.service;

import com.banking.projectserver.entity.User;

public interface UnityService {
    boolean addUnity(String userId, String unityId, String relationship);
    boolean verifyUnity(String userId);
    boolean deleteUnity(String userId);
}