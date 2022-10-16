package com.banking.projectserver.mapper;

import com.banking.projectserver.entity.SystemFunction;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ program: Bank
 * @ description:
 * @ author: Tianyu Lou
 * @ date: 2021-11-24 16:49:36
 */
@Mapper
@Repository
public interface SystemFunctionMapper {

    @Select("select * from bankingDB.systemFunction where functionId = #{functionId}")
    SystemFunction getSystemFunctionById(int functionId);

    @Select("select * from bankingDB.systemFunction")
    List<SystemFunction> getSystemFunction();

    @Insert("insert into bankingDB.systemFunction values(#{functionID},#{functionName},#{functionDescription}")
    boolean addSystemFunction(int functionID, String functionName, String functionDescription);

    @Delete("delete from bankingDB.systemFunction where functionId=#{functionID}")
    boolean deleteSystemFunctionById(int functionID);
}