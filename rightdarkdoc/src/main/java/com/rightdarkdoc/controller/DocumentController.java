package com.rightdarkdoc.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.Document;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.DocumentService;
import com.rightdarkdoc.service.UserFavDocService;
import com.rightdarkdoc.service.UserService;
import com.rightdarkdoc.service.UserViewDocService;
import com.rightdarkdoc.utils.JWTUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserFavDocService userFavDocService;

    @Autowired
    private UserViewDocService userViewDocService;


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
    public Map<String,Object> addDocument(HttpServletRequest request,@RequestBody(required = false) Document document){

        Map<String,Object> m = new HashMap<>();
        try {
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);

            if(document == null){
                document = new Document();
            }

            if(document.getContent()==null){
                document.setContent("");
            }
            if(document.getTitle()==null){
                document.setTitle("untitle");
            }
            Date date = new Date();

            //设置文档的创建者

            if(document.getCreatorid()==null){
                document.setCreatorid(userid);
            }

            if(document.getEditcount()==null){
                document.setEditcount(1);
            }

            if(document.getIstrash()==null){
                document.setIstrash(0);
            }

            if(document.getLastedituserid()==null){
                document.setLastedituserid(userid);
            }

            if(document.getAuth()==null){
                document.setAuth(0);
            }

            if(document.getTeamauth()==null){
                document.setTeamauth(1);
            }

            if(document.getCreattime()==null){
                document.setCreattime(date);
            }

            if(document.getLastedittime()==null){
                document.setLastedittime(date);
            }
            //创建document
            documentService.addDocument(document);
            userViewDocService.addUserViewDoc(userid,document.getDocid(),date);
            System.out.println(document);
            m.put("contents",document);
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
     * 功能：根据docid查找对应的doc文档,并将其加入用户的最近浏览列表
     * note ： 需要token
     * @param docid
     * @return
     */
    @GetMapping("{docid}")
    public Map<String,Object> getDocument(@PathVariable("docid") Integer docid,HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();
        try {
            String token = request.getHeader("token");
            Integer userid = 0;

            //未登陆的人也可以访问
            if(token!=null) {
                DecodedJWT decoder = JWTUtils.verify(token);
                String userTemp = decoder.getClaim("userid").asString();
                userid = Integer.valueOf(userTemp);
            }
            Document document = documentService.selectDocByDocId(docid);
            if(document!=null){
                remap.put("success",true);
                remap.put("contents",document);
                //检查是否是最近浏览，如果不是将其加入
                System.out.println(userid);
                System.out.println(document.getDocid());

                //检查用户是否存在
                if(userService.selectUserByUserId(userid)!=null){

                    //检查是否是已浏览，若是则只更改时间
                    if(userViewDocService.selectUserViewDocByUidAndDid(userid,document.getDocid())!=null){
                        Date date = new Date();
                        userViewDocService.updateViewtime(userid,document.getDocid(),date);
                    }
                    //若不是已浏览，则增加此记录并设置当前时间
                    else{
                        Date date = new Date();
                        userViewDocService.addUserViewDoc(userid,document.getDocid(),date);
                    }
                }
            }
            else{
                remap.put("success",false);
                remap.put("message","doc does not exists");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","token error");
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
            documentService.updateDocument(document,userid);
            remap.put("success",true);
            remap.put("message","modify doc successfully");
        } catch(Exception ex) {
            ex.printStackTrace();
            remap.put("success",false);
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
            List<Document> reList  = new ArrayList<>();
            int len = docs.size();
            for(int i=0;i<len;i++){
                Document doc = docs.get(i);
                if(docs.get(i).getIstrash()==0){
                    reList.add(doc);
                }
            }
            m.put("success",true);
            m.put("contents",reList);
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
                documentService.updateDocument(document,document.getLastedituserid());
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
     * @return  包含返回信息的对象
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
                documentService.updateDocument(document,document.getLastedituserid());
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

    /**
     * 请求方法：Delete
     * 请求Url：/document/permanent/{int:docid}
     * 功能：永久删除一个文档
     * @param docid 永久删除的文档id
     * @param request  请求体
     * @return  封装返回信息的map
     */
    @DeleteMapping("/permanent/{docid}")
    public Map<String,Object> permanentDelete(@PathVariable("docid") Integer docid,HttpServletRequest request){
        Map<String,Object> m = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            Document document = documentService.selectDocByDocId(docid);
            System.out.println(document);
            if(document==null){
                m.put("success",false);
                m.put("message","doc does not exists");
            }
            else if(!document.getCreatorid().equals(userid)){
                m.put("success",false);
                m.put("message","haven't authority to delete doc permanently");
            }
            else{
                documentService.deleteDoc(docid);
                m.put("success",true);
                m.put("message","successfully delete doc permanently");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            m.put("success",false);
            m.put("message","failed to delete permanently");
        }
        return m;
    }


    /**
     * 请求方法：Delete
     * 请求Url：/document/permanent/all
     * 功能:清空回收站里的所有doc文档，回收站里的文档的创建者都应该是这个用户，也就是说，只有
     *  创建者才有权限删除文档
     */
    @DeleteMapping("/permanent/all")
    public Map<String,Object> clearUserTrash(HttpServletRequest request){

        Map<String,Object> m = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            documentService.deleteAllDocInTrash(userid);
            m.put("success",true);
            m.put("message","successfully clear trash");
        } catch (Exception ex){
            ex.printStackTrace();
            m.put("success",false);
            m.put("message","failed to clear trash");
        }
        return m;
    }


    /**
     * 请求方法：Put
     * 请求Url：/recover/{int:docid}
     * 功能：恢复一个被放入回收站的文档
     * note : 需要token
     * @param docid 从回收站恢复的文档id
     * @param request   请求体
     * @return
     */
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
                documentService.updateDocument(document,document.getLastedituserid());
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

    /**
     * 请求方法：Post
     * 请求URL: /document/fav
     * 功能：增加用户的收藏文档
     * note： 需要token
     * @param request 请求
     * @param map 1.int:docid
     * @return
     */
    @PostMapping("fav")
    public Map<String,Object> addUserFavDoc(HttpServletRequest request, @RequestBody Map<String,Object> map){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            Integer docid = Integer.valueOf(map.get("docid").toString());
            Integer judge = userFavDocService.selectDocByUidAndDid(userid,docid);
            System.out.println(judge);
            if(judge!=null){
                remap.put("success",false);
                remap.put("message","already add this doc to favorites");
            }
            else {
                userFavDocService.addUserFavDoc(userid, docid);
                remap.put("success", true);
                remap.put("message", "Add to favorites successfully");
            }
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","failed to add to favorites");
        }
        return remap;
    }

    /**
     * 请求方法：Delete
     * 请求URL: /document/fav
     * 功能:删除用户的收藏文档
     * note： 需要token
     * @param request 请求
     * @param map 封装数据的map
     * @return
     */
    @DeleteMapping("fav")
    public Map<String,Object> delUserFavDoc(HttpServletRequest request,@RequestBody Map<String,Object> map){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            Integer docid = Integer.valueOf(map.get("docid").toString());
            userFavDocService.deleteUserFavDoc(userid,docid);
            remap.put("success",true);
            remap.put("message","remove from favorites successfully");
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","failed to remove from favorite");
        }
        return remap;
    }


    /**
     * 请求方法：Get
     * 请求URL: /document/fav
     * 功能:获取用户的收藏文档
     * note： 需要token
     * @param request 请求
     * @return
     */
    @GetMapping("fav")
    public Map<String,Object> getDocByUserid(HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            System.out.println(userid);
            List<Integer> docids = userFavDocService.selectDocByUserid(userid);
            List<Document> reList = new ArrayList<>();
            int len = docids.size();
            System.out.println(len);
            for(int i=0;i<len;i++){
                reList.add(documentService.selectDocByDocId(docids.get(i)));
            }
            remap.put("success",true);
            remap.put("contents",reList);
            remap.put("message","get favorites successfully");
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","failed to get favorites");
        }
        return remap;
    }


    /**
     * 请求方法：Get
     * 请求URL: /document/fav/{docid}
     * 功能:获取用户的是否收藏了文档
     * note： 需要token
     * @param docid 文档的id
     * @param request 请求体
     * @return
     */
    @GetMapping("fav/{docid}")
    public Map<String,Object> getFavDocByUidAndDid(@PathVariable("docid") Integer docid,HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            Integer judge = userFavDocService.selectDocByUidAndDid(userid,docid);
            System.out.println(judge);
            if(judge==null){
                remap.put("success",true);
                remap.put("isFav",false);
            } else{
                remap.put("success",true);
                remap.put("isFav",true);
            }
        } catch (Exception ex){
            remap.put("success",false);
            remap.put("isFav",false);
        }
        return remap;
    }


    /**
     * 请求方法：Get
     * 请求URL: /document/view
     * 功能: 查找用户最近浏览
     * note： 需要token
     * @param request 请求
     * @return 封装返回信息的map
     */
    @GetMapping("view")
    public Map<String,Object> getUserViewDoc(HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            List<Integer> docids = userViewDocService.selectUserViewDoc(userid);
            List<Document> reList = new ArrayList<>();
            int len = docids.size();
            System.out.println(len);
            for(int i=0;i<len;i++){
                Document doc = documentService.selectDocByDocId(docids.get(i));
                if(doc!=null){
                    reList.add(doc);
                }
            }
            System.out.println(reList);
            remap.put("success",true);
            remap.put("contents",reList);
            remap.put("message","get view successfully");
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","failed to get view doc");
        }
        return remap;
    }

    /**
     * 请求方法：Get
     * 请求URL: /document/trash
     * 功能: 查找用户回收站中的文档
     * note： 需要token
     * @param request 请求
     * @return 封装返回信息的map
     */
    @GetMapping("trash")
    public Map<String,Object> getUserTrash(HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            List<Document> docs = documentService.selectDocByCreatorId(userid);
            int len = docs.size();
            List<Document> reList = new ArrayList<>();
            for(int i=0;i<len;i++){
                Document doc = docs.get(i);
                if(doc.getIstrash()==1){
                    reList.add(doc);
                }
            }
            remap.put("success",true);
            remap.put("contents",reList);
            remap.put("message","get trash doc successfully");
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","failed to get trash doc");
        }
        return remap;
    }


}
