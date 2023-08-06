package com.example.springjwt.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenRes {
    private String uid;
    private String userimg;
    private String username;
    private String userbimg;
}
