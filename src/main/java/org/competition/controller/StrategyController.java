package org.competition.controller;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.competition.bean.Strategy;
import org.competition.bean.Information;
import org.competition.bean.RespBean;
import org.competition.service.InformationService;
import org.competition.service.StrategyService;
import org.competition.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 策略信息controller
 */
@RestController
@RequestMapping("/strategy")
public class StrategyController {
    @Autowired
    StrategyService strategyService;

    @RequestMapping(value = "/",method = RequestMethod.POST)
    public RespBean addNewStrategy(Strategy strategy){
        int result = strategyService.addNewStrategy(strategy);
        if (result == 1) {
            return new RespBean("success", strategy.getId() + "");
        } else {
            return new RespBean("error", strategy.getState() == 0 ? "策略信息保存失败!" : "策略信息发表失败!");
        }
    }



    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Map<String, Object> getAllStrategy(@RequestParam(value = "state", defaultValue = "-1") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
        int totalCount = strategyService.getStrategyCountByState(state, Util.getCurrentUser().getId(),keywords);
        List<JSONObject> strategy = strategyService.getStrategyByState(state, page, count,keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("strategy", strategy);
        return map;
    }

    @RequestMapping(value = "/{aid}", method = RequestMethod.GET)
    public JSONObject getStrategyById(@PathVariable Long aid) {
        return strategyService.getStrategyById(aid);
    }

    @RequestMapping(value = "/dustbin", method = RequestMethod.PUT)
    public RespBean updateStrategyState(Long[] aids, Integer state) {
        if (strategyService.updateStrategyState(aids, state) == aids.length) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

}
