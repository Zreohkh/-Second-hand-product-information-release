package cn.fighter3.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.fighter3.entity.Item;
import cn.fighter3.entity.ItemInfo;
@Repository
public interface searchMapper extends BaseMapper<Item> {
    Integer countProducts(String keyword,Integer pcid);
    List<Integer> PidList(String keyword,Integer pcid,Integer pageSize,Integer num);
    ItemInfo getItemInfo(Integer pid);
    List<String> getSrcList(Integer pid);
}
