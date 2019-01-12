package cn.bocom.r_service.datasource.ability;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.OriginEntity;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_service.datasource.DatasourceUtil;
import cn.bocom.r_service.datasource.DataSourcePlugin;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;

/**
 * 数据源能力类
 * @author liushengjie
 * @version $Id: DataSourceAbility.java, v 0.1 2019年1月11日 下午5:44:27 liushengjie Exp $
 */
@Component
public class DataSourceAbility {
    private static Logger logger = LoggerFactory.getLogger(DataSourceAbility.class);
    
    @Autowired
    private DataSourceOrigin datasourceOrigin;
    /**
     * 检测数据源是否有效
     * @param type
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends OriginEntity> boolean connect(int type, String obj) {
        DataSourcePlugin<T> op = (DataSourcePlugin<T>)DatasourceUtil.originPlugin(type);
        Class<? extends OriginEntity> oe = DatasourceUtil.originEntity(type);
        
        T originObj = (T)JSON.parseObject(obj, oe);
        DataSource datasource = op.convertDataSource(originObj, type);
        return op.connect(datasource);
    }
    
    /**
     * 显示所有数据表信息
     * @param datasourceId
     * @return
     */
    public List<TableInfo> showTablesInfo(String datasourceId) {
        DataSourcePlugin<?> op = DatasourceUtil.originPluginById(datasourceId);
        DataSource datasource = datasourceOrigin.selectDataSourceById(datasourceId);
        return op.showTablesInfo(datasource);
    }
    
    /**
     * 显示表的所有列信息
     * @param datasourceId
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> showColsInfo(DataSource datasource, String tableName){
        return null;
    }
    
    /**
     * 显示SQL的所有列信息
     * @param datasourceId
     * @param sql
     * @return
     */
    public List<Map<String, Object>> showColsInfoBySQL(DataSource datasource, String sql){
        return null;
    }
    
    
    /**
     * 显示表的数据量
     * @param datasourceId
     * @param tableName
     * @return
     */
    public int tableCount(DataSource datasource, String tableName) {
        return 0;
    }
}
