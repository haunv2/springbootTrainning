package com.jwtConfig.jwtModel;

import java.io.Serializable;

public class JwtRespone implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private String jwttoken = "";

    public JwtRespone(String token) {
        jwttoken = token;
    }

    public String getToken() {
        return this.jwttoken;
    }
}
