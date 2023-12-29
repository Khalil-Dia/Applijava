package com.khalil.applijava.dao;

import com.khalil.applijava.entities.User;

public interface IUser {
    public User seConnecter(String email, String password);
}
