package cn.fighter3.controller;

import cn.fighter3.dto.LoginDTO;
import cn.fighter3.dto.registerDTO;
import cn.fighter3.result.Result;
import cn.fighter3.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class LoginController {
    @Autowired
    LoginService loginService;

    @PostMapping(value = "/login")
    public Result login(@RequestBody LoginDTO loginDTO){
      return loginService.login(loginDTO);
    }
    @PostMapping(value = "/adminLogin")
    public Result adminLogin(@RequestBody LoginDTO loginDTO){
      return loginService.adminLogin(loginDTO);
    }
    @PostMapping(value = "/register")
    public Result login(@RequestBody registerDTO registerDTO){
      return loginService.register(registerDTO);
    }

}
