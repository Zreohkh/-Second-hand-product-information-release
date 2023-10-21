package cn.fighter3.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.fighter3.result.Result;
import cn.fighter3.service.PhotoService;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {
    @Autowired 
    private PhotoService photoService;
    @PostMapping("/list")
    public Result photoList(@RequestBody Map<String, Integer> requestBody){
        Integer id = requestBody.get("ppid");
        return new Result(200,"",photoService.photoList(id));
    }
    @PostMapping("/uploadPhoto")
    public Result uploadPhoto(@RequestParam("file") MultipartFile file,@RequestParam("ppid") String ppid) throws IllegalStateException, IOException{
        return new Result(200,"",photoService.uploadPhoto(file,ppid));
    }
    @PostMapping("/delete")
    public Result deletePhoto(@RequestBody Map<String, String> requestBody){
        photoService.deletePhoto(requestBody.get("url"));
        return new Result(200,"删除图片",null);
    }
}
