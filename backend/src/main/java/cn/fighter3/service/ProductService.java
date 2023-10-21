package cn.fighter3.service;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.entity.Product;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;


public interface ProductService {

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    IPage<Product> selectProductPage(QueryDTO queryDTO);

    Integer addProduct(Product product);

    Integer updateProduct(Product product);

    Integer deleteProduct(Integer id);

    Integer batchDelete(List<Integer> ids);
}
