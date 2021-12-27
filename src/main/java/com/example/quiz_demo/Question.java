package com.example.quiz_demo;

import com.example.quiz_demo.Database.DBConnection;

import java.util.ArrayList;

public class Question {
    private int questionId;
    private String questionText;
    private ArrayList<Answer> answers;

    public Question(int questionId, String questionText) {
        this.questionId = questionId;
        this.questionText = questionText;
        this.answers = getAnswers();
    }

    public ArrayList<Answer> getAnswers(){
        DBConnection connection = new DBConnection();
        return connection.getAnswersDB();
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionId=" + questionId +
                ", questionText='" + questionText + '\'' +
                ", answers=" + answers +
                '}';
    }
}
