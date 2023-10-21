package cn.fighter3.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName(value = "trade")
public class Trade {
    //订单编号
    @TableId(type = IdType.AUTO)
    private Integer tid;
    //请求用户编号
    private Integer tfcid;
    //物品编号
    private Integer tpid;
    //发送消息
    private String message;
    //日期
    private Date tdate;
    //订单状态
    private Integer tcon;
}
