package com.rightdarkdoc.service;


import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.entity.User;

import java.util.List;

import java.util.List;


public interface UserService {

    /**
     * 注册新用户的功能
     * @param user
     */
    public void registerNewUser(User user);

    /**
     * 根据用户名和密码登录
     * @param username
     * @param password
     * @return
     */
    public User findUserByUsernameAndPassword(String username, String password);

    /**
     * 更新用户的信息
     * note:不提供密码的修改功能
     * @param user 封装了需要更新的用户实体
     */
    public void updateUser(User user);

    /**
     * 根据用户id查找用户
     * @param userid 需要查找的用户id
     * @return  user对象
     */
    public User selectUserByUserId(Integer userid);

    public User selectUserByEmail(String email);

    /**
     * 根据用户username查找用户
     * @param username 需要查找的用户id
     * @return  user对象
     */
    public User selectUserByUsername(String username);


    /**
     * 根据用户username模糊查找用户名
     * @param username 需要查找的用户id
     * @return
     */
    public List<User> selectUserByUsernameFuz(String username);


    /**
     * 修改密码的功能
     * @param userid   用户id
     * @param password   新密码
     */
    public void updateUserPassword(Integer userid,String password);

    interface TeamService {

        /**
         * 创建新的团队
         * @param team
         */
        public void createNewTeam(Team team);

        /**
         * 根据团队名称和创建者id查询团队
         * @param teamname          团队名称
         * @param creatorid         创建者id
         * @return
         */
        public Team findTeamByTeamnameAndCreatorId(String teamname, Integer creatorid);

        /**
         * 根据团队id删除记录
         * @param teamid
         */
        public void deleteTeamByTeamid(Integer teamid);

        /**
         * 通过团队id查找团队
         * @param teamid
         * @return
         */
        public Team findTeamByTeamid(Integer teamid);

        /**
         * 修改team信息
         * @param team
         */
        public void updateTeam(Team team);

        /**
         * 根据搜索内容查找团队（模糊匹配teamname）
         * @param searchContent
         * @return
         */
        public List<Team> findTeamsBySearchContent(String searchContent);

        interface TeamDocumentService {
        }
    }
}
