package com.rightdarkdoc.utils;

import com.rightdarkdoc.entity.User;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PayloadUtils {
    public static Map<String, String> payloadCreator(User user) {
        Map<String, String> payload = new HashMap<>();

        //把user中的数据注入payload

        Integer userid = user.getUserid();
        String username = user.getUsername();
        String phone = user.getPhone();
        String email = user.getEmail();

        //生日Date转换为String
        Date birthday = user.getBirthday();
        String birthdayString = "";
        if (birthday != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            birthdayString = simpleDateFormat.format(birthday);
        }

        //头像 可能为空值
        String avatar = "";
        if(user.getAvatar() != null) {
            avatar = user.getAvatar();
        }

        //个人描述
        String description = "这个人很神秘，什么都没写。";
        if(user.getDescription() != null) {
            description = user.getDescription();
        }

        payload.put("userid", userid.toString());
        payload.put("username", username);
        payload.put("phone", phone);
        payload.put("birthday", birthdayString);
        payload.put("email", email);
        payload.put("avatar", avatar);
        payload.put("description", description);

        return payload;
    }
}
