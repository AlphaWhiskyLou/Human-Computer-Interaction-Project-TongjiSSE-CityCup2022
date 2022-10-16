package com.banking.projectserver.serviceImpl;

import com.banking.projectserver.mapper.UnityMapper;
import com.banking.projectserver.service.UnityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ program: ProjectServer
 * @ description: for user unity and verify
 * @ author: Ling Wang
 * @ date: 2021-12-28 20:08:43
 */
@Service
public class UnityServiceImpl implements UnityService {
    @Autowired
    private UnityMapper unityMapper;

    @Override
    public boolean addUnity(String userId, String unityId, String relationship) {
        return unityMapper.addUnity(userId,unityId,relationship);
    }

    @Override
    public boolean verifyUnity(String userId){
        return unityMapper.verifyUnity(userId);
    }
    @Override
    public boolean deleteUnity(String userId){
        return unityMapper.deleteUnity(userId);
    }


}
