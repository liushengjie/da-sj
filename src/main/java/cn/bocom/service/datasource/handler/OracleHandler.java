/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service.handler
 * @author: liushengjie
 * @date: 2018年8月28日 上午11:09:11
 */
package cn.bocom.service.datasource.handler;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.entity.DataSource;
import cn.bocom.entity.DataSource.DSTypeIdEnum;
import cn.bocom.entity.DsParam;
import cn.bocom.mapper.business.OracleHandlerMapper;
import cn.bocom.other.common.Constant;
import cn.bocom.other.util.RandomUtil;

/**
 * @ClassName: OrclHandler
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月28日 上午11:09:11
 */
@Component
public class OracleHandler extends AbstractDsHandler {

    @Autowired
    private OracleHandlerMapper oracleHandlerMapper;

    @Override
    public String getDSId() {
        return DSTypeIdEnum.ORACLE.DSTypeId();
    }

    @Override
    public DataSource getDataSource(DsParam dsp) {

        DataSource ds = new DataSource();
        if (dsp.getId() != null && !dsp.getId().equals("")) {
            ds.setId(dsp.getId());
        } else {
            ds.setId(RandomUtil.getRandomId(6)); // 数据源id
        }

        ds.setName(dsp.getName()); // 数据源名称
        ds.setType(dsp.getType()); // 数据源类型
        ds.setDriver(Constant.ORACLE_DRIVER_TPL); // 数据源驱动
        ds.setUrl(String.format(Constant.ORACLE_URL_TPL, dsp.getIp(), dsp.getPort(),
                dsp.getDatabase())); // 数据源url
        ds.setUsername(dsp.getUsername()); // 数据源用户名
        ds.setPwd(dsp.getPwd()); // 数据源密码
        ds.setXa(dsp.getXa()); // 数据源是否启用分布式事务
        ds.setCreateUser(dsp.getCreateUser()); // 数据源人
        ds.setCreateTime(new Date()); // 数据源创建时间
        if (dsp.getId() != null && !dsp.getId().equals("")) {
            ds.setState(dsp.getState()); // 数据源状态
        } else {
            ds.setState("1"); // 数据源状态
        }
        ds.setDataMode(dsp.getDataMode()); // 数据源使用方式

        return ds;
    }

    @Override
    public DsParam getDsParam(DataSource ds) {
        DsParam dsp = new DsParam();

        dsp.setId(ds.getId()); // 数据源id
        dsp.setName(ds.getName()); // 数据源名称
        dsp.setType(ds.getType()); // 数据源类型
        dsp.setTypeName(ds.gettypeName());
        dsp.setDriver(ds.getDriver()); // 数据源驱动
        String url = ds.getUrl();// 数据源url
        if (url != null && !url.equals("")) {
            String[] arr = url.replace("jdbc:oracle:thin:@", "").split(":");
            dsp.setIp(arr[0]);
            dsp.setPort(arr[1]);
            dsp.setDatabase(arr[2]);
        }
        dsp.setUsername(ds.getUsername()); // 数据源用户名
        dsp.setPwd(ds.getPwd()); // 数据源密码
        dsp.setXa(ds.getXa()); // 数据源是否启用分布式事务
        dsp.setCreateUser(ds.getCreateUser()); // 数据源人
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dsp.setCreateTime(sdf.format(ds.getCreateTime())); // 数据源创建时间
        dsp.setState(ds.getState()); // 数据源状态
        dsp.setDataMode(ds.getDataMode()); // 数据源使用方式

        return dsp;
    }

    @Override
    public List findTables(DsParam ds) {
    	return oracleHandlerMapper.findTables(ds);
    }
    
    @Override
	public List findColsByTable(DsParam ds, String SQL, String tableName) {
    	List result = null;
    	Connection conn = null;
        try {
        	conn = super.getConnection(ds);
        	if(conn==null) {
        		return result;
        	}
        	result = super.findAllColsBySQL(conn, "select * from ("+SQL+") t where rownum<2", null, tableName);
        } catch (Exception e) {
        	result = null;
        	e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
		return result;
	}
    
	@Override
	public List preloadData(String tableName, String limit) {
		return oracleHandlerMapper.preloadData(tableName, limit);
	}
	
	@Override
	public List preloadSQLData(String sql, String limit) {
		return oracleHandlerMapper.preloadSQLData(sql, limit);
	}

	@Override
	public int findCountByTable(String tableName) {
		return oracleHandlerMapper.findCountByTable(tableName);
	}
}
