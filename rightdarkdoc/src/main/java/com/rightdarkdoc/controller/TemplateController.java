package com.rightdarkdoc.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.Template;
import com.rightdarkdoc.service.Impl.TemplateServiceImpl;
import com.rightdarkdoc.utils.JWTUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("template")
public class TemplateController {

    @Autowired
    private TemplateServiceImpl templateService;



    @PostMapping("")
    public Map<String,Object> createTemp(HttpServletRequest request, @RequestBody Template template){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            templateService.createTemplate(template,userid);
            remap.put("success",true);
            remap.put("contents",template);
            remap.put("message","get message successfully");
            return  remap;
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","create template failed");
            return remap;
        }
    }



    @PostMapping("pub")
    public Map<String,Object> createPubTemp(HttpServletRequest request,
                                            @RequestBody Template template){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            templateService.createPubTemplate(template,userid);
            remap.put("success",true);
            remap.put("contents",template);
            remap.put("message","get message successfully");
            return remap;
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","create template failed");
            return remap;
        }
    }


    @GetMapping("{tempid}")
    public Map<String,Object> getTemplate(@PathVariable("tempid") Integer tempid,
                                          HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            templateService.delTemplate(tempid);
            remap.put("success",true);
            remap.put("message","get template successfully");
            return remap;
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","get template failed");
            return remap;
        }
    }

    @GetMapping("creator")
    public Map<String,Object> getTemplate(HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            List<Template> temps = templateService.selectTempByUserid(userid);
            remap.put("contents",temps);
            remap.put("success",true);
            remap.put("message","get template successfully");
            return remap;
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","get template failed");
            return remap;
        }
    }


}
