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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.bocom.other.common.DataResponse;
import cn.bocom.r_entity.datasource.Origins;
import cn.bocom.service.SysCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: TestController
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月15日 下午4:36:55
 */
@RestController
@RequestMapping(value = "/sysCode")
@Api(tags = "系统代码值管理")
public class SysCodeController {
    private static Logger logger = LoggerFactory.getLogger(SysCodeController.class);

    @Autowired
    private SysCodeService sysCodeService;

    // 分页查询数据源
    @ApiOperation(value = "查询数据源类别代码")
    @RequestMapping(value = "/selectDsType", method = RequestMethod.GET)
    public DataResponse selectDsType() {
        return new DataResponse(Origins.DataSourceEnum.getOrigins());
    }

    @ApiOperation(value = "查询分类类别代码")
    @RequestMapping(value = "/category_type", method = RequestMethod.GET)
    public DataResponse selectcategoryType() {
        return new DataResponse(sysCodeService.selectSysCode("category_type"));
    }
}
