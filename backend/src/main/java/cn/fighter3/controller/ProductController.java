package cn.fighter3.controller;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.entity.Product;
import cn.fighter3.result.Result;
import cn.fighter3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    @PostMapping("/list")
    public Result productList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",productService.selectProductPage(queryDTO));
    }

    /**
     * 添加
     * @param user
     * @return
     */
    @PostMapping("/add")
    public Result addProduct(@RequestBody Product product){
        return new Result(200,"",productService.addProduct(product));
    }

    /**
     * 更新
     * @param user
     * @return
     */
    @PostMapping("/update")
    public Result updateProduct(@RequestBody Product product){
        return new Result(200,"",productService.updateProduct(product));
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public Result deleteProduct(Integer id){
        return new Result(200,"",productService.deleteProduct(id));
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @PostMapping("/delete/batch")
    public Result batchDeleteProduct(@RequestBody List<Integer> ids){
        return new Result(200,"",productService.batchDelete(ids));
    }
}
