package com.example.springjwt.test;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("Token")
public class Jwtdome {

    /**
     * toekn 密钥
     */
    private final String JWT_SECRET = "qiesiyv";//自己随便起个字符串
    /**
     * token 过期时间
     */
    private final int calendarField = Calendar.DATE;
    private final int calendarInterval = 30;

    private final Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);

    // header Map
    private final Map<String, Object> map = new HashMap<>();

    private JWTCreator.Builder createTokenBuilder() {
        /** 加密方式 **/
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        // 签发时间
        Date iaDate = new Date();
        // 设置过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);//前者为时间单位，后者时间数量
        Date expiresDate = nowTime.getTime();
        JWTCreator.Builder builder = JWT.create().withHeader(map)
                .withIssuedAt(iaDate)
                .withExpiresAt(expiresDate)
                .withClaim("iss", "qiesiyv")  //payload iss jwt的签发者 看着起名
                .withClaim("aud", "app");   //aud  接收的乙方	 看着起名
        return builder;
    }

    /**
     * 生成token
     *
     * @param
     * @return
     */

    public String createToken(String uid,String userimg,String username,String userbimg) {//token保存的数据
        JWTCreator.Builder builder = createTokenBuilder();
        String token = builder.withClaim("uid", uid)
                .withClaim("userimg", userimg)
                .withClaim("username", username)
                .withClaim("userbimg", userbimg)
                .sign(algorithm);
        return token;
    }


    // 解析
    DecodedJWT verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            //解析方式和密钥
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = JWT.decode(token);
            jwt = verifier.verify(decodedJWT);
        } catch (Exception e) {
            e.printStackTrace();
            // token 校检失败
        }
        return jwt;
    }


    /**
     * 解析token获取内容
     * @param token
     * @return
     */
    public TokenRes getTokenRes(String token) {
        DecodedJWT jwt = verifyToken(token);
        Map<String, Claim> claimMap = jwt.getClaims();
        String uid = claimMap.get("uid").asString();//赋值返回值
        String userimg = claimMap.get("userimg").asString();
        String username = claimMap.get("username").asString();
        String userbimg = claimMap.get("userbimg").asString();
        TokenRes res = new TokenRes(uid,userimg,username,userbimg);
        return res;
    }
}

