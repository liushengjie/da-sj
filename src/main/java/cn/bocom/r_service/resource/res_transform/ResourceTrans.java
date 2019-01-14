package cn.bocom.r_service.resource.res_transform;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.bocom.other.util.RandomUtil;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_entity.resource.ResourceBody;
import cn.bocom.r_entity.resource.ResourceData;

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
    public Resource convert(DataSource datasource, String table) {
        //1、设置body属性
        ResourceBody res_body = new ResourceBody();
        String resId = RandomUtil.getRandomId(18);  
        String cacheTable = "res_" + resId;
        res_body.setId(resId);
        res_body.setCacheTable(cacheTable);
        
        //2、设置data属性
        ResourceData res_data = new ResourceData();
        res_data.setResId(resId);
        res_data.setDsId(datasource.getId());
        res_data.setTableName(table);
        
        
        
        return null;
    }
    

}
