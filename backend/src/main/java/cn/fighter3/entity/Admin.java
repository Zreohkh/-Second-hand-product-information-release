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
@TableName(value = "admin")
public class Admin {
    //用户id
    @TableId(type = IdType.AUTO)
    private int cid;
    //用户名
    private String cname;
    //用户密码
    private String cpass;
}
