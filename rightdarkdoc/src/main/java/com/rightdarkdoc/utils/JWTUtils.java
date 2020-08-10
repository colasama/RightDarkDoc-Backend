package com.rightdarkdoc.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

public class JWTUtils {

    /**
     * 密钥生成
     */
    private static final String  SING = "!xiao@yuan@yuan@tian@xia@di@yi!";

    /**
     * 生成token
     * @param map   保存放在payload中的数据
     * @return
     */
    public static String getToken(Map<String,String> map){

        //默认7天过期
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE,7);

        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        //map数据放入payload中
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(instance.getTime())    //指定令牌过期时间
                .sign(Algorithm.HMAC256(SING));                     //sign
        return token;
    }

    /**
     * 验证token 合法性
     *
     */
    public static DecodedJWT verify(String token){
        return JWT.require(Algorithm.HMAC256(SING)).build().verify(token);
    }
}
