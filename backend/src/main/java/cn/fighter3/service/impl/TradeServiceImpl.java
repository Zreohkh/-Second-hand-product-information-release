package cn.fighter3.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.dto.applyDTO;
import cn.fighter3.entity.ApplyItem;
import cn.fighter3.entity.Customer;
import cn.fighter3.entity.Trade;
import cn.fighter3.mapper.tradeMapper;
import cn.fighter3.service.TradeService;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    private tradeMapper tradeMapper;
    @Value("${apply.num}") // 从配置文件中获取对同一件商品的最大请求数量
    private Integer maxApply;
    @Override
    public String insertTrade(applyDTO applyDTO) {
        Integer tfcid = applyDTO.getTfcid();
        Integer tpid = applyDTO.getTpid();
        Customer cus = tradeMapper.getCusRcon(tfcid);
        if(cus.getRcon()!=1) return new String("个人信息有误");
        if(tradeMapper.countLines(tfcid, tpid)>=3) return new String("申请次数达上限，申请失败");
        Trade trade = new Trade();
        trade.setTfcid(tfcid);
        trade.setTpid(tpid);
        trade.setMessage(applyDTO.getMessage());
        tradeMapper.insert(trade);
        return new String("申请成功");
    }
    @Override
    public IPage<ApplyItem> selectAppliedPage(QueryDTO queryDTO) {
        String keyword = queryDTO.getKeyword();
        Integer pcid = queryDTO.getPcid();
        Integer pageNo = queryDTO.getPageNo();
        Integer pageSize = queryDTO.getPageSize();

        IPage<ApplyItem> page = new Page<>(pageNo, pageSize);
    
        // 查询记录总数
        int total = tradeMapper.countAppliedItem(keyword,pcid);
    
        // 设置总记录数
        page.setTotal(total);
        
        // 查询数据
        List<ApplyItem> ItemList = tradeMapper.selectAppliedPage(keyword, pcid,pageSize,(pageNo-1)*pageSize);

        // 设置查询结果
        page.setRecords(ItemList); 
        return page;
    }
    @Override
    public Integer deleteTrade(Integer id) {
    return tradeMapper.updateTcon(id);
    }

    @Override
    public Integer batchDelete(List<Integer> ids) {
    return tradeMapper.updateTconBatch(ids);
    }



    @Override
    public IPage<ApplyItem> selectApplyPage(QueryDTO queryDTO) {
        String keyword = queryDTO.getKeyword();
        Integer pcid = queryDTO.getPcid();
        Integer pageNo = queryDTO.getPageNo();
        Integer pageSize = queryDTO.getPageSize();

        IPage<ApplyItem> page = new Page<>(pageNo, pageSize);
    
        // 查询记录总数
        int total = tradeMapper.countApplyItem(keyword,pcid);
    
        // 设置总记录数
        page.setTotal(total);
        
        // 查询数据
        List<ApplyItem> ItemList = tradeMapper.selectApplyPage(keyword, pcid,pageSize,(pageNo-1)*pageSize);

        // 设置查询结果
        page.setRecords(ItemList); 
        return page;
    }
    
}
