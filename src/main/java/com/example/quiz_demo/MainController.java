package com.example.quiz_demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button btnStart;

    @FXML
    public void SceneSwitchtoQuiz(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Quiz-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = new Stage();
            stage.setTitle("Quiz Demo!");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }



    }

}