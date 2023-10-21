package cn.fighter3.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.fighter3.dto.idDTO;
import cn.fighter3.entity.Customer;
import cn.fighter3.mapper.customerMapper;
import cn.fighter3.result.Result;
import cn.fighter3.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private customerMapper customerMapper;

    @Override
    public Result CustomerSearch(idDTO idDTO) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("cid",idDTO.getId());
        Customer customer = customerMapper.selectOne(wrapper);
        return new Result(200,"", customer);
    }

    @Override
    public Result CustomerModify(Customer customer) {
        //用户名查重
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("cname", customer.getCname());
        int count=customerMapper.selectCount(wrapper);
        if(count>1) return new Result(400, "用户名已存在", null);
        int update = customerMapper.updateById(customer);
        if(update>0) return new Result(200, "上传成功", customer);
        return new Result(400, "上传失败", null);
    }
}
