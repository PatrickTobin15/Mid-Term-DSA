package com.patientms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

// Tests for the patient history DLL, see README for what each one checks
class PatientHistoryDLLTest {

    private PatientHistoryDLL history;

    @BeforeEach
    void setUp() {
        history = new PatientHistoryDLL();
        history.addRecord(new PatientRecord("2026-01-01", "Diagnosis A", "Notes A"));
        history.addRecord(new PatientRecord("2026-02-01", "Diagnosis B", "Notes B"));
        history.addRecord(new PatientRecord("2026-03-01", "Diagnosis C", "Notes C"));
    }

    @Test
    void testNavigateForwardThenBackward() {
        history.displayOldest();
        PatientRecord second = history.next();
        PatientRecord backToFirst = history.previous();

        assertEquals("Diagnosis B", second.getDiagnosis());
        assertEquals("Diagnosis A", backToFirst.getDiagnosis());
    }

    @Test
    void testDisplayNewestAndOldest() {
        assertEquals("Diagnosis C", history.displayNewest().getDiagnosis());
        assertEquals("Diagnosis A", history.displayOldest().getDiagnosis());
    }

    @Test
    void testCannotNavigatePastTheTail() {
        history.displayNewest();
        PatientRecord result = history.next();

        assertEquals("Diagnosis C", result.getDiagnosis());
    }

    @Test
    void testCannotNavigatePastTheHead() {
        history.displayOldest();
        PatientRecord result = history.previous();

        assertEquals("Diagnosis A", result.getDiagnosis());
    }

    @Test
    void testEmptyHistoryIsHandledSafely() {
        PatientHistoryDLL emptyHistory = new PatientHistoryDLL();

        assertNull(emptyHistory.next());
        assertNull(emptyHistory.previous());
        assertNull(emptyHistory.displayNewest());
        assertNull(emptyHistory.displayOldest());
    }
}
