package cn.bocom.r_service.resource.res_transform;

import java.util.List;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.bocom.other.util.RandomUtil;
import cn.bocom.r_entity.datasource.ColInfo;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_entity.resource.ResourceBody;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_entity.resource.ResourceData;
import cn.bocom.r_service.datasource.DataSourcePlugin;
import cn.bocom.r_service.datasource.DatasourceUtil;

/**
 * 资源转换层
 * 
 * @author liushengjie
 * @version $Id: ResourceTrans.java, v 0.1 2019年1月12日 下午4:12:21 liushengjie Exp $
 */
@Component
public class ResourceTrans {
    private static Logger logger = LoggerFactory.getLogger(ResourceTrans.class);

    /**
     * 数据源转换成资源类
     * 
     * @param datasource
     * @return
     */
    public Resource convertToRes(String datasourceId, TableInfo table) {
        Resource resource = new Resource();
        // 1、设置body属性
        ResourceBody res_body = new ResourceBody();
        //String resId = RandomUtil.getRandomId(18);
        //String cacheTable = "res_" + resId;
        //res_body.setId(resId);
        //res_body.setCacheTable(cacheTable);

        DataSourcePlugin<?> dp = DatasourceUtil.originPluginById(datasourceId);
        ResourceData res_data = dp.convertToResData("", datasourceId, table);

        List<ColInfo> cols = dp.showColsInfo(datasourceId, table.getTableName());
        List<ResourceCol> res_cols = Lists.newArrayList();
        for (int i = 0; i < cols.size(); i++) {
            ResourceCol res_col = dp.convertToResCol("", cols.get(i));
            res_col.setSort(String.valueOf(i));
            res_cols.add(res_col);
        }
        resource.setResourceBody(res_body);
        resource.setResourceData(res_data);
        resource.setResourceCols(res_cols);

        return resource;
    }
}
