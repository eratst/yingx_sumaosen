package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.service.CategroyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategroyController {

    @Autowired
    private CategroyService cs;

    //查二级类别
    @RequestMapping("/selectByLevels")
    public List<Category> selectByLevels(int levels){
        return cs.selectByLevels(levels);
    }

    //查一级类别
    @RequestMapping("/selectByParendId")
    public List<Category> selectByParendId(String id) {
        return cs.selectByParendId(id);
    }

    //添加类别
    @RequestMapping("/save")
    public void save(@RequestBody Category category){
        cs.save(category);
    }

    //删除类别
    @RequestMapping("/delete")
    public Map<String, Object> delete(String id){
        return cs.delete(id);
    }
}
