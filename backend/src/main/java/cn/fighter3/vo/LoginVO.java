package cn.fighter3.vo;

import cn.fighter3.entity.Customer;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoginVO implements Serializable {
    private Integer id;
    private String token;
    private Customer user;
}
