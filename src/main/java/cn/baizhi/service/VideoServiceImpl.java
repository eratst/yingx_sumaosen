package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.config.AliyuncsConfig;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.entity.Video;
import cn.baizhi.util.Aliyuncs;
import cn.baizhi.vo.VideoVo;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao vd;
    @Autowired
    private VideoVoDao vvd;

    //分页查所有视频
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectByPage(int page, int size) {
        Map<String, Object> map = new HashMap<>();
        List<Video> list = vd.selectByPage((page - 1) * size, size);
        int count = vd.selectVideoCount();
        map.put("data", list);
        if (count % size == 0) {
            map.put("pages", count / size);
        } else {
            map.put("pages", count / size + 1);
        }
        return map;
    }

    //添加视频
    @Override
    @DeleteCache
    public void add(MultipartFile videopath, Video video) {
        Date date = new Date();
        long time = date.getTime();
        String fileName = videopath.getOriginalFilename();
        String objectName = "yxvideo/" + time + fileName;
        String objectName1 = "http://eratst.oss-cn-beijing.aliyuncs.com/yxvideo/" + time + fileName;
        video.setVideoPath(objectName1);
        Aliyuncs.uploadByBytes(videopath, objectName);
        OSS ossClient = new OSSClientBuilder().build(AliyuncsConfig.ENDPOINT, AliyuncsConfig.ACCESS_KEY_ID, AliyuncsConfig.ACCESS_KEY_SECRET);
        // 填写视频文件所在的Bucket名称。
        String bucketName = "eratst";
        // 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_1000,f_jpg,w_800,h_600";
        // 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + (1000 * 60 * 10));
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        // 上传网络流。
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String f = time + fileName;
        String[] split = f.split("\\.");
        ossClient.putObject("eratst", "yxvideo/" + split[0] + ".jpg", inputStream);
        String videocover = "http://eratst.oss-cn-beijing.aliyuncs.com/yxvideo/" + split[0] + ".jpg";
        video.setCoverPath(videocover);
        // 关闭OSSClient。
        ossClient.shutdown();
        vd.save(video);
    }

    //删除视频
    @Override
    @DeleteCache
    public void deleteById(String id, String videoPath) {
        //文件名
        int i = videoPath.lastIndexOf("/") + 1;
        String objectName = "yxvideo/" + videoPath.substring(i);
        String vc = videoPath.substring(i);
        String[] split = vc.split("\\.");
        String videocover = "yxvideo/" + split[0] + ".jpg";
        Aliyuncs.deleteFile(objectName);
        Aliyuncs.deleteFile(videocover);
        vd.deleteById(id);
    }

    //app获取视频
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<VideoVo> queryByCreateDate() {
        return vvd.queryAllVideo();
    }
}
