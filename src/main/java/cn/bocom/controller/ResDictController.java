/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.controller
 * @author: liushengjie
 * @date: 2018年8月15日 下午4:36:55
 */
package cn.bocom.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lkx.util.ExcelUtil;

import cn.bocom.entity.ResDict;
import cn.bocom.entity.ResDictType;
import cn.bocom.other.common.DataResponse;
import cn.bocom.service.ResDictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/***
 * 
 * @author
 *
 */
@RestController
@RequestMapping(value = "/resDict")
@Api(tags = "资源代码值管理")
public class ResDictController {
    private static Logger logger = LoggerFactory.getLogger(ResDictController.class);

    @Autowired
    private ResDictService resDictService;

    // 查询资源代码表
    @ApiOperation(value = "查询资源代码")
    @RequestMapping(value = "/queryResDict", method = RequestMethod.GET)
    public DataResponse selectResDict(ResDict resDict) {
        return new DataResponse(resDictService.queryResDict(resDict));
    }

    // 查询资源代码类型
    @ApiOperation(value = "查询资源代码类型")
    @RequestMapping(value = "/queryResDictType", method = RequestMethod.GET)
    public DataResponse selectResDictType(ResDictType resDictType) {
        return new DataResponse(resDictService.queryResDictType(resDictType));
    }

    @ApiOperation(value = "新增资源代码")
    @RequestMapping(value = "/addResDict", method = RequestMethod.POST)
    public DataResponse addResDict(@RequestBody ResDict resDict) {
        return new DataResponse(resDictService.addResDict(resDict));
    }

    @ApiOperation(value = "新增资源代码类别")
    @RequestMapping(value = "/addResDictType", method = RequestMethod.POST)
    public DataResponse addResDict(@RequestBody ResDictType resDictType) {
        return new DataResponse(resDictService.addResDictType(resDictType));
    }

    @ApiOperation(value = "更新资源代码")
    @RequestMapping(value = "/updateResDict", method = RequestMethod.POST)
    public DataResponse updateResDict(@RequestBody ResDict resDict) {
        return new DataResponse(resDictService.updateResDict(resDict));
    }

    @ApiOperation(value = "新增资源代码类别")
    @RequestMapping(value = "/updateResDictType", method = RequestMethod.POST)
    public DataResponse updateResDictType(@RequestBody ResDictType resDictType) {
        return new DataResponse(resDictService.updateResDictType(resDictType));
    }

    // 查询资源代码表
    @ApiOperation(value = "删除资源代码表")
    @RequestMapping(value = "/delResDict", method = RequestMethod.GET)
    public DataResponse delResDict(String id) {
        return new DataResponse(resDictService.delResDict(id));
    }

    // 查询资源代码类型
    @ApiOperation(value = "删除资源代码类型")
    @RequestMapping(value = "/delResDictType", method = RequestMethod.GET)
    public DataResponse queryResDictType(String id) {
        return new DataResponse(resDictService.delResDictType(id));
    }

    // 查询资源代码类型
    @ApiOperation(value = "通过上传Excel文件来保存资源代码")
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    public DataResponse uploadExcel(@RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "codeType") String codeType, HttpServletRequest request,
            HttpServletResponse response) {
        String keyValue = "代码:code,值:name,备注:remark";
        List<ResDict> list = null;
        Map<String, Object> infoMap = null;
        int successNum = 0;
        try {
            list = ExcelUtil.readXls(file.getBytes(), ExcelUtil.getMap(keyValue),
                    "cn.bocom.entity.ResDict");
            infoMap = new HashMap<String, Object>();
            infoMap.put("flag", "true");
            if (list != null && list.size() > 0) {

                for (ResDict item : list) {
                    item.setCodeType(codeType);
                    if (resDictService.addResDict(item)) {
                        successNum++;
                    }
                }

            }
            infoMap.put("info", "共有数据" + list.size() + "条，导入数据" + successNum + "条.");

        } catch (Exception e) {
            e.printStackTrace();
            infoMap.put("flag", "false");
            infoMap.put("info", e.getMessage());
        }

        return new DataResponse(infoMap);
    }


}
