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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * @param request   传入创建团队的teamname和teaminfo
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
     * @param  inviteename  传入被邀请者的username，
     * @param  teamidString teamid
     * @return
     */
    @GetMapping("/{teamidString}/inviteMember")
    public Map<String, Object> registerNewUser(@RequestParam(value = "inviteename", required = false) String inviteename,
                                               @PathVariable String teamidString) {
        System.out.println("接收到一个团队邀请请求");

        Map<String, Object> map = new HashMap<>();
        try {
            /**
             * 取出被邀请对象的userid和teamid
             */
            //System.out.println(inviteename);
            User invitee = userService.selectUserByUsername(inviteename);
            //System.out.println(invitee);
            Integer inviteeId = invitee.getUserid();
            Integer teamid = Integer.valueOf(teamidString);
            //System.out.println(teamid);
            userTeamService.inviteTeamMember(teamid, inviteeId);
            map.put("success", true);
            map.put("message", "邀请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "邀请失败！");
        }
        return map;
    }


    /**
     * 删除一个团队成员
     * @param deletedname 被删除者用户名
     * @param teamidString  团队id
     * @param teamname 团队名称
     * @return
     */
    @GetMapping("/{teamidString}/deleteMember")
    public Map<String, Object> deleteMember(@RequestParam(value = "deletedname", required = false) String deletedname,
                                            @RequestParam(value = "teamname", required = false) String teamname,
                                            @PathVariable String teamidString, HttpServletRequest request) {

        System.out.println("接收到一个删除团队成员请求");
        Map<String, Object> map = new HashMap<>();

        try {
            //看是否是创建者发起的请求
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);

            String userid1 = verify.getClaim("userid").asString();
            //System.out.println(userid1);
            Integer userid = Integer.valueOf(userid1);

            Team team = teamService.findTeamByTeamnameAndCreatorId(teamname, userid);

            if (team == null) {
                map.put("success", false);
                map.put("message", "用户没有删除权限！");
            } else {
                //删除User_Team表对应的记录
                User deleted = userService.selectUserByUsername(deletedname);
                //System.out.println(invitee);
                Integer deletedid = deleted.getUserid();
                Integer teamid = Integer.valueOf(teamidString);
                userTeamService.deleteTeamMember(teamid, deletedid);
                map.put("success", true);
                map.put("message", "删除成员成功！");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "删除失败！");
        }
        return map;
    }

    /**
     * 删除团队
     * @param request
     * @param teamname  团队名称
     * @param teamidString 团队id
     * @return
     */
    @GetMapping("/{teamidString}/deleteTeam")
    public Map<String, Object> deleteTeam(HttpServletRequest request,
                                          @RequestParam(value = "teamname", required = false) String teamname,
                                          @PathVariable String teamidString) {
        System.out.println("接收到一个团队删除请求");
        Map<String, Object> map = new HashMap<>();

        try {
            //看是否是创建者发起的请求
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);

            String userid1 = verify.getClaim("userid").asString();
            //System.out.println(userid1);
            Integer userid = Integer.valueOf(userid1);

            Team team = teamService.findTeamByTeamnameAndCreatorId(teamname, userid);

            if (team == null) {
                map.put("success", false);
                map.put("message", "用户没有删除权限！");
            } else {
                //删除User_Team, Team表对应的记录
                Integer teamid = Integer.valueOf(teamidString);
                userTeamService.deleteTeamByTeamid(teamid);
                teamService.deleteTeamByTeamid(teamid);
                map.put("success", true);
                map.put("message", "删除团队成功！");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "删除失败！");
        }
        return map;
    }


    /**
     * 申请加入团队
     * @param  teamidString teamid
     * @return
     */
    @GetMapping("/{teamidString}/apply")
    public Map<String, Object> applyToBeATeamMember(@PathVariable String teamidString,
                                                    HttpServletRequest request) {
        System.out.println("接收到一个团队申请请求");

        Map<String, Object> map = new HashMap<>();
        try {
            /**
             * 取出申请对象的userid
             */
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            //System.out.println(userid1);
            Integer userid = Integer.valueOf(userid1);

            Integer teamid = Integer.valueOf(teamidString);
            userTeamService.applyToBeATeamMember(teamid, userid);
            map.put("success", true);
            map.put("message", "申请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "申请失败！");
        }
        return map;
    }

    /**
     * 退出团队
     * @param  teamidString teamid
     * @return
     */
    @GetMapping("/{teamidString}/exit")
    public Map<String, Object> exitTeam(@PathVariable String teamidString,
                                                    HttpServletRequest request) {
        System.out.println("接收到一个团队退出请求");

        Map<String, Object> map = new HashMap<>();
        try {
            /**
             * 取出申请对象的userid
             */
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            //System.out.println(userid1);
            Integer userid = Integer.valueOf(userid1);

            Integer teamid = Integer.valueOf(teamidString);
            userTeamService.exitTeam(teamid, userid);
            map.put("success", true);
            map.put("message", "退出团队成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "退出失败！");
        }
        return map;
    }


    /**
     * 查看团队信息
     * @param  teamidString teamid
     * @return
     */
    @GetMapping("/{teamidString}/view")
    public Map<String, Object> viewTeamInfo(@PathVariable String teamidString,
                                        HttpServletRequest request) {
        System.out.println("接收到一个查看团队信息请求");

        Map<String, Object> map = new HashMap<>();
        try {
            Integer teamid = Integer.valueOf(teamidString);

            //取出团队
            Team team = teamService.findTeamByTeamid(teamid);

            //取出团队创建者
            Integer creatorid = team.getCreatorid();
            User teamCreator = userService.selectUserByUserId(creatorid);
            teamCreator.setPassword("");

            //取出团队成员
            List<Integer> teamMembersIdList = new ArrayList<>();
            teamMembersIdList = userTeamService.findTeamMembers(teamid);
            List<User> teamMembers = new ArrayList<>();
            for (Integer integer : teamMembersIdList) {
                User usertemp = userService.selectUserByUserId(integer);
                usertemp.setPassword("");
                teamMembers.add(usertemp);
            }

            map.put("team", team);
            map.put("teamCreator", teamCreator);
            map.put("teamMembers", teamMembers);
            map.put("success", true);
            map.put("message", "查看成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "查看失败！");
        }
        return map;
    }

    /**
     * 修改团队信息
     * @param request
     * @param requsetmap
     * @param teamidString
     * @return
     */
    @RequestMapping("/{teamidString}/update")
    public Map<String, Object> updateTeamInfo(HttpServletRequest request,
                                              @RequestBody Map<String, String> requsetmap,
                                              @PathVariable String teamidString) {
        System.out.println("接收到一个团队删除请求");
        Map<String, Object> map = new HashMap<>();
        String newTeamname = requsetmap.get("newTeamname");
        String newTeaminfo = requsetmap.get("newTeaminfo");
        System.out.println(newTeamname + ":" + newTeaminfo);

        try {
            //看是否是创建者发起的请求
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);

            String userid1 = verify.getClaim("userid").asString();
            //System.out.println(userid1);
            Integer userid = Integer.valueOf(userid1);

            Integer teamid = Integer.valueOf(teamidString);
            Team team = teamService.findTeamByTeamid(teamid);

            if (!team.getCreatorid().equals(userid)) {
                map.put("success", false);
                map.put("message", "用户没有修改权限！");
            } else {
                //修改team表
                team.setTeaminfo(newTeaminfo);
                team.setTeamname(newTeamname);
                teamService.updateTeam(team);
                map.put("success", true);
                map.put("message", "修改团队信息成功！");
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "修改失败！");
        }
        return map;
    }




}
