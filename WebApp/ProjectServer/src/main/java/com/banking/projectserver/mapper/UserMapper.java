
package com.banking.projectserver.mapper;

import com.banking.projectserver.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * 用户表的数据访问接口
 *
 * @author wangling
 * @date 2021/11/22 23:10
 */
@Mapper
@Repository
public interface UserMapper {

    @Select("select * from bankingDB.user where userID=#{userId}")
    User getUserById(String userId);

    @Insert("insert into bankingDB.user (userID, userPassword,userName,gender,age) values(#{userId},#{userPassword},#{name},#{gender},#{age})")
    boolean insertUser(String userId, String userPassword,String name,String gender,int age);

    @Update("update user set isValid=0 where userID=#{userId}")
    boolean deleteUser(String userId);
}
