package cn.fighter3.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.fighter3.entity.ApplyItem;
import cn.fighter3.entity.Customer;
import cn.fighter3.entity.Trade;
@Repository
public interface tradeMapper extends BaseMapper<Trade> {
    Integer countAppliedItem(String keyword,Integer pcid);
    List<ApplyItem> selectAppliedPage(String keyword,Integer pcid,Integer pageSize,Integer num);

    List<ApplyItem> selectApplyPage(String keyword,Integer pcid,Integer pageSize,Integer num);

    Integer updateTcon(Integer id);
    Integer updateTconBatch(List<Integer> ids);


    Integer countLines(Integer tfcid,Integer tpid);

    Integer countApplyItem(String keyword,Integer pcid);
    Customer getCusRcon(Integer id);
}
