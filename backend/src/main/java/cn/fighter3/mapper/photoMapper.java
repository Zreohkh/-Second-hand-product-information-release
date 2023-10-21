package cn.fighter3.mapper;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import cn.fighter3.entity.Photo;
@Repository
public interface photoMapper extends BaseMapper<Photo>{
    void insertPhoto(Integer ppid,String name,String url);
    void deletePhoto(String url);
}
