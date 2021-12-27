package com.example.quiz_demo.Database;

public class DBData {
    private final static String URL = "jdbc:mariadb://localhost:3306/quiz_demo";
    private final static String USER ="root";
    private final static String PASSWORD ="root";

    public static String getURL() {return URL;}
    public static String getUSER(){return USER;}
    public static String getPASSWORD() {return PASSWORD;}
}
