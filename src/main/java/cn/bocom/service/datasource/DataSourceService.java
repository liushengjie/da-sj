/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service
 * @author: liushengjie
 * @date: 2018年8月15日 下午5:15:33
 */
package cn.bocom.service.datasource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.bocom.entity.Alias;
import cn.bocom.entity.DataCol;
import cn.bocom.entity.DataSource;
import cn.bocom.entity.DsParam;
import cn.bocom.mapper.main.DataSourceMapper;
import cn.bocom.other.util.BeanUtil;
import cn.bocom.other.util.ListUtil;
import cn.bocom.other.util.MapUtil;
import cn.bocom.r_service.system.alias.AliasService;
import cn.bocom.service.datasource.factory.DsHandlerFactory;
import cn.bocom.service.datasource.handler.AbstractDsHandler;

/**
 * @ClassName: DataSourceService
 * @Description: 数据源管理服务
 * @author: liushengjie
 * @date: 2018年8月15日 下午5:15:33
 */
@Component
public class DataSourceService {
    private static Logger logger = LoggerFactory.getLogger(DataSourceService.class);

    @Autowired
    private DataSourceMapper dataSourceMapper;

    @Autowired
    private AliasService aliasService;

    /**
     * @Title: selectDsByPage
     * @Description: 分页查询数据源
     * @param ds
     * @param currentPage
     * @param pageSize
     * @return PageInfo<DsParam>
     * @author liushengjie
     * @date 2018年9月13日下午2:25:05
     */
    public PageInfo<DsParam> selectDsByPage(DataSource ds, int currentPage, int pageSize) {
    	Page page = PageHelper.startPage(currentPage, pageSize);
        List<DataSource> docs = dataSourceMapper.selectDs(ds);
        PageInfo<DsParam> pageInfo = new PageInfo<>(docs.stream().map(x -> {
            AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(x);
            return dsHandler.getDsParam(x);
        }).collect(Collectors.toList()));   
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /**
     * @Title: insertDs
     * @Description: 插入数据源
     * @param ds
     * @return int
     * @author liushengjie
     * @date 2018年9月13日下午2:25:13
     */
    public int insertDs(DsParam ds) {
        AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
        return dataSourceMapper.insertDs(dsHandler.getDataSource(ds));
    }

    /**
     * @Title: updateDs
     * @Description: 更新数据源
     * @param ds
     * @return int
     * @author liushengjie
     * @date 2018年9月13日下午2:25:21
     */
    public int updateDs(DsParam ds) {
        AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
        return dataSourceMapper.updateDs(dsHandler.getDataSource(ds));
    }

    /**
     * @Title: deleteDs
     * @Description: 删除数据源
     * @param id
     * @return int
     * @author liushengjie
     * @date 2018年9月13日下午2:25:31
     */
    public int deleteDs(String id) {
        DataSource ds = new DataSource();
        ds.setId(id);
        return dataSourceMapper.deleteByPrimaryKey(ds);
    }

    /**
     * @Title: selectDsById
     * @Description: 根据ID查询数据源对象
     * @param id
     * @return DataSource
     * @author liushengjie
     * @date 2018年9月13日下午2:22:43
     */
    public DataSource selectDsById(String id) {
        DataSource ds = new DataSource();
        ds.setId(id);
        List<DataSource> docs = dataSourceMapper.selectDs(ds);
        return docs.get(0);
    }

    /**
     * @Title: connect
     * @Description: 测试数据源连接是否有效
     * @param ds
     * @return boolean
     * @author liushengjie
     * @date 2018年9月13日下午2:23:04
     */
    public boolean connect(DsParam ds) {
        AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
        return dsHandler.connect(ds);
    }

    /**
     * @Title: findTables
     * @Description: 查询数据源下的所有表
     * @param dataSource
     * @return List
     * @author liushengjie
     * @date 2018年9月13日下午2:23:18
     */
    public List findTables(String dataSource) {
        List retList = null;
        DataSource ds = selectDsById(dataSource);
        AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
        List<Map<String,Object>> tableList = dsHandler.findTables(dsHandler.getDsParam(ds));
        
        for(Map<String,Object> map:tableList){
        	String tableName = map.get("TABLE_NAME").toString();
        	int count = dsHandler.findCountByTable(tableName);
        	map.put("TABLE_ROWS", count);
        }
        
        
        List aliasList = getDSAlias(dataSource);
        if(aliasList!=null && aliasList.size()>0){
        	retList = ListUtil.mergeListLeft(tableList, aliasList, "TABLE_NAME", "tablename");
        }else{
        	retList = tableList;
        }
        
        return retList;
    }

    /**
     * @Title: getDSAlias
     * @Description: 获取表的别名信息
     * @param dataSource
     * @return List<Map<String,Object>>
     * @author liushengjie
     * @date 2018年9月13日下午2:23:35
     */
    public List<Map<String, Object>> getDSAlias(String dataSource) {
        List<Map<String, Object>> retList = null;
        Alias alias = new Alias();
        alias.setDatasource(dataSource);
        alias.setType("1");
        retList = aliasService.queryAlias(alias);
        return retList;
    }

    /**
     * @Title: findColsByTable
     * @Description: 查询表下的所有字段
     * @param dataSource
     * @param tableName
     * @return List
     * @author liushengjie
     * @throws Exception
     * @date 2018年9月13日下午2:23:53
     */
    public List findColsByTable(String dataSource, String tableName) {
    	DataSource ds = selectDsById(dataSource);
        AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
        List colList = dsHandler.findColsByTable(dsHandler.getDsParam(ds), "select * from " + tableName, tableName);
    	if(colList==null) {
    		return null;
    	}

    	List result = null;
        List colAliasList = getColAlias(dataSource, tableName);        
        if(colAliasList!=null && colAliasList.size()>0){
        	result = ListUtil.mergeListLeft(colList, colAliasList, "col", "colname");
        }else{
        	result = colList;
        }
        

        try {
        	result = new BeanUtil().mapToObject(result, DataCol.class);
        } catch (Exception e) {
        	e.printStackTrace();
        }

        return result;
    }

    /**
     * @Title: findColsBySQL
     * @Description: 查询SQL下的所有字段
     * @param dataSource
     * @param SQL
     * @return List
     * @author lishipeng
     * @throws Exception
     * @date 2018年9月14日上午10:28:53
     */ 
     public List findColsBySQL(String dataSource, String SQL) {
    	 DataSource ds = selectDsById(dataSource);
         AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
         List colList = dsHandler.findColsByTable(dsHandler.getDsParam(ds), SQL, null);
    	 if(colList==null) {
     		return null;
    	 }
    	 
    	 try {
    		 colList = new BeanUtil().mapToObject(colList, DataCol.class);
         } catch(Exception e) {
         	e.printStackTrace();
         }
    	 
    	 return colList;
     }
    
    /**
     * @Title: getColAlias
     * @Description: 获取字段的别名信息
     * @param dataSource
     * @param tableName
     * @return List
     * @author liushengjie
     * @date 2018年9月13日下午2:24:15
     */
    public List getColAlias(String dataSource, String tableName) {
        Alias alias = new Alias();
        alias.setDatasource(dataSource);
        alias.setTablename(tableName);
        alias.setType("2");
        return aliasService.queryAlias(alias);
    }

    /**
     * @Title: preloadData
     * @Description: 上游表数据预览
     * @param dataSource
     * @param tableName
     * @param limit
     * @return List
     * @author liushengjie
     * @date 2018年9月13日下午2:24:29
     */
    public List<Map<String, Object>> preloadData(String dataSource, String tableName,String limit) {
        DataSource ds = selectDsById(dataSource);
        AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
        List<Map<String, Object>> result = dsHandler.preloadData(tableName, limit);
        return result.stream().map(m -> {
			return MapUtil.transformLowerCase(m);
		}).collect(Collectors.toList());
    }

    public List<Map<String, Object>> preloadSQLData(String dataSource, String sql, String limit) {
        DataSource ds = selectDsById(dataSource);
        AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
        List<Map<String, Object>> result = dsHandler.preloadSQLData(sql, limit);
        return result.stream().map(m -> {
			return MapUtil.transformLowerCase(m);
		}).collect(Collectors.toList());
    }

    public int findCountByTable(String dataSource, String tableName) {
        DataSource ds = selectDsById(dataSource);
        AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
        int result = dsHandler.findCountByTable(tableName);
        return result;
    }
}
