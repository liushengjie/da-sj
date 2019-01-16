package cn.bocom.mapper.main;

import java.util.List;
import java.util.Map;
import cn.bocom.r_entity.resource.Resource;
import tk.mybatis.mapper.common.Mapper;

public interface R_ResourceMapper{// extends Mapper<Resource>
	
	public List<Map<String, Object>> selectResourceList(Resource resource);

	//public List<Resource> queryResListByPage(Resource res);
	
	//public int saveResource(Resource res);
	
	//public int delResource(String resId);
}
