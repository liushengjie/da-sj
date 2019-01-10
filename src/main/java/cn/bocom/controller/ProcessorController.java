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
import org.springframework.web.bind.annotation.RestController;

import cn.bocom.entity.Processor;
import cn.bocom.other.common.DataResponse;
import cn.bocom.service.ProcessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/processor")
@Api(tags = "处理器管理")
public class ProcessorController {

    private static Logger logger = LoggerFactory.getLogger(ProcessorController.class);

    @Autowired
    private ProcessorService service;
    
    @ApiOperation(value = "查询处理器列表，可根据type,ds_type及category查询")
    @RequestMapping(value = "/queryProcessor", method = RequestMethod.POST)
    public DataResponse queryProcessor(@RequestBody Processor processor) {
        return new DataResponse(service.queryProcessor(processor));
    }
}
