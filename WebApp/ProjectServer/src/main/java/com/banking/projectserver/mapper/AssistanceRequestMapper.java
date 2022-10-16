package com.banking.projectserver.mapper;

import com.banking.projectserver.entity.AssistanceRequest;
import com.banking.projectserver.entity.AssistanceRequestDisp;
import com.banking.projectserver.entity.unityUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 23:30:15
 */
@Mapper
@Repository
public interface AssistanceRequestMapper {

    @Select("select userID,accountID,functionName,amount from bankingDB.assistanceRequest natural join bankingDB.systemFunction natural join bankingDB.utilityFunction where transactionID=#{transactionID}")
    List<AssistanceRequestDisp> getAssistanceRequestByTransactionId(String transactionID);

    @Insert("insert into bankingDB.assistanceRequest (assistanceID, userID, accountID, payeeAccountID, functionID, amount, assistanceDate, isFinished) values (#{assistanceID}, #{userID}, #{accountID},#{payeeAccountID}, #{functionID}, #{amount}, #{assistanceDate}, #{isFinished})")
    void addAssistanceRequest(String assistanceID, String userID, String accountID, String payeeAccountID, int functionID, double amount, Date assistanceDate, int isFinished);

    @Update("update bankingDB.assistanceRequest set isFinished=#{isFinished}  where assistanceID=#{assistanceID}")
    void updateAssistanceRequestStatus(String assistanceID, int isFinished);

    @Select("select * from bankingDB.assistanceRequest where assistanceID=#{assistanceID}")
    AssistanceRequest getAssistanceRequestByAssistanceID(String assistanceID);

    @Select("select assistanceDate, assistanceID,userName,accountID,functionName,amount from bankingDB.assistanceRequest natural join bankingDB.systemFunction natural join bankingDB.user where userID=#{userID} and isFinished=1")
    List<AssistanceRequestDisp> getAssistanceRequestByUserID(String userID);

    @Select("select assistanceDate,assistanceID,userName,accountID,functionName,amount from bankingDB.assistanceRequest natural join bankingDB.systemFunction natural join bankingDB.user where userID=#{userID} and isFinished=0")
    List<AssistanceRequestDisp> getUnfinishedAssistanceRequestByUserID(String userID);

    @Select("select unityID, authorization from bankingDB.userUnity where userID=#{userID}")
    unityUser getUnityIDByUserID(String userID);

}