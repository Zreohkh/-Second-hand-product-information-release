package cn.fighter3.service.impl;

import cn.fighter3.common.JwtUtil;
import cn.fighter3.dto.LoginDTO;
import cn.fighter3.dto.registerDTO;
import cn.fighter3.entity.Admin;
import cn.fighter3.entity.Customer;
import cn.fighter3.mapper.customerMapper;
import cn.fighter3.mapper.adminMapper;
import cn.fighter3.result.Result;
import cn.fighter3.service.LoginService;
import cn.fighter3.vo.LoginVO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private customerMapper customerMapper;
    @Autowired
    private adminMapper adminMapper;
    @Override
    public Result login(LoginDTO loginDTO) {
        if (StringUtils.isEmpty(loginDTO.getLoginName())){
            return new Result(400,"账号不能为空",null);
        }
        if (StringUtils.isEmpty(loginDTO.getPassword())){
            return new Result(400,"密码不能为空",null);
        }
        //通过登录名查询用户
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("cname", loginDTO.getLoginName());
        Customer uer=customerMapper.selectOne(wrapper);
        //比较密码
        if (uer!=null&&uer.getCpass().equals(loginDTO.getPassword())){
            LoginVO loginVO=new LoginVO();
            loginVO.setId(uer.getCid());
            loginVO.setToken(JwtUtil.createToken(uer));
            loginVO.setUser(uer);
            return new Result(200,"登陆成功",loginVO);
        }
        return new Result(401,"登录失败",null);
    }
    @Override
    public Result register(registerDTO registerDTO)
    {
        //用户名查重
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("cname", registerDTO.getName());
        int count=customerMapper.selectCount(wrapper);
        if(count>0) return new Result(400, "用户名已存在", null);
        //插入用户表
        Customer c = new Customer();
        c.setCname(registerDTO.getName());
        c.setCpass(registerDTO.getPassword());
        c.setEmail(registerDTO.getEmail());
        int i = customerMapper.insert(c);
        //生成token，封装对象返回
        if(i>0)
        {
            Customer uer=customerMapper.selectOne(wrapper);
            LoginVO loginVO=new LoginVO();
            loginVO.setId(uer.getCid());
            loginVO.setToken(JwtUtil.createToken(uer));
            loginVO.setUser(uer);
            return new Result(200,"注册成功",loginVO);
        }
        return new Result(401,"注册失败",null);
    }


    @Override
    public Result adminLogin(LoginDTO loginDTO) {
       if (StringUtils.isEmpty(loginDTO.getLoginName())){
            return new Result(400,"账号不能为空",null);
        }
        if (StringUtils.isEmpty(loginDTO.getPassword())){
            return new Result(400,"密码不能为空",null);
        }
        //通过登录名查询用户
        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("cname", loginDTO.getLoginName());
        Admin uer=adminMapper.selectOne(wrapper);
        //比较密码
        if (uer!=null&&uer.getCpass().equals(loginDTO.getPassword())){
            LoginVO loginVO=new LoginVO();
            loginVO.setId(uer.getCid());
            loginVO.setToken(JwtUtil.createTokenA(uer));
            return new Result(200,"登陆成功",loginVO);
        }
        return new Result(401,"登录失败",null);
    }
}
