package cn.fighter3.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cn.fighter3.entity.Photo;

public interface PhotoService {
    public List<Photo> photoList(Integer id);
    public String uploadPhoto(MultipartFile file,String ppid) throws IllegalStateException, IOException;
    public void deletePhoto(String url);
}
