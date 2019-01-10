/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.controller
 * @author: liushengjie
 * @date: 2018年8月15日 下午4:36:55
 */
package cn.bocom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.bocom.entity.Category;
import cn.bocom.other.common.DataResponse;
import cn.bocom.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: CategoryController
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月15日 下午4:36:55 
 */
@RestController
@RequestMapping(value = "/category")
@Api(tags = "分类管理")
public class CategoryController {

    private static Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @Autowired
    private CategoryService categoryService;

    // 新增数据源
    @ApiOperation(value = "新增")
    @RequestMapping(value = "/addCategory", method = RequestMethod.POST)
    public DataResponse insertDs(@RequestBody Category category) {
        return new DataResponse(categoryService.addCategory(category));
    }

    // 修改数据源
    @ApiOperation(value = "修改")
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    public DataResponse updateDs(@RequestBody Category category) {
        return new DataResponse(categoryService.updateCategory(category));
    }

    // 删除数据源
    @ApiOperation(value = "删除类别")
    @RequestMapping(value = "/delCategory", method = RequestMethod.GET)
    public DataResponse deleteDs(String id) {
        return new DataResponse(categoryService.delCategory(id));
    }
    
    // 按照树形结构查询
    @ApiOperation(value = "查询类型树结构")
    @RequestMapping(value = "/selectCategoryTree", method = RequestMethod.GET)
    public DataResponse selectCategoryTree(@RequestParam(required=true,value="type")String type) {
        return new DataResponse(categoryService.selectCategoryTree(type));
    }
    
    // 按照树形结构查询
    @ApiOperation(value = "查询类型树结构及其下属资源")
    @RequestMapping(value = "/selectCategoryTreeWithRes", method = RequestMethod.GET)
    public DataResponse selectCategoryTreeWithRes(@RequestParam(required=true,value="type")String type) {
        return new DataResponse(categoryService.selectCategoryTreeWithRes(type));
    }
}
