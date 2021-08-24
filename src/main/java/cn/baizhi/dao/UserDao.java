package cn.baizhi.dao;

import cn.baizhi.entity.User;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface UserDao {
    //查所有用户
    List<User> selectRange(@Param("start") int start, @Param("end") int end);

    //查总用户数
    int selectCount();

    //修改状态
    void updateState(@Param("id") String id,@Param("status") int status);

    //删除用户
    void deleteById(String id);

    //添加用户
    void save(User user);

    //查所有
    List<User> findAll();

}
