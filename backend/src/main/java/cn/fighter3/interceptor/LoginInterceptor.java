package cn.fighter3.interceptor;

import cn.fighter3.common.JwtUtil;
import cn.fighter3.result.Result;
import cn.fighter3.util.HttpContextUtil;

import com.auth0.jwt.interfaces.Claim;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @Author: 三分恶
 * @Date: 2021/1/18
 * @Description: 用户登录拦截器
 **/

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {

        //从header中获取token
        String token = request.getHeader("token");
        //如果token为空
        if (StringUtils.isBlank(token)) {
            setReturn(response,400,"用户未登录，请先登录");
            return false;
        }
        Map<String, Claim> userData = JwtUtil.verifyToken(token);
        if (userData==null){
            setReturn(response,400,"登陆失效，请重新登录");
            return false;
        }
        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

    //返回json格式错误信息
    private static void setReturn(HttpServletResponse response, Integer code, String msg) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
        //UTF-8编码
        httpResponse.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=utf-8");
        Result result = new Result(code,msg,"");
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(result);
        httpResponse.getWriter().print(json);
    }

}
