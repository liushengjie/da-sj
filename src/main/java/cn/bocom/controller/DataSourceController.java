/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.controller
 * @author: liushengjie
 * @date: 2018年8月15日 下午4:36:55
 */
package cn.bocom.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationHome;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.bocom.entity.Alias;
import cn.bocom.entity.DataSource;
import cn.bocom.entity.DsParam;
import cn.bocom.other.common.DataResponse;
import cn.bocom.other.util.DateUtil;
import cn.bocom.other.util.FileUtil;
import cn.bocom.other.util.RandomUtil;
import cn.bocom.other.util.excel.ExcelSheetPO;
import cn.bocom.other.util.excel.POIExcelUtil;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;
import cn.bocom.service.AliasService;
import cn.bocom.service.datasource.DataSourceService;
import cn.bocom.service.datasource.factory.DsHandlerFactory;
import cn.bocom.service.datasource.handler.AbstractDsHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: DataSourceController
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月15日 下午4:36:55
 */
@RestController
@RequestMapping(value = "/dataSource")
@Api(tags = "连接管理")
public class DataSourceController {

    private static Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @Autowired
    private DataSourceService dataSourceService;
    @Autowired
    private AliasService aliasService;
    @Autowired
    private DataSourceOrigin datasourceOrigin;

    // 分页查询数据源
    @ApiOperation(value = "分页查询数据源")
    @RequestMapping(value = "/selectDsByPage", method = RequestMethod.GET)
    public DataResponse selectDsByPage(cn.bocom.r_entity.datasource.DataSource dataSource,
            @RequestParam("currentPage") int currentPage, @RequestParam("pageSize") int pageSize) {
        return new DataResponse(
        		datasourceOrigin.selectDataSourceByPage(dataSource, currentPage, pageSize));
    }

    // 新增数据源
    @ApiOperation(value = "新增数据源")
    @RequestMapping(value = "/insertDs", method = RequestMethod.POST)
    public DataResponse insertDs(@RequestParam int type, @RequestBody String obj) {
        return new DataResponse(datasourceOrigin.insertDataSource(type, obj));
    }

    // 修改数据源
    @ApiOperation(value = "修改数据源")
    @RequestMapping(value = "/updateDs", method = RequestMethod.POST)
    public DataResponse updateDs(@RequestBody DsParam ds) {
        return new DataResponse(dataSourceService.updateDs(ds));
    }

    // 删除数据源
    @ApiOperation(value = "删除数据源")
    @RequestMapping(value = "/deleteDs", method = RequestMethod.GET)
    public DataResponse deleteDs(String id) {
        return new DataResponse(dataSourceService.deleteDs(id));
    }

    // 数据源连接是否成功
    @ApiOperation(value = "数据源连接是否成功")
    @RequestMapping(value = "/connectDs", method = RequestMethod.POST)
    public DataResponse connectDs(@RequestBody DsParam ds) {
        return new DataResponse(dataSourceService.connect(ds), 0, "查询数据源连接");
    }

    // 获取数据源的所有表
    @ApiOperation(value = "获取数据源的所有表")
    @RequestMapping(value = "/findTables", method = RequestMethod.GET)
    public DataResponse findTables(String dataSource) {
        return new DataResponse(dataSourceService.findTables(dataSource));
    }

    /************************* 数据源备注start ***********************************/
    @ApiOperation(value = "给数据源下的表添加备注")
    @RequestMapping(value = "/addAlias", method = RequestMethod.POST)
    public DataResponse addAlias(@RequestBody Alias alias) {
        alias.setId(RandomUtil.get16TimeRandom());
        return new DataResponse(aliasService.addAlias(alias));
    }

    @ApiOperation(value = "给数据源下的表修改备注")
    @RequestMapping(value = "/updateAlias", method = RequestMethod.POST)
    public DataResponse updateAlias(@RequestBody Alias alias) {

        return new DataResponse(aliasService.updateAlias(alias));
    }

    /************************* 数据源备注end ***********************************/


    @ApiOperation(value = "获取表的所有列及别名")
    @RequestMapping(value = "/findColsByTable", method = RequestMethod.GET)
    public DataResponse findColsByTable(String dataSource, String tableName) {
        List list = dataSourceService.findColsByTable(dataSource, tableName);
        if (list == null) {
            return new DataResponse(false, -1, "获取字段信息错误！");
        } else {
            return new DataResponse(list);
        }
    }

    @ApiOperation(value = "获取SQL的所有列信息")
    @RequestMapping(value = "/findColsBySQL", method = RequestMethod.POST)
    public DataResponse findColsBySQL(@RequestBody Map map) {
        String dataSource = map.get("dataSource").toString();
        String SQL = map.get("SQL").toString();
        List list = dataSourceService.findColsBySQL(dataSource, SQL);
        if (list == null) {
            return new DataResponse(false, -1, "获取字段信息错误！");
        } else {
            return new DataResponse(list);
        }
    }

    @ApiOperation(value = "预加载指定表中指定数量的数据")
    @RequestMapping(value = "/preloadData", method = RequestMethod.GET)
    public DataResponse preloadData(String dataSource, String tableName, String limit) {
        return new DataResponse(dataSourceService.preloadData(dataSource, tableName, limit));
    }

    @ApiOperation(value = "预加载指定SQL指定数量的数据,Map中含有三个参数：dataSource、sql、limit")
    @RequestMapping(value = "/preloadSQLData", method = RequestMethod.POST)
    public DataResponse preloadSQLData(@RequestBody Map map) {
        String dataSource = map.get("dataSource").toString();
        String sql = map.get("sql").toString();
        String limit = map.get("limit").toString();
        return new DataResponse(dataSourceService.preloadSQLData(dataSource, sql, limit));
    }

    @ApiOperation(value = "获取表数据量")
    @RequestMapping(value = "/findCountByTable", method = RequestMethod.GET)
    public DataResponse findCountByTable(String dataSource, String tableName) {
        return new DataResponse(dataSourceService.findCountByTable(dataSource, tableName));
    }

    /**
     * 
     * @param file
     * @param headerMode
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "根据数据源信息预览Excel")
    @RequestMapping(value = "/previewExcel", method = RequestMethod.POST)
    public DataResponse previewExcel(@RequestBody DsParam ds) {

        List<ExcelSheetPO> list = null;
        try {
            String pathPrefix = "";
            ApplicationHome home = new ApplicationHome(getClass());
            File jarFile = home.getSource();
            pathPrefix = jarFile.getParentFile().toString();
            File filetemp = new File(pathPrefix);
            String excelPath = filetemp.getAbsolutePath();
            AbstractDsHandler dsHandler = DsHandlerFactory.getDataSourceBean(ds);
            String url = dsHandler.getDataSource(ds).getUrl();
            url = excelPath + url;
            String headerMode = dsHandler.getDataSource(ds).getDriver();
            File file = new File(url);
            list = POIExcelUtil.readExcel(file, null, null, headerMode);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DataResponse(list);
    }
    
    @ApiOperation(value = "根据文件路径预览Excel")
    @RequestMapping(value = "/preloadExcel", method = RequestMethod.POST)
    public DataResponse preloadExcel(String database, String driver) {

        List<ExcelSheetPO> list = null;
        try {
            String pathPrefix = "";
            ApplicationHome home = new ApplicationHome(getClass());
            File jarFile = home.getSource();
            pathPrefix = jarFile.getParentFile().toString();
            File filetemp = new File(pathPrefix);
            String excelPath = filetemp.getAbsolutePath();
            String url = excelPath + database;
            File file = new File(url);
            list = POIExcelUtil.readExcel(file, null, null, driver);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new DataResponse(list);
    }

    /**
     * 
     * @param file
     * @param headerMode
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "上传Excel")
    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
    public DataResponse uploadExcel(MultipartFile file) {

        String retStr = "";
        HashMap<String, String> retMap = new HashMap<String, String>();

        if (file.isEmpty()) {

            retStr = "上传失败，请选择文件";
            retMap.put("flag", "false");
            retMap.put("info", retStr);
        }
        try {
            ApplicationHome home = new ApplicationHome(getClass());
            File jarFile = home.getSource();
            String pathPrefix = ResourceUtils.getURL("classpath:").getPath();
            File filetemp = new File(pathPrefix);
            String excelPath =
                    jarFile.getParentFile() + File.separator + "excelFiles" + File.separator
                            + DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD)
                            + File.separator;

            excelPath +=
                    RandomUtil.getUUID32() + "." + FileUtil.getTypePart(file.getOriginalFilename());
            FileUtil.makeDirectory(excelPath);
            File dest = new File(excelPath);
            retStr = excelPath;
            file.transferTo(dest);
            retMap.put("flag", "true");
            retMap.put("info", excelPath.replace(jarFile.getParentFile().toString(), ""));

        } catch (IOException e) {
            retStr = "上传失败！";
            retMap.put("flag", "false");
            retMap.put("info", retStr);
            e.printStackTrace();
        }


        return new DataResponse(retMap);
    }
    
//    @ApiOperation(value = "上传Excel")
//    @RequestMapping(value = "/uploadExcel", method = RequestMethod.POST)
//    public DataResponse uploadExcel(MultipartFile file, String headPos) {
//
//        List<ExcelSheetPO> result = null;
//
//        if (file.isEmpty()) {
//        	return new DataResponse(result);
//        }
//        try {
//            ApplicationHome home = new ApplicationHome(getClass());
//            File jarFile = home.getSource();
////            String pathPrefix = ResourceUtils.getURL("classpath:").getPath();
////            File filetemp = new File(pathPrefix);
//            String excelPath =
//                    jarFile.getParentFile() + File.separator + "excelFiles" + File.separator
//                            + DateUtil.parseDateToStr(new Date(), DateUtil.DATE_FORMAT_YYYYMMDD)
//                            + File.separator;
//
//            excelPath +=
//                    RandomUtil.getUUID32() + "." + FileUtil.getTypePart(file.getOriginalFilename());
//            FileUtil.makeDirectory(excelPath);
//            File dest = new File(excelPath);
//            file.transferTo(dest);
//            result = POIExcelUtil.readExcel(new File(excelPath), null, null, headPos);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        return new DataResponse(result);
//    }


}
