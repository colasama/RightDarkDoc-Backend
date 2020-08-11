package com.rightdarkdoc.dao;

import com.rightdarkdoc.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserDao {

    /**
     * 注册新用户
     * @param user
     * @return
     */
      public void registerNewUser(User user);


    /**
     * 通过用户名和密码查找用户
     * @param username
     * @param password
     * @return 返回一个User对象
     */
      public User findUserByUsernameAndPassword(String username, String password);

//    @Insert("insert into User values(#{userId},#{userName},#{passWord},#{phone},#{birthday}," +
//            "#{email},#{avatar},#{description})")
//    public int addUser(User user);
//
//    @Update("update User set userName=#{userName},passWord=#{passWord},phone=#{phone},birthday=#{birthday}," +
//            "eamil=#{email},avatar=#{avatar},description=#{description} where userid=#{userId}")
//    public int updateUserByUserId(User user);
//
//    @Select("select * from Users where userid=#{userId}")
//    public User selectUserByUserId(@Param("userId") String userId);
//
//    @Select("select * from User")
//    public List<User> selectAllUser();
}
