package cn.fighter3.service;
import cn.fighter3.dto.idDTO;
import cn.fighter3.entity.Customer;
import cn.fighter3.result.Result;
public interface CustomerService {
    // 查询用户信息
    public Result CustomerSearch(idDTO idDTO);
    //修改用户信息
    public Result CustomerModify(Customer customer);
}
