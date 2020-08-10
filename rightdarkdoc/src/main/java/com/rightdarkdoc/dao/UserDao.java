package com.rightdarkdoc.dao;

import com.rightdarkdoc.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserDao {


    @Insert("insert into User values(#{userId},#{userName},#{passWord},#{phone},#{birthday}," +
            "#{email},#{avatar},#{description})")
    public int addUser(User user);

    @Update("update User set userName=#{userName},passWord=#{passWord},phone=#{phone},birthday=#{birthday}," +
            "eamil=#{email},avatar=#{avatar},description=#{description} where userid=#{userId}")
    public int updateUserByUserId(User user);

    @Select("select * from Users where userid=#{userId}")
    public User selectUserByUserId(@Param("userId") String userId);

    @Select("select * from User")
    public List<User> selectAllUser();


}
