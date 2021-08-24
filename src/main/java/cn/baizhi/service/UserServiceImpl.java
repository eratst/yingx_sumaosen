package cn.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.UserDao;
import cn.baizhi.dao.UserVoDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.Aliyuncs;
import cn.baizhi.vo.MonthAndCount;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao ud;
    @Autowired
    private UserVoDao uvd;

    //分页查用户
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> selectByPage(int page, int size) {
        Map<String, Object> map = new HashMap<>();
        List<User> list = ud.selectRange((page - 1) * size, size);
        int count = ud.selectCount();
        map.put("data", list);
        if (count % size == 0) {
            map.put("pages", count / size);
        } else {
            map.put("pages", count / size + 1);
        }
        return map;
    }

    //修改状态
    @Override
    @DeleteCache
    public void updateState(String id, int status) {
        ud.updateState(id, status);
    }

    //删除用户
    @Override
    @DeleteCache
    public void deleteById(String id, String headimg) {
        //文件名
        int i = headimg.lastIndexOf("/") + 1;
        String objectName = "yxphoto/" + headimg.substring(i);
        Aliyuncs.deleteFile(objectName);
        ud.deleteById(id);
    }

    //添加用户
    @Override
    @DeleteCache
    public void save(MultipartFile photo, User user) throws IOException {
        Date date = new Date();
        long time = date.getTime();
        String fileNames = photo.getOriginalFilename();
        String fileName = "yxphoto/" + time + fileNames;
        Aliyuncs.uploadByBytes(photo, fileName);
        String objectName1 = "http://eratst.oss-cn-beijing.aliyuncs.com/yxphoto/" + time + fileNames;
        user.setHeadimg(objectName1);
        ud.save(user);
    }

    //数据导出
    @Override
    public void finAll(){
        List<User> all = ud.findAll();
        for (User user : all) {
            String headimg = user.getHeadimg();
            int i = headimg.lastIndexOf("/")+ 1;
            String objectName = headimg.substring(i);//文件名
            String localFile="D:\\AAAAA\\yx_sumaosen\\src\\main\\webapp\\download\\"+objectName;  //下载本地地址  地址加保存名字
            Aliyuncs.DownLoad("yxphoto/"+objectName,localFile);
            user.setHeadimg(localFile);
        }
        Workbook sheets = ExcelExportUtil.exportExcel(new ExportParams("用户信息", "用户信息表"), User.class, all);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream("D:\\AAAAA\\user.xls");
            sheets.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //删除应用中下载得文件
        Aliyuncs.delete2(new File("D:\\AAAAA\\yx_sumaosen\\src\\main\\webapp\\download\\"));
    }

    //查所有用户注册时间
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> fincreatedate() {
//        List<Integer> man = Arrays.asList(0,0,0,0,0);
//        man.set(5,1);
        List<String> data = new ArrayList<>();
        for(int i=1;i<=12;i++){
            data.add(i+"月");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data",data);
        List<MonthAndCount> man = uvd.findcreatdate("男");
        List<Integer> manCount = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        for (MonthAndCount nan : man) {
            Integer month = nan.getMonth();
            manCount.set(month-1,nan.getCount());
        }
        map.put("manCount",manCount);
        List<MonthAndCount> woman = uvd.findcreatdate("女");
        List<Integer> womanCount = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0 , 0, 0, 0, 0);
        for (MonthAndCount nv: woman) {
            Integer month = nv.getMonth();
            womanCount.set(month-1,nv.getCount());
        }
        map.put("womanCount",womanCount);
        return map;
    }
}
