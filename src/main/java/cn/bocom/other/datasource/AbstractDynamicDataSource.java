/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.other.datasource 
 * @author: liushengjie   
 * @date: 2018年8月10日 下午5:48:34 
 */
package cn.bocom.other.datasource;

import java.util.Map;
import java.util.function.Function;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import cn.bocom.other.cache.CacheProviderService;

/**
 * @ClassName: AbstractDynamicDataSource
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月10日 下午5:48:34
 */
public abstract class AbstractDynamicDataSource<T extends DataSource> extends AbstractRoutingDataSource
		implements ApplicationContextAware {
	/** 日志 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
    @Qualifier("localCacheService")
    private CacheProviderService localCacheService;
    
	/** 默认的数据源KEY */
	protected static final String DEFAULT_DATASOURCE_KEY = "defaultDataSource";

	/** 数据源KEY-VALUE键值对 */
	private Map<Object, Object> targetDataSources;

	/** spring容器上下文 */
	private static ApplicationContext ctx;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ctx = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}

	public static Object getBean(String name) {
		return ctx.getBean(name);
	}

	public void setTargetDataSources(Map<Object, Object> targetDataSources) {
		this.targetDataSources = targetDataSources;
		super.setTargetDataSources(targetDataSources);
		super.afterPropertiesSet();
	}

	public Map<Object, Object> getTargetDataSources() {
		return this.targetDataSources;
	}

	/**
	 * @Title: createDataSource
	 * @Description: 创建XA数据源
	 * @param driverClassName
	 * @param url
	 * @param username
	 * @param password
	 * @return T
	 * @author liushengjie
	 * @date 2018年8月10日下午6:01:23
	 */
	public abstract T createXADataSource(String driverClassName, String url, String username, String password);
	
	
	/**
	 * @Title: createDataSource
	 * @Description: 创建数据源
	 * @param driverClassName
	 * @param url
	 * @param username
	 * @param password
	 * @return T
	 * @author liushengjie
	 * @date 2018年8月10日下午6:01:23
	 */
	public abstract T createDataSource(String driverClassName, String url, String username, String password);

	/*
	 * (non Javadoc)
	 * @Title: determineCurrentLookupKey
	 * @Description: * 设置系统当前使用的数据源 <p>数据源为空或者为0时，自动切换至默认数据源，即在配置文件中定义的默认数据源
	 * @return
	 * @see org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource#
	 * determineCurrentLookupKey()
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceKey = DBContextHolder.getDBType();
		if (StringUtils.isEmpty(dataSourceKey)) {
			logger.debug("【使用系统默认数据源：{}】", DEFAULT_DATASOURCE_KEY);
			// 使用默认数据源
			return DEFAULT_DATASOURCE_KEY;
		}
		// 判断数据源是否需要初始化
		logger.debug("【切换至数据源：{}】", dataSourceKey);
		return this.verifyAndInitDataSource();
	}

	/**
	 * @Title: verifyAndInitDataSource
	 * @Description: 判断数据源是否需要初始化
	 * @author liushengjie
	 * @date 2018年8月10日下午6:01:57
	 */
	private String verifyAndInitDataSource() {
		String dataSourceKey = DBContextHolder.getDBType();
		Object obj = this.targetDataSources.get(dataSourceKey);
		if (obj != null) {
			return dataSourceKey;
		}
		logger.info("【初始化数据源】");
		
		Function<String, DataSource> cb = s -> {
			String sql = "select * from t_data_source where id = '" + dataSourceKey + "' and state = '1'";
	        JdbcTemplate jdbcTemplate = new JdbcTemplate((DataSource)getTargetDataSources().get(DEFAULT_DATASOURCE_KEY));
	        Map<String, Object> r = jdbcTemplate.queryForMap(sql);
	        if(MapUtils.isEmpty(r)) {
	        	logger.info("【未查询到配置的数据源{}】",dataSourceKey);
	        	return null;
	        }
	        
	        T datasource;
	        if(Boolean.valueOf(r.get("xa").toString())) {
	        	datasource = this.createXADataSource(r.get("driver").toString(), r.get("url").toString(), r.get("username").toString(), r.get("pwd").toString());
	        }else {
	        	datasource = this.createDataSource(r.get("driver").toString(), r.get("url").toString(), r.get("username").toString(), r.get("pwd").toString());
	        }
	        localCacheService.set(dataSourceKey, datasource);
			return datasource;
		};
		
		@SuppressWarnings("unchecked")
		T datasource = (T)localCacheService.get(dataSourceKey, cb);
		if(datasource == null) {
			return DEFAULT_DATASOURCE_KEY;
		}
		this.addTargetDataSource(dataSourceKey, datasource);
		return dataSourceKey;
	}

	/**
	 * @Title: addTargetDataSource
	 * @Description: 往数据源key-value键值对集合添加新的数据源
	 * @param key
	 * @param dataSource
	 *            void
	 * @author liushengjie
	 * @date 2018年8月10日下午6:02:09
	 */
	public void addTargetDataSource(String key, T dataSource) {
		this.targetDataSources.put(key, dataSource);
		super.setTargetDataSources(this.targetDataSources);
		super.afterPropertiesSet();
	}
}
