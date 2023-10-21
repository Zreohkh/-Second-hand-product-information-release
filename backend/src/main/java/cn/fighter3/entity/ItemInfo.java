package cn.fighter3.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemInfo {
    private Integer pid; 
    private Integer pcid;
    private String pname;
    private Integer pcon;
    private String type;
    private String des;
    private Float price;
    private String item;
    private Integer rcon;
}
