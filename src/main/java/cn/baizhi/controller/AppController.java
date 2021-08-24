package cn.baizhi.controller;

import cn.baizhi.commont.CommontResult2;
import cn.baizhi.service.VideoService;
import cn.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/yingx/app")
public class AppController {

    @Autowired
    private VideoService vs;

    //app获取视频数据
    @RequestMapping("/queryByReleaseTime")
    public Map<String,Object> queryByReleaseTime(){
        List<VideoVo> videoVos = new ArrayList<>();
        try{
            videoVos = vs.queryByCreateDate();
            return CommontResult2.success("查询成功",videoVos);
        }catch (Exception e){
            e.printStackTrace();
            return CommontResult2.fail("查询失败",videoVos);
        }
    }
}
