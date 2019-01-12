package cn.bocom.r_service.datasource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.OriginEntity;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;

public class DatasourceUtil {
    @Autowired
    private static DataSourceOrigin datasourceOrigin;
    /**
     * 源对象处理插件
     * @param type
     * @return
     */
    public static OriginPlugin<?> originPlugin(int type){
        DataSourceEnum datasourceEnum = DataSourceEnum.match(type, null);
        return datasourceEnum!=null?datasourceEnum.getPluginClass():null;
    }
    
    /**
     * 源对象实体类
     * @param type
     * @return
     */
    public static Class<? extends OriginEntity> originEntity(int type){
        DataSourceEnum datasourceEnum = DataSourceEnum.match(type, null);
        return datasourceEnum!=null?datasourceEnum.getEntityClass():null;
    }
    
    public static OriginPlugin<?> originPluginById(String datasourceId){
        DataSource datasource = datasourceOrigin.selectDataSourceById(datasourceId);
        return originPlugin(Integer.valueOf(datasource.getType()));
    }
}
