package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.Processor;
import tk.mybatis.mapper.common.Mapper;

public interface ProcessorMapper extends Mapper<Processor> {
	
    public List<Processor> queryProcessor(Processor processor);
}
