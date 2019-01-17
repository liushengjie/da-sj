package cn.bocom.r_service.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.bocom.mapper.main.R_ResourceMapper;
import cn.bocom.other.util.DBUtil;
import cn.bocom.other.util.RandomUtil;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.resource.ResColInfo;
import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_entity.resource.ResourceBody;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_entity.resource.ResourceData;
import cn.bocom.r_service.resource.res_transform.ResourceTrans;

/**
 * 资源服务类
 * @author liushengjie
 * @version $Id: ResourceService.java, v 0.1 2019年1月12日 下午4:35:35 liushengjie Exp $
 */
@Component
public class ResourceService {
    private static Logger logger = LoggerFactory.getLogger(ResourceService.class);
    
    @Autowired
    private R_ResourceMapper resMapper;
    
    
    @Autowired
    private ResourceTrans resourceTrans;
    
    public Resource loadResource(String datasourceId, TableInfo table) {
        return resourceTrans.convertToRes(datasourceId, table);
    }
    
    
    /**
     * 根据资源获取列信息
     * @param resource
     * @return
     */
    public List<ResourceCol> showResourceCols(Resource resource){
        List<ResourceCol> res_cols = resource.getResourceCols();
//        List<ResColInfo> ret = Lists.newArrayList();
//        res_cols.forEach(c -> {
//            ret.add(resourceTrans.convertToResColInfo(c));
//        });
        return res_cols;
    }
    
    
    //TODO:根据资源获取数据预览
    public List<Map<String, Object>> loadDataByResource(Resource resource){
        return null;
    }
    
    
    //TODO: 资源对象增删改查

    /** 
	 * @Title: selectResourceList 
	 * @Description: 获取资所有源库信息
	 * @param resource
	 * @return List<Resource>
	 * @author lishipeng
	 * @date 2019年1月15日
    */ 
    public List<Resource> selectResourceList(Resource resource) {
    	//根据条件查询资源信息
    	List<Map<String, Object>> resList = resMapper.selectResourceList(resource);
    	if(resList==null||resList.size()==0) {
    		return null;
    	}
    	List<Resource> retList = new ArrayList<Resource>();
    	//整合资源信息
    	Resource res = null;
    	ResourceBody resBody = null;
    	List<ResourceCol> colsList = null;
    	ResourceData resData = null;
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    	for(int i=0;i<resList.size();i++) {
    		Map<String, Object> map = resList.get(i);
    		//当res为null时，或当前记录的res_id不等于已有的res_id时，说明循环到新资源，new所有对象，并赋值
    		if(res==null||!resBody.getId().equals(map.get("id").toString())) {
    			if(res!=null) {//res不为null，说明符合《前记录的res_id不等于已有的res_id》，即循环到新资源，整合当前资源
    				res.setResourceCols(colsList);
    				retList.add(res);
    			}
    			
    			res = new Resource();
    			resBody = new ResourceBody();
    			colsList = new ArrayList<ResourceCol>();
    			resData = new ResourceData();
    			
    			resBody.setId(map.get("id").toString());
    			resBody.setName(map.get("name")==null?"":map.get("name").toString());
    			resBody.setCategory(map.get("category")==null?"":map.get("category").toString());
    			resBody.setConnectType(map.get("connect_type")==null?"":map.get("connect_type").toString());
    			resBody.setNum(map.get("num")==null?0:Integer.parseInt(map.get("num").toString()));
    			resBody.setCacheTable(map.get("cache_table")==null?"":map.get("cache_table").toString());
    			resBody.setSchemaFlag(map.get("schema_flag")==null?"":map.get("schema_flag").toString());
    			resBody.setStatus(map.get("status")==null?"":map.get("status").toString());
    			resBody.setCreateUser(map.get("create_user")==null?"":map.get("create_user").toString());
    			try {
					resBody.setCreateTime(sdf.parse(map.get("create_time").toString()));
					resBody.setUpdateTime(sdf.parse(map.get("update_time").toString()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
    			
    			resData.setId(map.get("data_id")==null?"":map.get("data_id").toString());
    			resData.setResId(map.get("data_res_id")==null?"":map.get("data_res_id").toString());
    			resData.setDsId(map.get("data_ds_id")==null?"":map.get("data_ds_id").toString());
    			resData.setTableName(map.get("data_table_name")==null?"":map.get("data_table_name").toString());
    			resData.setConnModel(map.get("data_connmodel")==null?"":map.get("data_connmodel").toString());
    			
    			res.setResourceBody(resBody);
    			res.setResourceData(resData);
    		}
    		
    		ResourceCol resCol = new ResourceCol();
    		
    		resCol.setId(map.get("col_id")==null?"":map.get("col_id").toString());
    		resCol.setResId(map.get("col_res_id")==null?"":map.get("col_res_id").toString());
    		resCol.setType(map.get("col_type")==null?"":map.get("col_type").toString());
    		resCol.setCol(map.get("col_col")==null?"":map.get("col_col").toString());
    		resCol.setChangeType(map.get("col_change_type")==null?"":map.get("col_change_type").toString());
    		resCol.setAlias(map.get("col_alias")==null?"":map.get("col_alias").toString());
    		resCol.setColCache(map.get("col_col_cache")==null?"":map.get("col_col_cache").toString());
    		resCol.setPk(map.get("col_pk")==null?"":map.get("col_pk").toString());
    		resCol.setIdx(map.get("col_idx")==null?"":map.get("col_idx").toString());
    		resCol.setDict(map.get("col_dic")==null?"":map.get("col_dic").toString());
    		resCol.setStatus(map.get("col_status")==null?"":map.get("col_status").toString());
    		resCol.setOrigin(map.get("col_origin")==null?"":map.get("col_origin").toString());
    		resCol.setSort(map.get("col_sort")==null?"":map.get("col_sort").toString());
    		
    		colsList.add(resCol);
    		
    		//最后一次循环时，整合最后一个资源
    		if(i==resList.size()-1) {
    			res.setResourceCols(colsList);
    			retList.add(res);
    		}
    	}
    	return retList;
    }
    
	/** 
	 * @Title: selectResourceListByPage 
	 * @Description: 获取资源库分頁信息
	 * @param resource
	 * @param currentPage
	 * @param pageSize
	 * @return PageInfo<Resource>
	 * @author lishipeng
	 * @date 2019年1月15日
    */ 
    public PageInfo<Resource> selectResourceListByPage(Resource resource, int currentPage, int pageSize) {
    	PageHelper.startPage(currentPage, pageSize);
        List<Resource> list = selectResourceList(resource);
        PageInfo<Resource> pageInfo = new PageInfo<Resource>(list);
        return pageInfo;
    }
    
    /** 
	 * @Title: selectResourceById 
	 * @Description: 根据resId获取Resource
	 * @param resourceId
	 * @return Resource
	 * @author lishipeng
	 * @date 2019年1月15日
     */ 
	public Resource selectResourceById(String resourceId) {
		Resource resource = new Resource();
		resource.setResourceBody(new ResourceBody().setId(resourceId));
		List<Resource> list = selectResourceList(resource);
		if(list==null||list.size()==0) {
			return null;
		} else {
			return list.get(0);
		}
	}
    
	/** 
	 * @Title: selectResourceByCategory
	 * @Description: 根据category获取Resource
	 * @param category
	 * @return List<Resource>
	 * @author lishipeng
	 * @date 2019年1月15日
     */ 
	public List<Resource> selectResourceByCategory(String category) {
		Resource resource = new Resource();
		resource.setResourceBody(new ResourceBody().setCategory(category));
		List<Resource> list = selectResourceList(resource);
		return list;
	}
	
    /** 
   	 * @Title: saveResource 
   	 * @Description: 保存资源信息，有记录则更新反之插入
   	 * @param resource
   	 * @return int
   	 * @author lishipeng
   	 * @date 2019年1月15日
     */ 
    //@Transactional
   	public int saveResource(Resource resource) {
   		if(resource==null) {
    		return 0;
    	}
    	ResourceBody resBody = resource.getResourceBody();//资源信息
    	ResourceData resData = resource.getResourceData();//资源数据信息
    	List<ResourceCol> resColList = resource.getResourceCols();//资源表结构信息
    	
    	if(resBody==null||resData==null||resColList==null||resColList.size()==0) {
    		return 0;
    	}
    	
    	//>>>>设置id并保存ResourceBody
    	if(StringUtils.isBlank(resBody.getId())) {//新增则生成id
    		resBody.setId(RandomUtil.getRandomId(18));
    		resBody.setCacheTable("res_" + RandomUtil.getRandomId(18));
    	}else{//更新则先删后插
    		//查询库中Resource
    		Resource resOld = selectResourceById(resBody.getId());
    		//将库中的Resource部分属性赋值到新Resource里
    		resBody.setCreateUser(resOld.getResourceBody().getCreateUser());
    		resBody.setCreateTime(resOld.getResourceBody().getCreateTime());
    		resBody.setSchemaFlag(resOld.getResourceBody().getSchemaFlag());
    		resBody.setStatus(resOld.getResourceBody().getStatus());
    		resBody.setNum(resOld.getResourceBody().getNum());
    		resBody.setCacheTable(resOld.getResourceBody().getCacheTable());
    	}
    	resMapper.saveResourceBody(resBody);
    	
    	//>>>>设置id并保存ResourceData
		resData.setResId(resBody.getId());
		if(resData.getId()==null || "".equals(resData.getId())) {
			resData.setId(RandomUtil.getRandomId(18));
		}	    		
    	resMapper.saveResourceData(resData);
    	
    	//>>>>设置id并保存ResourceCol
    	if(resColList!=null&&resColList.size()>0) {
    		for(int i=0;i<resColList.size();i++) {
    			ResourceCol resCol = resColList.get(i);
    			resCol.setResId(resBody.getId());
    			if(resCol.getId()==null || "".equals(resCol.getId())) {
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
        		resCol.setSort(String.valueOf(i+1));
        		resCol.setColCache(resCol.getCol()+"_"+resBody.getId());
        		resMapper.saveResourceCol(resCol);
    		}
    	}
    	
   		return 1;
   	}
   	
   	/** 
	 * @Title: deleteResource 
	 * @Description: 刪除资源库信息
	 * @param resourceId
	 * @return int
	 * @author lishipeng
	 * @date 2019年1月15日
    */ 
    public int deleteResource(String resourceId) {
    	try {
        	resMapper.deleteResourceBody(resourceId);
        	resMapper.deleteResourceCol(resourceId);
        	resMapper.deleteResourceData(resourceId);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}
    	return 1;
    }
}
