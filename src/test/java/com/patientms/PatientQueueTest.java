package com.patientms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// Tests for the waiting queue, check the README for what each one checks
class PatientQueueTest {

    private PatientQueue queue;

    @BeforeEach
    void setUp() {
        queue = new PatientQueue();
    }

    @Test
    void testAddAndRemoveMaintainsFifoOrder() {
        queue.addPatient(new Patient("P1", "Alice", "Checkup"));
        queue.addPatient(new Patient("P2", "Bob", "Flu"));
        queue.addPatient(new Patient("P3", "Carla", "X-ray"));

        Patient first = queue.removePatient();

        assertEquals("P1", first.getPatientId());
    }

    @Test
    void testRemoveFromEmptyQueueReturnsNull() {
        Patient result = queue.removePatient();

        assertNull(result);
    }

    @Test
    void testInsertAtPositionZeroForEmergencyCase() {
        queue.addPatient(new Patient("P1", "Alice", "Checkup"));
        queue.addPatient(new Patient("P2", "Bob", "Flu"));
        queue.insertAtPosition(new Patient("E1", "Emergency Dave", "Chest Pain"), 0);

        Patient served = queue.removePatient();

        assertEquals("E1", served.getPatientId());
    }

    @Test
    void testInsertAtMiddlePosition() {
        queue.addPatient(new Patient("P1", "Alice", "Checkup"));
        queue.addPatient(new Patient("P2", "Bob", "Flu"));
        queue.insertAtPosition(new Patient("E1", "Emergency Dave", "Chest Pain"), 1);

        queue.removePatient();
        Patient second = queue.removePatient();

        assertEquals("E1", second.getPatientId());
    }

    @Test
    void testQueueSizeUpdatesCorrectly() {
        queue.addPatient(new Patient("P1", "Alice", "Checkup"));
        queue.addPatient(new Patient("P2", "Bob", "Flu"));

        assertEquals(2, queue.size());

        queue.removePatient();

        assertEquals(1, queue.size());
    }
}
