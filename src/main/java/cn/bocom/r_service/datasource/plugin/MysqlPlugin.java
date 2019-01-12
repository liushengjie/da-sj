package cn.bocom.r_service.datasource.plugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.mapper.business.R_MysqlHandlerMapper;
import cn.bocom.other.common.Constant;
import cn.bocom.other.util.RandomUtil;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.datasource.form.MySQL;
import cn.bocom.r_service.datasource.DatasourceUtil;
import cn.bocom.r_service.datasource.OriginPlugin;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;

/**
 * mysql 插件类
 * @author liushengjie
 * @version $Id: MysqlPlugin.java, v 0.1 2019年1月11日 下午2:20:10 liushengjie Exp $
 */
@Component
public class MysqlPlugin implements OriginPlugin<MySQL>{
    private static Logger logger = LoggerFactory.getLogger(MysqlPlugin.class);
    
    private static R_MysqlHandlerMapper mysqlHandlerMapper;
    
    @Autowired  
    public void setMapper(R_MysqlHandlerMapper mysqlHandlerMapper) {  
        MysqlPlugin.mysqlHandlerMapper = mysqlHandlerMapper;  
    } 

    @Override
    public DataSource convertDataSource(MySQL originObj, int typeCode) {
        DataSource datasource = new DataSource();
        datasource.setId(originObj.getId()!=null?originObj.getId():RandomUtil.getRandomId(6));
        datasource.setName(originObj.getName()); // 数据源名称
        datasource.setType(String.valueOf(typeCode)); // 数据源类型
        datasource.setDriver(Constant.MYSQL_DRIVER_TPL); // 数据源驱动
        datasource.setUrl(String.format(Constant.MYSQL_URL_TPL, originObj.getIp(), originObj.getPort(),
            originObj.getDatabase())); // 数据源url
        datasource.setUsername(originObj.getUsername()); // 数据源用户名
        datasource.setPwd(originObj.getPwd()); // 数据源密码
        datasource.setXa(originObj.getXa()); // 数据源是否启用分布式事务
        datasource.setCreateUser(originObj.getCreateUser()); // 数据源人
        datasource.setCreateTime(new Date()); // 数据源创建时间
        datasource.setState(originObj.getId()!=null?String.valueOf(originObj.getStatus()):"1");
        datasource.setDataMode(String.valueOf(originObj.getModel())); // 数据源使用方式

        return datasource;
    }

    @Override
    public MySQL converOrigin(DataSource datasource) {
    	MySQL mysql = new MySQL();
    	mysql.setId(datasource.getId());
    	mysql.setName(datasource.getName());
    	mysql.setType((datasource.getType()==null||datasource.getType().equals(""))?0:Integer.parseInt(datasource.getType()));
    	mysql.setTypeName(DataSourceEnum.MYSQL.getName());
    	mysql.setDriver(datasource.getDriver());
    	
    	String url = datasource.getUrl();// 数据源url
        if (url != null && !url.equals("")) {
            String url0 = url.replace("jdbc:mysql://", "")
                    .replace("?useUnicode=true&characterEncoding=utf8", "");
            String ip = url0.split(":")[0];
            mysql.setIp(ip);
            String port = url0.split(":")[1].split("/")[0];
            mysql.setPort(port);
            String db = url0.split(":")[1].split("/")[1];
            mysql.setDatabase(db);
        }
        
        mysql.setModel((datasource.getDataMode()==null||datasource.getDataMode().equals(""))?0:Integer.parseInt(datasource.getDataMode()));
        mysql.setStatus((datasource.getState()==null||datasource.getState().equals(""))?0:Integer.parseInt(datasource.getState()));
    	mysql.setXa(datasource.getXa());
		mysql.setUsername(datasource.getUsername());
		mysql.setPwd(datasource.getPwd());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mysql.setCreateTime(sdf.format(datasource.getCreateTime()));
		mysql.setCreateUser(datasource.getCreateUser());
		return mysql;
    }

    @Override
    public List<TableInfo> showTablesInfo(DataSource datasource) {
        MySQL mysql = converOrigin(datasource);
        List<TableInfo> ret = mysqlHandlerMapper.showTablesInfo(mysql.getDatabase());
        return ret;
    }
}









































