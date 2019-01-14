/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service.handler
 * @author: liushengjie
 * @date: 2018年8月28日 上午11:06:12
 */
package cn.bocom.service.datasource.handler;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.bocom.entity.DataSource;
import cn.bocom.entity.DsParam;
import cn.bocom.other.datasource.jdbc.AtomikosDynamicDataSource;
import cn.bocom.other.util.DBUtil;

/**
 * @ClassName: IDSHandler
 * @Description: 数据处理接口类
 * @author: liushengjie
 * @date: 2018年8月28日 上午11:06:12
 */
public abstract class AbstractDsHandler {

    /** 日志 */
    protected Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AtomikosDynamicDataSource atomikosDynamicDataSource;
    
    private DataSource ds;

	public abstract String getDSId();

    /**
     * @Title: getDataSource
     * @Description: 通过参数获取数据源对象
     * @param ds
     * @return DataSource
     * @author liushengjie
     * @date 2018年8月28日下午4:10:03
     */
    public abstract DataSource getDataSource(DsParam dsp);

    /**
     * @Title: getDsParam
     * @Description: 通过数据源获取参数对象
     * @param ds
     * @return DsParam
     * @author liushengjie
     * @date 2018年8月28日下午4:56:37
     */
    public abstract DsParam getDsParam(DataSource ds);

    /**
     * @Title: connect
     * @Description: 测试数据源连接是否成功
     * @param ds
     * @return boolean
     * @author liushengjie
     * @date 2018年8月28日下午1:34:45
     */
    public boolean connect(DsParam ds) {
        Connection conn = null;
        boolean connFlag = false;
        try {
            DataSource dsBean = getDataSource(ds);
            javax.sql.DataSource dataSource = atomikosDynamicDataSource.createDataSource(
                    dsBean.getDriver(), dsBean.getUrl(), dsBean.getUsername(), dsBean.getPwd());
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
     * @param ds
     * @return Connection
     * @author lishipeng
     * @date 2018年9月14日上午10:34:45
     */
    public Connection getConnection(DsParam ds) {
    	Connection conn = null;
        try {
            conn = atomikosDynamicDataSource.getConnection();
        } catch (Exception e) {
        	conn = null;
        	e.printStackTrace();
        }
		return conn;
	}

    /**
     * @Title: findTables
     * @Description: 获取数据源下所有表
     * @param ds
     * @return List
     * @author liushengjie
     * @date 2018年9月4日下午4:39:59
     */
    public abstract List<Map<String,Object>> findTables(DsParam ds);
    
    public abstract List<Map<String,Object>> findColsByTable(DsParam ds, String SQL, String tableName);
    
    public abstract List<Map<String,Object>> preloadData(String tableName, String limit);
    
    public abstract List<Map<String,Object>> preloadSQLData(String sql, String limit);
    
    public abstract int findCountByTable(String tableName);
    
    
    /** 
     * @Title: findAllColBySQL 
     * @Description: 根据SQL从conn里获取所有字段信息
     * @param conn
     * @param SQL
     * @return List
     * @author lishipeng
     * @date 2018年9月17日下午15:08:53
     */
    public List findAllColsBySQL(Connection conn, String SQL, String database, String tableName) {
    	List result = null;
    	PreparedStatement preparedStatement = null;
        try {
        	preparedStatement = conn.prepareStatement(SQL);
            // 获取ResultSetMetaData
            ResultSetMetaData metaData = preparedStatement.executeQuery().getMetaData();
            result = new ArrayList();
            for (int i = 0; i < metaData.getColumnCount(); i++) {
            	Map map = new HashMap();
                // resultSet数据下标从1开始  
                String columnName = metaData.getColumnName(i + 1);  
                String columnType = metaData.getColumnTypeName(i + 1);
                int length = metaData.getColumnDisplaySize(i+1);
                String isNullAble = metaData.isNullable(i+1)==0?"0":"1";
                
                map.put("col", columnName.toLowerCase());
                map.put("type", columnType);
                map.put("changeType", DBUtil.changeDBType(columnType));
                map.put("length", length+"");
                map.put("nullable", isNullAble);
                
                result.add(map);
            }
            
            //如果有单独的表名，则获取主键和索引信息
        	if(tableName!=null&&result!=null&&result.size()>0) {
        		DatabaseMetaData dbMetData = conn.getMetaData();
        		result = matchPkAndIndex(result, findPkColByTable(dbMetData, database, tableName), findIndexColByTable(dbMetData, database, tableName));
        	}
        } catch (Exception e) {
        	result = null;
        	e.printStackTrace();
        } finally {
            try {
            	if(preparedStatement != null) {
            		preparedStatement.close();
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
    private List findPkColByTable(DatabaseMetaData dbMetData, String database, String tableName) {
    	List result = null;
        try {
        	ResultSet pkRet = dbMetData.getPrimaryKeys(null, database, tableName);
        	result = new ArrayList();
            while(pkRet.next()){
                result.add(pkRet.getString("COLUMN_NAME"));
            }
        } catch (Exception e) {
        	result = null;
        	e.printStackTrace();
        }
    	return result;
    }
    
    //获取指定表的索引字段
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
    private List findIndexColByTable(DatabaseMetaData dbMetData, String database, String tableName) {
    	List result = null;
        try {
        	ResultSet indexRet = dbMetData.getIndexInfo(null, database, tableName, false, true);
        	result = new ArrayList();
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
    public List matchPkAndIndex(List result, List pkList, List indexList) {
        for(int i=0;i<result.size();i++) {
        	Map dc = (Map)result.get(i);
        	dc.put("pk", "0");
        	dc.put("idx", "0");
        	if(pkList!=null&&pkList.size()>0) {
	         	//匹配主键
	         	for(int m=pkList.size()-1;m>=0;m--) {
	         		if(dc.get("col").toString().equalsIgnoreCase(pkList.get(m).toString())) {
	         			dc.put("pk", "1");
	         			pkList.remove(m);
	         			break;
	         		}
	         	}
        	}
        	if(indexList!=null&&indexList.size()>0) {
	         	//匹配索引
	         	for(int n=indexList.size()-1;n>=0;n--) {
	         		if(dc.get("col").toString().equalsIgnoreCase(indexList.get(n).toString())) {
	         			dc.put("idx", "1");
	         			indexList.remove(n);
	         			break;
	         		}
	         	}
        	}
        }
   	 	return result;
    }
    
    /**  
	* @return ds  
	*/
	public DataSource getDs() {
		return ds;
	}

	/**  
	* @param ds 要设置的 ds  
	*/
	public void setDs(DataSource ds) {
		this.ds = ds;
	}


}
