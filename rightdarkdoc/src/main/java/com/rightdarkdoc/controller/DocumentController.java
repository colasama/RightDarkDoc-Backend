package com.rightdarkdoc.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.config.MyConfig;
import com.rightdarkdoc.entity.Document;
import com.rightdarkdoc.entity.Team;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.*;
import com.rightdarkdoc.utils.JWTUtils;
import com.rightdarkdoc.utils.TimeUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
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

    @Autowired
    private UserTeamService userTeamService;

    @Autowired
    private TeamService teamService;


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

            if(document.getTeamid()==null){
                document.setTeamid(0);
            }
            document = TimeUtils.setDocumentTimeString(document);
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
     * 请求方法：Post
     * 请求url：/document/template
     * note : 需要token来获取userid
     * @param request
     * @param document 封装document信息的对象
     * @param tempid 模版的文档id
     * @return
     */
    @PostMapping("template/{tempid}")
    public Map<String,Object> addDocumentByTemp(HttpServletRequest request,
                                                @RequestBody(required = false) Document document,
                                                @PathVariable("tempid") Integer tempid){
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
                Document tmpDoc = documentService.selectDocByDocId(tempid);
                if(tmpDoc!=null){
                    document.setContent(tmpDoc.getContent());
                }
                else {
                    document.setContent("");
                }
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

            if(document.getTeamid()==null){
                document.setTeamid(0);
            }
            document = TimeUtils.setDocumentTimeString(document);
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
            if(token != null) {
                DecodedJWT decoder = JWTUtils.verify(token);
                if (decoder != null) {
                    String userTemp = decoder.getClaim("userid").asString();
                    userid = Integer.valueOf(userTemp);
                }
            }
            System.out.println(userid);

            Document document = documentService.selectDocByDocId(docid);
            if(document!=null){             //对应别人分享了链接之后删除了的情况，保证文档存在

                //判断是否可读
                boolean canread = false;
                if(document.getAuth()>=MyConfig.U_R){
                    canread = true;
                }
                User user = userService.selectUserByUserId(userid);
                if (user != null){
                    if(document.getCreatorid().equals(user.getUserid())){
                        canread = true;
                    }
                    if(document.getTeamauth()>MyConfig.PRIVATE && userTeamService.isTeamMember(document.getTeamid(),userid)){
                        canread = true;
                    }
                }

                //该文档没有被删除且有可读权限
               if(document.getIstrash()==0&&canread==true) {

                    //检查是否是最近浏览，如果不是将其加入
                    //检查用户是否存在,若不存在为游客
                    if (user != null) {

                        //检查是否是已浏览，若是则只更改时间
                        if (userViewDocService.selectUserViewDocByUidAndDid(userid, document.getDocid()) != null) {
                            Date date = new Date();
                            userViewDocService.updateViewtime(userid, document.getDocid(), date);
                        }
                        //若不是已浏览，则增加此记录并设置当前时间
                        else {
                            Date date = new Date();
                            userViewDocService.addUserViewDoc(userid, document.getDocid(), date);
                        }
                    }
                    remap.put("success",true);
                    remap.put("contents",document);
                    remap.put("message","get doc success");
                }
               else{
                   remap.put("success", false);
                   if(document.getIstrash()==0){
                       remap.put("message", "haven't auth");
                   }
                   else{
                       remap.put("message","this doc has been deleted");
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
            remap.put("message","get doc failed");
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
            {

                Document doc = documentService.selectDocByDocId(document.getDocid());
                boolean canmodify = false;
                if(doc!=null){

                    //判断权限
                    if(doc.getCreatorid().equals(userid)){        //创建者有修改的权限
                        canmodify = true;
                    }
                    if(doc.getAuth()>=MyConfig.U_W){          //其他人如果文档有可写权限可以修改
                        canmodify = true;
                    }
                    if(doc.getTeamauth()>=MyConfig.U_W && userTeamService.isTeamMember(doc.getTeamid(),userid)){       //团队成员有可写权限可以修改
                        canmodify  = true;
                    }

                    if(canmodify==true && doc.getIstrash()==0){        //有修改权限且没有被删除

                        //重合原来的文档与新的文档
                        document.setTeamid(doc.getTeamid());
                        document.setEditcount(doc.getEditcount());
                        document.setCreattime(doc.getCreattime());
                        document.setCreatorid(doc.getCreatorid());
                        //后台更新编辑次数，最后编辑用户，最后编辑时间等信息
                        documentService.updateDocument(document, userid);
                        remap.put("success", true);
                        remap.put("message", "modify doc successfully");
                    }
                    else{
                        remap.put("success", false);
                        if(doc.getIstrash()==1){
                            remap.put("message", "doc has been deleted");
                        }
                        else{
                            remap.put("message", "haven't auth");
                        }
                    }
                }
                else{
                    remap.put("success",false);
                    remap.put("message","doc does not exists");
                }
            }
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
     * 功能：根据创建者的id查找doc文件,是创建者的个人文件
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

            //获得用户没有放入回收站中的文档
            List<Document> docs = documentService.selectDocByCreatorId(userid);
            m.put("success",true);
            m.put("contents",docs);
        } catch (Exception ex){
            m.put("success",false);
            m.put("message","failed to get");
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
    public Map<String,Object> updateDocTitle(@RequestBody Map<String,Object> m,HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();

        //调用接口更改文件名
        try{
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);

            //获取参数
            String docidTemp = m.get("docid").toString();
            Integer docid = Integer.valueOf(docidTemp);
            String title = m.get("title").toString();

            Document  doc = documentService.selectDocByDocId(docid);
            boolean canmodify = false;


            if(doc!=null){

                if(doc.getCreatorid().equals(userid)){        //创建者有修改的权限
                    canmodify = true;
                }
                if(doc.getAuth()>=MyConfig.U_W){          //其他人如果文档有可写权限可以修改
                    canmodify = true;
                }
                if(doc.getTeamauth()>=MyConfig.U_W && userTeamService.isTeamMember(doc.getTeamid(),userid)){       //团队成员有可写权限可以修改
                    canmodify  = true;
                }

                //文件名是可以更改的
                if(canmodify == true && doc.getIstrash()==0){
                    //更改文件名
                    documentService.updateDocTitle(docid,title);
                    remap.put("success",true);
                    remap.put("message","modify doc title successfully");
                }
                else{
                    remap.put("success", false);
                    if(doc.getIstrash()==1){
                        remap.put("message", "doc has been deleted");
                    }
                    else{
                        remap.put("message", "haven't auth");
                    }
                }
            }
            else{
                remap.put("success",false);
                remap.put("message","doc does not exists");
            }

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
    public Map<String,Object> updateDocAuth(@RequestBody Map<String,Object> m,HttpServletRequest request){

        Map<String,Object> remap = new HashMap<>();

        try{
            //获取用户名
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            //获取参数
            String docidTemp = m.get("docid").toString();
            Integer docid = Integer.valueOf(docidTemp);
            String authTemp = m.get("auth").toString();
            Integer auth = Integer.valueOf(authTemp);


            Document  doc = documentService.selectDocByDocId(docid);
            boolean canmodify = false;


            if(doc!=null){
                if(doc.getCreatorid().equals(userid)){        //创建者有修改的权限
                    canmodify = true;
                }
                if(canmodify == true){
                    documentService.updateDocAuth(docid,auth);
                    remap.put("success",true);
                    remap.put("message","modify doc auth successfully");
                }
                else{
                    remap.put("success",false);
                    remap.put("message","have't auth");
                }
            }else{
                remap.put("success", false);
                if(doc.getIstrash()==1){
                    remap.put("message", "doc has been deleted");
                }
                else{
                    remap.put("message", "haven't auth");
                }
            }
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
    public Map<String,Object> updateDocTeamAuth(@RequestBody Map<String,Object> m,HttpServletRequest request){
        Map<String,Object> remap = new HashMap<>();

        //获取参数
        try{
            //获取用户名
            String token = request.getHeader("token");
            DecodedJWT decoder = JWTUtils.verify(token);
            String userTemp = decoder.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userTemp);
            //
            String docidTemp = m.get("docid").toString();
            Integer docid = Integer.valueOf(docidTemp);
            String authTemp = m.get("teamauth").toString();
            Integer teamauth = Integer.valueOf(authTemp);
            Document document = documentService.selectDocByDocId(docid);

            boolean canmod = false;

            if(document==null){

                remap.put("success",false);
                remap.put("message","doc does not exists");
                return remap;
            }

            //检查权限:文档的创建者和团队组长能够修改文档的团队权限
            if(document.getCreatorid().equals(userid)) {
                canmod = true;
            }
            Team team = teamService.findTeamByTeamid(document.getTeamid());
            if(team!=null) {       //如果是一篇团队文档
                if(userid.equals(team.getCreatorid())){
                    canmod = true;
                }
            }

            if(canmod == true){
                document.setTeamauth(teamauth);
                documentService.updateDocument(document,document.getLastedituserid());
                remap.put("success", true);
                remap.put("message", "modify doc team auth successfully");
            }
            else{
                remap.put("success", true);
                remap.put("message", "haven't auth");
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
                return m;
            }

            boolean canDel = false;
            if(document.getCreatorid().equals(userid)){
                canDel = true;
            }
            Team team = teamService.findTeamByTeamid(document.getTeamid());
            if(team!=null){           //是一个团队文档
                if(team.getCreatorid().equals(userid)){
                    canDel = true;
                }
            }


            if(canDel==true){
                //将文件放入trash，并删除在文档上的收藏以及浏览
                documentService.docMoveToTrash(document);
                m.put("success",true);
                m.put("message","move to trash successfully");
            }
            else{
                m.put("success",false);
                m.put("message","haven't auth");
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
     * 功能：永久删除一个文档,只能是团队的创建者永久删除其文档
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
                return m;
            }
            if(!document.getCreatorid().equals(userid)){
                m.put("success",false);
                m.put("message","haven't authority to delete doc permanently");
                return  m;
            }
            else{
                //直接删除
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
     * 功能:清空回收站里的所有doc文档，回收站里的文档的创建者都应该是这个用户，也就是说，只有创建者才能删除所有文档
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
     * 功能：恢复一个被放入回收站的文档，恢复文档并不恢复收藏和浏览，只有创建者能够恢复
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
                Document doc = documentService.selectDocByDocId(docids.get(i));
                if(doc!=null){              //判断是否搜索到了这个文档
                    reList.add(doc);
                }
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
     * 功能: 查找用户最近浏览，根据时间排序，最多显示50个
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
            for(int i = 0,j=0; i<len&&j< MyConfig.MAX_VIEW_DOC_NUM; i++){
                Document doc = documentService.selectDocByDocId(docids.get(i));
                if(doc!=null){              //最多显示MAX_VIEW_DOC_NUM个最近浏览文档
                    j++;
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
            List<Document> docs = documentService.selectDocInTrashByCreatorId(userid);
            remap.put("success",true);
            remap.put("contents",docs);
            remap.put("message","get trash doc successfully");
        } catch (Exception ex){
            ex.printStackTrace();
            remap.put("success",false);
            remap.put("message","failed to get trash doc");
        }
        return remap;
    }

}
