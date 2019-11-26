package org.competition.service;
import com.alibaba.fastjson.JSONObject;
import org.competition.bean.Strategy;
import org.competition.bean.Information;
import org.competition.mapper.InformationMapper;
import org.competition.mapper.StrategyMapper;
import org.competition.mapper.TagsMapper;
import org.competition.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class StrategyService {
    @Autowired
    StrategyMapper strategyMapper;
    @Autowired
    TagsMapper tagsMapper;
    public int addNewStrategy(Strategy strategy) {
        //处理文章摘要
        if (strategy.getSummary() == null || "".equals(strategy.getSummary())) {
            //直接截取
            String stripHtml = stripHtml(strategy.getHtmlContent());
            strategy.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if (strategy.getId() == -1) {
            //添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (strategy.getState() == 1) {
                //设置发表日期
                strategy.setPublishDate(timestamp);
            }
            strategy.setEditTime(timestamp);
            //设置当前用户
            strategy.setUid(Util.getCurrentUser().getId());
            strategy.setId(null);
            int i = strategyMapper.insert(strategy);
            return i;
        } else {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (strategy.getState() == 1) {
                //设置发表日期
                strategy.setPublishDate(timestamp);
            }
            //更新
            strategy.setEditTime(new Timestamp(System.currentTimeMillis()));
            int i = strategyMapper.updateByPrimaryKeySelective(strategy);
            return i;
        }
    }

    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    public List<JSONObject> getStrategyByState(Integer state, Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        Long uid = Util.getCurrentUser().getId();
        return strategyMapper.getStrategyByState(state, start, count, uid,keywords);
    }

    public List<Strategy> getStrategyByStateByAdmin(Integer page, Integer count,String keywords) {
        int start = (page - 1) * count;
        return strategyMapper.getStrategyByStateByAdmin(start, count,keywords);
    }

    public int getStrategyCountByState(Integer state, Long uid,String keywords) {
        return strategyMapper.getStrategyCountByState(state, uid,keywords);
    }

    public int updateStrategyState(Long[] aids, Integer state) {
        if (state == 2) {
            return strategyMapper.deleteStrategyById(aids);
        } else {
            return strategyMapper.updateStrategyState(aids, 2);//放入到回收站中
        }
    }

    public JSONObject getStrategyById(Long aid) {
        JSONObject strategy = strategyMapper.getStrategyById(aid);
//        strategyMapper.pvIncrement(aid);
        return strategy;
    }

    public void pvStatisticsPerDay() {
        strategyMapper.pvStatisticsPerDay();
    }

    /**
     * 获取最近七天的日期
     * @return
     */
    public List<String> getCategories() {
        return strategyMapper.getCategories(Util.getCurrentUser().getId());
    }

    /**
     * 获取最近七天的数据
     * @return
     */
    public List<Integer> getDataStatistics() {
        return strategyMapper.getDataStatistics(Util.getCurrentUser().getId());
    }
}
