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

    private ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    public void cancelQuestionEditClick(ActionEvent actionEvent) {
        // Back to List of Questions Edit screen
        switchToEditQuestionsClick(actionEvent);
    }

    public void saveQuestionEditClick(ActionEvent actionEvent) {
        //TODO: handle DB Save Event

        addNewQestionInDb();


        // Back to List of Questions Edit screen
        switchToEditQuestionsClick(actionEvent);
    }

    public void addNewQestionInDb() {

//Wenn question id = 0 und text hat länge = insert  question text (wenn erzeugt brauche id von Question für Answers)
        int createtQuestionID = 0;
        int questionId = Integer.parseInt(id.getText());
        String question = questionText.getText().trim();

        if (questionId == 0 && !question.isEmpty()) {
            // insert in question table --> question_text
            //return generierte question id von DB --> LAST_INSERT_ID();
            DBConnection con = new DBConnection();
            createtQuestionID = con.insertNewQuestion(question);
            System.out.println("Neue QuestionID von DB = " + createtQuestionID);

            /////----> Bug zuerst feststellen ob frage erstellt werden konnte
            //wenn Frage erstellt mit neuer Frage Id, die Antworten erstellen

            boolean answersInDB = addAnswerFromCreatetQuestion(createtQuestionID);

            if (!answersInDB) {
                //lösche die neu erstellte Frage zu den fehlgeschlagenen Antworten
               //deleteQuestionInDB(createtQuestionID);
            }

        } else if (questionId > 0 && !question.isEmpty()) {
            //Question wird geupdatet und Answers werden geupdatet gelöscht oder neue hinzugefügt
            updateQuestionFromDb(questionId, question);
            editNewAnswer(questionId, createtQuestionID);
            System.out.println("QuestionId > 0 = Question Update in DB");

        } else if (questionId > 0 && question.isEmpty()) {
            //Question und dazugehörige answers werden gelöscht
            DBConnection con = new DBConnection();
            con.deleteQuestionAndAnswersInDb(questionId);
            System.out.println("QuestionId > 0 und text ist leer = Question löschen und dazugehörige Answer werden gelöscht in DB");
        }


    }

    public void updateQuestionFromDb(int questionId, String questionText) {

        // Wenn question id > 0 und Text hat länge = bestehende question updaten ( update mit where questionId = questionId )

        if (questionId > 0 && !questionText.isEmpty()) {
            DBConnection con = new DBConnection();
            con.UpdateQuestion(questionId, questionText);
            System.out.println("bestehende Question wird Updatet");
        } else {
            System.out.println("QuestionId ist = 0 addNewQuestionInDb() wird ausgeführt");
        }
    }



    public boolean editNewAnswer(int oldQuestionId, int newCreatetQuestionId) {
        // erstellt neue Answers zu den bereits erstellten Questions
        //ändert bestehende Answers
        //löscht bestehende Answers, wenn kein Text vorhanden ist.
        int aId0 = 0;
        int aId1 = 0;
        int aId2 = 0;
        int aId3 = 0;

        boolean answersCreatet = false;

        //Check wenn AnswerId Feld nicht leer ist, dann lese aus und parse in Integer.
        if (!answerId0.getText().trim().isEmpty()) {
            aId0 = Integer.parseInt(answerId0.getText());
        }
        if (!answerId1.getText().trim().isEmpty()) {
            aId1 = Integer.parseInt(answerId1.getText());
        }
        if (!answerId2.getText().trim().isEmpty()) {
            aId2 = Integer.parseInt(answerId2.getText());
        }
        if (!answerId3.getText().trim().isEmpty()) {
            aId3 = Integer.parseInt(answerId3.getText());
        }

        //.trim() verhindert das Leerschläge als Inhalt erkannt werden
        String answer0 = answerText0.getText().trim();
        String answer1 = answerText1.getText().trim();
        String answer2 = answerText2.getText().trim();
        String answer3 = answerText3.getText().trim();


        ArrayList<Answer> updateAnswers = createNewAnswerObjects(oldQuestionId);
        //Question addDBQuestion = new Question(questionId, question,createNewAnswerObjects(questionId));


        //Wenn answer id 0 und Text ist leer gar nichts machen

        //wenn answer id 0 und Text hat länge = neue antwort in Db hinzufügen (insert mit question id und answerText)
        //Wenn verifytrue ist, dann kontrollier ob Antworten id = 0 und Text vorhanden --> Create new Answer Insert in DB

        if (verifyIfMinTwoAnswers()) {

            if (verifyMinOneAnswerIsTrue()) {


                if (aId0 == 0 && !answer0.isEmpty()) {
                    // erstelle neue Antwort mit bestehender questionId
                    System.out.println("eingelesene Id = " + aId0);
                    DBConnection con = new DBConnection();
                    con.addOneAnswerInDB(updateAnswers.get(0));
                    System.out.println(updateAnswers.get(0));

                }
                if (aId0 > 0 && !answer0.isEmpty()) {
                    //Update Antwort
                    System.out.println("eingelesene Id = " + aId0);
                    DBConnection con = new DBConnection();
                    con.updateAnswers(updateAnswers.get(0).getQuestionId(), updateAnswers.get(0).getAnswerText(), updateAnswers.get(0).isCorrect(), updateAnswers.get(0).getAnswerId());
                    System.out.println(updateAnswers.get(0));

                }
                if (aId0 > 0 && answer0.isEmpty()) {
                    //lösche Antwort
                    DBConnection con = new DBConnection();
                    con.deleteAnswerInDb(updateAnswers.get(0).getQuestionId(),updateAnswers.get(0).getAnswerId());

                }

                if (aId1 == 0 && !answer1.isEmpty()) {
                    // erstelle neue Antwort
                    System.out.println("eingelesene Id = " + aId1);
                    DBConnection con = new DBConnection();
                    con.addOneAnswerInDB(updateAnswers.get(1));
                    System.out.println(updateAnswers.get(1));

                }
                if (aId1 > 0 && !answer1.isEmpty()) {
                    //Update Antwort
                    System.out.println("eingelesene Id = " + aId1);
                    DBConnection con = new DBConnection();
                    con.updateAnswers(updateAnswers.get(1).getQuestionId(), updateAnswers.get(1).getAnswerText(), updateAnswers.get(1).isCorrect(), updateAnswers.get(1).getAnswerId());
                    System.out.println(updateAnswers.get(1));
                }
                if (aId1 > 0 && answer1.isEmpty()) {
                    //lösche Antwort
                    DBConnection con = new DBConnection();
                    con.deleteAnswerInDb(updateAnswers.get(1).getQuestionId(), updateAnswers.get(1).getAnswerId());
                }

                if (aId2 == 0 && !answer2.isEmpty()) {
                    // erstelle neue Antwort
                    System.out.println("eingelesene Id = " + aId2);
                    DBConnection con = new DBConnection();
                    con.addOneAnswerInDB(updateAnswers.get(2));
                    System.out.println(updateAnswers.get(2));

                }
                if (aId2 > 0 && !answer2.isEmpty()) {
                    //Update Antwort
                    System.out.println("eingelesene Id = " + aId2);
                    DBConnection con = new DBConnection();
                    con.updateAnswers(updateAnswers.get(2).getQuestionId(), updateAnswers.get(2).getAnswerText(), updateAnswers.get(2).isCorrect(), updateAnswers.get(2).getAnswerId());
                    System.out.println(updateAnswers.get(2));
                }
                if (aId2 > 0 && answer2.isEmpty()){
                    //lösche Antwort
                    DBConnection con = new DBConnection();
                    con.deleteAnswerInDb(updateAnswers.get(2).getQuestionId(), updateAnswers.get(2).getAnswerId());
                }

                if (aId3 == 0 && !answer3.isEmpty()) {
                    // erstelle neue Antwort
                    System.out.println("eingelesene Id = " + aId3);
                    DBConnection con = new DBConnection();
                    con.addOneAnswerInDB(updateAnswers.get(3));
                    System.out.println(updateAnswers.get(3));
                }
                if (aId3 > 0 && !answer3.isEmpty()) {
                    //Update Antwort
                    System.out.println("eingelesene Id = " + aId3);
                    DBConnection con = new DBConnection();
                    con.updateAnswers(updateAnswers.get(3).getQuestionId(), updateAnswers.get(3).getAnswerText(), updateAnswers.get(3).isCorrect(), updateAnswers.get(3).getAnswerId());
                    System.out.println(updateAnswers.get(3));

                }
                if (aId3 > 0 && answer3.isEmpty()){
                    //lösche Antwort
                    DBConnection con = new DBConnection();
                    con.deleteAnswerInDb(updateAnswers.get(3).getQuestionId(), updateAnswers.get(3).getAnswerId());
                }

                answersCreatet = true;
            } else {
                System.out.println("Mindestens eine Antwort muss als korrekt markiert sein");
                answersCreatet = false;


            }
        } else {
            System.out.println("Mindestens 2 Antworten müssen eingetragen werden!");
            answersCreatet = false;

        }

        return answersCreatet;
    }

    public boolean addAnswerFromCreatetQuestion(int newCreatetQuestionId){
        // Macht answers für neu erstellte Fragen mit neu erstellter questionid
        //Diese Methode wird nur ausgeführt wenn neue question erstellt wird.
        int aId0 = 0;
        int aId1 = 0;
        int aId2 = 0;
        int aId3 = 0;

        boolean answersCreatet = false;

        //Check wenn AnswerId Feld nicht leer ist, dann lese aus und parse in Integer.
        if (!answerId0.getText().trim().isEmpty()) {
            aId0 = Integer.parseInt(answerId0.getText());
        }
        if (!answerId1.getText().trim().isEmpty()) {
            aId1 = Integer.parseInt(answerId1.getText());
        }
        if (!answerId2.getText().trim().isEmpty()) {
            aId2 = Integer.parseInt(answerId2.getText());
        }
        if (!answerId3.getText().trim().isEmpty()) {
            aId3 = Integer.parseInt(answerId3.getText());
        }


        String answer0 = answerText0.getText().trim();
        String answer1 = answerText1.getText().trim();
        String answer2 = answerText2.getText().trim();
        String answer3 = answerText3.getText().trim();

// array von Answers mit answerObjekten, die questionId enthält von neu erstellter Question
        ArrayList<Answer> addAnswers = createNewAnswerObjects(newCreatetQuestionId);


        //wenn answer id 0 und Text hat länge = neue antwort in Db hinzufügen (insert mit question id und answerText)
        //Wenn verifytrue ist, dann kontrollier ob Antworten id = 0 und Text vorhanden --> Create new Answer Insert in DB

        if (verifyIfMinTwoAnswers()) {

            if (verifyMinOneAnswerIsTrue()) {


                if (aId0 == 0 && !answer0.isEmpty()) {
                    // erstelle neue Antwort
                    System.out.println("eingelesene Id = " + aId0);
                    DBConnection con = new DBConnection();
                    con.addOneAnswerInDB(addAnswers.get(0));
                    System.out.println(addAnswers.get(0));

                }



                if (aId1 == 0 && !answer1.isEmpty()) {
                    // erstelle neue Antwort
                    System.out.println("eingelesene Id = " + aId1);
                    DBConnection con = new DBConnection();
                    con.addOneAnswerInDB(addAnswers.get(1));
                    System.out.println(addAnswers.get(1));

                }


                if (aId2 == 0 && !answer2.isEmpty()) {
                    // erstelle neue Antwort
                    System.out.println("eingelesene Id = " + aId2);
                    DBConnection con = new DBConnection();
                    con.addOneAnswerInDB(addAnswers.get(2));
                    System.out.println(addAnswers.get(2));

                }


                if (aId3 == 0 && !answer3.isEmpty()) {
                    // erstelle neue Antwort
                    System.out.println("eingelesene Id = " + aId3);
                    DBConnection con = new DBConnection();
                    con.addOneAnswerInDB(addAnswers.get(3));
                    System.out.println(addAnswers.get(3));
                }


                answersCreatet = true;
            } else {
                System.out.println("Mindestens eine Antwort muss als korrekt markiert sein");
                answersCreatet = false;


            }
        } else {
            System.out.println("Mindestens 2 Antworten müssen eingetragen werden!");
            answersCreatet = false;

        }

        return answersCreatet;
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

        } else {
            verify = true;
            System.out.println("Mindestens eine Antwort is_correct");
        }
        return verify;

    }

    //Verifiziert ob mindestens 2 Antworten ausgefüllt wurden (Alle mögliche Kombinationen getestet)
    public boolean verifyIfMinTwoAnswers() {
        boolean verify = false;
        String answer0 = answerText0.getText().trim();
        String answer1 = answerText1.getText().trim();
        String answer2 = answerText2.getText().trim();
        String answer3 = answerText3.getText().trim();

        //String genommen, weil keine lust zu Interger parsen
        String stringAnswerId0 = answerId0.getText().trim();
        String stringAnswerId1 = answerId1.getText().trim();
        String stringAnswerId2 = answerId2.getText().trim();
        String stringAnswerId3 = answerId3.getText().trim();

        //überprüfung ob AnswerText und eine AnswerId vorhanden ist, von immer 2 Answers in der Maske
        //Id nicht unbeidngt nötig, wenn sichergestellt, dass immer eine ID eingefüllt ist und kein Leerschlag.
        if ((!answer0.isEmpty() && !stringAnswerId0.isEmpty()) &&
                (!answer1.isEmpty() && !stringAnswerId1.isEmpty())) {
            verify = true;
            System.out.println(verify);

        } else if ((!answer1.isEmpty() && !stringAnswerId1.isEmpty()) &&
                (!answer2.isEmpty() && !stringAnswerId2.isEmpty())) {
            verify = true;
            System.out.println(verify);

        } else if ((!answer2.isEmpty() && !stringAnswerId2.isEmpty()) &&
                (!answer3.isEmpty() && !stringAnswerId3.isEmpty())) {
            verify = true;
            System.out.println(verify);

        } else if ((!answer3.isEmpty() && !stringAnswerId3.isEmpty()) &&
                (!answer0.isEmpty() && !stringAnswerId0.isEmpty())) {
            verify = true;
            System.out.println(verify);

        } else if ((!answer0.isEmpty() && !stringAnswerId0.isEmpty()) &&
                (!answer2.isEmpty() && !stringAnswerId2.isEmpty())) {
            verify = true;
            System.out.println(verify);

        } else if ((!answer3.isEmpty() && !stringAnswerId3.isEmpty()) &&
                (!answer1.isEmpty() && !stringAnswerId1.isEmpty())) {
            verify = true;
            System.out.println(verify);

        } else {
            verify = false;
            System.out.println(verify);
        }


        return verify;
    }


    public ArrayList<Answer> createNewAnswerObjects(int questionID) {

        //erzeugt neue Answerobjekte befüllt sie mit answerId, text, isCorrect, question id von zugehöriger Frage
        //Füllt alle Answerojekte in eine Answer ArrayList

        int aId0 = 0;
        int aId1 = 0;
        int aId2 = 0;
        int aId3 = 0;

        boolean answersCreatet = false;

        //Check wenn AnswerId Feld nicht leer ist, dann lese aus und parse in Integer.
        if (!answerId0.getText().trim().isEmpty()) {
            aId0 = Integer.parseInt(answerId0.getText());
        }
        if (!answerId1.getText().trim().isEmpty()) {
            aId1 = Integer.parseInt(answerId1.getText());
        }
        if (!answerId2.getText().trim().isEmpty()) {
            aId2 = Integer.parseInt(answerId2.getText());
        }
        if (!answerId3.getText().trim().isEmpty()) {
            aId3 = Integer.parseInt(answerId3.getText());
        }



        String answerObjText0 = answerText0.getText().trim();
        String answerObjText1 = answerText1.getText().trim();
        String answerObjText2 = answerText2.getText().trim();
        String answerObjText3 = answerText3.getText().trim();

        boolean correctAnswer0 = answerCorrect0.isSelected();
        boolean correctAnswer1 = answerCorrect1.isSelected();
        boolean correctAnswer2 = answerCorrect2.isSelected();
        boolean correctAnswer3 = answerCorrect3.isSelected();


        Answer addDBAnswer0 = new Answer(aId0, answerObjText0, correctAnswer0, questionID);
        Answer addDBAnswer1 = new Answer(aId1, answerObjText1, correctAnswer1, questionID);
        Answer addDBAnswer2 = new Answer(aId2, answerObjText2, correctAnswer2, questionID);
        Answer addDBAnswer3 = new Answer(aId3, answerObjText3, correctAnswer3, questionID);

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
            checkBoxes.get(i).setSelected(answers.get(i).isCorrect());
        }
    }

    public void fillAnswerTextAndAnswerIdsCheckboxesInArray() {
        //AnswerText in ArrayList abgefüllt
        //AnswerId's in ArrayList abgefüllt
        //Checkboxes in ArrayList abgefüllt
        answerText.add(answerText0);
        answerText.add(answerText1);
        answerText.add(answerText2);
        answerText.add(answerText3);

        answerIds.add(answerId0);
        answerIds.add(answerId1);
        answerIds.add(answerId2);
        answerIds.add(answerId3);

        checkBoxes.add(answerCorrect0);
        checkBoxes.add(answerCorrect1);
        checkBoxes.add(answerCorrect2);
        checkBoxes.add(answerCorrect3);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Question edit Init called");
        fillAnswerTextAndAnswerIdsCheckboxesInArray();

        /*ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(new Answer(2,"Antwort A",false, 0));
        answers.add(new Answer(8,"Antwort B",true, 0));

        Question tmpQ = new Question(0,"Eine einfache Frage oder?", answers );

        // **** end of Demo Obj
        initWithData(tmpQ);*/

        DBConnection con = new DBConnection();
        initWithData(con.getQuestionWithIdFromDB(32));

    }


}
