package com.example.quiz_demo;

import com.example.quiz_demo.Database.DBConnection;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuizController implements Initializable {

    @FXML
    private Label answer3;
    @FXML
    private Label answer2;
    @FXML
    private Label answer4;

    @FXML
    private Label answer1;
    @FXML
    private Label labelQuestionCount;

    boolean choiceA1 = false;
    boolean choiceA2 = false;
    boolean choiceA3 = false;
    boolean choiceA4 = false;
    boolean btnCheckPressed = false;

    private ArrayList<Answer> answerList;
    @FXML
    private ArrayList<Question> questionList;
    @FXML
    private Button btnCheck;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DBConnection connection = new DBConnection();
        ArrayList<Question> questions = connection.getQuestionDB();
        getAnswerPackage(questions);

        questions.forEach(System.out::println);

        questions.get(0);


    }

    @FXML
    public void choice(MouseEvent event) {
        //Antwort 1
        if (event.getSource() == answer1 && !choiceA1) {
            answer1.setStyle("-fx-background-color: steelblue;-fx-text-fill:white;");
            choiceA1 = true;
        } else if (event.getSource() == answer1 && choiceA1) {
            answer1.setStyle(null);
            choiceA1 = false;
        }
        //Antwort 2
        else if (event.getSource() == answer2 && !choiceA2) {
            answer2.setStyle("-fx-background-color: steelblue;-fx-text-fill:white;");
            choiceA2 = true;
        } else if (event.getSource() == answer2 && choiceA2) {
            answer2.setStyle(null);
            choiceA2 = false;
        }
        //Antwort 3
        else if (event.getSource() == answer3 && !choiceA3) {
            answer3.setStyle("-fx-background-color: steelblue;-fx-text-fill:white;");
            choiceA3 = true;

        } else if (event.getSource() == answer3 && choiceA3) {
            answer3.setStyle(null);
            choiceA3 = false;
        }
        //Antwort 4
        else if (event.getSource() == answer4 && !choiceA4) {
            answer4.setStyle("-fx-background-color: steelblue;-fx-text-fill:white;");
            choiceA4 = true;
        } else if (event.getSource() == answer4 && choiceA4) {
            answer4.setStyle(null);
            choiceA4 = false;
        }


    }


    @FXML
    public void CheckAnswers(ActionEvent actionEvent) {

        if (actionEvent.getSource() == btnCheck && !btnCheckPressed) {
            // Check if answer true = answer in DB True
            btnCheckPressed = true;
            btnCheck.setText("Next!");
        } else if (actionEvent.getSource() == btnCheck && btnCheckPressed) {
            // show next question


        }
    }

    public void displayQuestion() {


    }


    public void displayAnswers() {


    }

    public void getAnswerPackage(ArrayList<Question> questionList) {

        int questionId = 1;
        DBConnection dbConnection = new DBConnection();

        for (Question question : questionList) {
            ArrayList<Answer> answers = dbConnection.answerListForQuestion(questionId);
            question.setAnswers(answers);
            questionId++;

        }


    }
}
