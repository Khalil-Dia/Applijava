package com.khalil.applijava.entities;

public class User {
    private int idUser;
    private String email ;
    private String password;
    private String adress;
    private Role role;

    public User() {
    }

    public User(int idUser, String email, String password, String adress, Role role) {
        this.idUser = idUser;
        this.email = email;
        this.password = password;
        this.adress = adress;
        this.role = role;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
