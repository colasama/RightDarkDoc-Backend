package com.rightdarkdoc.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.Document;
import com.rightdarkdoc.service.DocumentService;
import com.rightdarkdoc.utils.JWTUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
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
     * 请求方法：Post
     * 请求URL：/document
     * 功能：创建一个新文档
     * note: 需要token
     * @param request
     * @param document
     * @return
     */
    @PostMapping("")
    public Map<String,Object> addDocument(HttpServletRequest request,@RequestBody Document document){

        Map<String,Object> m = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);


            if(document.getContent()==null){
                document.setContent("");
            }
            if(document.getTitle()==null){
                document.setTitle("untitle");
            }
            Date date = new Date();


            //设置文档的创建者
            document.setCreatorid(userid);
            document.setEditcount(1);
            document.setIstrash(0);
            document.setLastedituserid(userid);
            document.setAuth(7);
            document.setTeamauth(1);
            document.setCreattime(date);
            document.setLastedittime(date);
            //创建document
            documentService.addDocument(document);
            m.put("success",true);
            m.put("message","create file successfully");

        } catch (Exception ex){
            ex.printStackTrace();
            m.put("success",false);
            m.put("message","failed to create doc");
        }

        return m;
    }


    /**
     * 请求方法：Get
     * 请求url：/document/{docid}
     * 功能：根据docid查找对应的doc文档
     * @param docid
     * @return
     */
    @GetMapping("{docid}")
    public Map<String,Object> getDocument(@PathVariable("docid") Integer docid){
        Map<String,Object> remap = new HashMap<>();
        Document document = documentService.selectDocByDocId(docid);
        if(document!=null){
            remap.put("success",true);
            remap.put("contents",document);
            documentService.updateDocument(document);
        }
        else{
            remap.put("success",false);
            remap.put("contents",document);
        }
        return remap;
    }

    /**
     * 请求方法：Put
     * 请求Url：/document
     * 功能： 更新doc的信息，用于打开文件编辑之后的保存
     * note: 需要token，用来设置最后编辑用户的id
     * @param document  封装更新的信息
     * @return 封装了信息的map
     */
    @PutMapping("")
    public Map<String,Object> updateDocument(@RequestBody Document document,HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            //后台更新编辑次数，最后编辑用户，最后编辑时间等信息
            document.setEditcount(document.getEditcount()+1);
            Date date = new Date();
            document.setLastedittime(date);
            document.setLastedituserid(userid);
            documentService.updateDocument(document);
            remap.put("success",true);
            remap.put("message","modify doc successfully");
        } catch(Exception ex) {
            remap.put("success",true);
            remap.put("message","failed to modify doc");
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
    @GetMapping("creator")
    public Map<String,Object> getDocumentByCreator(HttpServletRequest request){
        Map<String,Object> m = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            List<Document> docs = documentService.selectDocByCreatorId(userid);
            m.put("success",true);
            m.put("contents",docs);
        } catch (Exception ex){
            m.put("success",false);
            m.put("message","token error");
        }
        return m;
    }


    /**
     *  请求方法：PUT
     *  请求URL：/document/mod_title
     *  功能：更改文档的名称
     *  参数：1.int:docid,string:title
     * @param m 包含参数的map
     * @return 包含返回信息的map
     */
    @PutMapping("mod_title")
    public Map<String,Object> updateDocTitle(@RequestBody Map<String,Object> m){
        Map<String,Object> remap = new HashMap<>();

        //获取参数
        String docidTemp = m.get("docid").toString();
        Integer docid = Integer.valueOf(docidTemp);
        String title = m.get("title").toString();

        //调用接口更改文件名
        try{
            documentService.updateDocTitle(docid,title);
            remap.put("success",true);
            remap.put("message","modify doc title successfully");
        } catch (Exception ex){
            remap.put("success",false);
            remap.put("message","failed to modify title");
        }

        return remap;
    }

    /**
     *  请求方法：PUT
     *  请求URL：/document/mod_auth
     *  功能：更改文档的权限
     *  参数：1.int:docid,int:auth
     * @param m 包含参数的map
     * @return
     */
    @PutMapping("mod_auth")
    public Map<String,Object> updateDocAuth(@RequestBody Map<String,Object> m){

        Map<String,Object> remap = new HashMap<>();

        //获取参数
        String docidTemp = m.get("docid").toString();
        Integer docid = Integer.valueOf(docidTemp);
        String authTemp = m.get("auth").toString();
        Integer auth = Integer.valueOf(authTemp);

        //调用接口更改用户权限
        try{
            documentService.updateDocAuth(docid,auth);
            remap.put("success",true);
            remap.put("message","modify doc auth successfully");
        } catch (Exception ex){
            remap.put("success",false);
            remap.put("message","failed to modify doc auth");
        }
        return remap;
    }



    /**
     *  请求方法：PUT
     *  请求URL：/document/mod_teamauth
     *  功能：更改文档的团队权限
     *  参数：1.int:docid,int:teamauth
     * @param m 包含参数的map
     * @return
     */
    @PutMapping("mod_teamauth")
    public Map<String,Object> updateDocTeamAuth(@RequestBody Map<String,Object> m){
        Map<String,Object> remap = new HashMap<>();

        //获取参数
        String docidTemp = m.get("docid").toString();
        Integer docid = Integer.valueOf(docidTemp);
        String authTemp = m.get("teamauth").toString();
        Integer teamauth = Integer.valueOf(authTemp);

        try{
            Document document = documentService.selectDocByDocId(docid);
            if(document==null){
                remap.put("success",false);
                remap.put("message","doc does not exists");
            }
            else {
                document.setTeamauth(teamauth);
                documentService.updateDocument(document);
                remap.put("success", true);
                remap.put("message", "modify doc team auth successfully");
            }
        } catch (Exception ex){
            remap.put("success",false);
            remap.put("message","failed to modify doc auth");
        }

        return remap;
    }


    /**
     * 请求方法：Delete
     * 请求Url：/document/{docid}
     * 功能：将指定的文件移入垃圾箱
     * note：需要token来查找用户身份，来确定用户是否有权限将其放入垃圾箱
     * @param docid 要移入垃圾箱的doc文件id
     * @return  包含返回怒信息的对象
     */
    @DeleteMapping("{docid}")
    public Map<String,Object> moveDocToTrash(@PathVariable("docid") Integer docid,HttpServletRequest request){

        Map<String,Object> m = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            Document document = documentService.selectDocByDocId(docid);
            if(document==null){
                m.put("success",false);
                m.put("message","doc does not exists");
            }
            else if(!document.getCreatorid().equals(userid)){
                m.put("success",false);
                m.put("message","haven't authority to move doc to trash");
            }
            else{
                document.setIstrash(1);
                documentService.updateDocument(document);
                m.put("success",true);
                m.put("message","move to trash successfully");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            m.put("success",false);
            m.put("message","failed to move to trash");
        }
        return m;
    }


    @PutMapping("recover/{docid}")
    public Map<String,Object> recoverDocFromTrash(@PathVariable("docid") Integer docid,HttpServletRequest request){

        Map<String,Object> m = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            Document document = documentService.selectDocByDocId(docid);
            if(document==null){
                m.put("success",false);
                m.put("message","doc does not exists");
            }
            else if(!document.getCreatorid().equals(userid)){
                m.put("success",false);
                m.put("message","haven't authority to recover doc");
            }
            else{
                document.setIstrash(0);
                documentService.updateDocument(document);
                m.put("success",true);
                m.put("message","recover from trash successfully");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            m.put("success",false);
            m.put("message","failed to move to trash");
        }
        return m;
    }




}
