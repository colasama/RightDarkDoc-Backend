package com.rightdarkdoc.service.Impl;

import com.rightdarkdoc.dao.TemplateDao;
import com.rightdarkdoc.entity.Template;
import com.rightdarkdoc.service.TemplateService;
import com.rightdarkdoc.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;



@Service
public class TemplateServiceImpl implements TemplateService {


    @Autowired
    private TemplateDao templateDao;


    @Override
    public int createTemplate(Template template,Integer userid) {
        if(template==null){
            template = new Template();
        }
        if(template.getCreatorid()==null){
            template.setCreatorid(userid);
        }
        if(template.getTitle()==null){
            template.setTitle("untitle");
        }
        if(template.getContent()==null){
            template.setTitle("");
        }
        Date date = new Date();
        template.setCreattime(date);
        template.setCreattimeString(TimeUtils.formatTime(date));
        template.setIstrash(0);
        template.setIspublic(0);                    //不是公共的，是个人模版
        return templateDao.addTemplate(template);
    }


    @Override
    public int createPubTemplate(Template template, Integer creatorid) {
        if(template==null){
            template = new Template();
        }
        if(template.getCreatorid()==null){
            template.setCreatorid(creatorid);
        }
        if(template.getTitle()==null){
            template.setTitle("untitle");
        }
        if(template.getContent()==null){
            template.setTitle("");
        }
        Date date = new Date();
        template.setCreattime(date);
        template.setCreattimeString(TimeUtils.formatTime(date));
        template.setIstrash(0);
        template.setIspublic(1);                    //公共模版
        return templateDao.addTemplate(template);
    }

    @Override
    public int delTemplate(Integer tempid) {
        return  templateDao.delTemplate(tempid);
    }

    @Override
    public Template selectTemplateByTempid(Integer tempid) {
        return templateDao.selectTempBytempid(tempid);
    }

    @Override
    public List<Template> selectTempByUserid(Integer userid) {
        return templateDao.selectTempByUserid(userid);
    }

    @Override
    public List<Template> selectPubTemp() {
        return templateDao.selectPubTemplate();
    }
}
