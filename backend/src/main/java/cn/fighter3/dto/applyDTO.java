package cn.fighter3.dto;
import lombok.Data;

@Data
public class applyDTO {
    //请求用户编号
    private Integer tfcid;
    //物品编号
    private Integer tpid;
    //发送消息
    private String message;
}
