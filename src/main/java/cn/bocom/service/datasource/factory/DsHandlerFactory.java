/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service.factory
 * @author: liushengjie
 * @date: 2018年8月28日 上午11:05:37
 */
package cn.bocom.service.datasource.factory;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

import cn.bocom.entity.DataSource;
import cn.bocom.entity.DsParam;
import cn.bocom.other.datasource.jdbc.DBContextHolder;
import cn.bocom.service.datasource.handler.AbstractDsHandler;

/**
 * @ClassName: DSHandlerFactory
 * @Description: 数据处理工厂
 * @author: liushengjie
 * @date: 2018年8月28日 上午11:05:37
 */
@Component
public class DsHandlerFactory implements ApplicationContextAware {

    private static Map<String, AbstractDsHandler> _dsHandlerBeanMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, AbstractDsHandler> map =
                applicationContext.getBeansOfType(AbstractDsHandler.class);
        _dsHandlerBeanMap = Maps.newConcurrentMap();
        map.forEach((key, value) -> _dsHandlerBeanMap.put(value.getDSId(), value));
    }

    @SuppressWarnings("unchecked")
    public static <T extends AbstractDsHandler> T getDataSourceBean(DataSource ds) {
        DBContextHolder.setDBType(ds.getId());
        AbstractDsHandler dsHandler = (T) _dsHandlerBeanMap.get(ds.getType());
        dsHandler.setDs(ds);
        return  (T)dsHandler;
    }

    @SuppressWarnings("unchecked")
    public static <T extends AbstractDsHandler> T getDataSourceBean(DsParam ds) {
        return (T) _dsHandlerBeanMap.get(ds.getType());
    }

//    @SuppressWarnings("unchecked")
//    public static <T extends AbstractDsHandler> T getDataSourceBean(String dsType) {
//        return (T) _dsHandlerBeanMap.get(dsType);
//    }

}
