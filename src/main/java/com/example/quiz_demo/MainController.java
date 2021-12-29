package com.example.quiz_demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {

    @FXML
    private Button btnStart;

    @FXML
    public void SceneSwitchtoQuiz(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Quiz-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = (Stage) btnStart.getScene().getWindow();
            stage.setTitle("Quiz Demo!");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }



    }



}