package org.competition.service;

import com.alibaba.fastjson.JSONObject;
import org.competition.bean.Information;
import org.competition.mapper.InformationMapper;
import org.competition.mapper.TagsMapper;
import org.competition.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class InformationService {
    @Autowired
    InformationMapper informationMapper;

    @Autowired
    TagsMapper tagsMapper;

    public int addNewInformation(Information information) {
        //处理文章摘要
        if (information.getSummary() == null || "".equals(information.getSummary())) {
            //直接截取
            String stripHtml = stripHtml(information.getHtmlContent());
            information.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if (information.getId() == -1) {
            //添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (information.getState() == 1) {
                //设置发表日期
                information.setPublishDate(timestamp);
            }
            information.setEditTime(timestamp);
            //设置当前用户
            information.setUid(Util.getCurrentUser().getId());
//                        int i = informationMapper.addNewInformation(information);
            information.setId(null);
            int i = informationMapper.insert(information);
            //打标签
            String[] dynamicTags = information.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToInformation(dynamicTags, information.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (information.getState() == 1) {
                //设置发表日期
                information.setPublishDate(timestamp);
            }
            //更新
            information.setEditTime(new Timestamp(System.currentTimeMillis()));
//                        int i = informationMapper.updateInformation(information);
            int i = informationMapper.updateByPrimaryKeySelective(information);
            //打标签
            String[] dynamicTags = information.getDynamicTags();
            if (dynamicTags != null && dynamicTags.length > 0) {
                int tags = addTagsToInformation(dynamicTags, information.getId());
                if (tags == -1) {
                    return tags;
                }
            }
            return i;
        }
    }


    private int addTagsToInformation(String[] dynamicTags, Long aid) {
        //1.删除该文章目前所有的标签
        tagsMapper.deleteTagsByAid(aid);
        //2.将上传上来的标签全部存入数据库
        tagsMapper.saveTags(dynamicTags);
        //3.查询这些标签的id
        List<Long> tIds = tagsMapper.getTagsIdByTagName(dynamicTags);
        //4.重新给文章设置标签
        int i = tagsMapper.saveTags2ArticleTags(tIds, aid);
        return i == dynamicTags.length ? i : -1;
    }

    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    public JSONObject getInformationById(Long aid) {
        JSONObject information = informationMapper.getInformationById(aid);
        List<JSONObject> tagList = tagsMapper.findInTags(aid);
        information.put("tagList",tagList);
        informationMapper.pvIncrement(aid);
        return information;
    }

    public int updateInformationState(Long[] aids, Integer state) {
        if (state == 2) {
            return informationMapper.deleteInformationById(aids);
        } else {
            return informationMapper.updateInformationState(aids, 2);//放入到回收站中
        }

    }

    public List<Information> getInformationByState(Integer state, Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        Long uid = Util.getCurrentUser().getId();
        return informationMapper.getInformationByState(state, start, count, uid, keywords);
    }

    public List<Information> getInformationByStateByAdmin(Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        return informationMapper.getInformationByStateByAdmin(start, count, keywords);
    }

    public int getInformationCountByState(Integer state, Long uid, String keywords) {
        return informationMapper.getInformationCountByState(state, uid, keywords);
    }

    public void pvStatisticsPerDay() {
        informationMapper.pvStatisticsPerDay();
    }

    /**
     * 获取最近七天的日期
     *
     * @return
     */
    public List<String> getCategories() {
        return informationMapper.getCategories(Util.getCurrentUser().getId());
    }

    /**
     * 获取最近七天的数据
     *
     * @return
     */
    public List<Integer> getDataStatistics() {
        return informationMapper.getDataStatistics(Util.getCurrentUser().getId());
    }

}
