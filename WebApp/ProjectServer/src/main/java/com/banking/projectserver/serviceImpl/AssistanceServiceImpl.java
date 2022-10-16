package com.banking.projectserver.serviceImpl;

import com.banking.projectserver.entity.AssistanceRequest;
import com.banking.projectserver.entity.AssistanceRequestDisp;
import com.banking.projectserver.entity.unityUser;
import com.banking.projectserver.mapper.AssistanceRequestMapper;
import com.banking.projectserver.service.AssistanceService;
import com.banking.projectserver.service.BankAccountService;
import com.banking.projectserver.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 23:29:48
 */
@Service
public class AssistanceServiceImpl implements AssistanceService {
    @Autowired
    private AssistanceRequestMapper assistanceRequestMapper;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private UtilityService utilityService;

    @Override
    public unityUser getUnityUserByUserID(String userID){
        return assistanceRequestMapper.getUnityIDByUserID(userID);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addAssistanceRequest(String userID, String accountID, String payeeAccountID, int functionID, double amount, int isFinished){
        String assistanceID = UUID.randomUUID().toString();
        Date assistanceDate = new Date(System.currentTimeMillis());
        assistanceRequestMapper.addAssistanceRequest(assistanceID, userID, accountID, payeeAccountID, functionID, amount, assistanceDate, isFinished);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateAssistanceRequestStatus(String assistanceID, int isFinished) throws Exception {
        if(isFinished==1){
            assistanceRequestMapper.updateAssistanceRequestStatus(assistanceID, isFinished);
            AssistanceRequest assistanceRequest = assistanceRequestMapper.getAssistanceRequestByAssistanceID(assistanceID);
            if(assistanceRequest.getFunctionID()==1){
                bankAccountService.transferMoney(assistanceRequest.getAccountID(), assistanceRequest.getPayeeAccountID(), assistanceRequest.getAmount());
            }
            if(assistanceRequest.getFunctionID()==7){
                bankAccountService.openAccount(assistanceRequest.getUserID(), assistanceRequest.getPayeeAccountID());
            }
            else{
                // String userID,double payment,int functionID, String accountID
                utilityService.utilityPayment(assistanceRequest.getUserID(), assistanceRequest.getAmount(), assistanceRequest.getFunctionID(), assistanceRequest.getAccountID());
            }
        }
        else assistanceRequestMapper.updateAssistanceRequestStatus(assistanceID, isFinished);
        return true;
    }

    @Override
    public List<AssistanceRequestDisp> getAssistanceRequestByTransactionID(String transactionID){
        return assistanceRequestMapper.getAssistanceRequestByTransactionId(transactionID);
    }

    @Override
    public List<AssistanceRequestDisp> getAssistanceRequestByUserID(String userID){
        return assistanceRequestMapper.getAssistanceRequestByUserID(userID);
    }
    @Override
    public AssistanceRequest getAssistanceRequestByAssistanceID(String assistanceID){
        return assistanceRequestMapper.getAssistanceRequestByAssistanceID(assistanceID);
    }

    @Override
    public List<AssistanceRequestDisp> getUnfinishedAssistanceRequestByUserID(String userID){
        return assistanceRequestMapper.getUnfinishedAssistanceRequestByUserID(userID);
    }
}