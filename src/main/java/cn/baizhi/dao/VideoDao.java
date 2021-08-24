package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {

    //分页查所有用户的视频
    List<Video> selectByPage(@Param("start") int start, @Param("end") int end);

    //查视频总数
    int selectVideoCount();

    //添加视频
    void save(Video video);

    //删除视频
    void deleteById(String id);
}
