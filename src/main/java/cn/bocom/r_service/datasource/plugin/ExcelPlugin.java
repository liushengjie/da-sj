package cn.bocom.r_service.datasource.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.other.datasource.excel.EasyExcelUtil;
import cn.bocom.other.util.DBUtil;
import cn.bocom.other.util.ListUtil;
import cn.bocom.other.util.RandomUtil;
import cn.bocom.r_entity.datasource.ColInfo;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.Origins.DataSourceEnum;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.datasource.form.Excel;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_entity.resource.ResourceData;
import cn.bocom.r_service.datasource.DataSourcePlugin;
import cn.bocom.r_service.system.alias.AliasService;

/**
 * excel 插件类
 * @author liushengjie
 * @version $Id: ExcelPlugin.java, v 0.1 2019年1月11日 下午2:20:25 liushengjie Exp $
 */
@Component
public class ExcelPlugin implements DataSourcePlugin<Excel>{
    private static Logger logger = LoggerFactory.getLogger(ExcelPlugin.class);
    
    private DataSource datasource ;

    public ExcelPlugin() {
        super();
    }

    public ExcelPlugin(DataSource datasource) {
        super();
        this.datasource = datasource;
    }

    private static AliasService aliasService;
    
    @Autowired  
    public void setAliasService(AliasService aliasService) {  
    	ExcelPlugin.aliasService = aliasService;  
    } 
    
    
    @Override
    public DataSource convertDataSource(Excel originObj, int typeCode) {
    	DataSource datasource = new DataSource();
        datasource.setId(originObj.getId()!=null?originObj.getId():RandomUtil.getRandomId(6));
        datasource.setName(originObj.getName()); // 数据源名称
        datasource.setType(String.valueOf(typeCode)); // 数据源类型
        datasource.setUrl(originObj.getDatabase()); // 数据源url
        datasource.setXa(originObj.getXa()); // 表头行数，0时代表自动生成表头即文件里没有表头，ABCD自动排列作为表头 
        datasource.setCreateUser(originObj.getCreateUser()); // 数据源人
        datasource.setCreateTime(new Date()); // 数据源创建时间
        datasource.setState(originObj.getId()!=null?String.valueOf(originObj.getStatus()):"1");
        datasource.setDataMode(String.valueOf(originObj.getModel())); // 数据源使用方式

        return datasource;
    }

    @Override
    public Excel converOrigin(DataSource datasource) {
    	Excel excel = new Excel();
    	excel.setId(datasource.getId());
    	excel.setName(datasource.getName());
    	excel.setType((datasource.getType()==null||datasource.getType().equals(""))?0:Integer.parseInt(datasource.getType()));
    	excel.setTypeName(DataSourceEnum.EXCEL.getName());
    	excel.setDatabase(datasource.getUrl());
        excel.setModel((datasource.getDataMode()==null||datasource.getDataMode().equals(""))?0:Integer.parseInt(datasource.getDataMode()));
        excel.setStatus((datasource.getState()==null||datasource.getState().equals(""))?0:Integer.parseInt(datasource.getState()));
    	excel.setXa(datasource.getXa());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		excel.setCreateTime(sdf.format(datasource.getCreateTime()));
		excel.setCreateUser(datasource.getCreateUser());
		return excel;
    }

    @Override
    public List<TableInfo> showTablesInfo(String datasourceId) {
        Excel excel = converOrigin(datasource);
        List<Map<String, Object>> sheetList = null;
        try {
        	sheetList = EasyExcelUtil.getExcelSheets(new File(excel.getDatabase()));
		} catch (FileNotFoundException e) {
			sheetList = null;
			logger.error("文件" + excel.getDatabase() + "不存在！");
			e.printStackTrace();
		}
        if(sheetList==null||sheetList.size()==0) {
        	return null;
        }
        List<TableInfo> ret = new ArrayList<TableInfo>();
        for(int i=0;i<sheetList.size();i++) {
        	Map<String, Object> m = sheetList.get(i);
        	TableInfo t = new TableInfo();
        	t.setId(m.get("index").toString());
        	t.setTableName(m.get("sheetName").toString());
        	t.setObjType("TABLE");
        	ret.add(t);
        }
        List<Map<String, Object>> aliasList = aliasService.getTableAlias(datasource.getId());
        ret.stream().forEach(m -> {
            Object alias = ListUtil.pick(aliasList, "tablename", m.getTableName(), "alias");
            m.setAlias(alias == null ? "" : alias.toString());
        });
        return ret;
    }

    @Override
    public List<ColInfo> showColsInfo(String datasourceId, String table) {
        Excel excel = converOrigin(datasource);
        List<List<String>> list = null;
        try {
        	list = EasyExcelUtil.readExcelWithStringList(new File(excel.getDatabase()), getSheetIndexBySheetName(table));
		} catch (FileNotFoundException e) {
			list = null;
			logger.error("文件" + excel.getDatabase() + "不存在！");
			e.printStackTrace();
		}
        if(list==null||list.size()==0) {
        	return null;
        }
        List<ColInfo> ret = new ArrayList<ColInfo>();
        int headRowNum = Integer.parseInt(datasource.getXa().toString());
        if(headRowNum<1) {//文件里没有表头，自动生成
        	List<String> firstList = list.get(0)==null?(new ArrayList<String>()):list.get(0);
        	for(int i=0;i<firstList.size();i++) {
            	ColInfo c = new ColInfo();
            	c.setCol("COL" + (i+1));
            	c.setType("VARCHAR");
            	c.setIdx("0");
            	c.setNullable("0");
            	c.setPk("0");
            	ret.add(c);
            }
        } else {
        	List<String> headList = list.get(0);
        	for(int i=0;i<headList.size();i++) {
            	ColInfo c = new ColInfo();
            	c.setCol(headList.get(i));
            	c.setType("VARCHAR");
            	c.setIdx("0");
            	c.setNullable("0");
            	c.setPk("0");
            	ret.add(c);
            }
        }
        
        List<Map<String, Object>> aliasList = aliasService.getTableAlias(datasource.getId());
        ret.stream().forEach(m -> {
            Object alias = ListUtil.pick(aliasList, "colname", m.getCol(), "alias");
            m.setAlias(alias == null ? "" : alias.toString());
        });
        return ret;
    }

    @Override
    public int tableCount(String table) {
        Excel excel = converOrigin(datasource);
        List<List<String>> list = null;
        try {
        	list = EasyExcelUtil.getExcelSheetData(new File(excel.getDatabase()), getSheetIndexBySheetName(table), Integer.parseInt(datasource.getXa()), -1);
		} catch (FileNotFoundException e) {
			list = null;
			logger.error("文件" + excel.getDatabase() + "不存在！");
			e.printStackTrace();
		}
        if(list==null||list.size()==0) {
        	return 0;
        } else {
        	return list.size();
        }
    }

    @Override
    public List<Map<String, Object>> loadData(String table, String limit) {
        Excel excel = converOrigin(datasource);
        List<List<String>> list = null;
        List<ColInfo> headList = null;
        int sheetIndex = getSheetIndexBySheetName(table);
        try {
        	headList = showColsInfo(null,table);
        	list = EasyExcelUtil.getExcelSheetData(new File(excel.getDatabase()), sheetIndex, Integer.parseInt(datasource.getXa().toString()), -1);
		} catch (FileNotFoundException e) {
			list = null;
			logger.error("文件" + excel.getDatabase() + "不存在！");
			e.printStackTrace();
		}
        if(list==null||list.size()==0) {
        	return null;
        }
        
        List<Map<String, Object>> ret = new ArrayList<Map<String, Object>>();
        int len = list.size()>Integer.parseInt(limit)?Integer.parseInt(limit):list.size();
        for(int j=0;j<len;j++) {
        	List<String> row = list.get(j);
        	Map<String, Object> m = new HashMap<String, Object>();
        	for(int w=0;w<headList.size();w++) {
        		ColInfo c = headList.get(w);
        		m.put(c.getCol(), row.get(w));
        	}
        	ret.add(m);
        }
        return ret;
    }

    @Override
    public ResourceData convertToResData(String resourceId, String datasourceId, TableInfo tbi) {
    	ResourceData rd = new ResourceData();
        rd.setConnModel("");
        rd.setResId(resourceId);
        rd.setTableName(tbi.getTableName());
        rd.setDsId(datasourceId);
        return rd;
    }

    @Override
    public ResourceCol convertToResCol(String resourceId, ColInfo col) {
    	ResourceCol res_col = new ResourceCol();
        res_col.setAlias(col.getAlias());
        res_col.setCol(col.getCol());
        res_col.setChangeType(DBUtil.changeDBType(col.getType()));
        res_col.setColCache(col.getCol() + "_" + resourceId);
        res_col.setIdx(col.getIdx());
        res_col.setPk(col.getPk());
        res_col.setStatus("1");
        res_col.setResId(resourceId);
        res_col.setType(col.getType());
        return res_col;
    }
    
    //根据sheet名称获取sheet索引
    private int getSheetIndexBySheetName(String sheetName) {
    	List<TableInfo> list = showTablesInfo(null);
    	if(list==null||list.size()==0) {
    		return -1;
    	}
    	int sheetIndex = -1;
    	for(int i=0;i<list.size();i++) {
    		TableInfo t = list.get(i);
    		if(t.getTableName().equalsIgnoreCase(sheetName)) {
    			sheetIndex = i;
    			break;
    		}
    	}
    	return sheetIndex;
    }
}
