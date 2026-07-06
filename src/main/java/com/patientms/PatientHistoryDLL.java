package com.patientms;

// Doubly linked list so we can move forward and backward through a patient's history. head means the oldest record, tail the means newest record
public class PatientHistoryDLL {

    private class Node {
        PatientRecord data;
        Node next;
        Node prev;

        Node(PatientRecord data) {
            this.data = data;
        }
    }

    private Node head;
    private Node tail;
    private Node current;
    private int size;

    public PatientHistoryDLL() {
        head = null;
        tail = null;
        current = null;
        size = 0;
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int size() {
        return size;
    }

    // add a new record to the end (newest visit)
    public void addRecord(PatientRecord record) {
        Node newNode = new Node(record);
        if (isEmpty()) {
            head = newNode;
            tail = newNode;
            current = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // move to the next (more recent) record
    public PatientRecord next() {
        if (isEmpty()) {
            System.out.println("No patient history records available.");
            return null;
        }
        if (current.next == null) {
            System.out.println("You are already at the newest record, cannot move forward.");
            return current.data;
        }
        current = current.next;
        return current.data;
    }

    // move to the previous (older) record
    public PatientRecord previous() {
        if (isEmpty()) {
            System.out.println("No patient history records available.");
            return null;
        }
        if (current.prev == null) {
            System.out.println("You are already at the oldest record, cannot move back.");
            return current.data;
        }
        current = current.prev;
        return current.data;
    }

    public PatientRecord displayNewest() {
        if (isEmpty()) {
            System.out.println("No patient history records available.");
            return null;
        }
        current = tail;
        return current.data;
    }

    public PatientRecord displayOldest() {
        if (isEmpty()) {
            System.out.println("No patient history records available.");
            return null;
        }
        current = head;
        return current.data;
    }

    public PatientRecord getCurrent() {
        return current == null ? null : current.data;
    }
}
