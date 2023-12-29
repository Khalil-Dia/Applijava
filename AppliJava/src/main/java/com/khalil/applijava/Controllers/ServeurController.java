package com.khalil.applijava.Controllers;

import com.khalil.applijava.dao.DBConnection;
import com.khalil.applijava.dao.IRole;
import com.khalil.applijava.dao.RoleImpl;
import com.khalil.applijava.entities.Role;
import com.khalil.applijava.entities.User;
import com.khalil.applijava.tools.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;

import java.net.URL;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;

public class ServeurController implements Initializable {

    @FXML
    private TableColumn<User, String> adressCol;

    @FXML
    private TextField adressTfb;

    @FXML
    private TableColumn<User, String> emailCol;

    @FXML
    private TextField emailTfb;

    @FXML
    private TableColumn<User, Integer> idCol;

    @FXML
    private TableColumn<User, String> passwordCol;

    @FXML
    private TextField passwordTfd;

    @FXML
    private TableColumn<User, Role> roleCol;

    @FXML
    private ChoiceBox<Role> roleSelec;

    @FXML
    private TableView<User> serveurTb;

    @FXML
    private Button EffacerBtn;

    @FXML
    private Button enregistrerBtn;

    @FXML
    private Button modifierbtn;

    @FXML
    private Button supprimerBtn;

    private int idUs;

    // Utiliser la classe RroleImpl pour récupérer les rôles
    private IRole roleImpl = new RoleImpl();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadRoles();
        loabTable();
    }
    private DBConnection db = new DBConnection();
    public ObservableList<User> getUser() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM user ORDER BY idUser ASC";
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();
            while (rs.next()) {
                int roleId = rs.getInt("role_id");

                // Utilisez la méthode getRoleById de votre RoleImpl pour obtenir le vrai nom du rôle
                Role role = roleImpl.getRoleById(roleId);

                User user = new User(
                        rs.getInt("idUser"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("adress"),
                        role
                );
                users.add(user);
            }
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    public void loabTable() {
        ObservableList<User> liste = getUser();
        serveurTb.setItems(liste);

        idCol.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("password"));
        adressCol.setCellValueFactory(new PropertyValueFactory<>("adress"));

        // Utilisez une Cell Factory pour afficher le nom du rôle
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

        roleCol.setCellFactory(column -> new TableCell<User, Role>() {
            @Override
            protected void updateItem(Role role, boolean empty) {
                super.updateItem(role, empty);

                if (role == null || empty) {
                    setText(null);
                } else {
                    setText(role.getName());
                }
            }
        });
    }
    private void loadRoles() {
        List<Role> roles = roleImpl.getAllRoles();
        ObservableList<Role> roleList = FXCollections.observableArrayList(roles);
        roleSelec.setItems(roleList);

        // Définir le convertisseur pour afficher le nom du rôle dans le ChoiceBox
        roleSelec.setConverter(new StringConverter<Role>() {
            @Override
            public String toString(Role role) {
                return (role == null) ? null : role.getName();
            }

            @Override
            public Role fromString(String string) {
                // Vous n'avez pas besoin de cette méthode pour l'affichage
                return null;
            }
        });
    }


    @FXML
    void clear(ActionEvent event) {
        clearFields();
    }

    @FXML
    void delete(ActionEvent event) {
        String sql = "DELETE FROM  user WHERE idUser = ?";
        try {
            db.initPrepar(sql);

            db.getPstm().setInt(1, idUs);
            db.executeMaj();
            db.closeConnection();
            loabTable();  // Rafraîchir la table après l'ajout
            clearFields();
            enregistrerBtn.setDisable(false);
            Notification.NotifSuccess("Succes","Utilisateur supprimer !" );
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    void save(ActionEvent event) {
        try {
            Role selectedRole = roleSelec.getValue();

            if (selectedRole != null) {
                System.out.println("Selected Role ID: " + selectedRole.getId());

                String sql = "INSERT INTO user (email, password, adress, role_id) VALUES (?, ?, ?, ?)";
                db.initPrepar(sql);
                db.getPstm().setString(1, emailTfb.getText());
                db.getPstm().setString(2, passwordTfd.getText());
                db.getPstm().setString(3, adressTfb.getText());
                db.getPstm().setInt(4, selectedRole.getId());
                db.executeMaj();
                db.closeConnection();
                loabTable();  // Rafraîchir la table après l'ajout
                clearFields();
                Notification.NotifSuccess("Succes","Utilisateur enregistrer !" );

                // Vérifier si les données sont correctement mises à jour dans la TableView
                serveurTb.getItems().forEach(user -> System.out.println(user.getRole().getName()));
            } else {
                System.out.println("Selected Role is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearFields() {
        emailTfb.setText("");
        passwordTfd.setText("");
        adressTfb.setText("");
        roleSelec.setAccessibleText("");
    }

    @FXML
    void update(ActionEvent event) {
        String sql = "UPDATE user SET email = ?, password = ?, adress = ?, role_id = ?  WHERE idUser = ?";
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1, emailTfb.getText());
            db.getPstm().setString(2, passwordTfd.getText());
            db.getPstm().setString(3, adressTfb.getText());

            // Utilisez getValue() pour obtenir l'objet Role sélectionné
            Role selectedRole = roleSelec.getValue();
            if (selectedRole != null) {
                db.getPstm().setInt(4, selectedRole.getId());
            } else {
                // Gérez le cas où aucun rôle n'est sélectionné
                db.getPstm().setInt(4, 0); // Ou une valeur par défaut appropriée
            }

            db.getPstm().setInt(5, idUs);
            db.executeMaj();
            db.closeConnection();
            loabTable();  // Rafraîchir la table après l'ajout
            clearFields();
            enregistrerBtn.setDisable(false);
            Notification.NotifSuccess("Succes","Utilisateur modifier!" );
        } catch (Exception e) {
            e.printStackTrace(); // À des fins de débogage ; vous pouvez gérer les exceptions de manière appropriée
            // Gérer l'exception correctement, ne pas simplement la jeter à nouveau
        }
    }


    @FXML
    void getData(MouseEvent event) {
        User user = serveurTb.getSelectionModel().getSelectedItem();
        idUs = user.getIdUser();
        emailTfb.setText(user.getEmail());
        passwordTfd.setText(user.getPassword());
        adressTfb.setText(user.getAdress());
        // Sélectionnez le rôle dans la ChoiceBox
        roleSelec.setValue(user.getRole());
        enregistrerBtn.setDisable(true);
    }
}
