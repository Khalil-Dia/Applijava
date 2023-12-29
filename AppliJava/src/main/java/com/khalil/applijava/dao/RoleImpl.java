package com.khalil.applijava.dao;
import com.khalil.applijava.entities.Role;
import com.khalil.applijava.dao.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

public class RoleImpl implements IRole{
    private DBConnection db = new DBConnection();

    @Override
    public Role getRoleById(int roleId) {
        Role roles = null;
        // Initialise un objet Role à null
        String query = "SELECT * FROM role WHERE id = ?";

        try (Connection connection = db.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            // Passe la valeur du paramètre à la requête préparée
            statement.setInt(1, roleId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupère les données de la base de données et initialise l'objet Role
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    roles = new Role(id, name);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return roles;  // Renvoie la liste d'objets Role
    }

    @Override
    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();// Initialise une liste vide d'objets Role
        String query = "SELECT * FROM role";

        try (Connection connection = db.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Parcourt tous les résultats du ResultSet
            while (resultSet.next()) {
                // Récupère les données de la base de données et ajoute un nouvel objet Role à la liste
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                roles.add(new Role(id, name));
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return roles;  // Renvoie la liste d'objets Role
    }
}
