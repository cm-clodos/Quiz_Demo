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
        int questionId = Integer.parseInt(id.getText());
        String question = questionText.getText();


        updateQuestionFromDb(questionId, question);
        editAnswerFromDb(questionId);
       // addNewQestionInDb();


/*
        for (int i = 0; i < answerText.size(); i++) {
            answers.add(answerText.get(i).getText());
        }*/




        //  Question addDBQuestion = new Question(questionId, question,createNewAnswerObjects(questionId));


        //else update question id =  id.getText in int umwandeln
        //Update where id = question id

        // Back to List of Questions Edit screen
        switchToEditQuestionsClick(actionEvent);
    }

    public void addNewQestionInDb() {

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

            boolean answersInDB = addNewAnswer(createtQuestionID);

            if (!answersInDB){
                //lösche die neu erstellte Frage zu den fehlgeschlagenen Antworten
                deleteQuestionInDB(createtQuestionID);
            }

        } else if (questionId > 0 && !question.isEmpty()){
            updateQuestionFromDb(questionId, question);
            System.out.println("QuestionId > 0 = Question Update in DB");

        } else if (questionId > 0 && question.isEmpty()){
            deleteQuestionInDB(questionId);
            System.out.println("QuestionId > 0 und text ist leer = Question löschen in DB");
        }


    }

    public void updateQuestionFromDb(int questionId, String questionText){

        // Wenn question id > 0 und Text hat länge = bestehende question updaten ( update mit where questionId = questionId )

        if (questionId > 0 && !questionText.isEmpty()){
            DBConnection con = new DBConnection();
            con.UpdateQuestion(questionId, questionText);
            System.out.println("bestehende Question wird Updatet");
        }else {
            System.out.println("QuestionId ist = 0 addNewQuestionInDb() wird ausgeführt");
        }
    }
    public void deleteQuestionInDB(int questionId){
        //TODO lösche question und dazugehörige Antworten

    }

    public boolean addNewAnswer(int questionId) {
        int aId0 = 0;
        int aId1 = 0;
        int aId2 = 0;
        int aId3 = 0;

        boolean answersCreatet = false;

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

        if (verifyIfMinTwoAnswers()) {

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

                    answersCreatet = true;
            }else {
                System.out.println("Mindestens eine Antwort muss als korrekt markiert sein");
                answersCreatet = false;


            }
        }else {
            System.out.println("Mindestens 2 Antworten müssen eingetragen werden!");
            answersCreatet = false;

        }

       return answersCreatet;
    }

    public void editAnswerFromDb(int questionId) {
        //TODO Wenn answer id > 0 und Text hat länge = bestehende antwort updaten ( update mit where answerId = answerid )

        //String queryAnswer = "UPDATE tbl_answers SET answer_text='" + question +  " WHERE answer_id=" +  ;

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

        String answer0 = answerText0.getText();
        String answer1 = answerText1.getText();
        String answer2 = answerText2.getText();
        String answer3 = answerText3.getText();

        ArrayList<Answer> answers = createNewAnswerObjects(questionId);

        if (verifyIfMinTwoAnswers()) {

            if (verifyMinOneAnswerIsTrue()) {
                if (aId0 > 0 && !answer0.isEmpty()) {
                    System.out.println("eingelesene Id = " + aId0);
                    DBConnection con = new DBConnection();
                    con.updateAnswers(answers.get(0).getQuestionId(), answers.get(0).getAnswerText(), answers.get(0).isCorrect());
                    System.out.println(answers.get(0));

                }
                if (aId1 > 0 && !answer1.isEmpty()){
                    System.out.println("eingelesene Id = " + aId1);
                    DBConnection con = new DBConnection();
                    con.updateAnswers(answers.get(1).getQuestionId(), answers.get(1).getAnswerText(), answers.get(1).isCorrect());
                    System.out.println(answers.get(1));


                }
                if (aId2 > 0 && !answer2.isEmpty()){
                    System.out.println("eingelesene Id = " + aId2);
                    DBConnection con = new DBConnection();
                    con.updateAnswers(answers.get(2).getQuestionId(), answers.get(2).getAnswerText(), answers.get(2).isCorrect());
                    System.out.println(answers.get(2));


                }
                if (aId3 > 0 && !answer3.isEmpty()){
                    System.out.println("eingelesene Id = " + aId3);
                    DBConnection con = new DBConnection();
                    con.updateAnswers(answers.get(3).getQuestionId(), answers.get(3).getAnswerText(), answers.get(3).isCorrect());
                    System.out.println(answers.get(3));


                }

            }else {
                System.out.println("Mindestens eine Antwort muss als korrekt markiert sein");
            }

        }else {
            System.out.println("Mindestens 2 Antworten müssen eingetragen werden!");
        }

    }
    public void deleteAnswerFromDb(){
        //TODO wenn answer id > 0 und text ist leer = Delete answer where answerid = answerid

    }
// Verifiziert ob mindestens eine Antwort als korrekt markiert wurde
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
//Verifiziert ob mindestens 2 Antworten ausgefüllt wurden (Alle mögliche Kombinationen getestet)
    public boolean verifyIfMinTwoAnswers(){
        boolean verify = false;
        String answer0 = answerText0.getText();
        String answer1 = answerText1.getText();
        String answer2 = answerText2.getText();
        String answer3 = answerText3.getText();

        //String genommen, weil keine lust zu Interger parsen
        String stringAnswerId0 = answerId0.getText();
        String stringAnswerId1 = answerId1.getText();
        String stringAnswerId2 = answerId2.getText();
        String stringAnswerId3 = answerId3.getText();

        if ((!answer0.isEmpty() && !stringAnswerId0.isEmpty()) &&
                (!answer1.isEmpty() && !stringAnswerId1.isEmpty())){
            verify = true;
            System.out.println(verify);
        }
        else if ((!answer1.isEmpty() && !stringAnswerId1.isEmpty()) &&
                (!answer2.isEmpty() && !stringAnswerId2.isEmpty())){
            verify = true;
            System.out.println(verify);
        }
        else if ((!answer2.isEmpty() && !stringAnswerId2.isEmpty()) &&
                (!answer3.isEmpty() && !stringAnswerId3.isEmpty())){
            verify = true;
            System.out.println(verify);
        }
        else if ((!answer3.isEmpty() && !stringAnswerId3.isEmpty()) &&
                (!answer0.isEmpty() && !stringAnswerId0.isEmpty())){
            verify = true;
            System.out.println(verify);
        }
        else if ((!answer0.isEmpty() && !stringAnswerId0.isEmpty()) &&
                (!answer2.isEmpty() && !stringAnswerId2.isEmpty())){
            verify = true;
            System.out.println(verify);
        }
        else if ((!answer3.isEmpty() && !stringAnswerId3.isEmpty()) &&
                (!answer1.isEmpty() && !stringAnswerId1.isEmpty())){
            verify = true;
            System.out.println(verify);
        }else {
            verify = false;
            System.out.println(verify);
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
