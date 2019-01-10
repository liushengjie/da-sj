/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service
 * @author: liushengjie
 * @date: 2018年8月15日 下午5:15:33
 */
package cn.bocom.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.entity.SysCode;
import cn.bocom.mapper.main.SysCodeMapper;

/**
 * @ClassName: SysCodeService
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月15日 下午5:15:33
 */
@Component
public class SysCodeService {
    private static Logger logger = LoggerFactory.getLogger(SysCodeService.class);

    @Autowired
    private SysCodeMapper sysCodeMapper;

    // 按类别查询系统代码值
    public List<SysCode> selectSysCode(String typeCode) {
        SysCode sc = new SysCode();
        sc.setTypeCode(typeCode);
        return sysCodeMapper.select(sc);
    }
}
