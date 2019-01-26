package cn.bocom.r_service.datasource.plugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.mapper.business.R_OracleHandlerMapper;
import cn.bocom.other.common.Constant;
import cn.bocom.other.util.ListUtil;
import cn.bocom.other.util.RandomUtil;
import cn.bocom.r_entity.datasource.ColInfo;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.datasource.form.Oracle;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_entity.resource.ResourceData;
import cn.bocom.r_service.datasource.DataSourcePlugin;
import cn.bocom.r_service.datasource.DatasourceUtil;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;
import cn.bocom.r_service.system.alias.AliasService;

/**
 * oracle 插件类
 * @author liushengjie
 * @version $Id: OraclePlugin.java, v 0.1 2019年1月11日 下午2:20:36 liushengjie Exp $
 */
@Component
public class OraclePlugin implements DataSourcePlugin<Oracle>{
    private static Logger logger = LoggerFactory.getLogger(OraclePlugin.class);

    private static R_OracleHandlerMapper oracleHandlerMapper;
    private static AliasService aliasService;
    private static DataSourceOrigin datasourceOrigin;
    
    @Autowired  
    public void setAliasService(AliasService aliasService) {  
        OraclePlugin.aliasService = aliasService;  
    } 
    
    @Autowired  
    public void setDatasourceOrigin(DataSourceOrigin datasourceOrigin) {  
        OraclePlugin.datasourceOrigin = datasourceOrigin;  
    }
    @Autowired  
    public void setMapper(R_OracleHandlerMapper oracleHandlerMapper) {  
    	OraclePlugin.oracleHandlerMapper = oracleHandlerMapper;  
    } 
    
    
    @Override
    public DataSource convertDataSource(Oracle originObj, int typeCode) {
    	DataSource datasource = new DataSource();
        datasource.setId(originObj.getId()!=null?originObj.getId():RandomUtil.getRandomId(6));
        datasource.setName(originObj.getName()); // 数据源名称
        datasource.setType(String.valueOf(typeCode)); // 数据源类型
        datasource.setDriver(Constant.ORACLE_DRIVER_TPL); // 数据源驱动
        datasource.setUrl(String.format(Constant.ORACLE_URL_TPL, originObj.getIp(), originObj.getPort(),
            originObj.getSid())); // 数据源url
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
    public Oracle converOrigin(DataSource datasource) {
    	Oracle oracle = new Oracle();
    	oracle.setId(datasource.getId());
    	oracle.setName(datasource.getName());
    	oracle.setType((datasource.getType()==null||datasource.getType().equals(""))?0:Integer.parseInt(datasource.getType()));
    	oracle.setTypeName(DataSourceEnum.ORACLE.getName());
    	oracle.setDriver(datasource.getDriver());
    	
    	String url = datasource.getUrl();// 数据源url
        if (url != null && !url.equals("")) {
        	 String[] arr = url.replace("jdbc:oracle:thin:@", "").split(":");
        	 oracle.setIp(arr[0]);
        	 oracle.setPort(arr[1]);
        	 oracle.setSid(arr[2]);
        }
        
        oracle.setModel((datasource.getDataMode()==null||datasource.getDataMode().equals(""))?0:Integer.parseInt(datasource.getDataMode()));
        oracle.setStatus((datasource.getState()==null||datasource.getState().equals(""))?0:Integer.parseInt(datasource.getState()));
    	oracle.setXa(datasource.getXa());
		oracle.setUsername(datasource.getUsername());
		oracle.setPwd(datasource.getPwd());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		oracle.setCreateTime(sdf.format(datasource.getCreateTime()));
		oracle.setCreateUser(datasource.getCreateUser());
		return oracle;
    }

    @Override
    public List<TableInfo> showTablesInfo(String datasourceId) {
    	DataSource datasource = datasourceOrigin.selectDataSourceById(datasourceId);
        Oracle oracle = converOrigin(datasource);
        List<TableInfo> ret = oracleHandlerMapper.showTablesInfo(oracle);
        List<Map<String, Object>> aliasList = aliasService.getTableAlias(datasource.getId());
        ret.stream().forEach(m -> {
            Object alias = ListUtil.pick(aliasList, "tablename", m.getTableName(), "alias");
            m.setAlias(alias == null ? "" : alias.toString());
        });
        return ret;
    }

    @Override
    public List<ColInfo> showColsInfo(String datasourceId, String table) {
		DataSource datasource = datasourceOrigin.selectDataSourceById(datasourceId);
		List<ColInfo> ret = DatasourceUtil.findColsByJDBC(datasource, table, null);//converOrigin(datasource).getSid()
		List<Map<String, Object>> aliasList = aliasService.getColAlias(datasource.getId(), table);
		ret.stream().forEach(m -> {
			Object alias = ListUtil.pick(aliasList, "colname", m.getCol(), "alias");
			m.setAlias(alias == null ? "" : alias.toString());
		});
		return ret;
    }

    @Override
    public int tableCount(String table) {
    	return oracleHandlerMapper.tableCount(table);
    }

    @Override
    public List<Map<String, Object>> loadData(String table, String limit) {
    	return oracleHandlerMapper.loadData(table,limit);
    }

    @Override
    public ResourceData convertToResData(String resourceId, String datasourceId, TableInfo tbi) {
        return null;
    }

    @Override
    public ResourceCol convertToResCol(String resourceId, ColInfo col) {
        return null;
    }
    
}
