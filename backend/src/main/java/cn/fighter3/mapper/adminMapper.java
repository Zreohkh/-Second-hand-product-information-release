package cn.fighter3.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.fighter3.entity.Admin;
import cn.fighter3.entity.Customer;
import cn.fighter3.entity.ItemInfo;
import cn.fighter3.entity.Trade;

@Repository
public interface adminMapper extends BaseMapper<Admin> {
    List<Customer> selecCustomer(String keyword,Integer rcon);

    List<Integer> PidList(String keyword,Integer rcon);
    ItemInfo getItemInfo(Integer pid);
    List<String> getSrcList(Integer pid);

    List<Trade> selectTrade();

    Integer updateProductRcon(Integer id,Integer rcon);
    Integer updateCustomerRcon(Integer id,Integer rcon);

    void deleteProduct(Integer id);
    void deletePTrade(Integer id);
     
}