package com.khalil.applijava.dao;

import com.khalil.applijava.entities.User;

import java.sql.ResultSet;

public class UserImpl implements IUser{
    private DBConnection db = new DBConnection();
    // Objet pour gérer la connexion à la base de données
    private ResultSet rs;
    // Objet pour stocker les résultats d'une requête SQL
    @Override
    public User seConnecter(String email, String password) {
        User user = null; // Initialise un objet User à null
        String sql = "SELECT * FROM user WHERE email =  ? AND password = ? ";
        try {
            //Initialise une requête préparée avec la connexion à la base de données
            db.initPrepar(sql);

            // Passe les valeurs des paramètres à la requête préparée
            db.getPstm().setString(1, email);
            db.getPstm().setString(2, password);

            // Exécute la requête et récupère les résultats dans ResultSet
            rs = db.executeSelect();
            if (rs.next()){
                // Crée un nouvel objet User et initialise ses propriétés avec les données de la base de données
                user = new User();
                user.setIdUser(rs.getInt("idUser"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));

            }

            // Ferme la connexion à la base de données
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace(); // Affiche les détails de l'exception en cas d'erreur
        }
        return user;
        // Renvoie l'objet User ou null si l'authentification a échoué
    }
}
