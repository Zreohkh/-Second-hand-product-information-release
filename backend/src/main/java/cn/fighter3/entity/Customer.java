package cn.fighter3.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "customer")
public class Customer {
    //用户id
    @TableId(type = IdType.AUTO)
    private int cid;
    //用户名
    private String cname;
    //用户密码
    private String cpass;
    //邮箱
    private String email;
    //qq
    private String qq;
    //微信
    private String wechat;
    //电话
    private String phone;
    //用户描述
    private String des;
    //审核状态
    private Integer rcon;
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cid).append(cname).append(cpass).append(email).append(qq).append(wechat).append(phone).append(des);
        return stringBuilder.toString();
    }
}
