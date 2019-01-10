/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.controller 
 * @author: liushengjie   
 * @date: 2018年9月18日 上午10:58:16 
 */
package cn.bocom.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.bocom.entity.SrvBean;
import cn.bocom.other.common.DataResponse;
import cn.bocom.service.service.SrvService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: ServiceController 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月18日 上午10:58:16  
 */
@RestController
@RequestMapping(value = "/srv")
@Api(tags = "服务管理")
public class SrvController {

	private static Logger logger = LoggerFactory.getLogger(SrvController.class);
	
    @Autowired
    private SrvService srvService;
    
    @ApiOperation(value = "预览")
    @RequestMapping(value = "/previewData", method = RequestMethod.POST)
    public DataResponse previewData(@RequestBody Map<String,Object> map, String targetNode, String limit) throws Exception {
        return new DataResponse(srvService.previewData(map, targetNode, limit));
    }
    
    @ApiOperation(value = "查看预览SQL")
    @RequestMapping(value = "/previewDebug", method = RequestMethod.POST)
    public DataResponse previewDebug(@RequestBody Map<String,Object> map, String targetNode) throws Exception {
        return new DataResponse(srvService.previewDebug(map, targetNode));
    }
    
    @ApiOperation(value = "获取表的所有列及别名")
    @RequestMapping(value = "/findCols", method = RequestMethod.POST)
    public DataResponse findCols(@RequestBody SrvBean srvBean, String resId) {
    	return new DataResponse(srvService.findCols(srvBean, resId));
    }
    
    @ApiOperation(value = "解析Json")
    @RequestMapping(value = "/buildSQL", method = RequestMethod.POST)
    public DataResponse buildSQL(@RequestBody SrvBean srvBean, String target) {
        return new DataResponse(srvService.buildSQL(srvBean, target));
    }
    
    @ApiOperation(value = "构建SQL")
    @RequestMapping(value = "/generateQrySql", method = RequestMethod.POST) //
    public DataResponse generateQrySql(@RequestBody Map<String,Object> map, String targetNode) throws Exception {
        return new DataResponse(srvService.generateSql(map, targetNode));
    }
    
}
