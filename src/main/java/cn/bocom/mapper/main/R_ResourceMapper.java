package cn.bocom.mapper.main;

import java.util.List;
import java.util.Map;

import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_entity.resource.ResourceBody;


public interface R_ResourceMapper  {//extends Mapper<Resource>
	
	public List<Map<String, Object>> selectResourceList(Resource resource);

	public int deleteResourceBody(String resourceId);
	public int deleteResourceCol(String resourceId);
	public int deleteResourceData(String resourceId);
	
	public int saveResourceBody(ResourceBody resourceBody);
	
	
}
