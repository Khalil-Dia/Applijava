module com.khalil.applijava {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires TrayTester;


    opens com.khalil.applijava to javafx.fxml;
    exports com.khalil.applijava;
    // Expositions des controllers
    opens com.khalil.applijava.Controllers to javafx.fxml;
    exports com.khalil.applijava.Controllers;
    opens com.khalil.applijava.entities to javafx.fxml;
    exports com.khalil.applijava.entities;
    exports com.khalil.applijava.dao;
    opens com.khalil.applijava.dao to javafx.fxml;
}