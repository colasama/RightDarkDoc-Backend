package com.rightdarkdoc.utils;

import com.rightdarkdoc.entity.Comment;
import com.rightdarkdoc.entity.Document;

import java.util.ArrayList;
import java.util.Comparator;

public class SortUtils {

    /**
     * 文档根据最后编辑时间排序 从大到小排序
     * @param documents
     * @return
     */
    public static ArrayList<Document>  sortByLastEditTime(ArrayList<Document> documents) {
        documents.sort(new Comparator<Document>() {
            @Override
            public int compare(Document o1, Document o2) {
                if (o2 == null) {
                    return -1;
                } else if (o1 == null) {
                    return 1;
                } else {
                    return o2.getLastedittime().compareTo(o1.getLastedittime());
                }
            }
        });

        return documents;
    }

    /**
     * 给文档评论按照评论时间排序 从小到大排序
     * @param comments
     * @return
     */
    public static ArrayList<Comment> sortByCommentCreatTime(ArrayList<Comment> comments) {
        comments.sort(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                if (o2 == null) {
                    return 1;
                } else if (o1 == null) {
                    return -1;
                } else {
                    return o1.getCommenttime().compareTo(o2.getCommenttime());
                }
            }
        });
        return comments;
    }
}
