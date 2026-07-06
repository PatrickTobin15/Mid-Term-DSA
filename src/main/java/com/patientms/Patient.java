package com.patientms;

// Represents one patient in the waiting queue
public class Patient {

    private String patientId;
    private String name;
    private String reasonForVisit;

    public Patient(String patientId, String name, String reasonForVisit) {
        this.patientId = patientId;
        this.name = name;
        this.reasonForVisit = reasonForVisit;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getReasonForVisit() {
        return reasonForVisit;
    }

    @Override
    public String toString() {
        return "Patient ID: " + patientId + " | Name: " + name + " | Reason: " + reasonForVisit;
    }
}
