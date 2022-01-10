package com.example.quiz_demo.Database;

import com.example.quiz_demo.Answer;
import com.example.quiz_demo.Question;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

    public DBConnection() {
    }

    /* DBData conData = new DBData();
     try (
             Connection con = DriverManager.getConnection(
                     DBData.getURL(),
                     DBData.getUSER(),
                     DBData.getPASSWORD())
     ) {
         //System.out.println("Connection OK");
     } catch (
             SQLException e) {
         System.out.println(e.getMessage());
         System.out.println("Connection NOT OK");
         e.printStackTrace();
     }
 }*/
public void addOneAnswerInDB(Answer answer){

    String query = "INSERT INTO answers (answer_text, answer_correct, question_id) VALUE ('" + answer.getAnswerText() + "',"
            + answer.isCorrect() + "," + answer.getQuestionId() + ");";

    try (Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
         Statement statement = con.createStatement();
         ResultSet rs = statement.executeQuery(query)) {

        while (rs.next()) {
        }



    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
        System.out.println("Connection NOT OK");
        ex.printStackTrace();


    }


}


    public void addAnswersInDB(ArrayList<Answer> answerListFromEditMask){
        for (int i = 0; i < answerListFromEditMask.size(); i++) {
           Answer answerFromList =  answerListFromEditMask.get(i);
            String query = "INSERT INTO answers (answer_text, answer_correct, question_id) VALUE ('" + answerFromList.getAnswerText() + "',"
                    + answerFromList.isCorrect() + "," + answerFromList.getQuestionId() + ");";

            try (Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
                 Statement statement = con.createStatement();
                 ResultSet rs = statement.executeQuery(query)) {

                while (rs.next()) {
                }



            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                System.out.println("Connection NOT OK");
                ex.printStackTrace();


            }


        }


    }
    public int insertNewQuestion(String questionText) {
        String query = "INSERT INTO questions (question_text) VALUE ('" + questionText + "') RETURNING question_id;";
        //String query2 = "SELECT LAST_INSERT_ID();";
        int lastCreatetQuestionId = 0;
        try (Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            // ResultSet rs2 = statement.executeQuery(query2);

            while (rs.next()) {
                lastCreatetQuestionId = rs.getInt("question_id");
            }
            System.out.println("letze generierte question_ID " + lastCreatetQuestionId);


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Connection NOT OK");
            ex.printStackTrace();


        }
        return lastCreatetQuestionId;
    }




    public Question getQuestionWithIdFromDB(int questionId) {
        String query = "SELECT * FROM questions WHERE question_id =" + questionId;
        Question question = new Question();

        try (Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                ArrayList<Answer> answers = answerListForQuestion(rs.getInt("question_id"));
                question.setQuestionId(rs.getInt("question_id"));
                question.setQuestionText(rs.getString("question_text"));
                question.setAnswers(answers);
                // rs.getString("question_text"), answers);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Connection NOT OK");
            ex.printStackTrace();
        }
        return question;

    }

    //All questions from DB
    public ArrayList<Question> getQuestionDB() {
        ArrayList<Question> questionList = new ArrayList<>();
        String query = "SELECT * FROM questions";

        try (Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                ArrayList<Answer> answers = answerListForQuestion(rs.getInt("question_id"));

                questionList.add(new Question(rs.getInt("question_id"),
                        rs.getString("question_text"), answers));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Connection NOT OK");
            ex.printStackTrace();
        }
        return questionList;
    }

    //Only Answers for questionid x
    public ArrayList<Answer> answerListForQuestion(int questionID) {

        ArrayList<Answer> listOfAnswerPerQuestion = new ArrayList<>();
        String query = "SELECT * FROM answers WHERE question_id=" + questionID;

        try (Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(query)) {


            while (rs.next()) {
                listOfAnswerPerQuestion.add(new Answer(rs.getInt("answer_id"),
                        (rs.getString("answer_text")),
                        (rs.getBoolean("answer_correct")),
                        (rs.getInt("question_id"))
                ));
            }
        } catch (SQLException ex) {

            System.out.println(ex.getMessage());
            System.out.println("Connection NOT OK");
            ex.printStackTrace();
        }
        return listOfAnswerPerQuestion;
    }

    //All Answers from DB
    public ArrayList<Answer> getAnswersDB() {
        ArrayList<Answer> answerList = new ArrayList<>();
        String query = "SELECT * FROM answers";

        try (Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
             Statement statement = con.createStatement();
             ResultSet rs = statement.executeQuery(query)) {


            while (rs.next()) {
                answerList.add(new Answer(rs.getInt("answer_id"),
                        (rs.getString("answer_text")),
                        (rs.getBoolean("answer_correct")),
                        (rs.getInt("question_id"))
                ));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Connection NOT OK");
            ex.printStackTrace();

        }
        return answerList;
    }

}
