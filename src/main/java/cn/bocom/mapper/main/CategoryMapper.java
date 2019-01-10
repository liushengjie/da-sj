package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.Category;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {
    public List<Category> queryCategoryTree(Category category);

    public int addCategory(Category category);

    public int updateCategory(Category category);

    public int delCategory(String id);
    
    public List<Category> selectCategoryTree(String type);
}
