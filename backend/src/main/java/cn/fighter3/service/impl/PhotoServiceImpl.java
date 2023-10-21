package cn.fighter3.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import cn.fighter3.entity.Photo;
import cn.fighter3.mapper.ProductMapper;
import cn.fighter3.mapper.photoMapper;
import cn.fighter3.service.PhotoService;

@Service
@Transactional
public class PhotoServiceImpl implements PhotoService{
    @Autowired
    private photoMapper photoMapper;
    @Autowired
    private ProductMapper productMapper;
    @Value("${upload.path}") // 从配置文件中获取文件上传路径
    private String uploadPath;
    @Value("${upload.uri}") // 从配置文件中获取URi
    private String uploadUri;
    @Override
    public List<Photo> photoList(Integer id) {
        QueryWrapper<Photo> wrapper = new QueryWrapper<>();
        wrapper.eq("ppid",id);
        return photoMapper.selectList(wrapper);
    }

    @Override
    public String uploadPhoto(MultipartFile file,String ppid) throws IllegalStateException, IOException {
        String originalFilename = file.getOriginalFilename();
        if(originalFilename==null) return null;
        int lastIndex = originalFilename.lastIndexOf(".");
        String type = originalFilename.substring(lastIndex + 1);
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String filePath = uploadPath + uuid + "." + type;
        String url = uploadUri + uuid + "." + type;
        System.out.println(filePath);
        File dest = new File(filePath);
        file.transferTo(dest);
        photoMapper.insertPhoto(Integer.parseInt(ppid), originalFilename, url);
        productMapper.updateRcon(Integer.parseInt(ppid));
        return url;
    }

    @Override
    public void deletePhoto(String url) {
        photoMapper.deletePhoto(url);
    }
}
