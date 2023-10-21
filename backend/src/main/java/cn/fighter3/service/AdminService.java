package cn.fighter3.service;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.fighter3.entity.Customer;
import cn.fighter3.entity.Item;


public interface AdminService {
    // 查询
    IPage<Customer> getCustomerPage(Map<String, String> map);
    IPage<Item> getProductPage(Map<String, String> map);
    // 审核
    Integer review(Map<String, String> map);
    // 删除
    void delete(Map<String, String> map);

}
