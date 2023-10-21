package cn.fighter3.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fighter3.result.Result;
import cn.fighter3.service.AdminService;

@RestController()
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @PostMapping(value = "/customerList")
    public Result customerList(@RequestBody Map<String, String> map){
      return new Result(200,"",adminService.getCustomerPage(map));
    }
    @PostMapping(value = "/productList")
    public Result productList(@RequestBody Map<String, String> map){
      return new Result(200,"",adminService.getProductPage(map));
    }

    @PostMapping(value = "/review")
    public Result review(@RequestBody Map<String, String> map){
      return new Result(200,"",adminService.review(map));
    }

    @PostMapping(value = "/delete")
    public Result delete(@RequestBody Map<String, String> map){
      adminService.delete(map);
      return new Result(200,"",null);
    }






}
