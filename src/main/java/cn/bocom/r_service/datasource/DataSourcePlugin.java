package cn.bocom.r_service.datasource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import cn.bocom.other.datasource.jdbc.AtomikosDynamicDataSource;
import cn.bocom.other.util.DBUtil;
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
    public List<TableInfo> showTablesInfo(DataSource datasource);
    
    /**
     * 根据表查询所有列信息
     * @param datasource
     * @param table(表名、sql、 collection 等等)
     * @return
     */
    public List<ColInfo> showColsInfo(DataSource datasource, String table);
    
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
    
    /**
     * @Title: getConnection
     * @Description: 获取数据源连接
     * @param DataSource
     * @return Connection
     * @author lishipeng
     * @date 2018年9月14日上午10:34:45
     */
    default Connection getConnection(DataSource ds) {
    	Connection conn = null;
    	try {
            AtomikosDynamicDataSource atomikosDynamicDataSource = new AtomikosDynamicDataSource();
            javax.sql.DataSource dataSource =
                    atomikosDynamicDataSource.createDataSource(ds.getDriver(),
                            ds.getUrl(), ds.getUsername(), ds.getPwd());
            conn = dataSource.getConnection();
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
		return conn;
	}
    
    /** 
     * @Title: findAllColBySQL 
     * @Description: 根据SQL从conn里获取所有字段信息
     * @param conn
     * @param SQL
     * @return List
     * @author lishipeng
     * @date 2018年9月17日下午15:08:53
     */
    default List<ColInfo> findAllColsByTable(DataSource datasource, String tableName, String database) {
    	List<ColInfo> result = null;
    	Connection conn = null;
    	PreparedStatement preparedStatement = null;
        try {
        	conn = getConnection(datasource);
        	if(conn==null) {
        		return result;
        	}
        	preparedStatement = conn.prepareStatement("select * from "+tableName+" t limit 1");
            // 获取ResultSetMetaData
            ResultSetMetaData metaData = preparedStatement.executeQuery().getMetaData();
            result = new ArrayList<ColInfo>();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
            	ColInfo col = new ColInfo();
                // resultSet数据下标从1开始  
                String columnName = metaData.getColumnName(i + 1);  
                String columnType = metaData.getColumnTypeName(i + 1);
                int length = metaData.getColumnDisplaySize(i+1);
                String isNullAble = metaData.isNullable(i+1)==0?"0":"1";
                
                col.setCol(columnName.toLowerCase());
                col.setType(columnType);
                col.setChangeType(DBUtil.changeDBType(columnType));
                col.setLength(length);
                col.setNullable(isNullAble);
                
                result.add(col);
            }
            //如果有单独的表名，则获取主键和索引信息
        	if(tableName!=null&&result!=null&&result.size()>0) {
        		DatabaseMetaData dbMetData = conn.getMetaData();
        		result = matchPkAndIndex(result, 
        				findPkColByTable(dbMetData, database, tableName), 
        				findIndexColByTable(dbMetData, database, tableName));
        	}
        } catch (Exception e) {
        	result = null;
        	e.printStackTrace();
        } finally {
            try {
            	if(preparedStatement != null) {
            		preparedStatement.close();
            	}
            	if(conn != null) {
            		conn.close();
            	}
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    	return result;
    }
    
    /** 
     * @Title: findPkColByTable 
     * @Description: 获取指定表的主键字段
     * @param dbMetData
     * @param database
     * @param tableName
     * @return List
     * @author lishipeng
     * @date 2018年9月17日下午15:08:53
     */
    default List<String> findPkColByTable(DatabaseMetaData dbMetData, String database, String tableName) {
    	List<String> result = null;
        try {
        	ResultSet pkRet = dbMetData.getPrimaryKeys(null, database, tableName);
        	result = new ArrayList<String>();
            while(pkRet.next()){
                result.add(pkRet.getString("COLUMN_NAME"));
            }
        } catch (Exception e) {
        	result = null;
        	e.printStackTrace();
        }
    	return result;
    }
    
    /** 
     * @Title: findIndexColByTable 
     * @Description: 获取指定表的索引字段
     * @param dbMetData
     * @param database
     * @param tableName
     * @return List
     * @author lishipeng
     * @date 2018年9月17日下午15:08:53
     */
    default List<String> findIndexColByTable(DatabaseMetaData dbMetData, String database, String tableName) {
    	List<String> result = null;
        try {
        	ResultSet indexRet = dbMetData.getIndexInfo(null, database, tableName, false, true);
        	result = new ArrayList<String>();
            while(indexRet.next()){
            	if(indexRet.getString("COLUMN_NAME")!=null) {
            		result.add(indexRet.getString("COLUMN_NAME"));
            	}
            }
        } catch (Exception e) {
        	result = null;
        	e.printStackTrace();
        }
    	return result;
    }
    
    /** 
     * @Title: matchPkAndIndex 
     * @Description: 把主键和索引字段list，匹配到字段list中
     * @param pkList
     * @param indexList
     * @param result
     * @return List
     * @author lishipeng
     * @date 2018年9月17日下午15:08:53
     */
    default List<ColInfo> matchPkAndIndex(List<ColInfo> result, List<String> pkList, List<String> indexList) {
        for(int i=0;i<result.size();i++) {
        	ColInfo dc = result.get(i);
        	dc.setPk("0");
        	dc.setIdx("0");
        	if(pkList!=null&&pkList.size()>0) {
	         	//匹配主键
	         	for(int m=pkList.size()-1;m>=0;m--) {
	         		if(dc.getCol().toString().equalsIgnoreCase(pkList.get(m).toString())) {
	         			dc.setPk("1");
	         			pkList.remove(m);
	         			break;
	         		}
	         	}
        	}
        	if(indexList!=null&&indexList.size()>0) {
	         	//匹配索引
	         	for(int n=indexList.size()-1;n>=0;n--) {
	         		if(dc.getCol().toString().equalsIgnoreCase(indexList.get(n).toString())) {
	         			dc.setIdx("1");
	         			indexList.remove(n);
	         		}
	         	}
        	}
        }
   	 	return result;
    }
    
}
