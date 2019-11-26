package org.competition.controller;


import com.alibaba.fastjson.JSONObject;
import org.competition.bean.Information;
import org.competition.bean.RespBean;
import org.competition.service.InformationService;
import org.competition.utils.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 竞争信息controller
 */
@RestController
@RequestMapping("/information")
public class InformationController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    InformationService informationService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewInformation(Information information) {
        int result = informationService.addNewInformation(information);
        if (result == 1) {
            return new RespBean("success", information.getId() + "");
        } else {
            return new RespBean("error", information.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
    }

    /**
     * 上传图片
     *
     * @return 返回值为图片的地址
     */
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    public RespBean uploadImg(HttpServletRequest req, @RequestParam("file") MultipartFile image) {
        StringBuffer url = new StringBuffer();
        String filePath = "/informationimg/" + sdf.format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            return new RespBean("success", url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RespBean("error", "上传失败!");
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Map<String, Object> getInformationByState(@RequestParam(value = "state", defaultValue = "-1") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords) {
        int totalCount = informationService.getInformationCountByState(state, Util.getCurrentUser().getId(),keywords);
        List<Information> informations = informationService.getInformationByState(state, page, count,keywords);
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("informations", informations);
        return map;
    }

    @RequestMapping(value = "/{aid}", method = RequestMethod.GET)
    public JSONObject getInformationById(@PathVariable Long aid) {
        return informationService.getInformationById(aid);
    }

    @RequestMapping(value = "/dustbin", method = RequestMethod.PUT)
    public RespBean updateInformationState(Long[] aids, Integer state) {
        if (informationService.updateInformationState(aids, state) == aids.length) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @RequestMapping("/dataStatistics")
    public Map<String,Object> dataStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<String> categories = informationService.getCategories();
        List<Integer> dataStatistics = informationService.getDataStatistics();
        map.put("categories", categories);
        map.put("ds", dataStatistics);
        return map;
    }

}
