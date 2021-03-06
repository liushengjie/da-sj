package cn.bocom.r_service.datasource;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import cn.bocom.other.datasource.jdbc.AtomikosDynamicDataSource;
import cn.bocom.r_entity.datasource.ColInfo;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.OriginEntity;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_service.resource.res_transform.ResourceProtocol;

/**
 * 插件接口
 * 
 * @author liushengjie
 * @version $Id: OriginPlugin.java, v 0.1 2019年1月11日 下午2:22:06 liushengjie Exp $
 */

public interface DataSourcePlugin<T extends OriginEntity> extends ResourceProtocol{
    
    /**
     * 转换为DataSource对象，进行数据存储
     * 
     * @param originObj
     * @param typeCode
     * @return
     */
    public DataSource convertDataSource(T originObj, int typeCode);

    /**
     * 从DataSource对象转换为数据源表单对象
     * 
     * @param datasource
     * @return
     */
    public T converOrigin(DataSource datasource);
    
    /**
     * 获取datasource下的所有表信息
     * @param datasourceId
     * @return
     */
    public List<TableInfo> showTablesInfo(String datasourceId);
    
    /**
     * 根据表查询所有列信息
     * @param datasource
     * @param table(表名、sql、 collection 等等)
     * @return
     */
    public List<ColInfo> showColsInfo(String datasourceId, String table);
    
    /**
     * 查询表数量
     * @param datasource
     * @param tableName
     * @return
     */
    public int tableCount(String table);
    
    /**
     * 读取数据
     * @param table
     * @param limit(limit为0时 不限制)
     * @return
     */
    public List<Map<String,Object>> loadData(String table, String limit);
    

    /**
     * 检测数据源的连通性(针对于jdbc)
     * 
     * @param originObj
     * @param typeCode
     * @return
     */
    default boolean connect(DataSource datasource) {
        Connection conn = null;
        boolean connFlag = false;
        try {
            AtomikosDynamicDataSource atomikosDynamicDataSource = new AtomikosDynamicDataSource();
            javax.sql.DataSource dataSource =
                    atomikosDynamicDataSource.createDataSource(datasource.getDriver(),
                            datasource.getUrl(), datasource.getUsername(), datasource.getPwd());
            dataSource.setLoginTimeout(1);
            conn = dataSource.getConnection();
            if (conn != null) {
                connFlag = true;
            }
        } catch (Exception e) {
            connFlag = false;
            return connFlag;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                connFlag = false;
                return connFlag;
            }

        }
        return connFlag;
    }
}
