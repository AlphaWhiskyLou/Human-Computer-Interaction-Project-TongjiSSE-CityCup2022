package com.banking.projectserver.serviceImpl;

import com.banking.projectserver.entity.Concierge;
import com.banking.projectserver.entity.UserConcierge;
import com.banking.projectserver.service.ConciergeService;
import com.banking.projectserver.entity.ConciergeRequest;
import com.banking.projectserver.mapper.ConciergeRequestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ program: Bank
 * @ description: concierge
 * @ author: Ling Wang
 * @ date: 2021-11-29 20:14:19
 */
@Service
public class ConciergeServiceImpl implements ConciergeService {
    @Autowired
    private ConciergeRequestMapper conciergeRequestMapper;
    @Override
    public List<Concierge> ConciergeFreeRequest(){
        return conciergeRequestMapper.ConciergeInfoFreeRequest();
    }
    @Override
    public List<ConciergeRequest> ConciergeInfoRequest(String userID){
        System.out.println(conciergeRequestMapper.ConciergeRequest(userID));
        return conciergeRequestMapper.ConciergeRequest(userID);
    }
    @Override
    public List<Concierge> ConciergeRequest(String gender, String language){
        return conciergeRequestMapper.ConciergeInfoRequest(gender,language);
    }

    @Override
    public boolean addConciergeRequest(UserConcierge userConcierge){
        conciergeRequestMapper.addConciergeRequest(userConcierge);
        return true;
    }
    @Override
    public int selectConciergeRequest(String userID){
        return conciergeRequestMapper.selectConciergeRequest(userID);
    }

    @Override
    public boolean updateConciergeRequest(String userID, String conciergeID){
        conciergeRequestMapper.updateConciergeRequest(userID, conciergeID);
        return true;
    }
    @Override
    public boolean deleteConciergeRequest(String userID){
        conciergeRequestMapper.deleteConciergeRequest(userID);
        return true;
    }



}
