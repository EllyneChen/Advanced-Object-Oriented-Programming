package com.therapybooking.database;

import com.therapybooking.model.Booking;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBOperations {
    public static ObservableList<Booking> getAllBookings() {
        ObservableList<Booking> bookings = FXCollections.observableArrayList();
        String query = "SELECT * FROM bookings ORDER BY booking_id";

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                bookings.add(new Booking(
                    rs.getInt("booking_id"),
                    rs.getString("child_name"),
                    rs.getString("therapy_type"),
                    rs.getString("session_date"),
                    rs.getString("caregiver_name"),
                    rs.getString("contact_number")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Database error in getAllBookings(): " + e.getMessage());
        }
        return bookings;
    }

    public static boolean addBooking(Booking booking) {
        String query = "INSERT INTO bookings (child_name, therapy_type, session_date, caregiver_name, contact_number) "
                     + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, booking.getChildName());
            pstmt.setString(2, booking.getTherapyType());
            pstmt.setString(3, booking.getSessionDate());
            pstmt.setString(4, booking.getCaregiverName());
            pstmt.setString(5, booking.getContactNumber());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.err.println("Error in addBooking(): " + e.getMessage());
            return false;
        }
    }
}