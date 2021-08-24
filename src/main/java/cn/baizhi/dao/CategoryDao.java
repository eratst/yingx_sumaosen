package cn.baizhi.dao;

import cn.baizhi.entity.Category;

import java.util.List;

public interface CategoryDao {

    //根据级别查类别
    List<Category> selectByLevels(int levens);

    //根据一级类别查二级类别
    List<Category> selectByParendId(String id);

    //根据一级类别添加二级类别
    void save(Category category);

    //删除类别
    void delete(String id);
}
