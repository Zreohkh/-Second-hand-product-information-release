package cn.fighter3.service.impl;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.entity.Product;
import cn.fighter3.mapper.ProductMapper;
import cn.fighter3.service.ProductService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;

    @Override
    public IPage<Product> selectProductPage(QueryDTO queryDTO) {
        String keyword = queryDTO.getKeyword();
        Integer pcid = queryDTO.getPcid();
        Integer pageNo = queryDTO.getPageNo();
        Integer pageSize = queryDTO.getPageSize();

        IPage<Product> page = new Page<>(pageNo, pageSize);    

        // 查询数据
        List<Product> productList = productMapper.selectProductPage(keyword, pcid);
        if(productList==null||productList.size()==0) {page.setTotal(0); return page;}
        // 设置总记录数
        page.setTotal(productList.size());

        // 设置查询结果
        page.setRecords(productList.subList((pageNo-1)*pageSize, pageNo*pageSize>productList.size()?productList.size():pageNo*pageSize));
    
        return page;
    }

    @Override
    public Integer addProduct(Product product) {
        return productMapper.insert(product);
    }

    @Override
    public Integer updateProduct(Product product) {
        product.setRcon(0);
        return productMapper.updateById(product);
    }

    @Override
    public Integer deleteProduct(Integer id) {
    return productMapper.updatePcon(id);
    }

    @Override
    public Integer batchDelete(List<Integer> ids) {
    return productMapper.updatePconBatch(ids);
    }

}
