/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service
 * @author: liushengjie
 * @date: 2018年8月15日 下午5:15:33
 */
package cn.bocom.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.entity.Alias;
import cn.bocom.entity.Category;
import cn.bocom.entity.CategoryNode;
import cn.bocom.entity.Res;
import cn.bocom.entity.ResCol;
import cn.bocom.mapper.main.AliasMapper;
import cn.bocom.mapper.main.CategoryMapper;
import cn.bocom.mapper.main.ResColMapper;
import cn.bocom.mapper.main.ResMapper;

/**
 * 
 * @author Administrator
 *
 */
@Component
public class CategoryService {
    private static Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryMapper categoryMapper;
    
    @Autowired
    private ResMapper resMapper;
    
    @Autowired
    private ResColMapper resColMapper;
    
    @Autowired
    private AliasMapper aliasMapper;

    /***
     * 查询树形结构
     * 
     * @param category
     * @return
     */
    public List<Category> queryCategoryTree(Category category) {
        return categoryMapper.queryCategoryTree(category);
    }

    /**
     * 新增
     * 
     * @param category
     * @return
     */
    public boolean addCategory(Category category) {
        if (categoryMapper.addCategory(category) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改
     * 
     * @param category
     * @return
     */
    public boolean updateCategory(Category category) {
        if (categoryMapper.updateCategory(category) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /***
     * 删除 
     * 
     * @param id
     * @return
     */
    public boolean delCategory(String id) {
        if (categoryMapper.delCategory(id) > 0) {
            return true;
        } else {
            return false;
        }
    }
    
    public List<CategoryNode> selectCategoryTree(String type){
    	List<Category> list = categoryMapper.selectCategoryTree(type);  
    	List<CategoryNode> newList = new ArrayList<CategoryNode>();
    	for(Category cat:list){
    		CategoryNode node = new CategoryNode();
    		node.setId(cat.getId());
    		node.setTitle(cat.getName());
    		node.setPid(cat.getPid());
    		node.setSort(cat.getSort());
    		node.setType(cat.getType());
    		newList.add(node);
    	}
    	return buildTree_type(newList);
	}
    
    public List<CategoryNode> selectCategoryTreeWithRes(String type){
    	List<Category> list = categoryMapper.selectCategoryTree(type);  
    	List<CategoryNode> newList = new ArrayList<CategoryNode>();
    	for(Category cat:list){
    		CategoryNode node = new CategoryNode();
    		node.setId(cat.getId());
    		node.setTitle(cat.getName());
    		node.setPid(cat.getPid());
    		node.setSort(cat.getSort());
    		node.setType(cat.getType());
    		
    		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

    		List<Res> resList = resMapper.select(new Res().setCategory(cat.getId().toString()));
    		for(Res res:resList){
    			Map<String, Object> map = new HashMap<String, Object>();
    			List<ResCol> resColList = resColMapper.select(new ResCol().setResId(res.getId()));
    			List<Map<String, String>> newResColList = new ArrayList<Map<String, String>>();
    			String id,col = null;
    			for(ResCol resCol:resColList){ 
    				Map<String, String> map_ = new HashMap<String, String>();
    				map_.put("id", resCol.getColCache());
    				map_.put("type", resCol.getChangeType());
    				if(StringUtils.isBlank(resCol.getAliasId())){
    					map_.put("name", resCol.getCol());
    				}else{
    					Alias alias = aliasMapper.selectOne(new Alias().setId(resCol.getAliasId()));
        				if(alias==null || StringUtils.isBlank(alias.getAlias())){
        					map_.put("name", resCol.getCol());
        				}else{
        					map_.put("name", alias.getAlias());
        				}
    				}   				  				 		
    				newResColList.add(map_);
    			}
    			map.put("res", res);
    			map.put("resCol", newResColList);
    			data.add(map);
    		}
    		node.setData(data);
    		
    		newList.add(node);
    	}
    	return buildTree_type(newList);
	}
	
    //嵌套输出树型
    public List<CategoryNode> buildTree_type(List<CategoryNode> nodes){  
    	if(nodes==null){
    		return null;
    	}
    	List<CategoryNode> tree = new ArrayList<CategoryNode>();
        for (CategoryNode node : nodes) {
            String id = node.getId().toString(); 
            //首先加入根节点,0代表根节点
            if ("0".equals(node.getPid().toString())) {
            	node.setChildren(new ArrayList<CategoryNode>());
                tree.add(node);
                build_type(nodes, node);  
            }  
        }  
        return tree;  
    }  
      
    //循环子节点
    private void build_type(List<CategoryNode> nodes, CategoryNode node){ 
    	node.setChildren(new ArrayList<CategoryNode>());
        List<CategoryNode> children = getChildren(nodes, node);  
        if (!children.isEmpty()) {  
            for (CategoryNode child : children) {            	            
                child.setTitle(child.getTitle());
                node.getChildren().add(child);
                build_type(nodes, child);  
            }  

        }   
    }
    
    //查找子节点
    private List<CategoryNode> getChildren(List<CategoryNode> nodes, CategoryNode node){  
        List<CategoryNode> children = new ArrayList<CategoryNode>();  
        String id = node.getId().toString();  
        for (CategoryNode child : nodes) {  
            if (id.equals(child.getPid().toString())) {  
                children.add(child);  
            }  
        }  
        return children;  
    }
}
