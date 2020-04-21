package com.mustafa.picaword;

public class Users {
    String email, password, username;

    Users(String email, String username, String pw){
        this.email=email;
        this.username=username;
        this.password=pw;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
