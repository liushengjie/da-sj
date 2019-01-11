package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.r_entity.datasource.DataSource;
import tk.mybatis.mapper.common.Mapper;

public interface R_DataSourceMapper extends Mapper<DataSource>{
    public List<DataSource> selectDs(DataSource ds);
    public int insertDs(DataSource ds);
    public int updateDs(DataSource ds);
}
