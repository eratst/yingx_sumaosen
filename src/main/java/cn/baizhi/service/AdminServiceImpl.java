package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminDao ad;


    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<Object, Object> login(String username,String password) {
        Map<Object, Object> map = new HashMap<>();
        Admin yxadmin1 = ad.login(username);
        if(yxadmin1 != null){
            //用户名正确 再判断密码
            if(yxadmin1.getPassword().equals(password)){
                map.put("flag",true);
                map.put("msg","登录成功！");
            }else {
                map.put("msg","密码错误！");
                map.put("flag",false);
            }
        }else {
            map.put("msg","没有此用户！");
            map.put("flag",false);
        }
        return map;
    }
}
