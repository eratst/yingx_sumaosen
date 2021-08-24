package cn.baizhi.util;

import cn.baizhi.config.AliyuncsConfig;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Aliyuncs {

    //短信
    public static Map<String, Object> sendSms(String phone) {

        Map<String, Object> map = new HashMap<>();
        DefaultProfile profile =
                DefaultProfile
                        .getProfile("cn-hangzhou", AliyuncsConfig.ACCESS_KEY_ID, AliyuncsConfig.ACCESS_KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "登录验证");

        /*
       【登录验证】验证码1111,您正在进行333身份验证,打死不要告诉别人哦
        * */

        request.putQueryParameter("TemplateCode", "SMS_4020642");
        String s = SecurityCode.getSecurityCode();//验证码
        map.put("yzm", s);
        String mes = "【应学】"; //  正在进行【微信】身份验证
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + s + "\",\"product\":\"" + mes + "\"}");

        CommonResponse response = null;
        try {
            response = client.getCommonResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(response.getData());

        Map map1 = JSONObject.parseObject(response.getData(), Map.class);
//        System.out.println(map);

        Object code = map1.get("Code");
        if (code.equals("OK")) {
            map.put("code", true);
        } else {
            map.put("code", false);
        }

        return map;
    }

    //上传（字节数组）
    public static void uploadByBytes(MultipartFile file, String fileName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = AliyuncsConfig.ENDPOINT;
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = AliyuncsConfig.ACCESS_KEY_ID;
        String accessKeySecret = AliyuncsConfig.ACCESS_KEY_SECRET;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 上传Byte数组。
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ossClient.putObject("eratst", fileName, new ByteArrayInputStream(content));
// 关闭OSSClient。
        ossClient.shutdown();
    }

    //文件删除
    public static void deleteFile(String objectName) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = AliyuncsConfig.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建
        // 并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = AliyuncsConfig.ACCESS_KEY_ID;
        String accessKeySecret = AliyuncsConfig.ACCESS_KEY_SECRET;
        String bucketName = "eratst";  //存储空间名
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //文件下载
    public static void DownLoad(String objectName, String localFile) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = AliyuncsConfig.ENDPOINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = AliyuncsConfig.ACCESS_KEY_ID;
        String accessKeySecret = AliyuncsConfig.ACCESS_KEY_SECRET;
        String bucketName = "eratst";  //存储空间名
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    //导出数据的文件删除
    public static void delete2(@org.jetbrains.annotations.NotNull File file) {
        File[] files = file.listFiles();
        if (files.length == 0) { //目录下的子目录为0
            file.delete();//删除此目录
            return;
        } else {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) { //判断是否为目录
                    delete2(files[i]);  //是目录调用递归
                    files[i].delete();//子目录删除成功后删除此目录
                } else {
                    files[i].delete();//不是目录删除文件
                }
            }
        }
        //file.delete();  //在删除完最高层目录下的子目录后删除最高层目录
    }
}
