package cn.bocom.r_service.resource;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.resource.ResColInfo;
import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_service.resource.res_transform.ResourceTrans;

/**
 * 资源服务类
 * @author liushengjie
 * @version $Id: ResourceService.java, v 0.1 2019年1月12日 下午4:35:35 liushengjie Exp $
 */
@Component
public class ResourceService {
    private static Logger logger = LoggerFactory.getLogger(ResourceService.class);
    
    @Autowired
    private ResourceTrans resourceTrans;
    
    public Resource loadResource(String datasourceId, TableInfo table) {
        return resourceTrans.convertToRes(datasourceId, table);
    }
    
    /**
     * 根据资源获取列信息
     * @param resource
     * @return
     */
    public List<ResColInfo> showResourceCols(Resource resource){
        List<ResourceCol> res_cols = resource.getResourceCols();
        List<ResColInfo> ret = Lists.newArrayList();
        res_cols.forEach(c -> {
            ret.add(resourceTrans.convertToResColInfo(c));
        });
        return ret;
    }
    
    
    //TODO:根据资源获取数据预览
    public List<Map<String, Object>> loadDataByResource(Resource resource){
        return null;
    }
    
    
    //TODO: 资源对象增删改查

}
