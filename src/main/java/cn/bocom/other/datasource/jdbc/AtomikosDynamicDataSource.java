/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.other.datasource
 * @author: liushengjie
 * @date: 2018年8月13日 下午3:10:52
 */
package cn.bocom.other.datasource.jdbc;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.xa.DruidXADataSource;
import com.atomikos.jdbc.AtomikosDataSourceBean;

import cn.bocom.other.util.RandomUtil;

/**
 * @ClassName: AtomikosDynamicDataSource
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月13日 下午3:10:52
 */
@Primary
public class AtomikosDynamicDataSource extends AbstractDynamicDataSource<DataSource> {
    private boolean testWhileIdle = true;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;

    // 是否打开连接泄露自动检测
    private boolean removeAbandoned = false;
    // 连接长时间没有使用，被认为发生泄露时长
    private long removeAbandonedTimeoutMillis = 300 * 1000;
    // 发生泄露时是否需要输出 log，建议在开启连接泄露检测时开启，方便排错
    private boolean logAbandoned = false;

    // private int maxPoolPreparedStatementPerConnectionSize = -1;

    // 配置监控统计拦截的filters
    private String filters = "wall,log4j"; // 监控统计："stat" 防SQL注入："wall" 组合使用： "stat,wall"
    private List<Filter> filterList;

    @Override
    public AtomikosDataSourceBean createXADataSource(String driverClassName, String url,
            String username, String password) {

        DruidXADataSource ds = new DruidXADataSource();

        ds.setName(RandomUtil.get16TimeRandom());
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setInitialSize(20);
        ds.setMinIdle(2);
        ds.setMaxActive(100);
        ds.setMaxWait(60000);
        ds.setUseUnfairLock(true);
        ds.setTimeBetweenEvictionRunsMillis(60000);
        ds.setMinEvictableIdleTimeMillis(300000);

        ds.setValidationQuery("select 1 from dual");
        ds.setTestWhileIdle(testWhileIdle);
        ds.setTestOnBorrow(testOnBorrow);
        ds.setTestOnReturn(testOnReturn);

        ds.setRemoveAbandoned(removeAbandoned);
        ds.setRemoveAbandonedTimeoutMillis(removeAbandonedTimeoutMillis);
        ds.setLogAbandoned(logAbandoned);

        ds.setMaxPoolPreparedStatementPerConnectionSize(20);

        if (StringUtils.isNotBlank(filters)) try {
            ds.setFilters(filters);
        } catch (SQLException e) {
            ds.close();
            throw new RuntimeException(e);
        }

        AtomikosDataSourceBean atomikosDataSourceBean = new AtomikosDataSourceBean();
        atomikosDataSourceBean.setXaDataSource(ds);
        // 设置数据源的唯一名称，不允许重复
        atomikosDataSourceBean.setUniqueResourceName(RandomUtil.getTimeRandomId(32));
        atomikosDataSourceBean.setMaxPoolSize(20);
        atomikosDataSourceBean.setMinPoolSize(5);
        atomikosDataSourceBean.setBorrowConnectionTimeout(60);
        atomikosDataSourceBean.setMaxLifetime(0);

        addFilterList(ds);
        return atomikosDataSourceBean;
    }

    @Override
    public DataSource createDataSource(String driverClassName, String url, String username,
            String password) {

        DruidDataSource ds = new DruidDataSource();

        ds.setName(RandomUtil.get16TimeRandom());
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driverClassName);
        ds.setInitialSize(10);
        ds.setMinIdle(2);
        ds.setMaxActive(30);
        ds.setMaxWait(60000);
        ds.setUseUnfairLock(true);
        ds.setTimeBetweenEvictionRunsMillis(60000);
        ds.setMinEvictableIdleTimeMillis(300000);

        ds.setValidationQuery("select 1 from dual");
        ds.setTestWhileIdle(testWhileIdle);
        ds.setTestOnBorrow(testOnBorrow);
        ds.setTestOnReturn(testOnReturn);

        ds.setRemoveAbandoned(removeAbandoned);
        ds.setRemoveAbandonedTimeoutMillis(removeAbandonedTimeoutMillis);
        ds.setLogAbandoned(logAbandoned);

        ds.setMaxPoolPreparedStatementPerConnectionSize(20);

        if (StringUtils.isNotBlank(filters)) try {
            ds.setFilters(filters);
        } catch (SQLException e) {
            ds.close();
            throw new RuntimeException(e);
        }

        addFilterList(ds);
        return ds;
    }

    private void addFilterList(DruidDataSource ds) {
        if (filterList != null) {
            List<Filter> targetList = ds.getProxyFilters();
            for (Filter add : filterList) {
                boolean found = false;
                for (Filter target : targetList) {
                    if (add.getClass().equals(target.getClass())) {
                        found = true;
                        break;
                    }
                }
                if (!found) targetList.add(add);
            }
        }
    }
}
