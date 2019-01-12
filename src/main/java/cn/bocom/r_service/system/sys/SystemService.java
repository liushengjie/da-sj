package cn.bocom.r_service.system.sys;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.bocom.entity.SysCode;
import cn.bocom.mapper.main.SysCodeMapper;

/**
 * 系统服务
 * @author liushengjie
 * @version $Id: SystemService.java, v 0.1 2019年1月12日 上午9:43:39 liushengjie Exp $
 */
public class SystemService {
    private static Logger logger = LoggerFactory.getLogger(SystemService.class);
    @Autowired
    private SysCodeMapper sysCodeMapper;
    
    /**
     * 按类别查询系统代码值
     * @param typeCode
     * @return
     */
    public List<SysCode> selectSysCode(String typeCode) {
        SysCode sc = new SysCode();
        sc.setTypeCode(typeCode);
        return sysCodeMapper.select(sc);
    }
}
