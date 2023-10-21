package cn.fighter3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApplyItem {
    private Integer tid;
    private String cname;
    private Integer tpid;
    private String pname;
    private String type;
    private String message;
    private String contact;
    private Integer tcon;
}
