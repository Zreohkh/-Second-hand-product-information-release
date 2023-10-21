package cn.fighter3.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.entity.Item;

public interface SearchService {
        IPage<Item> selectProductPage(QueryDTO queryDTO);
}
