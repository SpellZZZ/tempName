package com.example.demo.dto;


import java.io.Serializable;

public class AuthRequest implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;

    private String username;
    private String password;






    public AuthRequest() {
    }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}