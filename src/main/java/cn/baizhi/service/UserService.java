package cn.baizhi.service;

import cn.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Map;

public interface UserService {

    //分页查
    Map<String,Object> selectByPage(int page, int size);

    //修改状态
    void updateState(String id,int status);

    //删除用户
    void deleteById(String id,String headimg);

    //添加用户
    void save(MultipartFile photo,User user) throws IOException;

    //查所有
    void finAll() throws IOException;

    //查所有用户注册时间
    Map<String,Object> fincreatedate();
}
