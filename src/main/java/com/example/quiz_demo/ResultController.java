package com.example.quiz_demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ResultController {
    @FXML
    private Label labelYourPoints;
    @FXML
    private Button btnBack;
   @FXML
    private Label labelResults;
    @FXML
    private Label labelPoints;

    @FXML
    public void backToStart(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Start-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Quiz Demo!");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }



    }
    }

