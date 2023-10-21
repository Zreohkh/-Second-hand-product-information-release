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
@TableName(value = "product")
public class Product {
    //物品id
    @TableId(type = IdType.AUTO)
    Integer pid;
    //物品所属用户id
    Integer pcid;
    //物品名称
    String pname;
    //物品描述
    String des;
    //类型
    String type;
    //状态
    Integer pcon;
    //目标价格
    Float price;
    //目标物品
    String item;
    //审核状态
    Integer rcon;
}
