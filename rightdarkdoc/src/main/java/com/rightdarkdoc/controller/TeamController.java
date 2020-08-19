package com.rightdarkdoc.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.*;
import com.rightdarkdoc.service.*;
import com.rightdarkdoc.utils.JWTUtils;
import com.rightdarkdoc.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.adapter.ForwardedHeaderTransformer;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.rightdarkdoc.config.MyConfig.*;

@RestController
@RequestMapping("team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private UserService userService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private MessageService messageService;


    @Autowired
    private TemplateService templateService;

    @Autowired
    private DocEditService docEditService;

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
            Integer userid = Integer.valueOf(userid1);
            Team tempTeam = teamService.findTeamByTeamnameAndCreatorId(team.getTeamname(), userid);
            if(tempTeam != null) {
                map.put("success", false);
                map.put("message", "您已经创建过一个同名团队，请尝试更改团队名称。");

            } else {
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
            }
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
    public Map<String, Object> registerNewUser(HttpServletRequest request,
                                               @RequestParam(value = "inviteename", required = false) String inviteename,
                                               @PathVariable String teamidString) {
        System.out.println("接收到一个团队邀请请求");

        Map<String, Object> map = new HashMap<>();
        try {
            //看是否是创建者发起的请求
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            //System.out.println(userid1);
            Integer userid = Integer.valueOf(userid1);
            /**
             * 取出被邀请对象的userid和teamid
             */
            User invitee = userService.selectUserByUsername(inviteename);
            Integer inviteeId = invitee.getUserid();
            Integer teamid = Integer.valueOf(teamidString);
            Team team = teamService.findTeamByTeamid(teamid);

            //判断是否有邀请权限
            if (team.getCreatorid().equals(userid)) {
                //判断是否在团队中
                Boolean isInTeam = userTeamService.isTeamMember(teamid, inviteeId);
                if (isInTeam) {
                    map.put("success", false);
                    map.put("message", "该成员已在团队中！");
                } else {
                    int ans = messageService.inviteTeamMemberMessage(teamid, userid, inviteeId, INVITE_MESSAGE);
                    if (ans == 1) {
                        map.put("success", true);
                        map.put("message", "邀请已发送，请等待对方同意！");
                    } else if (ans == 0){
                        map.put("success", false);
                        map.put("message", "邀请信息发送失败！");
                    } else if (ans == 2){
                        map.put("success", false);
                        map.put("message", "您已经发送邀请信息，请不要重复邀请！");
                    }
                }
            } else {
                map.put("success", false);
                map.put("message", "您没有邀请权限！");
            }
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
     * @return
     */
    @GetMapping("/{teamidString}/deleteMember")
    public Map<String, Object> deleteMember(@RequestParam(value = "deletedname", required = false) String deletedname,
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

            Integer teamid = Integer.valueOf(teamidString);
            Team team = teamService.findTeamByTeamid(teamid);

            if (team == null||(!team.getCreatorid().equals(userid))) {
                map.put("success", false);
                map.put("message", "用户没有删除权限！");
            } else {
                //删除User_Team表对应的记录
                User deleted = userService.selectUserByUsername(deletedname);
                Integer deletedid = deleted.getUserid();
                userTeamService.deleteTeamMember(teamid, deletedid);

                //3.将被删除者的文档全部置为个人文档
                documentService.setTeamUserDocTeamidToZero(teamid, deletedid);


                //4.给被删除者发一条消息
                Message message = new Message();
                message.setUserid(deletedid);
                message.setContent("您已被移出团队"+team.getTeamname()+"。");
                messageService.addMessage(message, SYS_MESSAGE);

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
     * @param teamidString 团队id
     * @return
     */
    @GetMapping("/{teamidString}/deleteTeam")
    public Map<String, Object> deleteTeam(HttpServletRequest request,
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

            Integer teamid = Integer.valueOf(teamidString);
            Team team = teamService.findTeamByTeamid(teamid);

            List<Integer> teamMembersId = userTeamService.findTeamMembers(teamid);

            if (!team.getCreatorid().equals(userid)) {
                map.put("success", false);
                map.put("message", "用户没有删除权限！");
            } else {
                //3.给所有团队文档teamid置0
                documentService.setDocTeamidToZero(teamid);

                //删除User_Team, Team表对应的记录
                userTeamService.deleteTeamByTeamid(teamid);
                teamService.deleteTeamByTeamid(teamid);

                //4.给所有用户发一条消息。
                for (Integer integer : teamMembersId) {
                    Message message = new Message();
                    message.setUserid(integer);
                    message.setContent("团队"+team.getTeamname()+"(teamid:"+team.getTeamid().toString()+")已解散， " + "您已被移出团队");
                    messageService.addMessage(message, SYS_MESSAGE);
                }

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
            //给团队创建者发送申请MESSAGE

            Boolean isInTeam = userTeamService.isTeamMember(teamid, userid);
            if (isInTeam) {
                map.put("success", false);
                map.put("message", "你已经是该团队成员！");
            }
            else {

                int ans = messageService.applyTeamMessage(teamid, userid, APPLY_MESSAGE);
                if (ans == 1) {
                    map.put("success", true);
                    map.put("message", "申请信息已发送，请等待团队管理员审核！");
                } else if (ans == 0){
                    map.put("success", false);
                    map.put("message", "申请信息发送失败！");
                } else if (ans == 2){
                    map.put("success", false);
                    map.put("message", "您已经发送申请信息，请不要重复申请！");
                }
            }
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

            //1.把所有自己创建的文档都置为0
            documentService.setTeamUserDocTeamidToZero(teamid, userid);

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
    @PostMapping("/{teamidString}/update")
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
                if (newTeamname != null) {
                    team.setTeamname(newTeamname);
                }
                if (newTeaminfo != null) {
                    team.setTeaminfo(newTeaminfo);
                }
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

    /**
     * 搜索团队
     * @param  searchContent
     * @return
     */
    @GetMapping("search")
    public Map<String, Object> searchTeamInfo(@RequestParam(value = "searchContent", required = false) String searchContent) {
        System.out.println("接收到一个搜索团队信息请求");
        Map<String, Object> map = new HashMap<>();
        try {
            //取出searchContent的内容(模糊匹配teamname)
//            String searchContent = requsetmap.get("searchContent");
            List<Team> teams = teamService.findTeamsBySearchContent(searchContent);
            map.put("teams", teams);
            map.put("success", true);
            map.put("message", "搜索成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "搜索失败！");
        }
        return map;
    }


    /**
     * 查看团队的所有文章
     * @param teamidString
     * @return
     */
    @GetMapping("/{teamidString}/documents")
    public Map<String, Object> findAllTeamDocuments(@PathVariable String teamidString) {
        System.out.println("接收到一个查看团队所有文档的请求");
        Map<String, Object> map = new HashMap<>();
        try {
            Integer teamid = Integer.valueOf(teamidString);
            //取出docids
            List<Document> documents = documentService.selectDocByTeamId(teamid);
            List<Document> docre = new ArrayList<>();
            for (Document doc : documents) {
                //判断一下是不是垃圾文件
                if (doc.getIstrash() == 0) {
                    doc = TimeUtils.setDocumentTimeString(doc);         //给时间赋值
                    docre.add(doc);
                }
            }
            map.put("documents", docre);
            map.put("success", true);
            map.put("message", "查看文章成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "查看文章失败！");
        }
        return map;
    }


    /**
     * 查看我的团队
     * @param request
     * @return
     */
    @GetMapping("/myTeams")
    public Map<String, Object> viewMyTeams(HttpServletRequest request) {
        System.out.println("接收到一个查看我的团队的请求");

        Map<String, Object> map = new HashMap<>();
        try {

            //看是否是创建者发起的请求
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            //System.out.println(userid1);
            Integer userid = Integer.valueOf(userid1);

            List<Team> myCreateTeams = new ArrayList<>();
            myCreateTeams = teamService.findMyCreateTeamsByCreatorId(userid);

            List<Integer> myAttendTeamsId = new ArrayList<>();
            myAttendTeamsId = userTeamService.findMyAttendTeams(userid);
//            System.out.println(myAttendTeamsId);
            List<Team> myAttendTeams = new ArrayList<>();

            for (Integer integer : myAttendTeamsId) {
                Team team = teamService.findTeamByTeamid(integer);
                myAttendTeams.add(team);
            }

            for (Team myCreateTeam : myCreateTeams) {
                myAttendTeams.add(myCreateTeam);
            }
//            map.put("myCreateTeams", myCreateTeams);
            map.put("myAttendTeams", myAttendTeams);
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
     * 创建团队文档
     * @param request
     * @param document
     * @return
     */
    @PostMapping("/{teamidString}/createDocument")
    public Map<String, Object> createNewTeamDoc(HttpServletRequest request,
                                                @RequestBody(required = false) Document document,
                                                @PathVariable String teamidString) {
        Map<String, Object> m = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            Integer teamid = Integer.valueOf(teamidString);
            //判断你是不是团队成员
            Boolean isTeamMember = userTeamService.isTeamMember(teamid, userid);

            //是团队成员
            if (isTeamMember) {
                if (document == null) {
                    document = new Document();
                }

                //给团队文档附上团队值
                document.setTeamid(teamid);

                if (document.getContent() == null) {
                    document.setContent("");
                }

                if (document.getTitle() == null) {
                    document.setTitle("untitle");
                }
                //设置当前时间
                Date date = new Date();
                //设置文档的创建者
//                System.out.println(date);

                if (document.getCreatorid() == null) {
                    document.setCreatorid(userid);
                }

                if (document.getEditcount() == null) {
                    document.setEditcount(1);
                }

                if (document.getIstrash() == null) {
                    document.setIstrash(0);
                }

                if (document.getLastedituserid() == null) {
                    document.setLastedituserid(userid);
                }

                if (document.getAuth() == null) {
                    document.setAuth(0);
                }

                if (document.getTeamauth() == null) {
                    document.setTeamauth(1);
                }

                if (document.getCreattime() == null) {
                    document.setCreattime(date);
                }

                if (document.getLastedittime() == null) {
                    document.setLastedittime(date);
                }

                if (document.getLastedituserid() == null) {
                    document.setLastedittime(date);
                }

                //创建document
                documentService.addDocument(document);

                //增加编辑记录
                docEditService.addANewEditRecord(userid,document.getDocid());
                document = TimeUtils.setDocumentTimeString(document);
                m.put("teamDocument", document);
                m.put("success", true);
                m.put("message", "create file successfully");
            } else {
                m.put("success", false);
                m.put("message", "您不是团队成员，没有权限创建文档！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            m.put("success", false);
            m.put("message", "failed to create doc");
        }
        return m;
    }


    @PostMapping("/{teamidString}/createDocument/template/{tempid}")
    public Map<String, Object> createNewTeamDocByTemp(HttpServletRequest request,
                                                        @RequestBody(required = false) Document document,
                                                        @PathVariable("teamidString") String teamidString,
                                                        @PathVariable("tempid") Integer tempid) {
        Map<String, Object> m = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            Integer teamid = Integer.valueOf(teamidString);
            //判断你是不是团队成员
            Boolean isTeamMember = userTeamService.isTeamMember(teamid, userid);

            //是团队成员
            if (isTeamMember) {
                if (document == null) {
                    document = new Document();
                }

                //给团队文档附上团队值
                document.setTeamid(teamid);

                if (document.getContent() == null) {
                    Template template = templateService.selectTemplateByTempid(tempid);
                    if(template!=null){
                        document.setContent(template.getContent());
                    }
                    else{
                        document.setContent("");
                    }
                }

                if (document.getTitle() == null) {
                    document.setTitle("untitle");
                }
                //设置当前时间
                Date date = new Date();
                //设置文档的创建者
//                System.out.println(date);

                if (document.getCreatorid() == null) {
                    document.setCreatorid(userid);
                }

                if (document.getEditcount() == null) {
                    document.setEditcount(1);
                }

                if (document.getIstrash() == null) {
                    document.setIstrash(0);
                }

                if (document.getLastedituserid() == null) {
                    document.setLastedituserid(userid);
                }

                if (document.getAuth() == null) {
                    document.setAuth(0);
                }

                if (document.getTeamauth() == null) {
                    document.setTeamauth(1);
                }

                if (document.getCreattime() == null) {
                    document.setCreattime(date);
                }

                if (document.getLastedittime() == null) {
                    document.setLastedittime(date);
                }

                if (document.getLastedituserid() == null) {
                    document.setLastedittime(date);
                }

                //创建document
                documentService.addDocument(document);

                //增加编辑记录
                docEditService.addANewEditRecord(userid,document.getDocid());
                document = TimeUtils.setDocumentTimeString(document);
                m.put("teamDocument", document);
                m.put("success", true);
                m.put("message", "create file successfully");
            } else {
                m.put("success", false);
                m.put("message", "您不是团队成员，没有权限创建文档！");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            m.put("success", false);
            m.put("message", "failed to create doc");
        }
        return m;
    }

}