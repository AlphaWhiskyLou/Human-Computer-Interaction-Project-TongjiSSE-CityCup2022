package com.banking.projectserver.mapper;

import com.banking.projectserver.entity.AssistanceRequestDisp;
import com.banking.projectserver.entity.Concierge;
import com.banking.projectserver.entity.ConciergeRequest;
import com.banking.projectserver.entity.UserConcierge;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface ConciergeRequestMapper {
    @Select("select * from bankingDB.conciergeInfo")
    List<Concierge> ConciergeInfoFreeRequest();

    @Select("select * from bankingDB.userConcierge natural join bankingDB.conciergeInfo where userID = #{userID}")
    List<ConciergeRequest> ConciergeRequest(String userID);

    @Select("select * from bankingDB.conciergeInfo where gender=#{gender} and conciergeLanguage=#{ConciergeLanguage}")
    List<Concierge> ConciergeInfoRequest(String gender, String ConciergeLanguage);

    @Insert("insert into bankingDB.userConcierge (userID, conciergeID,booktime) values (#{userID}, #{conciergeID}, #(booktime))")
    void addConciergeRequest(UserConcierge userConcierge);

    @Select("select count(*) from bankingDB.userConcierge where userID=#{userID}")
    int selectConciergeRequest(String userID);

    @Update("update bankingDB.userConcierge set conciergeID=#{conciergeID} where userID=#{userID}")
    void updateConciergeRequest(String userID, String conciergeID);

    @Delete("delete from bankingDB.userConcierge where userID=#{userID}")
    void deleteConciergeRequest(String userID);

}
