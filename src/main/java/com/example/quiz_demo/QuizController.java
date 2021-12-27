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
import javafx.scene.text.Text;
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
    @FXML
    private Label labelShowQuestion;
    @FXML
    private Text textIsCorrect;

    boolean choiceA1 = false;
    boolean choiceA2 = false;
    boolean choiceA3 = false;
    boolean choiceA4 = false;
    boolean btnCheckPressed = false;

    private ArrayList<Answer> answerList = new ArrayList<>();
    @FXML
    private ArrayList<Question> questionList = new ArrayList<>();
    @FXML
    private ArrayList<Label> answerLabels = new ArrayList<>();
    @FXML
    private ArrayList<Boolean> choices = new ArrayList<>();
    @FXML
    private Button btnCheck;

    private int actualQuestion = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DBConnection connection = new DBConnection();
        questionList = connection.getQuestionDB();
        getAnswerPackage(questionList);

        questionList.forEach(System.out::println);
        Question question = questionList.get(actualQuestion);
        actualQuestion++;


        answerLabels.add(answer1);
        answerLabels.add(answer2);
        answerLabels.add(answer3);
        answerLabels.add(answer4);




        labelShowQuestion.setText(question.getQuestionText());
        labelQuestionCount.setText("Question" + question.getQuestionId() + "/" + questionList.size());

        answerList = question.getAnswers();

        answer1.setText(answerList.get(0).getAnswerText());
        answer2.setText(answerList.get(1).getAnswerText());
        answer3.setText(answerList.get(2).getAnswerText());
        answer4.setText(answerList.get(3).getAnswerText());

        System.out.println(question);


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
        int questionCheckerCount = 0;
        int points =0;
        if (actionEvent.getSource() == btnCheck && !btnCheckPressed) {
            // Check if answer true = answer in DB True

            /*for (int i = 0; i < answerList.size(); i++) {

                Answer answer = answerList.get(i);
                System.out.println(answer.isCorrect());
                System.out.println(choices.get(0));
                if (answer.isCorrect() && choices.get(i) ) {
                    points++;
                }else {
                    textIsCorrect.setText("Answer is false ");
                }
            }
            System.out.println(points);*/


            btnCheckPressed = true;
            btnCheck.setText("Next!");
        } else if (actionEvent.getSource() == btnCheck && btnCheckPressed) {
            // show next question
            Question question = questionList.get(actualQuestion);
            labelShowQuestion.setText(question.getQuestionText());
            labelQuestionCount.setText("Question" + question.getQuestionId() + "/" + questionList.size());

            answerList = question.getAnswers();
            for (int i = 0; i < answerList.size(); i++) {
                answerLabels.get(i).setText(answerList.get(i).getAnswerText());
            }

            btnCheckPressed = false;
            btnCheck.setText("Check!");


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
