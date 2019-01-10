package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.ResColProc;
import cn.bocom.entity.ResFilterProc;
import tk.mybatis.mapper.common.Mapper;

public interface ResColProcMapper extends Mapper<ResColProc> {
	
	public int saveResColProc(ResColProc resColProc);
	
	public List<ResColProc> findResColProc(ResColProc resColProc);
}