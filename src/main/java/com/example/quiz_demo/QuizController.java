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

    private boolean choiceA1 = false;
    private boolean choiceA2 = false;
    private boolean choiceA3 = false;
    private boolean choiceA4 = false;
    private boolean btnCheckPressed = false;

    private boolean correctAnswer1 = false;
    private boolean correctAnswer2 = false;
    private boolean correctAnswer3 = false;
    private boolean correctAnswer4 = false;


    private ArrayList<Answer> answerList = new ArrayList<>();
    @FXML
    private ArrayList<Question> questionList = new ArrayList<>();
    @FXML
    private ArrayList<Label> answerLabels = new ArrayList<>();


    @FXML
    private Button btnCheck;

    private int actualQuestion = 0;
    protected static int points = 0;
    protected static int maxPoints = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resetPoints();
        resetMaxPoints();

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

        for (int i = 0; i < answerList.size(); i++) {
            answerLabels.get(i).setText(answerList.get(i).getAnswerText());
        }

        System.out.println(question);


    }

    @FXML
    public void choice(MouseEvent event) {
        //Antwort 1
        if (event.getSource() == answer1 && !choiceA1) {
            answer1.setStyle("-fx-background-color: steelblue;-fx-text-fill:white;");
            choiceA1 = true;
            if (answerList.get(0).isCorrect() && choiceA1) {
                correctAnswer1 = true;

            }


        } else if (event.getSource() == answer1 && choiceA1) {
            answer1.setStyle(null);
            choiceA1 = false;
            correctAnswer1 = false;


        }
        //Antwort 2
        else if (event.getSource() == answer2 && !choiceA2) {
            answer2.setStyle("-fx-background-color: steelblue;-fx-text-fill:white;");
            choiceA2 = true;
            if (answerList.get(1).isCorrect() && choiceA2) {
                correctAnswer2 = true;
            }


        } else if (event.getSource() == answer2 && choiceA2) {
            answer2.setStyle(null);
            choiceA2 = false;
            correctAnswer2 = false;


        }
        //Antwort 3
        else if (event.getSource() == answer3 && !choiceA3) {
            answer3.setStyle("-fx-background-color: steelblue;-fx-text-fill:white;");
            choiceA3 = true;
            if (answerList.get(2).isCorrect() && choiceA3) {
                correctAnswer3 = true;
            }


        } else if (event.getSource() == answer3 && choiceA3) {
            answer3.setStyle(null);
            choiceA3 = false;
            correctAnswer3 = false;


        }
        //Antwort 4
        else if (event.getSource() == answer4 && !choiceA4) {
            answer4.setStyle("-fx-background-color: steelblue;-fx-text-fill:white;");
            choiceA4 = true;
            if (answerList.get(3).isCorrect() && choiceA4) {
                correctAnswer4 = true;
            }


        } else if (event.getSource() == answer4 && choiceA4) {
            answer4.setStyle(null);
            choiceA4 = false;
            correctAnswer4 = false;

        }

    }


    @FXML
    public void CheckAnswers(ActionEvent actionEvent) {


        if (actionEvent.getSource() == btnCheck && !btnCheckPressed) {
            // Check if answer true = answer in DB True

            if (correctAnswer1) {
                points+=1;
                System.out.println("Answer1 = " + points);
            }
            if (correctAnswer2) {
                points+=1;
                System.out.println("Answer2 = " + points);
            }
            if (correctAnswer3) {
                points+=1;
                System.out.println("Answer3 = " + points);
            }
            if (correctAnswer4) {
                points+=1;
                System.out.println("Answer4 = " + points);
            } else {
                //textIsCorrect.setText("Answer is false ");
            }
            System.out.println("Total points for this question = " + points);


            btnCheckPressed = true;
            btnCheck.setText("Next!");

            maxPointsPerQuestion();
            markTheResultAnswerLabels();

        } else if (actionEvent.getSource() == btnCheck && btnCheckPressed) {
            //delete old answerslabels
            refreshOldAnswerLabels();

            //delete old answersMarker
            refreshOldAnswerMarkers();

            //reset correct answers
            resetCorrectAnswers();

            //show results if no more questions

            if (actualQuestion >= questionList.size()) {
                showResultsScene(actionEvent);

            } else {
                // show next question
                Question question = questionList.get(actualQuestion);
                labelShowQuestion.setText(question.getQuestionText());
                labelQuestionCount.setText("Question" + question.getQuestionId() + "/" + questionList.size());

                answerList = question.getAnswers();
                for (int i = 0; i < answerList.size(); i++) {
                    answerLabels.get(i).setText(answerList.get(i).getAnswerText());
                }
                actualQuestion++;

                btnCheckPressed = false;
                btnCheck.setText("Check!");


            }
        }
    }

    public void showResultsScene(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Result-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stage = (Stage) btnCheck.getScene().getWindow();
            stage.setTitle("Quiz Demo!");
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

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

    public void refreshOldAnswerLabels() {

        for (int i = 0; i < answerList.size(); i++) {
            answerLabels.get(i).setText("");
        }
    }

    public void refreshOldAnswerMarkers() {

        for (int i = 0; i < answerLabels.size(); i++) {
            answerLabels.get(i).setStyle(null);
        }
    }

    public void maxPointsPerQuestion() {
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).isCorrect()) {
                maxPoints++;
            }
        }


    }
    public void resetPoints(){
        if (points!=0){
            points =0;
        }
    }
    public void resetMaxPoints(){
        if (maxPoints!=0){
            maxPoints=0;
        }
    }
    public void resetCorrectAnswers(){
        correctAnswer1= false;
        correctAnswer2= false;
        correctAnswer3= false;
        correctAnswer4= false;
    }

    public void markTheResultAnswerLabels(){
        for (int i = 0; i < answerList.size(); i++) {
            if (answerList.get(i).isCorrect()){
                answerLabels.get(i).setStyle("-fx-background-color: green;-fx-text-fill:white;");
            }else {
                answerLabels.get(i).setStyle("-fx-background-color: red;-fx-text-fill:white;");
            }
        }


    }
}
