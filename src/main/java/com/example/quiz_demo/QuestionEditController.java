package com.example.quiz_demo;

import com.example.quiz_demo.Database.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import jdk.internal.icu.text.UnicodeSet;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class QuestionEditController implements Initializable {

    @FXML
    private TextField id;
    @FXML
    private TextArea questionText, answerText0, answerText1, answerText2, answerText3;
    @FXML
    private CheckBox answerCorrect0, answerCorrect1, answerCorrect2, answerCorrect3;
    private ArrayList<TextArea> answerText = new ArrayList<>();
    @FXML
    private VBox editListQuestionContainerOutter;
    @FXML
    private TextField answerId0, answerId1, answerId2, answerId3;

    @FXML
    private Text subTitleTtB;

    private ArrayList<TextField> answerIds = new ArrayList<>();

    public void cancelQuestionEditClick(ActionEvent actionEvent) {
        // Back to List of Questions Edit screen
        switchToEditQuestionsClick(actionEvent);
    }

    public void saveQuestionEditClick(ActionEvent actionEvent) {
        //TODO: handle DB Save Event

        //insert datatoDB if id == empty
        addNewQestionInDb();


/*
        for (int i = 0; i < answerText.size(); i++) {
            answers.add(answerText.get(i).getText());
        }*/


        int questionId = Integer.parseInt(id.getText());
        String question = questionText.getText();

        //  Question addDBQuestion = new Question(questionId, question,createNewAnswerObjects(questionId));


        //else update question id =  id.getText in int umwandeln
        //Update where id = question id

        // Back to List of Questions Edit screen
        switchToEditQuestionsClick(actionEvent);
    }

    public void addNewQestionInDb() {
        //insert datatoDB if id == empty

//Wenn question id = 0 und text hat länge = insert  question text (wenn erzeugt brauche id von Question für Answers)
        int createtQuestionID = 0;
        int questionId = Integer.parseInt(id.getText());
        String question = questionText.getText();

        if (questionId == 0 && !question.isEmpty()) {
            // insert in question table --> question_text
            //return generierte question id von DB --> LAST_INSERT_ID();
            DBConnection con = new DBConnection();
            createtQuestionID = con.insertNewQuestion(question);
            System.out.println("Neue QuestionID von DB = " + createtQuestionID);

            /////----> Bug zuerst feststellen ob frage erstellt werden konnte
            //wenn Frage erstellt mit neuer Frage Id, die Antworten erstellen
            editNewAnswer(createtQuestionID);
        } else {
            System.out.println("Question ID = 0 question schon in DB!! Oder Text ist leer!");
        }


    }

    public void editNewAnswer(int questionId) {
        int aId0 = 0;
        int aId1 = 0;
        int aId2 = 0;
        int aId3 = 0;

  //Check wenn AnswerId Feld nicht leer ist, dann lese aus und parse in Integer.
        if (!answerId0.getText().isEmpty()) {
             aId0 = Integer.parseInt(answerId0.getText());
        }
        if (!answerId1.getText().isEmpty()) {
            aId1 = Integer.parseInt(answerId1.getText());
        }
        if (!answerId2.getText().isEmpty()) {
             aId2 = Integer.parseInt(answerId2.getText());
        }
        if (!answerId3.getText().isEmpty()) {
            aId3 = Integer.parseInt(answerId3.getText());
        }


        String answers0 = answerText0.getText();
        String answers1 = answerText1.getText();
        String answer2 = answerText2.getText();
        String answer3 = answerText3.getText();

        ArrayList<Answer> answers = createNewAnswerObjects(questionId);
        //Question addDBQuestion = new Question(questionId, question,createNewAnswerObjects(questionId));


        //Wenn answer id 0 und Text ist leer gar nichts machen

        //wenn answer id 0 und Text hat länge = neue antwort in Db hinzufügen (insert mit question id und answerText)
        //Wenn verifytrue ist, dann kontrollier ob Antworten id = 0 und Text vorhanden --> Create new Answer Insert in DB

        //////---> Bug es werden nur antworten in Db gespeichert wenn alle ids = 0 und alle antworttext inhalt haben
        if (verifyMinOneAnswerIsTrue()) {
            if (aId0 == 0 && !answers0.isEmpty()) {
                System.out.println("eingelesene Id = " + aId0);
                DBConnection con = new DBConnection();
                con.addOneAnswerInDB(answers.get(0));
                System.out.println(answers.get(0));

            }
            if (aId1 == 0 && !answers1.isEmpty()) {
                System.out.println("eingelesene Id = " + aId1);
                DBConnection con = new DBConnection();
                con.addOneAnswerInDB(answers.get(1));
                System.out.println(answers.get(1));

            }
            if (aId2 == 0 && !answer2.isEmpty()) {
                System.out.println("eingelesene Id = " + aId2);
                DBConnection con = new DBConnection();
                con.addOneAnswerInDB(answers.get(2));
                System.out.println(answers.get(2));

            }
            if (aId3 == 0 && !answer3.isEmpty()) {
                System.out.println("eingelesene Id = " + aId3);
                DBConnection con = new DBConnection();
                con.addOneAnswerInDB(answers.get(3));
                System.out.println(answers.get(3));
            }


        }


    }

    public void updateAnswerFromDb() {
        //Wenn answer id > 0 und Text hat länge = bestehende antwort updaten ( update mit where answerId = answerid )
        //String queryAnswer = "UPDATE tbl_answers SET answer_text='" + question +  " WHERE answer_id=" +  ;
    }

    public void deleteAnswerFromDb(){
        //wenn answer id > 0 und text ist leer = Delete answer where answerid = answerid

    }

    public boolean verifyMinOneAnswerIsTrue() {
        boolean verify = false;
        boolean correctAnswer0 = answerCorrect0.isSelected();
        boolean correctAnswer1 = answerCorrect1.isSelected();
        boolean correctAnswer2 = answerCorrect2.isSelected();
        boolean correctAnswer3 = answerCorrect3.isSelected();

        if (!correctAnswer0 && !correctAnswer1 && !correctAnswer2 && !correctAnswer3) {
            verify = false;
            System.out.println("Alle Antworten sind not_correct");
            System.out.println(correctAnswer0);
            System.out.println(correctAnswer1);
            System.out.println(correctAnswer2);
            System.out.println(correctAnswer3);
        } else {
            verify = true;
            System.out.println("Mindestens eine Antwort is_correct");
            System.out.println(correctAnswer0);
            System.out.println(correctAnswer1);
            System.out.println(correctAnswer2);
            System.out.println(correctAnswer3);
        }
        return verify;

    }


    public ArrayList<Answer> createNewAnswerObjects(int questionID) {

        //erzeugt neue Answerobjekte befüllt sie mit answerId, text, isCorrect, question id von zugehöriger Frage
        //Füllt alle Answerojekte in eine Answer ArrayList

       /* int aId0 = Integer.parseInt(answerId0.getText());
        int aId1 = Integer.parseInt(answerId1.getText());
        int aId2 = Integer.parseInt(answerId2.getText());
        int aId3 = Integer.parseInt(answerId3.getText());*/

        String answerObjText0 = answerText0.getText();
        String answerObjText1 = answerText1.getText();
        String answerObjText2 = answerText2.getText();
        String answerObjText3 = answerText3.getText();

        boolean correctAnswer0 = answerCorrect0.isSelected();
        boolean correctAnswer1 = answerCorrect1.isSelected();
        boolean correctAnswer2 = answerCorrect2.isSelected();
        boolean correctAnswer3 = answerCorrect3.isSelected();


        Answer addDBAnswer0 = new Answer(answerObjText0, correctAnswer0, questionID);
        Answer addDBAnswer1 = new Answer(answerObjText1, correctAnswer1, questionID);
        Answer addDBAnswer2 = new Answer(answerObjText2, correctAnswer2, questionID);
        Answer addDBAnswer3 = new Answer(answerObjText3, correctAnswer3, questionID);

        ArrayList<Answer> answerAddList = new ArrayList<>();
        answerAddList.add(addDBAnswer0);
        answerAddList.add(addDBAnswer1);
        answerAddList.add(addDBAnswer2);
        answerAddList.add(addDBAnswer3);

        return answerAddList;

    }

    public void switchToEditQuestionsClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getTarget()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Start-view.fxml"));
        try {
            Scene startScene = new Scene(loader.load());
            MainController mainController = loader.getController();
            stage.setScene(startScene);
        } catch (IOException ioe) {
            System.out.println("Could not load scene");
            ioe.printStackTrace();
        }
    }

    public void initWithData(Question question) {
        id.setText(Integer.toString(question.getQuestionId()));
        questionText.setText(question.getQuestionText());
        ArrayList<Answer> answers = question.getAnswers();


        //answers werden den answerText zugeteilt, wenn nur 3 answers bleibt 4. textarea einfach leer.
        for (int i = 0; i < answers.size(); i++) {
            answerText.get(i).setText(answers.get(i).getAnswerText());
            answerIds.get(i).setText(String.valueOf(answers.get(i).getAnswerId()));
        }
    }

    public void fillAnswerTextAndAnswerIdsInArray() {
        //AnswerText in ArrayList abgefüllt
        answerText.add(answerText0);
        answerText.add(answerText1);
        answerText.add(answerText2);
        answerText.add(answerText3);

        answerIds.add(answerId0);
        answerIds.add(answerId1);
        answerIds.add(answerId2);
        answerIds.add(answerId3);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Question edit Init called");
        fillAnswerTextAndAnswerIdsInArray();

        /*ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(new Answer(2,"Antwort A",false, 0));
        answers.add(new Answer(8,"Antwort B",true, 0));

        Question tmpQ = new Question(0,"Eine einfache Frage oder?", answers );

        // **** end of Demo Obj
        initWithData(tmpQ);*/

        DBConnection con = new DBConnection();
        initWithData(con.getQuestionWithIdFromDB(2));

    }


}
