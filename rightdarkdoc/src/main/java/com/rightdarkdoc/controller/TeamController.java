package com.rightdarkdoc.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.dao.TeamDao;
import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.TeamService;
import com.rightdarkdoc.service.UserService;
import com.rightdarkdoc.service.UserTeamService;
import com.rightdarkdoc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private UserService userService;

    /**
     * 创建新的团队
     * @param request
     * @param team
     * @return
     */
    @PostMapping("create")
    public Map<String, Object> userCreateANewTeam(HttpServletRequest request, @RequestBody Team team) {
        System.out.println("接收到一个创建新团队的请求");
        Map<String, Object> map = new HashMap<>();
        try {

            /**
             * 1.将表信息插入team表中；
             */
            //取出request中的token
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);

            String userid1 = verify.getClaim("userid").asString();
            //System.out.println(userid1);
            Integer userid = Integer.valueOf(userid1);
            //System.out.println(userid);

            //取出request body中的信息作为team
//            //teamid
//            Integer teamid = Integer.valueOf(request.getParameter("teamid"));
//
//            //teaminfo
//            String teaminfo = request.getParameter("teaminfo");
//
//            //teamname
//            String teamname = request.getParameter("teamname");
            team.setCreatorid(userid);
            teamService.createNewTeam(team);

            /**
             * 2. 将表插入user_team表中
             */
            Team team1 = teamService.findTeamByTeamnameAndCreatorId(team.getTeamname(), team.getCreatorid());
            userTeamService.userCreateTeam(userid, team1.getTeamid());

            /**
             * 3. 设置返回数据
             */
            map.put("success", true);
            map.put("message", "创建团队成功！");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "创建团队失败！");
        }
        return map;
    }


    /**
     * 邀请团队新成员
     * @param request   传入被邀请者的username，teamname
     * @return
     */
    @PostMapping("inviteMember")
    public Map<String, Object> registerNewUser(HttpServletRequest request) {
        System.out.println("接收到一个团队邀请请求");
        Map<String, Object> map = new HashMap<>();
        try {
            /**
             * 取出被邀请对象的userid
             */
            String username = request.getParameter("inviteename");

            String teamname = request.getParameter("teamid");



            map.put("success", true);
            map.put("message", "邀请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "邀请失败！");
        }
        return map;
    }

    @PostMapping("deleteMember")
    public Map<String, Object> deleteMember(HttpServletRequest request) {

        //看是否是创建者发起的请求



        //删除Team表对应记录



        //删除User_Team表对应的记录


        System.out.println("接收到一个团队邀请请求");
        Map<String, Object> map = new HashMap<>();
        try {
            /**
             * 取出被邀请对象的userid
             */
            String username = request.getParameter("inviteename");

            String teamname = request.getParameter("teamid");



            map.put("success", true);
            map.put("message", "邀请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "邀请失败！");
        }
        return map;
    }


    @PostMapping("deleteTeam")
    public Map<String, Object> deleteTeam(HttpServletRequest request) {

        //看是否是创建者发起的请求



        //删除Team表对应记录



        //删除User_Team表对应的记录

        System.out.println("接收到一个团队邀请请求");
        Map<String, Object> map = new HashMap<>();
        try {
            /**
             * 取出被邀请对象的userid
             */
            String username = request.getParameter("inviteename");

            String teamname = request.getParameter("teamid");



            map.put("success", true);
            map.put("message", "邀请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "邀请失败！");
        }
        return map;
    }

}
