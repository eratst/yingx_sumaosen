package cn.baizhi;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.dao.AdminDao;
import cn.baizhi.dao.UserDao;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import cn.baizhi.service.AdminService;
import cn.baizhi.vo.VideoVo;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class YxSumaosenApplicationTests {

    @Autowired
    private AdminService as;
    @Autowired
    private AdminDao ad;
    @Autowired
    private UserDao ud;
    @Autowired
    private VideoDao vd;
    @Autowired
    private VideoVoDao vvd;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Test
    void contextLoads() {
        /*List<User> users = ud.selectRange(3, 3);
        for (User user : users) {
            System.out.println(user);*/
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI5tC6dzMGkoxLz5pCrWBb";
        String accessKeySecret = "1T9gjEEAkm5dNQS8EXUsPaHoVRhtj8";
        String objectName = "http://sumaosen.oss-cn-hangzhou.aliyuncs.com/" + "photo.txt";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 上传Byte数组。
        byte[] content = "photo".getBytes();
        //http://sumaosen.oss-cn-hangzhou.aliyuncs.com/hdImg_5551542b9be16fff21e03310c4bc6ee11619799978491.jpg
        ossClient.putObject("sumaosen", objectName, new ByteArrayInputStream(content));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    @Test
    void aaa() {
        List<Video> videos = vd.selectByPage(0, 1);
        for (Video video : videos) {
            System.out.println(video);
        }
    }

    String a = "abcdefg";

    @Test
    void bbb() {
//            int i = a.indexOf(a); //返回指定字符的索引。
//            char c = a.charAt(2); //返回指定索引处的字符。
//            String abc = a.replace("abc", "123");//字符串替换。
//            String trim = a.trim();//去除字符串两端空白。
//            String[] split = a.split("d");//分割字符串，返回一个分割后的字符串数组。
//            String s = a.toUpperCase();//将字符串转成大写字符。 toLowerCase()：将字符串转成小写字母。
//            String substring = a.substring(2);//截取字符串。
//            System.out.println("输出结果："+substring);
    }

    @Test
    void test1() {
        List<User> all = ud.findAll();
        for (User user : all) {
            String headimg = user.getHeadimg();
            int i = headimg.lastIndexOf("/")+1;
            String substring = headimg.substring(i);
            System.out.println(substring);
        }
    }

    @Test
    public void testRedis(){
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        valueOperations.set("xiaoxiao","123");
    }
}


