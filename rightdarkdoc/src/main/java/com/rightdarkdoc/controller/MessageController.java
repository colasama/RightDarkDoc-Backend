package com.rightdarkdoc.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.Message;
import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.MessageService;
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

import static com.rightdarkdoc.config.MyConfig.*;

@RestController
@RequestMapping("message")
public class MessageController {

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    /**
     * 团队创建者同意别人的申请
     * @param messageidString 消息id
     * @return
     */
    @GetMapping("apply/{messageidString}/agree")
    public Map<String, Object> agreeTeamApply(HttpServletRequest request,
                                                  @PathVariable String messageidString) {
        System.out.println("接收到一个 同意他人申请加入团队 的请求");
        Map<String, Object> map = new HashMap<>();
        try {
            Integer messageid = Integer.valueOf(messageidString);

            //2. 取出消息
            Message message = messageService.selectMessageByMessageId(messageid);
            if (userTeamService.isTeamMember(message.getApplyteamid(), message.getApplyuserid())) {
                map.put("success", false);
                map.put("message", "该成员已在团队中！");
            }
            else {
                //3. 插入user_team表中
                userTeamService.inviteTeamMember(message.getApplyteamid(), message.getApplyuserid());
                map.put("success", true);
                map.put("message", "已同意申请！");
                //4. 给申请者发一条消息，表示已经加入团队成功。
                Message message1 = new Message();
                message1.setUserid(message.getApplyuserid());
                message1.setContent("你的申请信息已通过，你已成功加入团队：" + teamService.findTeamByTeamid(message.getApplyteamid()).getTeamname() + "。");
                messageService.addMessage(message1, SYS_MESSAGE);
                //1. 将消息设置为已读
                messageService.updateIsRead(messageid, 1);
                map.put("success", true);
                map.put("message", "同意申请成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "同意失败！");
        }
        return map;
    }

    /**
     * 团队管理员拒绝同意别人的申请
     * @param request
     * @param messageidString
     * @return
     */
    @GetMapping("apply/{messageidString}/reject")
    public Map<String, Object> rejectTeamApply(HttpServletRequest request,
                                                  @PathVariable String messageidString) {
        Map<String, Object> map = new HashMap<>();
        try {
            Integer messageid = Integer.valueOf(messageidString);


            Message message = messageService.selectMessageByMessageId(messageid);

            //2. 给申请者发一条消息，表示拒绝对方加入团队。
            Message message1 = new Message();
            message1.setUserid(message.getApplyuserid());
            message1.setContent("你的申请信息已处理，团队创建者拒绝你加入团队：" + teamService.findTeamByTeamid(message.getApplyteamid()).getTeamname() + "。");
            messageService.addMessage(message1, SYS_MESSAGE);
            //1. 将消息设置为已读
            messageService.updateIsRead(messageid, 1);
            map.put("success", true);
            map.put("message", "拒绝申请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "拒绝失败！");
        }
        return map;
    }

    /**
     * 查看用户的所有消息
     * @param request
     * @return
     */
    @GetMapping("/messages")
    public Map<String, Object> showAllMyMessages(HttpServletRequest request) {
        System.out.println("接收到一个 拒绝他人申请加入团队 的请求");
        Map<String, Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userid1);

            List<Message> normalMessages = messageService.selectMessageByUserIdAndType(userid, SYS_MESSAGE);
            List<Message> applyMessages = messageService.selectMessageByUserIdAndType(userid, APPLY_MESSAGE);
            List<Message> inviteMessages = messageService.selectMessageByUserIdAndType(userid, INVITE_MESSAGE);

            map.put("normalMessage", normalMessages);
            map.put("applyMessage", applyMessages);
            map.put("inviteMessage", inviteMessages);
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
     * 用户同意别人的邀请请求
     * @param messageidString 消息id
     * @return
     */
    @GetMapping("invite/{messageidString}/agree")
    public Map<String, Object> agreeTeamInvite(HttpServletRequest request,
                                              @PathVariable String messageidString) {
        System.out.println("接收到一个 同意他人邀请加入团队 的请求");
        Map<String, Object> map = new HashMap<>();
        try {
            Integer messageid = Integer.valueOf(messageidString);

            //2. 取出消息
            Message message = messageService.selectMessageByMessageId(messageid);
            if (userTeamService.isTeamMember(message.getInviteteamid(), message.getUserid())) {
                map.put("success", false);
                map.put("message", "您已经在团队中！");
            }
            else {
                //3. 插入user_team表中
                userTeamService.inviteTeamMember(message.getInviteteamid(), message.getUserid());

                map.put("success", true);
                map.put("message", "已同意邀请！");
                //4. 给团队创建者发一条消息，表示已经加入团队成功。
                Message message1 = new Message();
                Team team = teamService.findTeamByTeamid(message.getInviteteamid());
                message1.setUserid(team.getCreatorid());

                User user = userService.selectUserByUserId(message.getUserid());
                message1.setContent(user.getUsername() + "已成功加入团队：" + team.getTeamname() + "。");
                messageService.addMessage(message1, SYS_MESSAGE);

                //1. 将消息设置为已读
                messageService.updateIsRead(messageid, 1);
                map.put("success", true);
                map.put("message", "同意邀请成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "同意邀请失败！");
        }
        return map;
    }

    /**
     * 用户拒绝同意别人的邀请请求
     * @param request
     * @param messageidString
     * @return
     */
    @GetMapping("invite/{messageidString}/reject")
    public Map<String, Object> rejectTeamInvite(HttpServletRequest request,
                                               @PathVariable String messageidString) {
        System.out.println("接收到一个 拒绝他人邀请 的请求");
        Map<String, Object> map = new HashMap<>();
        try {
            Integer messageid = Integer.valueOf(messageidString);
            Message message = messageService.selectMessageByMessageId(messageid);
            User user = userService.selectUserByUserId(message.getUserid());
            //2. 给团队管理员发一条消息，表示已拒绝对方的邀请。
            Message message1 = new Message();
            message1.setUserid(message.getInviteuserid());
            message1.setContent(user.getUsername() + "已拒绝加入您的团队：" +
                                teamService.findTeamByTeamid(message.getInviteteamid()).getTeamname() + "。");
            messageService.addMessage(message1, SYS_MESSAGE);
            //1. 将消息设置为已读
            messageService.updateIsRead(messageid, 1);
            map.put("success", true);
            map.put("message", "拒绝邀请成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "拒绝邀请失败！");
        }
        return map;
    }

    /**
     * 确认未读消息
     * @param request
     * @return
     */
    @GetMapping("/{messageidString}")
    public Map<String, Object> viewMessage(HttpServletRequest request,
                                           @PathVariable String messageidString) {
        System.out.println("接收到一个 已读消息 的请求");
        Map<String, Object> map = new HashMap<>();
        try {
            Integer messageid = Integer.valueOf(messageidString);
            messageService.updateIsRead(messageid, 1);
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
     * 查看用户的所有未读消息
     * @param request
     * @return
     */
    @GetMapping("/unread")
    public Map<String, Object> showUnReadMyMessages(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userid1);

            ArrayList<Message> allMessages = (ArrayList<Message>) messageService.selectMessageByUserId(userid);
            ArrayList<Message> unReadMessages = new ArrayList<>();
            ArrayList<Message> readMessages = new ArrayList<>();
            for (Message message : allMessages) {
                if (message.getIsread() == 0) {
                    unReadMessages.add(message);
                } else {
                    readMessages.add(message);
                }
            }
            map.put("unReadMessages", unReadMessages);
            map.put("readMessages", readMessages);
            map.put("success", true);
            map.put("message", "查看成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "查看失败！");
        }
        return map;
    }
}
