package com.librarymgt.librarysystem.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseHandler {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/LibraryDB";
    private static final String USER = "root";
    private static final String PASSWORD = "password";
//    private static Connection = null;
//    private static Statement stmt;


    //important method
//    public DatabaseHandler() throws SQLException {
//        createConnection();
    //   setupBookTable();
//    }





    public static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


    void setupBookTable() {
        String TABLE_NAME = "BOOK";
        try {


            //SQL COMMAND FOR CREATE A TABLE
            // "CREATE TABLE" + TABLE_NAME + "("
            //  +"  id varchar(200) primary key, \n
//  +"  title varchar(200), \n
//  +"  author varchar(200)  key, \n
            //  +"  publisher varchar(100), \n
            //  +"  intcode varchar(100), \n
            //  +"  isAvail boolean default true"
            //+ ")");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
