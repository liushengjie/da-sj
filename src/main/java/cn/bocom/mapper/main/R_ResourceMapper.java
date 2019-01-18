package cn.bocom.mapper.main;

import java.util.List;
import java.util.Map;

import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_entity.resource.ResourceBody;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_entity.resource.ResourceData;
import cn.bocom.r_entity.resource.ResourceView;
import tk.mybatis.mapper.common.Mapper;


public interface R_ResourceMapper extends Mapper<ResourceBody> {
	
	public List<Map<String, Object>> selectResourceList(Resource resource);
	public List<ResourceView> selectResourceViewListByPage(ResourceView resourceView);

	public int deleteResourceBody(String resourceId);
	public int deleteResourceCol(String resourceId);
	public int deleteResourceData(String resourceId);
	
	public int saveResourceBody(ResourceBody resourceBody);
	public int saveResourceData(ResourceData resourceData);
	public int saveResourceCol(ResourceCol resourceCol);
	
	
}
