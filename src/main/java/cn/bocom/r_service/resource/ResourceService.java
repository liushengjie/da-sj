package cn.bocom.r_service.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.bocom.mapper.main.R_ResourceMapper;
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
    	System.out.println("::::::::::::::::::::::::::::::::");
        
    	//根据条件查询资源信息
    	List<Map<String, Object>> resList = resMapper.selectResourceList(resource);
    	System.out.println("================="+resList.size());
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
    			
    			resBody.setId("");
    			resBody.setName("");
    			resBody.setCategory("");
    			resBody.setConnectType("");
    			resBody.setNum(0);
    			resBody.setCacheTable("");
    			resBody.setSchemaFlag("");
    			resBody.setStatus("");
    			resBody.setCreateUser("");
    			resBody.setCreateTime(null);
    			resBody.setUpdateTime(null);
    			
    			resData.setId("");
    			resData.setResId("");
    			resData.setDsId("");
    			resData.setTableName("");
    			resData.setConnModel("");
    			
    			res.setResourceBody(resBody);
    			res.setResourceData(resData);
    		}
    		
    		ResourceCol resCol = new ResourceCol();
    		
    		
    		
    		colsList.add(resCol);
    		
    		//最后一次循环时，整合最后一个资源
    		if(i==resList.size()-1) {
    			res.setResourceCols(colsList);
    			retList.add(res);
    		}
    	}
    	System.out.println("*********************"+retList.size());
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
		
		return null;
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
		return null;
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
   		return 0;
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
    	/*try {
    		DataSource ds = new DataSource();
            ds.setId(datasourceId);
        	return dataSourceMapper.deleteByPrimaryKey(ds);	
    	} catch(Exception e) {
    		e.printStackTrace();
    		return -1;
    	}*/
    	return 0;
    }
}
