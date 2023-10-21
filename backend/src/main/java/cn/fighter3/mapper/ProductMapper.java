package cn.fighter3.mapper;

import cn.fighter3.entity.Product;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

@Repository
public interface ProductMapper extends BaseMapper<Product> {
    List<Product> selectProductPage(String keyword,Integer pcid);
    Integer countProducts(String keyword,Integer pcid);
    Integer updatePcon(Integer id);
    Integer updatePconBatch(List<Integer> ids);
    Integer updateRcon(Integer id);
}
