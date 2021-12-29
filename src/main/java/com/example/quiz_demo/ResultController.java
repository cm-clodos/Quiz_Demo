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

public class ResultController implements Initializable {
    @FXML
    private Label labelYourPoints;
    @FXML
    private Button btnBack;
   @FXML
    private Label labelResults;
    @FXML
    private Label labelPoints;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showPoints();

    }

    @FXML
    public void backToStart(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Start-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setTitle("Quiz Demo!");
            stage.setScene(scene);
            stage.show();
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }




    }

    public void showPoints(){
        int points = QuizController.points;
        int maxPoints = QuizController.maxPoints;

        labelPoints.setText(points + "/" + maxPoints);

    }


}

