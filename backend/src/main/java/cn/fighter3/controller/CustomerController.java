package cn.fighter3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.fighter3.dto.idDTO;
import cn.fighter3.entity.Customer;
import cn.fighter3.result.Result;
import cn.fighter3.service.CustomerService;

@RestController()
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/customer")
    public Result login(@RequestBody idDTO idDTO){
       return customerService.CustomerSearch(idDTO);
    }
    @PostMapping(value = "/update_customer")
    public Result login(@RequestBody Customer customer){
      return customerService.CustomerModify(customer);
    }





}
