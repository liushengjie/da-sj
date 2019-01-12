package cn.bocom.r_service.datasource;

import org.springframework.beans.factory.annotation.Autowired;

import cn.bocom.other.datasource.DBContextHolder;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.OriginEntity;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;

/**
 * 数据源工具类
 * @author liushengjie
 * @version $Id: DatasourceUtil.java, v 0.1 2019年1月12日 下午2:23:43 liushengjie Exp $
 */
public class DatasourceUtil {
    @Autowired
    private static DataSourceOrigin datasourceOrigin;
    /**
     * 通过源类型获取处理插件
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
    
    /**
     * 通过源ID获取插件
     * @param datasourceId
     * @return
     */
    public static OriginPlugin<?> originPluginById(String datasourceId){
        DataSource datasource = datasourceOrigin.selectDataSourceById(datasourceId);
        changeDataSourceContext(datasourceId);
        return originPlugin(Integer.valueOf(datasource.getType()));
    }
    
    /**
     * 切换数据源
     * @param datasourceId
     */
    public static void changeDataSourceContext(String datasourceId) {
        DBContextHolder.setDBType(datasourceId);
    }
}
