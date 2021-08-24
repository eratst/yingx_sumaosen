package cn.baizhi.controller;

import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService us;

    //分页查用户
    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page) {
        int size = 3;
        return us.selectByPage(page, size);
    }

    //修改状态
    @RequestMapping("updateState")
    public void updateState(String id, int status) {
        us.updateState(id, status);
    }

    //删除用户
    @RequestMapping("deleteById")
    public void deleteById(String id, String headimg) {
        us.deleteById(id, headimg);
    }

    //添加用户
    @RequestMapping("save")
    public void save(MultipartFile photo, String username, String phone, String brief, String sex) throws IOException {
        User user = new User(UUID.randomUUID().toString(),username,sex,phone,null,brief,null,new Date(),0);
        us.save(photo, user);
    }

    //导出数据
    @RequestMapping("download")
    public void download() throws IOException {
        us.finAll();
    }

    //查用户注册时间
    @RequestMapping("registCount")
    public Map<String,Object> registCount(){
        return us.fincreatedate();
    }
}
