package cn.baizhi.dao;

import cn.baizhi.vo.MonthAndCount;

import java.util.List;

public interface UserVoDao {

    //查用户注册时间
    List<MonthAndCount> findcreatdate(String sex);

}
