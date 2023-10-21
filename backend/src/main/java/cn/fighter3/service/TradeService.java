package cn.fighter3.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.dto.applyDTO;
import cn.fighter3.entity.ApplyItem;

public interface TradeService {
        String insertTrade(applyDTO applyDTO);

        IPage<ApplyItem> selectAppliedPage(QueryDTO queryDTO);

        IPage<ApplyItem> selectApplyPage(QueryDTO queryDTO);

        Integer deleteTrade(Integer id);

        Integer batchDelete(List<Integer> ids);
}
