package com.banking.projectserver.mapper;

import com.banking.projectserver.entity.SystemFunction;
import com.banking.projectserver.entity.UtilityAccount;
import com.banking.projectserver.entity.UtilityAccountDisp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 18:01:17
 */
@Mapper
@Repository
public interface UtilityMapper {

    @Select("select * from bankingDB.systemFunction where isUtility=1")
    List<SystemFunction> getUtilityFunction();

    @Select("select * from bankingDB.utilityAccount where userID=#{userId} ")
    UtilityAccount getUtilityAccountById(String userId);

    @Select("select accountID,utilityName,UserID,utilityBalance from bankingDB.utilityAccount natural join bankingDB.utilityFunction")
    List<UtilityAccountDisp> getUtilityAccount();

    @Insert("insert into bankingDB.utilityFunction values(#{utilityID},#{utilityName})")
    void addUtilityFunction(int utilityID, String utilityName);

    @Delete("delete from bankingDB.utilityFunction where utilityID=#{utilityId}")
    void deleteUtilityFunctionById(int utilityId);

    @Update("update bankingDB.utilityAccount set utilityBalance=utilityBalance+#{amount} where userID=#{userId} and functionID=#{functionId}")
    void utilityPayment(String userId, BigDecimal amount, int functionId);

    @Insert("insert into bankingDB.utilityAccount values(#{userId},#{utilityId},#{accountId},#{utilityBalance})")
    void addUtilityAccount(String userId, int utilityId, String accountId, double utilityBalance);

}