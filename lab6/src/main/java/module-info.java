module com.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lab6 to javafx.fxml;
    opens com.example.lab6.controllers to javafx.fxml;
    opens com.example.lab6.entities to javafx.fxml;
    opens com.example.lab6.algorithms to javafx.fxml;
    exports com.example.lab6;
    exports com.example.lab6.controllers;
    exports com.example.lab6.entities;
    exports com.example.lab6.algorithms;
}