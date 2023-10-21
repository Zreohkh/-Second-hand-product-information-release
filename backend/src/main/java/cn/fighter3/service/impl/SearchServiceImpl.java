package cn.fighter3.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.fighter3.dto.QueryDTO;
import cn.fighter3.entity.Image;
import cn.fighter3.entity.Item;
import cn.fighter3.entity.ItemInfo;
import cn.fighter3.mapper.searchMapper;
import cn.fighter3.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private searchMapper searchMapper;
    @Override
    public IPage<Item> selectProductPage(QueryDTO queryDTO) {
        String keyword = queryDTO.getKeyword();
        Integer pcid = queryDTO.getPcid();
        Integer pageNo = queryDTO.getPageNo();
        Integer pageSize = queryDTO.getPageSize();

        IPage<Item> page = new Page<>(pageNo, pageSize);
            
        // 查询记录总数
        int total = searchMapper.countProducts(keyword,pcid);

        // 设置总记录数
        page.setTotal(total);
            
        // 查询数据
        List<Item> itemList = new ArrayList<>();
        
        for(Integer pid:searchMapper.PidList(keyword,pcid,pageSize,(pageNo-1)*pageSize))
        {
            Item i = new Item();
            ItemInfo itemInfo = searchMapper.getItemInfo(pid);
            List<String> srcList = searchMapper.getSrcList(pid);
            Image image = new Image();
            if(srcList!=null&&srcList.size()!=0){
                image.setUrl(srcList.get(0));
                image.setSrcList(srcList.toArray(new String[0]));
            }
            i.setItemInfo(itemInfo);
            i.setImage(image);
            itemList.add(i);
        }
        
        // 设置查询结果
        page.setRecords(itemList);

        return page;
    }
    
}
