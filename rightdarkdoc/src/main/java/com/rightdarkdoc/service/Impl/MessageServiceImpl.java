package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.MessageDao;
import com.rightdarkdoc.entity.Message;
import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.MessageService;
import com.rightdarkdoc.service.TeamService;
import com.rightdarkdoc.service.UserService;
import com.rightdarkdoc.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    /**
     * 添加一个某类型的message
     * @param message       消息
     * @param type          消息类型
     * @return
     */
    @Override
    public Integer addMessage(Message message, Integer type) {
        //加上时间
        Date date = new Date();
        String dateString = TimeUtils.formatTime(date);
        message.setMessagetime(date);
        message.setMessagetimestring(dateString);

        //加上类型
        message.setType(type);

        //设置默认为未读
        message.setIsread(0);

        //添加消息
        return messageDao.addMessage(message);
    }

    /**
     * 根据用户id和type查找message
     *
     * @param userid
     * @param type
     * @return
     */
    @Override
    public List<Message> selectMessageByUserIdAndType(Integer userid, Integer type) {
        return messageDao.selectMsgByUidAndType(userid, type);
    }

    /**
     * 用户申请加入团队时创建消息
     *  @param teamid 申请加入的团队id
     * @param applyuserid 申请者的用户id
     * @param type   APPLY_MESSAGE
     * @return
     */
    @Override
    public boolean applyTeamMessage(Integer teamid, Integer applyuserid, Integer type) {
        Message message = new Message();
        //取出team
        Team team = teamService.findTeamByTeamid(teamid);

        if (team != null) {
            //取出团队创建者id，作为message的接受方
            Integer teamCreatorId = team.getCreatorid();
            message.setUserid(teamCreatorId);

            //设置message的applyuserid和applyteamid
            message.setApplyuserid(applyuserid);
            message.setApplyteamid(teamid);
            User user = userService.selectUserByUserId(applyuserid);

            //设置message内容
            message.setContent("您收到一条来自用户" + user.getUsername() + "(uid: " + applyuserid.toString() + ")" + "的团队申请消息。");

            //创建这条消息
            addMessage(message, type);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * 邀请团队新成员时创建消息
     *
     * @param inviteteamid
     * @param inviteuserid
     * @param type
     * @return
     */
    @Override
    public void inviteTeamMemberMessage(Integer inviteteamid, Integer invitorid, Integer inviteuserid, Integer type) {
        Message message = new Message();
        //取出被邀请者id，作为message的接受方
        message.setUserid(inviteuserid);

        //设置message的inviteteamid
        message.setInviteteamid(inviteteamid);
        message.setInviteuserid(invitorid);

        //设置message内容
        Team team = teamService.findTeamByTeamid(inviteteamid);
        message.setContent("您收到一条来自团队" + team.getTeamname() + "(teamid: " + team.getTeamid().toString() + ")" + "的邀请。");

        //创建这条消息
        addMessage(message, type);
    }

    /**
     * 修改消息的isread字段
     *
     * @param messageid
     * @param isread
     */
    @Override
    public void updateIsRead(Integer messageid, Integer isread) {
        messageDao.updateIsRead(messageid, isread);
    }

    /**
     * 根据messageid查找message
     *
     * @param messageid
     * @return
     */
    @Override
    public Message selectMessageByMessageId(Integer messageid) {
        return messageDao.selectMsgById(messageid);
    }

}
