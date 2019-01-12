package cn.bocom.r_service.datasource;

import cn.bocom.r_entity.datasource.OriginEntity;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;

public class DatasourceUtil {
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
}
