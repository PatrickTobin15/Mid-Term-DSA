// Queue for the waiting room, built with a linked list instead of java.util.LinkedList so it shows how a queue actually works
public class PatientQueue {

    private class Node {
        Patient data;
        Node next;

        Node(Patient data) {
            this.data = data;
        }
    }

    private Node front;
    private Node rear;
    private int size;

    public PatientQueue() {
        front = null;
        rear = null;
        size = 0;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int size() {
        return size;
    }

    // add a patient to the back of the line
    public void addPatient(Patient patient) {
        Node newNode = new Node(patient);
        if (isEmpty()) {
            front = newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // takes the patient off the front and returns them
    public Patient removePatient() {
        if (isEmpty()) {
            System.out.println("The waiting queue is empty, no patients to serve.");
            return null;
        }
        Patient served = front.data;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        size--;
        return served;
    }

    // inserts a patient at a specific spot which should be used for emergencies
    // position 0 is for the front of the line
    public void insertAtPosition(Patient patient, int position) {
        if (position < 0 || position > size) {
            System.out.println("Invalid position, adding to the back of the queue instead.");
            addPatient(patient);
            return;
        }

        if (position == 0) {
            Node newNode = new Node(patient);
            newNode.next = front;
            front = newNode;
            if (rear == null) {
                rear = newNode;
            }
            size++;
            return;
        }

        Node current = front;
        for (int i = 0; i < position - 1; i++) {
            current = current.next;
        }

        Node newNode = new Node(patient);
        newNode.next = current.next;
        current.next = newNode;
        if (newNode.next == null) {
            rear = newNode;
        }
        size++;
    }

    public void printQueue() {
        if (isEmpty()) {
            System.out.println("The waiting queue is currently empty.");
            return;
        }
        System.out.println("--- Current Patient Waiting Queue ---");
        Node current = front;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.data);
            current = current.next;
            position++;
        }
    }
}
