package com.example.njlchat.model;

/***
 * a user is a person that uses the application's fonctionnalities
 * a user has an account which consist of a username and a password stored in the application database
 * a user can create and upload post on the application
 */
public class User {
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
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
