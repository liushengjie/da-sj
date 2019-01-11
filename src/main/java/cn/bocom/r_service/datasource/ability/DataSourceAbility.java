package cn.bocom.r_service.datasource.ability;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 数据源能力类
 * @author liushengjie
 * @version $Id: DataSourceAbility.java, v 0.1 2019年1月11日 下午5:44:27 liushengjie Exp $
 */
@Component
public class DataSourceAbility {
    private static Logger logger = LoggerFactory.getLogger(DataSourceAbility.class);
    /**
     * 检测数据源是否有效
     * @param type
     * @param obj
     * @return
     */
    public boolean connect(int type, String obj) {
        return true;
    }
    
    /**
     * 显示所有数据表信息
     * @param datasourceId
     * @return
     */
    public List<Map<String, Object>> showTablesInfo(String datasourceId) {
        return null;
    }
    
    /**
     * 显示表的所有列信息
     * @param datasourceId
     * @param tableName
     * @return
     */
    public List<Map<String, Object>> showColsInfo(String datasourceId, String tableName){
        return null;
    }
    
    /**
     * 显示SQL的所有列信息
     * @param datasourceId
     * @param sql
     * @return
     */
    public List<Map<String, Object>> showColsInfoBySQL(String datasourceId, String sql){
        return null;
    }
    
    
    /**
     * 显示表的数据量
     * @param datasourceId
     * @param tableName
     * @return
     */
    public int tableCount(String datasourceId, String tableName) {
        return 0;
    }
}
