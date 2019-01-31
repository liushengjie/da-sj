package cn.bocom.r_service.datasource;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.other.datasource.jdbc.DBContextHolder;
import cn.bocom.r_entity.datasource.ColInfo;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.OriginEntity;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;

/**
 * 数据源工具类
 * @author liushengjie
 * @version $Id: DatasourceUtil.java, v 0.1 2019年1月12日 下午2:23:43 liushengjie Exp $
 */
@Component
public class DatasourceUtil {
    
    private static DataSourceOrigin datasourceOrigin;
    
    @Autowired  
    public void setOrigin(DataSourceOrigin datasourceOrigin) {  
        DatasourceUtil.datasourceOrigin = datasourceOrigin;  
    }  
    
    /**
     * 通过源类型获取处理插件
     * @param type
     * @return
     */
    public static DataSourcePlugin<?> originPlugin(DataSource datasource){
        DataSourceEnum datasourceEnum = DataSourceEnum.match(Integer.valueOf(datasource.getType()), null);
        return datasourceEnum!=null?datasourceEnum.getPluginClass(datasource):null;
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
    public static DataSourcePlugin<?> originPluginById(String datasourceId){
        DataSource datasource = datasourceOrigin.selectDataSourceById(datasourceId);
        changeDataSourceContext(datasourceId);
        return originPlugin(datasource);
    }
    
    /**
     * 切换数据源
     * @param datasourceId
     */
    public static void changeDataSourceContext(String datasourceId) {
        DBContextHolder.setDBType(datasourceId);
    }
    
    /**
     * @Title: getConnection
     * @Description: 获取数据源连接
     * @param DataSource
     * @return Connection
     * @author lishipeng
     * @date 2018年9月14日上午10:34:45
     */
    public static Connection getJDBCConnection(DataSource ds) {
        String url = ds.getUrl();
        String user = ds.getUsername();
        String password = ds.getPwd();
 
        Connection connection = null;
        try {
        	Class.forName(ds.getDriver());
            connection = DriverManager.getConnection(url,user,password);
            return connection;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    public static List<ColInfo> findColsByJDBC(DataSource datasource, String tableName, String database) {
    	List<ColInfo> result = null;
    	Connection conn = null;
    	PreparedStatement preparedStatement = null;
        try {
        	conn = getJDBCConnection(datasource);
        	if(conn==null) {
        		return result;
        	}
        	preparedStatement = conn.prepareStatement("select * from "+tableName+" t where 1=2");
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
    private static List<String> findPkColByTable(DatabaseMetaData dbMetData, String database, String tableName) {
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
    private static List<String> findIndexColByTable(DatabaseMetaData dbMetData, String database, String tableName) {
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
    private static List<ColInfo> matchPkAndIndex(List<ColInfo> result, List<String> pkList, List<String> indexList) {
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
