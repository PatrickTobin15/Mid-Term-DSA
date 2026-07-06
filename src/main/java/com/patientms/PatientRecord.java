package com.patientms;

// One past visit for a patient, stored in the history list
public class PatientRecord {

    private String visitDate;
    private String diagnosis;
    private String treatmentNotes;

    public PatientRecord(String visitDate, String diagnosis, String treatmentNotes) {
        this.visitDate = visitDate;
        this.diagnosis = diagnosis;
        this.treatmentNotes = treatmentNotes;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public String getTreatmentNotes() {
        return treatmentNotes;
    }

    @Override
    public String toString() {
        return "Visit Date: " + visitDate + " | Diagnosis: " + diagnosis + " | Notes: " + treatmentNotes;
    }
}
