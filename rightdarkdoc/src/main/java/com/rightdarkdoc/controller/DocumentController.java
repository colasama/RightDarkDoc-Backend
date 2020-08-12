package com.rightdarkdoc.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.Document;
import com.rightdarkdoc.service.DocumentService;
import com.rightdarkdoc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/document")
@CrossOrigin
public class DocumentController {

    @Autowired
    private DocumentService documentService;


    /**
     * 请求方法：Get
     * 请求url：/document/{docid}
     * 功能：根据docid查找对应的doc文档
     * @param docid
     * @return
     */
    @GetMapping("/{docid}")
    public Map<String,Object> getDocument(@PathVariable("docid") Integer docid){
        Map<String,Object> remap = new HashMap<>();
        Document document = documentService.selectDocByDocId(docid);
        if(document!=null){
            remap.put("success",true);
            remap.put("contents",document);
        }
        else{
            remap.put("success",false);
        }
        return remap;
    }


    /**
     * 请求方法：Get
     * 请求url：/document/creator
     * 功能：根据创建者的id查找doc文件
     * note: 需要token，用token来识别当前用户身份
     * @return
     */
    @GetMapping("/creator")
    public Map<String,Object> getDocumentByCreator(HttpServletRequest request){
        Map<String,Object> m = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            Integer userid = decoder.getClaim("userid").asInt();
            List<Document> docs = documentService.selectDocByCreatorId(userid);
            m.put("success",true);
            m.put("contents",docs);
        } catch (Exception ex){
            m.put("success",false);
        }
        return m;
    }




}
