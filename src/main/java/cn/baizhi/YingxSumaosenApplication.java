package cn.baizhi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("cn.baizhi.dao")
@SpringBootApplication
public class YingxSumaosenApplication {

    public static void main(String[] args) {
        SpringApplication.run(YingxSumaosenApplication.class, args);
    }

}
