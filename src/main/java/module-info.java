module com.example.quiz_demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.quiz_demo to javafx.fxml;
    exports com.example.quiz_demo;
}