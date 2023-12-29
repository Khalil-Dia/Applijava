package com.khalil.applijava.dao;

import com.khalil.applijava.entities.Role;

import java.util.List;

public interface IRole {
    List<Role> getAllRoles();
    Role getRoleById(int roleId);
    // Nouvelle méthode pour récupérer un rôle par ID
}
