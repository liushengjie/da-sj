package cn.bocom.r_service.resource.res_transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.resource.Resource;

/**
 * 资源转换层
 * @author liushengjie
 * @version $Id: ResourceTrans.java, v 0.1 2019年1月12日 下午4:12:21 liushengjie Exp $
 */
@Component
public class ResourceTrans {
    private static Logger logger = LoggerFactory.getLogger(ResourceTrans.class);
    
    /**
     * 数据源转换成资源类
     * @param datasource
     * @return
     */
    public Resource convert(DataSource datasource) {
        return null;
    }
}
