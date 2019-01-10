package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.Res;
import cn.bocom.entity.ResView;
import tk.mybatis.mapper.common.Mapper;

public interface ResMapper extends Mapper<Res> {
	
	public List<ResView> queryResListByPage(ResView res);
	
	public int saveRes(Res res);
	
	public int saveRes2(Res res);
	
	public List<Res> findRes(Res res);
	
	public int delRes(String id);
}