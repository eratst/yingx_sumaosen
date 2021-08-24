package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@CrossOrigin //跨域
@RequestMapping("/admin")
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    @Autowired
    private AdminService as;

    //管理员登录
    @RequestMapping("/login")
    public Map<Object, Object> login(@RequestBody Admin yxadmin){
        log.debug(yxadmin.toString());
        return as.login(yxadmin.getUsername(),yxadmin.getPassword());
    }
}
