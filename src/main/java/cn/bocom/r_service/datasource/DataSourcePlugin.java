package cn.bocom.r_service.datasource;

import java.sql.Connection;
import java.util.List;

import cn.bocom.other.datasource.AtomikosDynamicDataSource;
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
    public List<TableInfo> showTablesInfo(DataSource datasource);

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
