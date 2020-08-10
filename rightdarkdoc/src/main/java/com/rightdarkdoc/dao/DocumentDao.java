package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Document;
import org.apache.ibatis.annotations.*;

@Mapper
public interface DocumentDao {

    @Insert("insert into Document values(#{docId},#{creatorId},#{title},#{content},#{createTime},#{lastEditTime}," +
            "#{editCount},#{lastEditUserId},#{auth},#{teamAuth},#{isTrash})")
    public int addDocument(Document document);


    @Update("update Document set title=#{title},content=#{content},lastedittime=#{lastEditTime},editcount=#{editCount}," +
            "lastedituserid=#{lastEditUserId},auth=#{auth},teamAuth=#{teamAuth},istrash=#{isTrash} where docid=#{docId}")
    public int updateDocument(Document document);

    @Select("select * from Document where docid=#{docId}")
    public Document selectDocByDocId(@Param("userId") int docId);
}
