package com.banking.projectserver.service;

import com.banking.projectserver.entity.Concierge;
import com.banking.projectserver.entity.ConciergeRequest;
import com.banking.projectserver.entity.UserConcierge;

import java.util.Date;
import java.util.List;

public interface ConciergeService {
    List<Concierge> ConciergeFreeRequest();
    List<ConciergeRequest> ConciergeInfoRequest(String userID);
    List<Concierge> ConciergeRequest(String gender, String language);
    int selectConciergeRequest(String userID);
    boolean addConciergeRequest(UserConcierge userConcierge);
    boolean updateConciergeRequest(String userID, String conciergeID);
    boolean deleteConciergeRequest(String userID);

}
