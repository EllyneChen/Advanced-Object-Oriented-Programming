package com.therapybooking.model;

public class Booking {
    private final int bookingId; 
    private final String childName;
    private final String therapyType;
    private final String sessionDate;
    private final String caregiverName;
    private final String contactNumber;  

    // Constructor
    public Booking(int bookingId, String childName, String therapyType, 
                  String sessionDate, String caregiverName, String contactNumber) {
        this.bookingId = bookingId;
        this.childName = childName;
        this.therapyType = therapyType;
        this.sessionDate = sessionDate;
        this.caregiverName = caregiverName;
        this.contactNumber = contactNumber;  
    }

    // Getters (corrected syntax and case sensitivity)
    public int getBookingId() { return bookingId; }  

    public String getChildName() { return childName; }  

    public String getTherapyType() { return therapyType; }

    public String getSessionDate() { return sessionDate; }

    public String getCaregiverName() { return caregiverName; }

    public String getContactNumber() { return contactNumber; }  

    // Optional toString() for debugging
    @Override
    public String toString() {
        return String.format(
            "Booking[ID=%d, Child=%s, Therapy=%s]",
            bookingId, childName, therapyType
        );
    }
}