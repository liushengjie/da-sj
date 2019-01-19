package cn.bocom.r_service.resource.res_process;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.Origins.ConnModelEnum;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;
import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;

/**
 * 资源重组层
 * @author liushengjie
 * @version $Id: ResourceRecombine.java, v 0.1 2019年1月12日 下午4:12:41 liushengjie Exp $
 */
@Component
public class ResourceDataProcess {
    
    @Autowired
    private DataSourceOrigin datasourceOrigin;
    /**
     * 获取资源数据集
     * @param resource
     * @return
     */
    public List<Map<String, Object>> loadResourceData(Resource resource, int limit, boolean isPreview){
        DataSource datasource = datasourceOrigin.selectDataSourceById(resource.getResourceData().getDsId());
        DataSourceEnum datasourceEnum = DataSourceEnum.match(Integer.valueOf(datasource.getType()), null);
        ConnModelEnum connModelEnum = ConnModelEnum.match(datasourceEnum.getConnModel(), null);
        IHandler<?> handler = connModelEnum.getHandlerClass();
        return handler.readData(resource, limit, isPreview);
    }
}
