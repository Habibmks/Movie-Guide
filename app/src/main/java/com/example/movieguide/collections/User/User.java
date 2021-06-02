package com.example.movieguide.collections.User;

import java.io.Serializable;

public class User implements Serializable {
    String mail,password,name,surname,username;

    public User() {
    }

    public User(String mail, String password, String name, String surname, String username) {
        this.mail = mail;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
