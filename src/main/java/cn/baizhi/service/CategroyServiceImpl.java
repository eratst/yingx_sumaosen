package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class CategroyServiceImpl implements CategroyService{

    @Autowired
    private CategoryDao cd;

    //查二级类别
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> selectByLevels(int levens) {
        return cd.selectByLevels(levens);
    }

    //差一级类别
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> selectByParendId(String id) {
        return cd.selectByParendId(id);
    }

    //添加
    @Override
    @DeleteCache
    public void save(Category category) {
        category.setId(UUID.randomUUID().toString());
        cd.save(category);
    }

    //删除
    @Override
    @DeleteCache
    public Map<String, Object> delete(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("flag",false);
        List<Category> categories = cd.selectByParendId(id);
        if(categories.size() != 0){
            map.put("msg","此类别下有二级类别不能删除");
        }else{
            map.put("flag",true);
            map.put("msg","删除成功");
            cd.delete(id);
        }
        return map;
    }
}
