package com.khalil.applijava;

import com.khalil.applijava.dao.DBConnection;
import com.khalil.applijava.dao.IUser;
import com.khalil.applijava.dao.UserImpl;
import com.khalil.applijava.entities.User;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       Parent parent = FXMLLoader.load(getClass().getResource("/pages/login.fxml"));
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setTitle("Page de connection");
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}
