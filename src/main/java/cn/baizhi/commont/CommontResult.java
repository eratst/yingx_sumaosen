package cn.baizhi.commont;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommontResult { //对象  代表 一个状态
    private String status;
    private String message;
    private Object data;

    //定义一个方法表示成功的状态
    public static CommontResult success(String message,Object data){
        CommontResult cr = new CommontResult();
        cr.setStatus("100");
        cr.setMessage(message);
        cr.setData(data);
        return cr;
    }


    public static CommontResult fail(String message,Object data){
        CommontResult cr = new CommontResult();
        cr.setStatus("104");
        cr.setMessage(message);
        cr.setData(data);
        return cr;
    }
}
