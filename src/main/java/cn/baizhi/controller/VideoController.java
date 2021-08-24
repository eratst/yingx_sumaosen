package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/video")
public class VideoController {

    private static final Logger log = LoggerFactory.getLogger(VideoController.class);
    @Autowired
    private VideoService vs;

    //分页查视频
    @RequestMapping("/findAll")
    public Map<String, Object> findAll(int page) {
        int size = 2;
        return vs.selectByPage(page, size);
    }

    //添加视频
    @RequestMapping("/add")
    public void add(MultipartFile videopath, String title, String brief, String id) throws IOException {
        Video video = new Video(UUID.randomUUID().toString(),title,brief,null,null,new Date(),new Category(id,null,null,null),null,null);
        vs.add(videopath, video);
    }

    //删除视频
    @RequestMapping("/deleteById")
    public void deleteById(String id, String videoPath) {
        vs.deleteById(id, videoPath);
    }
}
