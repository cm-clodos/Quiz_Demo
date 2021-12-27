package com.example.quiz_demo.Database;
import com.example.quiz_demo.Answer;
import com.example.quiz_demo.Question;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {

    public DBConnection() {
        DBData conData = new DBData();
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
    }

    public ArrayList<Question> getQuestionDB() {
        ArrayList<Question> questionList = new ArrayList<>();
        String query = "SELECT * FROM questions";

        try {
            Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                questionList.add(new Question(rs.getInt("question_id"),
                        (rs.getString("question_text"))
                ));

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            System.out.println("Connection NOT OK");
            ex.printStackTrace();
        }
        return questionList;
    }

    public ArrayList<Answer> getAnswersDB() {
        ArrayList<Answer> answerList = new ArrayList<>();
        String query = "SELECT * FROM answers";

        try {
            Connection con = DriverManager.getConnection(DBData.getURL(), DBData.getUSER(), DBData.getPASSWORD());
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()){
                answerList.add(new Answer(rs.getInt("answer_id"),
                        (rs.getInt("question_id")),
                        (rs.getString("answer_text")),
                        (rs.getBoolean("answer_correct"))
                        ));
            }

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
            System.out.println("Connection NOT OK");
            ex.printStackTrace();

        }
       return answerList;
    }

}
