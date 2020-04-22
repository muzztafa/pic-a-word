package com.mustafa.picaword;

public class Users {
    String email, username;

    Users(String email, String username){
        this.email=email;
        this.username=username;

    }

    Users (){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
