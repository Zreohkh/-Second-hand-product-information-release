package cn.fighter3.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.fighter3.entity.Customer;
import cn.fighter3.entity.Image;
import cn.fighter3.entity.Item;
import cn.fighter3.entity.ItemInfo;
import cn.fighter3.mapper.adminMapper;
import cn.fighter3.service.AdminService;

@Service
@Transactional
public class AdminServiceImpl implements AdminService{
    @Autowired
    adminMapper adminmapper;

    @Override  
    public IPage<Customer> getCustomerPage(Map<String, String> map) {
        String keyword = map.get("keyword");
        Integer pageNo = Integer.parseInt(map.get("pageNo"));
        Integer pageSize = Integer.parseInt(map.get("pageSize"));
        Integer rcon = Integer.parseInt(map.get("type"));

        IPage<Customer> page = new Page<>(pageNo, pageSize);   
        List<Customer> list = adminmapper.selecCustomer(keyword, rcon);

        if(list==null||list.size()==0) {page.setTotal(0); return page;}
        // 设置总记录数
        page.setTotal(list.size());
        // 设置查询结果
        page.setRecords(list.subList((pageNo-1)*pageSize, pageNo*pageSize>list.size()?list.size():pageNo*pageSize));
        return page;
    }


    @Override  
    public IPage<Item> getProductPage(Map<String, String> map) {
        String keyword = map.get("keyword");
        Integer pageNo = Integer.parseInt(map.get("pageNo"));
        Integer pageSize = Integer.parseInt(map.get("pageSize"));
        Integer rcon = Integer.parseInt(map.get("type"));

        IPage<Item> page = new Page<>(pageNo, pageSize);   

        List<Integer> list = adminmapper.PidList(keyword, rcon);
        if(list==null||list.size()==0) {page.setTotal(0); return page;}
        // 查询数据
        List<Item> itemList = new ArrayList<>();
        for(Integer pid:list.subList((pageNo-1)*pageSize, pageNo*pageSize>list.size()?list.size():pageNo*pageSize))
        {
            Item i = new Item();
            ItemInfo itemInfo = adminmapper.getItemInfo(pid);
            List<String> srcList = adminmapper.getSrcList(pid);
            Image image = new Image();
            if(srcList!=null&&srcList.size()!=0){
                image.setUrl(srcList.get(0));
                image.setSrcList(srcList.toArray(new String[0]));
            }
            i.setItemInfo(itemInfo);
            i.setImage(image);
            itemList.add(i);
        }
        if(list==null||list.size()==0) {page.setTotal(0); return page;}
        page.setTotal(list.size());

        // 设置查询结果
        page.setRecords(itemList);
    
        return page;
    }



    @Override
    public Integer review(Map<String, String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        Integer rcon = Integer.parseInt(map.get("rcon"));
        String tableName = map.get("tableName");
        Integer num =0;
        switch(tableName){
            case "customer" : num = adminmapper.updateCustomerRcon(id, rcon);
            case "product" : num = adminmapper.updateProductRcon(id, rcon);
        }
        return num;
    }

    @Override
    public void delete(Map<String, String> map) {
        Integer id = Integer.parseInt(map.get("id"));
        adminmapper.deletePTrade(id);
        adminmapper.deleteProduct(id);
        
    }

}
