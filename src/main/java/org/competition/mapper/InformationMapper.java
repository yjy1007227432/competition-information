package org.competition.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;
import org.competition.bean.Information;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


public interface InformationMapper extends Mapper<Information> {

    void pvIncrement(Long aid);

    List<Information> getInformationByStateByAdmin(@Param("start") int start, @Param("count") Integer count, @Param("keywords") String keywords);

    List<Information> getInformationByState(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Long uid, @Param("keywords") String keywords);

    int deleteInformationById(@Param("aids")Long[] aids);

    int updateInformationState(@Param("aids")Long[] aids,@Param("state") int state);

    JSONObject getInformationById(Long aid);

    int getInformationCountByState(@Param("state")Integer state, @Param("uid")Long uid, @Param("keywords")String keywords);

    void pvStatisticsPerDay();

    int updateInformation(Information information);

    int addNewInformation(Information information);

    List<String> getCategories(Long id);

    List<Integer> getDataStatistics(Long uid);
}
