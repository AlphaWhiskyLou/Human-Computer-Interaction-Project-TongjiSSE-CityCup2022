package com.banking.projectserver.mapper;

import com.banking.projectserver.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface UnityMapper {

    @Select("select count(*) from bankingDB.userUnity where userID=#{userId}")
    int checkUnity(String userID);

    @Insert("insert into bankingDB.userUnity (userID, unityID, unityRelationship) values(#{userId},#{unityId},#{relationship})")
    boolean addUnity(String userId, String unityId, String relationship);

    @Update("update bankingDB.userUnity set authorization=1 where unityID=#{userId}")
    boolean verifyUnity(String userId);

    @Update("update bankingDB.userUnity set authorization=0 where unityID=#{userId}")
    boolean deleteUnity(String userId);
}

