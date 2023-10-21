package cn.fighter3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.result.Result;
import cn.fighter3.service.SearchService;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 分页查询
     * @param queryDTO
     * @return
     */
    @PostMapping("/list")
    public Result searchList(@RequestBody QueryDTO queryDTO){
        return new Result(200,"",searchService.selectProductPage(queryDTO));
    }


}
