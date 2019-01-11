package cn.bocom.r_service.datasource.origin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import cn.bocom.entity.DataSource;
import cn.bocom.mapper.main.DataSourceMapper;
import cn.bocom.r_entity.datasource.OriginEntity;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;

/**
 * 数据源对象处理类
 * @author liushengjie
 * @version $Id: DataSourceService.java, v 0.1 2019年1月11日 上午10:28:06 liushengjie Exp $
 */
@Component
public  class DataSourceOrigin {
    private static Logger logger = LoggerFactory.getLogger(DataSourceOrigin.class);
    
    @Autowired
    private DataSourceMapper dataSourceMapper;
    
    @SuppressWarnings("unchecked")
    public <T extends OriginEntity> int insertDataSource(int type, String obj) {
        OriginPlugin<T> op = (OriginPlugin<T>)originPlugin(type);
        Class<? extends OriginEntity> oe = originEntity(type);
        
        T originObj = (T)JSON.parseObject(obj, oe);
        DataSource datasource = op.convertDataSource(originObj, type);
        
        return dataSourceMapper.insert(datasource);
    }
    
    
    /**
     * 源对象处理插件
     * @param type
     * @return
     */
    private OriginPlugin<?> originPlugin(int type){
        DataSourceEnum datasourceEnum = DataSourceEnum.match(type, null);
        return datasourceEnum!=null?datasourceEnum.getPluginClass():null;
    }
    
    /**
     * 源对象实体类
     * @param type
     * @return
     */
    private Class<? extends OriginEntity> originEntity(int type){
        DataSourceEnum datasourceEnum = DataSourceEnum.match(type, null);
        return datasourceEnum!=null?datasourceEnum.getEntityClass():null;
    }
}

































