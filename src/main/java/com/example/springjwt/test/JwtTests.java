package com.example.springjwt.test;

import io.jsonwebtoken.*;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

public class JwtTests {
    private long time=1000 * 60 * 60 * 1;
    private String sign="admin";
    @Test
    public String createJwt()
    {
        //创建一个JwtBuilder对象
        JwtBuilder jwtBuilder= Jwts.builder();
        //jwtToken -> abc.def.xyz
        String jwtToken =jwtBuilder
                //Hader:头部
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                //payload:载荷
                .claim("username","tom")
                .claim("role","admin")
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis()+time))//设置token过期时间
                .setId(UUID.randomUUID().toString())//id字段
                //Signatrue:签名
                .signWith(SignatureAlgorithm.HS256,sign)//设置加密算法和签名
                //使用“，”连接成一个完整的字符串
                .compact();
        return jwtToken;
    }
    //验证jwt
    @Test
    public Boolean checkJwt()
    {
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2ODk5NjM1NjQsImp0aSI6ImNmMjNjOGQ3LTg4ZjEtNDJiYi04NDg3LTM5YzE2ZjU5ZTM1ZiJ9.Gu_CqlQ2gVuxeQhwk9mjRxNXYQsPQ2vYymLNhUPiTsk
        //eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2ODk5NjM1NjQsImp0aSI6ImNmMjNjOGQ3LTg4ZjEtNDJiYi04NDg3LTM5YzE2ZjU5ZTM1ZiJ9.Gu_CqlQ2gVuxeQhwk9mjRxNXYQsPQ2vYymLNhUPiTsk
        String token="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InRvbSIsInJvbGUiOiJhZG1pbiIsInN1YiI6ImFkbWluLXRlc3QiLCJleHAiOjE2OTEyMjgyMzIsImp0aSI6IjBmMGI0NzU4LWE0OWYtNGNjZi1hODQ1LTJhYTI3ZGRhMTNkYyJ9.oEbXFQQ-DSZjcmxDmjlhkJjkJKT-l0UJMk6lznQrU44\n";
        boolean result = Jwts.parser().isSigned(token);
        System.out.println(result);
        return result;
    }
    //解析Jwt
    @Test
    public void parseJwt(String token)
    {
        JwtParser jwtparser = Jwts.parser();//获取jwt的解析对象
        //将jwt转化成key-value 类似于Map集合
        Jws<Claims> claimsJws = jwtparser.setSigningKey(sign).parseClaimsJws(token);
        Claims claims=claimsJws.getBody();
        System.out.println(claims.get("username"));
        System.out.println(claims.get("role"));
        System.out.println(claims.getId());
        System.out.println(claims.getSubject());
        System.out.println(claims.getExpiration());
    }
}
