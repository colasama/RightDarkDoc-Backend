package com.rightdarkdoc.dao;


import com.rightdarkdoc.entity.Template;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TemplateDao {


    /**
     * 创建模版
     * @param template
     * @return
     */
    public int addTemplate(Template template);


    /**
     * 删除模版
     * @param tempid
     * @return
     */
    public int delTemplate(@Param("tempid") Integer tempid);

    /**
     * 选择一个模版
     * @param tempid
     * @return
     */
    public Template selectTempBytempid(@Param("tempid") Integer tempid);


    /**
     * 根据作者的id选择文档
     * @param creatorid
     * @return
     */
    public List<Template> selectTempByUserid(@Param("creatorid") Integer creatorid);


    /**
     * 查找公共文档
     * @return
     */
    public List<Template> selectPubTemplate();







}
