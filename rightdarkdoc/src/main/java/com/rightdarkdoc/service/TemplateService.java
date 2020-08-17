package com.rightdarkdoc.service;


import com.rightdarkdoc.entity.Template;

import java.util.List;

public interface TemplateService {


    /**
     * 创建文档
     * @param template  封装类型的对象
     * @param userid    创建者的用户id
     * @return
     */
    public int createTemplate(Template template,Integer userid);


    /**
     * 创建公共文档
     * @param template      封装类型的对象
     * @param creatorid     创建者的用户id
     * @return
     */
    public int createPubTemplate(Template template,Integer creatorid);

    /**
     * 根据模版的id删除模版
     * @param tempid
     * @return
     */
    public int delTemplate(Integer tempid);


    /**
     * 根据模版的id访问模版
     * @param tempid
     * @return
     */
    public Template selectTemplateByTempid(Integer tempid);


    /**
     * 根据用户的id获得某个用户的模版
     * @param userid
     * @return
     */
    public List<Template> selectTempByUserid(Integer userid);


    /**
     * 获取公共的模版
     * @return
     */
    public List<Template> selectPubTemp();

}
