package com.rightdarkdoc.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.rightdarkdoc.entity.Comment;
import com.rightdarkdoc.entity.Document;
import com.rightdarkdoc.entity.User;
import com.rightdarkdoc.service.CommentService;
import com.rightdarkdoc.service.DocumentService;
import com.rightdarkdoc.service.TeamService;
import com.rightdarkdoc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class CommentController {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TeamService teamService;

    /**
     * 给团队文档创建一个新的评论
     * @param request
     * @param requestmap        需要content和里面的内容
     * @param docidString
     * @param teamidString
     * @return
     */
    @PostMapping("/team/{teamidString}/document/{docidString}/createComment")
    public Map<String, Object> createTeamDocComment(HttpServletRequest request,
                                               @RequestBody Map<String, String> requestmap,
                                               @PathVariable String docidString,
                                               @PathVariable String teamidString) {
        System.out.println("接收到一个团队文档评论请求");
        Map<String, Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userid1);

            //判断document是否可评论
            Integer docid = Integer.valueOf(docidString);
            Document document = documentService.selectDocByDocId(docid);
            Boolean isCommentable = true;
            if (document.getIstrash() == 1 || document.getTeamauth() <= 1) {
                isCommentable = false;
            }

            if (isCommentable) {
                String content = requestmap.get("content");
                Comment comment = new Comment(docid, content, userid);
                commentService.createNewComment(comment);
                map.put("success", true);
                map.put("message", "用户评论成功！");
            }
            else {
                map.put("success", false);
                map.put("message", "用户没有评论权限！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "用户评论失败！");
        }
        return map;
    }

    /**
     * 删除评论   只有评论者自己或者是文档作者可以删除评论
     * @param request
     * @param requestmap        需要评论的id comid
     * @param teamidString
     * @param docidString
     * @return
     */
    @PostMapping("/document/{docidString}/deleteComment")
    public Map<String, Object> deleteTeamDocComment(HttpServletRequest request,
                                             @RequestBody Map<String, String> requestmap,
//                                             @PathVariable String teamidString,
                                             @PathVariable String docidString) {
        System.out.println("接收到一个团队文档评论请求");
        Map<String, Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userid1);

            //判断是否有删除权限（只有评论者自己和团队的队长可以删除）
//            Integer teamid = Integer.valueOf(teamidString);
//            Integer teamCreatorId = teamService.findTeamByTeamid(teamid).getCreatorid();    //团队队长
            String comidString = requestmap.get("comid");
            Integer comid = Integer.valueOf(comidString);
            Integer commentCreatorId = commentService.findCommentByCommentId(comid).getUserid();    //创建者id

            //文档创建者id
            Integer docid = Integer.valueOf(docidString);
            Integer documentCreatorId = documentService.selectDocByDocId(docid).getCreatorid();

            Boolean isDeleteable = false;
//            if ((teamCreatorId.equals(userid)) || (commentCreatorId.equals(userid))) {
//                isDeleteable = true;
//            }

            if(documentCreatorId.equals(userid) || commentCreatorId.equals(userid)) {
                isDeleteable = true;
            }

            if (isDeleteable) {
                commentService.deleteComment(comid);
                map.put("success", true);
                map.put("message", "删除评论成功！");
            }
            else {
                map.put("success", false);
                map.put("message", "用户没有删除权限！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "用户删除失败！");
        }
        return map;
    }

    /**
     * 显示文档的所有评论
     * @param request
     * @param docidString
     * @return
     */
    @GetMapping("/document/{docidString}/comments")
    public Map<String, Object> showTeamDocComments(HttpServletRequest request,
                                             @PathVariable String docidString) {
        System.out.println("接收到一个查看团队文档所有评论请求");
        Map<String, Object> map = new HashMap<>();
        try {
            Integer docid = Integer.valueOf(docidString);
            List<Comment> comments = new ArrayList<>();
            comments = commentService.findAllCommentsByDocumentId(docid);
            map.put("comments", comments);
            map.put("success", true);
            map.put("commentsNumber", comments.size());
            map.put("message", "查看评论成功！");

        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "查看评论失败！");
        }
        return map;
    }


    /**
     * 给一个普通文档创建评论
     * @param request
     * @param requestmap
     * @param docidString
     * @return
     */
    @PostMapping("/document/{docidString}/createComment")
    public Map<String, Object> createNormalDocComment(HttpServletRequest request,
                                                    @RequestBody Map<String, String> requestmap,
                                                    @PathVariable String docidString) {
        System.out.println("接收到一个普通文档评论请求");
        Map<String, Object> map = new HashMap<>();
        try {
            String token = request.getHeader("token");
            //取出token中的用户id
            DecodedJWT verify = JWTUtils.verify(token);
            String userid1 = verify.getClaim("userid").asString();
            Integer userid = Integer.valueOf(userid1);

            //判断document是否可评论
            Integer docid = Integer.valueOf(docidString);
            Document document = documentService.selectDocByDocId(docid);
            Boolean isCommentable = true;
            System.out.println(document.getIstrash());
            System.out.println(document.getAuth());
            if (document.getIstrash() == 1 || document.getAuth() <= 1) {
                isCommentable = false;
            }
            System.out.println(isCommentable);
            if (isCommentable) {
                String content = requestmap.get("content");
                Comment comment = new Comment(docid, content, userid);
                commentService.createNewComment(comment);
                map.put("success", true);
                map.put("message", "用户评论成功！");
            }
            else {
                map.put("success", false);
                map.put("message", "该文档您不能评论！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("message", "用户评论失败！");
        }
        return map;
    }

}
