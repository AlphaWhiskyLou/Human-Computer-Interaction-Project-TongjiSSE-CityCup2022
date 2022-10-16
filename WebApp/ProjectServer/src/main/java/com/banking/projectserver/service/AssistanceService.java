package com.banking.projectserver.service;

import com.banking.projectserver.entity.AssistanceRequest;
import com.banking.projectserver.entity.AssistanceRequestDisp;
import com.banking.projectserver.entity.unityUser;

import java.util.List;

public interface AssistanceService {
    boolean addAssistanceRequest(String userID, String accountID, String payeeAccountID, int functionID, double amount, int isFinished);
    boolean updateAssistanceRequestStatus(String assistanceID, int isFinished) throws Exception;
    AssistanceRequest getAssistanceRequestByAssistanceID(String assistanceID);
    List<AssistanceRequestDisp> getAssistanceRequestByTransactionID(String transactionID);
    List<AssistanceRequestDisp> getAssistanceRequestByUserID(String userID);
    List<AssistanceRequestDisp> getUnfinishedAssistanceRequestByUserID(String userID);
    unityUser getUnityUserByUserID(String userID);
}
