package org.competition.controller.Admin;

import com.alibaba.fastjson.JSONObject;
import org.competition.bean.Information;
import org.competition.bean.RespBean;
import org.competition.service.InformationService;
import org.competition.service.StrategyService;
import org.competition.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    InformationService informationService;
    @Autowired
    StrategyService strategyService;

    @RequestMapping(value = "/information/all", method = RequestMethod.GET)
    public Map<String, Object> getInformationByStateByAdmin(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
        List<Information> informations = informationService.getInformationByState(-2, page, count, keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("information", informations);
        map.put("totalCount", informationService.getInformationCountByState(1, null, keywords));
        return map;
    }

    @RequestMapping(value = "/information/dustbin", method = RequestMethod.PUT)
    public RespBean updateInformationState(Long[] aids, Integer state) {
        if (informationService.updateInformationState(aids, state) == aids.length) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @RequestMapping(value = "/strategy/all", method = RequestMethod.GET)
    public Map<String, Object> getAllStrategy(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
        int totalCount = strategyService.getStrategyCountByState(1, null,keywords);
        List<JSONObject> strategy = strategyService.getStrategyByState(-2, page, count,keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("strategy", strategy);
        return map;
    }



    @RequestMapping(value = "/strategy/dustbin", method = RequestMethod.PUT)
    public RespBean updateStrategyState(Long[] aids, Integer state) {
        if (strategyService.updateStrategyState(aids, state) == aids.length) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }
}
