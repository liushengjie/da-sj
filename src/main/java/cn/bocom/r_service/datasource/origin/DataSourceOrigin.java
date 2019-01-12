package cn.bocom.r_service.datasource.origin;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.bocom.mapper.main.R_DataSourceMapper;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.OriginEntity;
import cn.bocom.r_service.datasource.DatasourceUtil;
import cn.bocom.r_service.datasource.DataSourcePlugin;

/**
 * 数据源对象处理类
 * @author liushengjie
 * @version $Id: DataSourceService.java, v 0.1 2019年1月11日 上午10:28:06 liushengjie Exp $
 */
@Component
public  class DataSourceOrigin {
    private static Logger logger = LoggerFactory.getLogger(DataSourceOrigin.class);
    
    @Autowired
    private R_DataSourceMapper dataSourceMapper;
    
    /**
     * 插入数据源
     * @param type
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T extends OriginEntity> int insertDataSource(int type, String obj) {
        DataSourcePlugin<T> op = (DataSourcePlugin<T>)DatasourceUtil.originPlugin(type);
        Class<? extends OriginEntity> oe = DatasourceUtil.originEntity(type);
        
        T originObj = (T)JSON.parseObject(obj, oe);
        DataSource datasource = op.convertDataSource(originObj, type);
        
        return dataSourceMapper.insert(datasource);
    }
    
    /**
     * 分页查询数据源信息
     * @param datasource
     * @param currentPage
     * @param pageSize
     * @return
     */
    public PageInfo<? extends OriginEntity> selectOriginListByPage(DataSource datasource, int currentPage, int pageSize) {
    	Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        List<DataSource> docs = dataSourceMapper.selectDs(datasource);
        PageInfo<? extends OriginEntity> pageInfo = new PageInfo<>(docs.stream().map(x -> {
        	DataSourcePlugin<? extends OriginEntity> op = (DataSourcePlugin<? extends OriginEntity>)DatasourceUtil.originPlugin(Integer.parseInt(x.getType()));
            return op.converOrigin(x);
        }).collect(Collectors.toList()));   
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }
    
    /**
     * 根据ID查询数据源信息
     * @param datasourceId
     * @return
     */
    public DataSource selectDataSourceById(String datasourceId) {
    	DataSource ds = new DataSource();
        ds.setId(datasourceId);
        List<DataSource> docs = dataSourceMapper.selectDs(ds);
        if(docs==null||docs.size()==0) {
        	return null;
        }
        DataSource d = docs.get(0);
        return d;
    }
    
    /**
     * 更新数据源信息
     * @param type
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
	public <T extends OriginEntity> int updateDataSource(int type, String obj) {
    	DataSourcePlugin<T> op = (DataSourcePlugin<T>)DatasourceUtil.originPlugin(type);
        Class<? extends OriginEntity> oe = DatasourceUtil.originEntity(type);
        
        T originObj = (T)JSON.parseObject(obj, oe);
        DataSource datasource = op.convertDataSource(originObj, type);
        
        return dataSourceMapper.updateDs(datasource);
    }
    
    
    /**
     * 删除数据源信息
     * @param id
     * @return
     */
    public int deleteDataSource(String datasourceId) {
    	try {
    		DataSource ds = new DataSource();
            ds.setId(datasourceId);
        	return dataSourceMapper.deleteByPrimaryKey(ds);	
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
    }
}

































