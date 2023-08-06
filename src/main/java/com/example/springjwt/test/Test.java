package com.example.springjwt.test;

public class Test {
    public static void main(String[] args) {
//        Jwtdome jwtdome = new Jwtdome();
//        String token = jwtdome.createToken("1", "jk", "admin", "f");
//        System.out.println(token);
//        System.out.println(jwtdome.verifyToken(token));
//        System.out.println(jwtdome.getTokenRes(token));
        JwtTests jwtTests = new JwtTests();
        jwtTests.parseJwt(jwtTests.createJwt());



    }
}
