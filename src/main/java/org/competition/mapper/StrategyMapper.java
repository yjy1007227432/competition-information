package org.competition.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.competition.bean.Strategy;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StrategyMapper extends Mapper<Strategy> {

    void pvIncrement(Long aid);

    List<JSONObject> getStrategyByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Long uid, @Param("keywords") String keywords);

    int deleteStrategyById(Long[] aids);

    int updateStrategyState(@Param("aids")Long[] aids, @Param("state")int i);

    JSONObject getStrategyById(Long aid);

    int getStrategyCountByState(@Param("state")Integer state, @Param("uid")Long uid, @Param("keywords")String keywords);

    void pvStatisticsPerDay();

    int updateStrategy(Strategy information);

    int addNewStrategy(Strategy information);

    List<String> getCategories(Long id);

    List<Integer> getDataStatistics(Long uid);

    List<Strategy> getStrategyByStateByAdmin(int start, Integer count, String keywords);
}
