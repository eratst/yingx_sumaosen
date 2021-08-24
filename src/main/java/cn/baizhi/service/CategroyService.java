package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategroyService {

    //根据级别查类别
    List<Category> selectByLevels(int levens);

    //根据一级类别查二级类别
    List<Category> selectByParendId(String id);

    //根据一级类别添加二级类别
    void save(Category category);

    //删除类别
    Map<String, Object> delete(String id);
}
