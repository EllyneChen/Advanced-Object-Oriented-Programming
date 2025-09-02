package com.therapybooking.database;
import java.sql.*;
public class DBConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/therapy_booking_system";
    private static final String USER = "postgres";
    private static final String PASSWORD = "100608lhe"; 

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("PostgreSQL Driver not found!");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}