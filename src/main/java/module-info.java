module com.empresa.javafx_mongo {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;


    opens com.empresa.javafx_mongo to javafx.fxml;
    exports com.empresa.javafx_mongo;
    exports com.empresa.javafx_mongo.controller;
    opens com.empresa.javafx_mongo.controller to javafx.fxml;
    exports com.empresa.javafx_mongo.model;
    opens com.empresa.javafx_mongo.model to javafx.fxml;
}