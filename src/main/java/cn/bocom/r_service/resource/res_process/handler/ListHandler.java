package cn.bocom.r_service.resource.res_process.handler;

import java.util.List;
import java.util.Map;

import cn.bocom.other.common.SjException;
import cn.bocom.r_entity.process.ProcessEntity;
import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_service.datasource.DataSourcePlugin;
import cn.bocom.r_service.datasource.DatasourceUtil;
import cn.bocom.r_service.resource.res_process.IHandler;

/**
 * List数据结构处理器
 * @author liushengjie
 * @version $Id: ListHandler.java, v 0.1 2019年1月18日 下午1:04:26 liushengjie Exp $
 */
public class ListHandler implements IHandler<List<Map<String, Object>>>{

    @Override
    public List<Map<String, Object>> handlerCol(int datasourceType, String col,
            List<ProcessEntity> processors) throws SjException {
        return null;
    }

    @Override
    public List<Map<String, Object>> handlerRow(int datasourceType,
            List<ProcessEntity> processors) {
        return null;
    }

    @Override
    public List<Map<String, Object>> readData(Resource resource , int limit, boolean isPreview) {
    	DataSourcePlugin<?> plugin = DatasourceUtil.originPluginById(resource.getResourceData().getDsId());
        return plugin.loadData(resource.getResourceData().getTableName(), limit+"");
    }

    @Override
    public List<Map<String, Object>> pretreadment(Resource resource, boolean isPreview) {
        return null;
    }

    
}
