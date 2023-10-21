package cn.fighter3.mapper;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import cn.fighter3.entity.Customer;

@Repository
public interface customerMapper extends BaseMapper<Customer> {
}