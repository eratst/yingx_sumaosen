package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  @Excel(name = "id")
  private String id;
  @Excel(name = "姓名")
  private String username;
  private String sex;
  @Excel(name = "手机号")
  private String phone;
  @Excel(name = "头像",type = 2)
  private String headimg;
  @Excel(name = "描述")
  private String brief;
  private String wechat;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date createdate;
  private Integer status;


}
