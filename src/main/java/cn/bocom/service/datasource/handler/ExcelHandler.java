/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service.datasource.handler
 * @author: liushengjie
 * @date: 2018年9月18日 上午11:35:27
 */
package cn.bocom.service.datasource.handler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.ApplicationHome;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import cn.bocom.entity.DataSource;
import cn.bocom.entity.DataSource.DSTypeIdEnum;
import cn.bocom.entity.DsParam;
import cn.bocom.other.datasource.excel.ExcelSheetPO;
import cn.bocom.other.datasource.excel.POIExcelUtil;
import cn.bocom.other.util.RandomUtil;

/**
 * @ClassName: ExcelHandler
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月18日 上午11:35:27
 */
@Component
public class ExcelHandler extends AbstractDsHandler {

    /*
     * (non Javadoc)
     * 
     * @Title: getDSId
     * 
     * @Description: TODO
     * 
     * @return
     * 
     * @see cn.bocom.service.datasource.handler.AbstractDsHandler#getDSId()
     */
    @Override
    public String getDSId() {
        // TODO Auto-generated method stub
        return DSTypeIdEnum.EXCEL.DSTypeId();
    }

    /*
     * (non Javadoc)
     * 
     * @Title: getDataSource
     * 
     * @Description: TODO
     * 
     * @param dsp
     * 
     * @return
     * 
     * @see
     * cn.bocom.service.datasource.handler.AbstractDsHandler#getDataSource(cn.bocom.entity.DsParam)
     */
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
        ds.setDriver(dsp.getDriver());// headermode
        ds.setUrl(dsp.getDatabase()); // 数据源url
        ds.setUsername(dsp.getUsername()); // 数据源用户名
        ds.setPwd(dsp.getPwd()); // 数据源密码
        ds.setXa(dsp.getXa()); // 模式：自动生成表头，按照第一行作为表头
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

    /*
     * (non Javadoc)
     * 
     * @Title: getDsParam
     * 
     * @Description: TODO
     * 
     * @param ds
     * 
     * @return
     * 
     * @see
     * cn.bocom.service.datasource.handler.AbstractDsHandler#getDsParam(cn.bocom.entity.DataSource)
     */
    @Override
    public DsParam getDsParam(DataSource ds) {
        DsParam dsp = new DsParam();

        dsp.setId(ds.getId()); // 数据源id
        dsp.setName(ds.getName()); // 数据源名称
        dsp.setType(ds.getType()); // 数据源类型
        dsp.setTypeName(ds.gettypeName());
        dsp.setDatabase(ds.getUrl());
        dsp.setUsername(ds.getUsername()); // 数据源用户名
        dsp.setPwd(ds.getPwd()); // 数据源密码
        dsp.setXa(ds.getXa()); // 数据源是否启用分布式事务
        dsp.setCreateUser(ds.getCreateUser()); // 数据源人
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dsp.setCreateTime(sdf.format(ds.getCreateTime())); // 数据源创建时间
        dsp.setState(ds.getState()); // 数据源状态
        dsp.setDataMode(ds.getDataMode()); // 数据源使用方式
        dsp.setDriver(ds.getDriver());

        return dsp;

    }

    /*
     * (non Javadoc)
     * 
     * @Title: findTables
     * 
     * @Description: TODO
     * 
     * @param ds
     * 
     * @return
     * 
     * @see
     * cn.bocom.service.datasource.handler.AbstractDsHandler#findTables(cn.bocom.entity.DsParam)
     */
    @Override
    public List findTables(DsParam ds) {
        // TODO Auto-generated method stub
        // String url = ds.getDatabase();
        File file = new File(getExcelPath(super.getDs().getUrl()));
        // File file = new File(url);
        ArrayList<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        try {
            List<ExcelSheetPO> list = POIExcelUtil.readExcel(file, null, null, super.getDs().getDriver());
            for (ExcelSheetPO espo : list) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("TABLE_NAME", espo.getSheetName());
                map.put("TABLE_ROWS", espo.getRowCount());
                map.put("TABLE_COMMENT", "");
                map.put("UPDATE_TIME", "");
                map.put("ID", "");
                map.put("OBJ_TYPE", "sheet");
                retList.add(map);

            }
        } catch (Exception e) {

        }


        return retList;
    }

    /*
     * (non Javadoc)
     * 
     * @Title: findColsByTable
     * 
     * @Description: TODO
     * 
     * @param database
     * 
     * @param tableName
     * 
     * @return
     * 
     * @see cn.bocom.service.datasource.handler.AbstractDsHandler#findColsByTable(java.lang.String,
     * java.lang.String)
     */
    @Override
    public List findColsByTable(DsParam ds, String database, String tableName) {
        // TODO Auto-generated method stub
        File file = new File(getExcelPath(super.getDs().getUrl()));

        ArrayList<Map<String, Object>> retList = new ArrayList<Map<String, Object>>();
        try {
            List<ExcelSheetPO> list =
                    POIExcelUtil.readExcel(file, null, null, super.getDs().getDriver());

            for (ExcelSheetPO espo : list) {

                if (espo.getSheetName().equals(tableName)) {

                    for (int i = 0; i < espo.getHeaders().length; i++) {

                        HashMap<String, Object> map = new HashMap<String, Object>();


                        map.put("col", espo.getHeaders()[i]);
                        map.put("type", espo.getHeaderTypes()[i]);
//                        map.put("changetype", "");
//                        map.put("length", "");
//                        map.put("nullable", "");
//                        map.put("comment", "");
//                        map.put("pk", "");
//                        map.put("index", "");
                        retList.add(map);

                    }
                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retList;
    }

    /*
     * (non Javadoc)
     * 
     * @Title: preloadData
     * 
     * @Description: TODO
     * 
     * @param tableName
     * 
     * @param limit
     * 
     * @return
     * 
     * @see cn.bocom.service.datasource.handler.AbstractDsHandler#preloadData(java.lang.String,
     * java.lang.String)
     */
    @Override
    public List preloadData(String tableName, String limit) {
        // TODO Auto-generated method stub

        File file = new File(getExcelPath(super.getDs().getUrl()));
        List<List<Object>> retList = null;
        ArrayList<Map<String, Object>> retMapList = new ArrayList<Map<String, Object>>();
        try {
        	
        	Integer limitNum = null;
        	if(limit!=null){
        		limitNum = Integer.parseInt(limit);
        	}

            List<ExcelSheetPO> list = POIExcelUtil.readExcel(file, limitNum, null, super.getDs().getDriver());
            
            for (ExcelSheetPO espo : list) {
                if (espo.getSheetName().equals(tableName)) {

                    String[] headers = espo.getHeaders();


                    retList = espo.getDataList();
                    for (int i = 0; i < retList.size(); i++) {
                        List<Object> datalist = retList.get(i);
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        for (int j = 0; j < datalist.size(); j++) {

                            map.put(headers[j], datalist.get(j));
                        }
                        retMapList.add(map);

                    }

                }



            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retMapList;

    }

    /*
     * (non Javadoc)
     * 
     * @Title: findCountByTable
     * 
     * @Description: TODO
     * 
     * @param tableName
     * 
     * @return
     * 
     * @see cn.bocom.service.datasource.handler.AbstractDsHandler#findCountByTable(java.lang.String)
     */
    @Override
    public int findCountByTable(String tableName) {
    	File file = new File(getExcelPath(super.getDs().getUrl()));
        int count = 0;
        try {
            List<ExcelSheetPO> list = POIExcelUtil.readExcel(file, null, null, super.getDs().getDriver());
            for (ExcelSheetPO espo : list) {
            	if (espo.getSheetName().equals(tableName)) {
            		count = espo.getRowCount();
            	}
                
            }
        } catch (Exception e) {

        }

        return count;
    }

    /*
     * (non Javadoc)
     * 
     * @Title: hashCode
     * 
     * @Description: TODO
     * 
     * @return
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return super.hashCode();
    }

    /*
     * (non Javadoc)
     * 
     * @Title: equals
     * 
     * @Description: TODO
     * 
     * @param obj
     * 
     * @return
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    /*
     * (non Javadoc)
     * 
     * @Title: clone
     * 
     * @Description: TODO
     * 
     * @return
     * 
     * @throws CloneNotSupportedException
     * 
     * @see java.lang.Object#clone()
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        // TODO Auto-generated method stub
        return super.clone();
    }

    /*
     * (non Javadoc)
     * 
     * @Title: toString
     * 
     * @Description: TODO
     * 
     * @return
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }

    /*
     * (non Javadoc)
     * 
     * @Title: finalize
     * 
     * @Description: TODO
     * 
     * @throws Throwable
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable {
        // TODO Auto-generated method stub
        super.finalize();
    }

    /*
     * (non Javadoc)
     * 
     * @Title: preloadSQLData
     * 
     * @Description: TODO
     * 
     * @param sql
     * 
     * @param limit
     * 
     * @return
     * 
     * @see cn.bocom.service.datasource.handler.AbstractDsHandler#preloadSQLData(java.lang.String,
     * java.lang.String)
     */
    @Override
    public List preloadSQLData(String sql, String limit) {
        // TODO Auto-generated method stub
        return null;
    }

    public String getExcelPath(String path) {
        String ret = "";
        String pathPrefix = "";
        try {
            ApplicationHome home = new ApplicationHome(getClass());
            File jarFile = home.getSource();
            pathPrefix = jarFile.getParentFile().toString();
        } catch (Exception e) {

        }
        File filetemp = new File(pathPrefix);
        String excelPath = filetemp.getAbsolutePath();
        ret = excelPath + path;
        return ret;
    }



}
