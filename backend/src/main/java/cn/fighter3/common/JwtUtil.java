package cn.fighter3.common;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import cn.fighter3.entity.Admin;
import cn.fighter3.entity.Customer;
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    /**
     * 秘钥
     */
    private static final String SECRET = "my_secret";

    /**
     * 过期时间
     **/
    private static final long EXPIRATION = 18000L;//单位为秒

    /**
     * 生成用户token,设置token超时时间
     */
    public  static String createToken(Customer user){
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token= JWT.create()
                .withHeader(map)                //添加头部
                //可以把数据存在claim中
                .withClaim("id",user.getCid())      //userId
                .withClaim("name",user.getCname())
                .withExpiresAt(expireDate)          //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }

    public  static String createTokenA(Admin user){
        //过期时间
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token= JWT.create()
                .withHeader(map)                //添加头部
                //可以把数据存在claim中
                .withClaim("id",user.getCid())      //userId
                .withClaim("name",user.getCname())
                .withExpiresAt(expireDate)          //超时设置,设置过期的日期
                .withIssuedAt(new Date()) //签发时间
                .sign(Algorithm.HMAC256(SECRET)); //SECRET加密
        return token;
    }

    /**
     * 检验token并解析token
     */
    public static Map<String, Claim> verifyToken(String token){
        DecodedJWT jwt=null;
        try {
            JWTVerifier verifier=JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt=verifier.verify(token);
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error("解析编码异常");
        }
        if(jwt!=null)
        {
            return jwt.getClaims();
        }
        return null;
    }
}