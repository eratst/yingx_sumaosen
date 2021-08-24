package cn.baizhi.service;

import cn.baizhi.entity.Video;
import cn.baizhi.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface VideoService {

    //分页查所有用户的视频
    Map<String,Object> selectByPage(int page,int size);

    //添加视频
    void add(MultipartFile videopath, Video video) throws IOException;

    //删除视频
    void deleteById(String id,String videoPath);

    //app获取视频
    List<VideoVo> queryByCreateDate();
}
