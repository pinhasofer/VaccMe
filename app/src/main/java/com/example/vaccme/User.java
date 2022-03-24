package com.example.vaccme;

public class User {
    private String email, username;
    //private String password; - no need because we dont want to save plain password
    boolean admin;

    public User() {
    }



    public User(String email, String username) {
        this.username = username;
        this.email = email;
        //this.password = password;
        this.admin = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public String getPassword() {
//        return password;
//    }

//    public void setPassword(String password) {
//        this.password = password;
//    }

    public boolean isAdmin() {
        return admin;
    }

    private void setAdmin(boolean Admin) {
        admin = Admin;
    }
}
