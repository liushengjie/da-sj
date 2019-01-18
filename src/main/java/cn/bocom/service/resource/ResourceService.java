/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service.resource 
 * @author: liushengjie   
 * @date: 2018年9月13日 下午2:16:51 
 */
package cn.bocom.service.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.ignite.Ignite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.bocom.entity.Alias;
import cn.bocom.entity.Category;
import cn.bocom.entity.CategoryNode;
import cn.bocom.entity.DataSource;
import cn.bocom.entity.Res;
import cn.bocom.entity.ResBean;
import cn.bocom.entity.ResCol;
import cn.bocom.entity.ResColProc;
import cn.bocom.entity.ResData;
import cn.bocom.entity.ResFilter;
import cn.bocom.entity.ResFilterProc;
import cn.bocom.entity.ResView;
import cn.bocom.mapper.main.AliasMapper;
import cn.bocom.mapper.main.CategoryMapper;
import cn.bocom.mapper.main.ResColMapper;
import cn.bocom.mapper.main.ResColProcMapper;
import cn.bocom.mapper.main.ResDataMapper;
import cn.bocom.mapper.main.ResFilterMapper;
import cn.bocom.mapper.main.ResFilterProcMapper;
import cn.bocom.mapper.main.ResMapper;
import cn.bocom.other.util.DBUtil;
import cn.bocom.other.util.MapUtil;
import cn.bocom.other.util.RandomUtil;
import cn.bocom.service.datasource.DataSourceService;
import cn.bocom.service.resource.processor.ColumnProcessor;
import cn.bocom.service.resource.processor.DataProcessor;
import cn.bocom.service.resource.processor.FilterProcessor;
import cn.bocom.service.service.SrvService;
import cn.sj.common.CacheServer;

/** 
 * @ClassName: ResouceService 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月13日 下午2:16:51  
 */
public class ResourceService{
	
	@Autowired
	private DataProcessor dataProc;
	
	@Autowired 
	private ColumnProcessor colProc;
	
	@Autowired 
	private FilterProcessor filterProc;
	
	@Autowired
    private AliasMapper aliasMapper;
	
	@Autowired
    private ResMapper resMapper;
	
	@Autowired
    private ResDataMapper resDataMapper;
	
	@Autowired
    private ResColMapper resColMapper;
	
	@Autowired
    private ResColProcMapper resColProcMapper;
	
	@Autowired
    private ResFilterMapper resFilterMapper;
	
	@Autowired
    private ResFilterProcMapper resFilterProcMapper;
	
	@Autowired
	private DataSourceService dataSourceService;
	
	//@Autowired
    //private Ignite ignite;
	
	@Autowired
    private CategoryMapper categoryMapper;
	
	private static Logger logger = LoggerFactory.getLogger(ResourceService.class);
	
	public List<Map<String, Object>> previewData(ResBean resBean, String limit){
		List<Map<String, Object>> ret = null;
		ResData resData = resBean.getResData();	
		DataSource ds = dataSourceService.selectDsById(resData.getDsId());
        if("3".equals(ds.getType())){
        	ret = dataSourceService.preloadData(resData.getDsId(), resData.getTableName(), limit);
        	ret = buildQueryData(resBean, ret);
        }else{
        	String str = buildQuerySQL(resBean, false);
        	ret = dataSourceService.preloadSQLData(resData.getDsId(), str, limit);
        }
		
		return ret.stream().map(m -> {
			return MapUtil.transformLowerCase(m);
		}).collect(Collectors.toList());
	}
	
	/** 
	 * @Title: queryResListByPage 
	 * @Description: 获取资源库信息
	 * @param res
	 * @param currentPage
	 * @param pageSize
	 * @return PageInfo<Map<String, Object>>
	 * @author lishipeng
	 * @date 2018年9月18日下午3:50:05
    */ 
    public PageInfo<ResView> queryResListByPage(ResView res, int currentPage, int pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<ResView> list = resMapper.queryResListByPage(res);
        PageInfo<ResView> pageInfo = new PageInfo<ResView>(list);
        return pageInfo;
    }
    
    public List<Res> queryResAll(){
    	List<Res> data = resMapper.select(new Res());
    	return data;
	}
	
    
    /** 
	 * @Title: saveRes 
	 * @Description: 保存资源信息，有记录则更新反之插入
	 * @param resBean
	 * @return String
	 * @author lishipeng
	 * @date 2018年9月20日上午9:10:05
    */ 
    //@Transactional
	public String saveRes(ResBean resBean) {
    	if(resBean==null) {
    		return null;
    	}
    	Res res = resBean.getRes();//资源信息
    	ResData resData = resBean.getResData();//资源数据信息
    	List<ResCol> resColList = resBean.getResColList();//资源表结构信息
    	List<ResFilter> resFilterList = resBean.getResFilterList();//资源表筛选信息
    	
    	if(res==null||resData==null||resColList==null||resColList.size()==0) {
    		return null;
    	}
    	
    	//定义资源id
    	String resId = RandomUtil.getRandomId(18);
    	String cacheName = "res_" + resId;
    	
    	//设置id并保存Res
    	if(StringUtils.isBlank(res.getId())) {//新增则生成id
    		res.setId(resId);
    		res.setCacheTable(cacheName);
    		resMapper.saveRes(res);
    	}else{//更新则先删后插
    		//查询库中Res
    		Res resOld = new Res();
    		resOld.setId(res.getId());
    		resOld = resMapper.selectOne(resOld);
    		//将库中的Res部分属性赋值到新Res里
    		res.setCreateTime(resOld.getCreateTime());
    		res.setSchemaFlag(resOld.getSchemaFlag());
    		res.setStatus(resOld.getStatus());
    		res.setNum(resOld.getNum());
    		//删除资源及关联表
    		resMapper.delRes(res.getId());
    		//保存资源
    		resMapper.saveRes2(res);
    	}
    	
    	
    	//设置id并保存ResData
    	if(StringUtils.isBlank(resData.getId())) {
    		resData.setResId(res.getId());
    		if(resData.getId()==null || "".equals(resData.getId())) {
    			resData.setId(RandomUtil.getRandomId(18));
    		}	    		
    	}
    	resDataMapper.saveResData(resData);
    	
    	//设置id并保存ResCol
    	int sort = 1;
    	List<ResCol> resColListNew = new ArrayList<ResCol>();
    	for(ResCol resCol:resColList){
    		resCol.setResId(res.getId());
    		if(StringUtils.isBlank(resCol.getId())) {
    			resCol.setId(RandomUtil.getRandomId(18));
        	}
    		if(StringUtils.isBlank(resCol.getPk())) {
    			resCol.setPk("0");
    		}
    		if(StringUtils.isBlank(resCol.getIdx())) {
    			resCol.setIdx("0");
    		}
    		if(StringUtils.isBlank(resCol.getStatus())) {
    			resCol.setStatus("1");
    		}
    		if(StringUtils.isBlank(resCol.getChangeType())) {
    			resCol.setChangeType(DBUtil.changeDBType(resCol.getType()));
    		}
    		resCol.setSort(String.valueOf(sort++));
    		resCol.setColCache(resCol.getCol()+"_"+resId);
    		resColMapper.saveResCol(resCol);
    		
    		//设置id并保存ResColProc
    		
    		List<ResColProc> resColProcList = resCol.getProc();
    		if(resColProcList!=null&&!resColProcList.isEmpty()) {
    			int _sort = 1;
	    		for(ResColProc resColProc:resColProcList){
	    			if(StringUtils.isBlank(resColProc.getId())) {
	    				resColProc.setId(RandomUtil.getRandomId(18)); 
	    			}	    			     		        		
	        		resColProc.setColId(resCol.getId());  
	        		resColProc.setSort(String.valueOf(_sort++));
	        		resColProcMapper.saveResColProc(resColProc);
	    		}
    		}
    		
    		resColListNew.add(resCol);
		}
    	resBean.setResColList(resColListNew);
    	

    	//设置id并保存ResFilter
    	sort = 1;
        for(ResFilter resFilter:resFilterList){
    		resFilter.setResId(res.getId());
    		if(StringUtils.isBlank(resFilter.getId())) {
    			resFilter.setId(RandomUtil.getRandomId(18));
        	}
    		resFilter.setSort(String.valueOf(sort++));
    		resFilterMapper.saveResFilter(resFilter);
    		
    		//设置id并保存ResFilterProc
    		List<ResFilterProc> resFilterProcList = resFilter.getProc();
    		if(resFilterProcList!=null&&!resFilterProcList.isEmpty()) {
    			int _sort = 1;
	    		for(ResFilterProc resFilterProc:resFilterProcList){
	    			if(StringUtils.isBlank(resFilterProc.getId())) {
	    				resFilterProc.setId(RandomUtil.getRandomId(18));
	            	}    		        		
	    			resFilterProc.setFilterId(resFilter.getId());  
	    			resFilterProc.setSort(String.valueOf(_sort++));
	    			resFilterProcMapper.saveResFilterProc(resFilterProc);
	    		}
    		}   		
		}
        
        DataSource ds = dataSourceService.selectDsById(resData.getDsId());
        if("3".equals(ds.getType())){
        	
        	List data = dataSourceService.preloadData(resData.getDsId(), resData.getTableName(), null);
        	data = buildQueryData(resBean, data);
        	System.out.println(data.toString());
        	
        }else{
        	
        	//建表
        	String createSQL = buildCreateTableSQL(resBean);
        	logger.info("createSQL:"+createSQL);
        	
        	//抽取数据
        	String querySQL = buildQuerySQL(resBean, true);
        	logger.info("querySQL:"+querySQL);
        	
//        	new Thread() {
//    			public void run() {
//    				CacheServer cacheService = ignite.services().serviceProxy(CacheServer.SERVICE_NAME, CacheServer.class, false);
//    		        try {
//    		        	if("0".equals(res.getStatus())){
//    		        		String[] sql = createSQL.split(";");
//    		        		for(int i=0;i<sql.length;i++){
//    		        			if(StringUtils.isNotBlank(sql[i])){
//    		        				cacheService.cacheExecDDL(resData.getResId(), sql[i]);
//    		        			}        			
//    		        		}		        		
//    		        	}				
//    					cacheService.cacheDatasFull(resData.getResId(), resData.getDsId(), cacheName, querySQL);
//    				} catch (Exception e) {
//    					e.printStackTrace();
//    				}  
//    			}
//    		}.start();
        }

    	

    	return "";
    }
    
    /** 
	 * @Title: buildQuerySQL 
	 * @Description: 构建查询语句
	 * @param resBean
	 * @return String
	 * @author lishipeng
	 * @date 2018年9月20日下午14:10:05
    */ 
    public String buildQuerySQL(ResBean resBean, boolean saveFlag) {
    	ResData resData = resBean.getResData();//资源数据信息
    	List<ResCol> resColList = resBean.getResColList();//资源表结构信息
    	List<ResFilter> resFilterList = resBean.getResFilterList();//资源表结构信息
    	
		String colStr,tbStr,whereStr = "";
		
		tbStr = dataProc.generateTbStr(resData);
		colStr = colProc.generateColStr(resData, resColList, saveFlag);
		whereStr = filterProc.generateFilterStr(resData, resFilterList);
		
		StringBuffer sb = new StringBuffer().append("select ").append(colStr).append(" from ").append(tbStr).append(" where 1=1 ").append(whereStr);
		System.out.println("生成sql:"+sb.toString());
		return sb.toString();
    }
    
    /** 
	 * @Title: buildCreateTableSQL 
	 * @Description: 构建建表语句
	 * @param resBean
	 * @return String
	 * @author lishipeng
	 * @date 2018年9月20日上午9:10:05
    */ 
    public String buildCreateTableSQL(ResBean resBean) {
    	Res res = resBean.getRes();//资源库信息
    	List<ResCol> resColList = resBean.getResColList();//资源表结构信息
    	
    	StringBuffer sb = new StringBuffer("CREATE TABLE IF NOT EXISTS ");
    	
    	//表名
    	String tableName = res.getCacheTable();
    	sb.append(tableName).append("(_id varchar PRIMARY KEY,");
    	
    	List<String> SQLList = new ArrayList<String>();
    	List<String> pkList = new ArrayList<String>();
    	List<String> indexList = new ArrayList<String>();
    	
    	for(int i=0;i<resColList.size();i++) {
    		StringBuffer colSb = new StringBuffer("");
    		ResCol resCol = resColList.get(i);
    		if("1".equals(resCol.getStatus())){//只处理显示字段，隐藏字段不处理
    			//创建列
        		SQLList.add(colSb.append(resCol.getColCache()).append(" ").append(resCol.getChangeType()).toString());
        		//主键
        		if("1".equals(resCol.getPk())) {
        			pkList.add(resCol.getColCache());
        		}
        		//索引
        		if("1".equals(resCol.getIdx())) {
        			indexList.add(resCol.getColCache());
        		}
    		}  		
    	}
    	if(SQLList.size()>0) {
    		sb.append(StringUtils.join(SQLList.toArray(), ","));
    		
//    		if(pkList.size()>0) {
//    			sb.append(", PRIMARY KEY(").append(StringUtils.join(pkList.toArray(), ",")).append(")");
//        	}	
    	}
    	sb.append(");");
    	
		if(indexList.size()>0) {
			for(int j=0;j<indexList.size();j++) {
				sb.append("CREATE INDEX ").append("INDEX_" + RandomUtil.getRandomId(6)).append(" on ").append(tableName).append(" (").append(indexList.get(j)).append(");");	
			}
    	}
    	
    	return sb.toString();
    }
    
    /** 
	 * @Title: findResBean 
	 * @Description: 根据resId获取ResBean
	 * @param resId
	 * @return ResBean
	 * @author lishipeng
	 * @date 2018年9月21日下午16:10:05
    */ 
	public ResBean findResBean(String resId) {
		ResBean resBean = new ResBean();
		try {
			//Res
			Res res = new Res();
			res.setId(resId);
			res = resMapper.selectOne(res);
			if(res!=null) {
				
				Category category = new Category();
				category.setId(Integer.parseInt(res.getCategory()));
				category = categoryMapper.selectOne(category);
				res.setCategoryName(category.getName());
				
				resBean.setRes(res);
			} else {
				return resBean;
			}
			
			//ResData
			ResData resData = new ResData();
			resData.setResId(resId);
			resData = resDataMapper.selectOne(resData);
			if(StringUtils.isNotBlank(resData.getAliasId())){
				Alias alias = new Alias();
				alias.setId(resData.getAliasId());
				alias = aliasMapper.selectOne(alias);
				if(alias!=null){
					resData.setAlias(alias.getAlias());
				}				
			}			
			resBean.setResData(resData);
			
			//ResCol
			ResCol resCol = new ResCol();
			resCol.setResId(resId);
			List<ResCol> resColList = resColMapper.findResCol(resCol);
			for(ResCol resCol_:resColList){
				ResColProc resColProc = new ResColProc();
				resColProc.setColId(resCol_.getId());
				List<ResColProc> proc = resColProcMapper.findResColProc(resColProc);
				resCol_.setProc(proc);
				//设置字段别名
				if(StringUtils.isNotBlank(resCol_.getAliasId())){
					//如果是拆分字段，则别名直接取AliasId
					if(StringUtils.isNotBlank(resCol_.getOrigin())){
						resCol_.setAlias(resCol_.getAliasId());
					}else{
						Alias alias_ = new Alias();
						alias_.setId(resCol_.getAliasId());
						alias_ = aliasMapper.selectOne(alias_);
						if(alias_!=null){
							resCol_.setAlias(alias_.getAlias());
						}
					}									
				}
			}			
			resBean.setResColList(resColList);
			
			//ResFilter
			ResFilter resFilter = new ResFilter();
			resFilter.setResId(resId);
			List<ResFilter> resFilterList = resFilterMapper.select(resFilter);
			for(ResFilter resFilter_:resFilterList){
				ResFilterProc resFilterProc = new ResFilterProc();
				resFilterProc.setFilterId(resFilter_.getId());
				List<ResFilterProc> proc = resFilterProcMapper.findResFilterProc(resFilterProc);
				resFilter_.setProc(proc);
			}
			resBean.setResFilterList(resFilterList);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return resBean;
	}
	
	public List buildQueryData(ResBean resBean, List data) {
		ResData resData = resBean.getResData();//资源数据信息
    	List<ResCol> resColList = resBean.getResColList();//资源表结构信息
    	List<ResFilter> resFilterList = resBean.getResFilterList();//资源表结构信息

    	data = filterProc.generateFilterData(resData, resFilterList, data);
    	data = colProc.generateColData(resData, resColList, data);
    	 	
		return data;
	}
}
