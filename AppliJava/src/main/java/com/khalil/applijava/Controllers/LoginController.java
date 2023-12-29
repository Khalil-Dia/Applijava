package com.khalil.applijava.Controllers;

import com.khalil.applijava.dao.IUser;
import com.khalil.applijava.dao.UserImpl;
import com.khalil.applijava.entities.User;
import com.khalil.applijava.tools.Notification;
import com.khalil.applijava.tools.Outils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button connexionBtn;

    @FXML
    private TextField emailTfd;

    @FXML
    private PasswordField passwordTfd;

    IUser userDao = new UserImpl();

    @FXML
    void getLogin(ActionEvent event) {
        String email = emailTfd.getText();
        String password = passwordTfd.getText();
        if (email.equals("") || password.equals("")){
            Notification.NotifError("Erreur", "Tous les champs sont obligatoire !");
        }else {
            try {
                User user = userDao.seConnecter(email, password);
                if (user != null){
                   Notification.NotifSuccess("Succes","Connexion Reussie !");
                    Outils.load(event,"CRUD Serveur", "/pages/serveur.fxml");
                }
                else{
                   Notification.NotifError("Erreur","Email et/ou mot de pasee incorects !");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
