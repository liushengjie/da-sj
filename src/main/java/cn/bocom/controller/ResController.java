/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.controller 
 * @author: liushengjie   
 * @date: 2018年9月18日 上午10:58:16 
 */
package cn.bocom.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.bocom.entity.Processor;
import cn.bocom.other.common.DataResponse;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_service.resource.ResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/** 
 * @ClassName: ResController 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月18日 上午10:58:16  
 */
@RestController
@RequestMapping(value = "/res")
@Api(tags = "资源库管理")
public class ResController {

	private static Logger logger = LoggerFactory.getLogger(ResController.class);
	
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private cn.bocom.r_service.resource.ResourceService r_resService;
    
//    @ApiOperation(value = "预览sql")
//    @RequestMapping(value = "/previewData", method = RequestMethod.POST)
//    public DataResponse previewData(@RequestBody ResBean resBean, String limit) {
//        return new DataResponse(resService.previewData(resBean, limit));
//    }
//    
//    @ApiOperation(value = "获取资源库信息")
//    @RequestMapping(value = "/queryResListByPage", method = RequestMethod.GET)
//    public DataResponse queryResListByPage(ResView res,
//            @RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
//        return new DataResponse(
//        		resService.queryResListByPage(res, currentPage, pageSize));
//    }
//    
//    @ApiOperation(value = "获取全部资源库信息")
//    @RequestMapping(value = "/queryResAll", method = RequestMethod.GET)
//    public DataResponse queryResAll() {
//        return new DataResponse(resService.queryResAll());
//    }
//    
//    @ApiOperation(value = "保存资源库信息")
//    @RequestMapping(value = "/saveRes", method = RequestMethod.POST)
//    public DataResponse saveRes(@RequestBody ResBean resBean) {
//    	String ret = resService.saveRes(resBean);
//    	if(ret==null) {
//    		return new DataResponse(false, -1, "保存失败，请检查传入参数！");
//    	}else {
//    		return new DataResponse("保存成功！");
//    	}
//    }
    
//    @ApiOperation(value = "根据资源ID获取该资源信息")
//    @RequestMapping(value = "/findResBean", method = RequestMethod.GET)
//    public DataResponse findResBean(String resId) {
//    	ResBean resBean = resService.findResBean(resId);
//    	if(resBean==null) {
//    		return new DataResponse(false, -1, "根据ID获取资源信息错误！");
//    	}else {
//    		return new DataResponse(resBean);
//    	}
//    }

    @ApiOperation(value = "获取资源对象")
    @RequestMapping(value = "/loadResourceObj", method = RequestMethod.POST)
    public DataResponse loadResourceObj(@RequestParam("datasourceId") String datasourceId, @RequestBody TableInfo table) {
        Resource ret = resourceService.loadResourceObj(datasourceId, table);
        return new DataResponse(ret);
    }
    
    @ApiOperation(value = "获取资源对象的列信息")
    @RequestMapping(value = "/loadResourceCols", method = RequestMethod.POST)
    public DataResponse loadResourceCols(@RequestBody Resource resource) {
        List<ResourceCol> ret = resourceService.showResourceCols(resource);
        return new DataResponse(ret);
    }
    
    @ApiOperation(value = "获取全部资源库信息")
    @RequestMapping(value = "/selectResourceList", method = RequestMethod.GET)
    public DataResponse selectResourceList() {
        return new DataResponse(r_resService.selectResourceList(new Resource()));
    }
    
    @ApiOperation(value = "获取分页资源库信息")
    @RequestMapping(value = "/selectResourceListByPage", method = RequestMethod.GET)
    public DataResponse selectResourceListByPage(@RequestBody Resource resource, 
    		@RequestParam("currentPage") int currentPage, 
    		@RequestParam("pageSize") int pageSize) {
        return new DataResponse(r_resService.selectResourceListByPage(resource,currentPage,pageSize));
    }
    
    @ApiOperation(value = "根据id获取资源库信息")
    @RequestMapping(value = "/selectResourceById", method = RequestMethod.GET)
    public DataResponse selectResourceById(String id) {
        return new DataResponse(r_resService.selectResourceById(id));
    }
    
    @ApiOperation(value = "根据类型获取资源库信息")
    @RequestMapping(value = "/selectResourceByCategory", method = RequestMethod.GET)
    public DataResponse selectResourceByCategory(String category) {
        return new DataResponse(r_resService.selectResourceByCategory(category));
    }
    
    @ApiOperation(value = "保存资源信息，有记录则更新反之插入")
    @RequestMapping(value = "/saveResource", method = RequestMethod.GET)
    public DataResponse saveResource(@RequestBody Resource resource) {
        return new DataResponse(r_resService.saveResource(resource));
    }
    
    @ApiOperation(value = "刪除资源库信息")
    @RequestMapping(value = "/deleteResource", method = RequestMethod.GET)
    public DataResponse deleteResource(String id) {
        return new DataResponse(r_resService.deleteResource(id));
    }
}
