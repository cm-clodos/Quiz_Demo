module com.example.quiz_demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.quiz_demo to javafx.fxml;
    exports com.example.quiz_demo;
    exports com.example.quiz_demo.Database;
    opens com.example.quiz_demo.Database to javafx.fxml;
}