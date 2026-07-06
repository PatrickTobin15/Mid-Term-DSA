package com.patientms;

import java.util.Scanner;

// A menu that ties the queue and the history system together
public class Main {

    private static PatientQueue waitingQueue = new PatientQueue();
    private static PatientHistoryDLL history = new PatientHistoryDLL();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedHistoryRecords();
        boolean running = true;

        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addPatientToQueue();
                    break;
                case "2":
                    serveNextPatient();
                    break;
                case "3":
                    emergencyInsert();
                    break;
                case "4":
                    waitingQueue.printQueue();
                    break;
                case "5":
                    showNextHistoryRecord();
                    break;
                case "6":
                    showPreviousHistoryRecord();
                    break;
                case "7":
                    showNewestRecord();
                    break;
                case "8":
                    showOldestRecord();
                    break;
                case "9":
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option, please choose a number from the menu.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("===== Patient Management System =====");
        System.out.println("1. Add the tient to waiting queue");
        System.out.println("2. Serve the next patient (remove from front)");
        System.out.println("3. Emergency insert patient at position");
        System.out.println("4. Print the waiting queue");
        System.out.println("5. Patient History: Next record");
        System.out.println("6. Patient History: Previous record");
        System.out.println("7. Patient History: Show newest record");
        System.out.println("8. Patient History: Show oldest record");
        System.out.println("9. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addPatientToQueue() {
        System.out.print("Enter The Patient ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Patients Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the Reason for Visit: ");
        String reason = scanner.nextLine();
        waitingQueue.addPatient(new Patient(id, name, reason));
        System.out.println("Patient has been added to the queue.");
    }

    private static void serveNextPatient() {
        Patient served = waitingQueue.removePatient();
        if (served != null) {
            System.out.println("Now serving: " + served);
        }
    }

    private static void emergencyInsert() {
        System.out.print("Enter the Patient ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter the Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter the Reason for Visit: ");
        String reason = scanner.nextLine();
        System.out.print("Enter the position to insert at (0 = very front of queue): ");

        int position;
        try {
            position = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("That is not a valid number, cancelling the emergency insert.");
            return;
        }

        waitingQueue.insertAtPosition(new Patient(id, name, reason), position);
        System.out.println("Emergency patient has been entered into the queue.");
    }

    private static void showNextHistoryRecord() {
        PatientRecord record = history.next();
        if (record != null) {
            System.out.println(record);
        }
    }

    private static void showPreviousHistoryRecord() {
        PatientRecord record = history.previous();
        if (record != null) {
            System.out.println(record);
        }
    }

    private static void showNewestRecord() {
        PatientRecord record = history.displayNewest();
        if (record != null) {
            System.out.println("Newest record -> " + record);
        }
    }

    private static void showOldestRecord() {
        PatientRecord record = history.displayOldest();
        if (record != null) {
            System.out.println("Oldest record -> " + record);
        }
    }

    // preloads 10 records so there's data to navigate through
    private static void seedHistoryRecords() {
        history.addRecord(new PatientRecord("2026-01-05", "Seasonal Flu", "Prescribed rest and fluids, follow up in 1 week."));
        history.addRecord(new PatientRecord("2026-01-20", "Sprained Ankle", "Applied brace, recommended ice and elevation."));
        history.addRecord(new PatientRecord("2026-02-10", "Routine Checkup", "All vitals normal, no concerns noted."));
        history.addRecord(new PatientRecord("2026-02-28", "Migraine", "Prescribed pain relief, advised to reduce screen time."));
        history.addRecord(new PatientRecord("2026-03-15", "Allergic Reaction", "Administered antihistamine, monitored for 30 minutes."));
        history.addRecord(new PatientRecord("2026-04-02", "Minor Laceration", "Cleaned and stitched wound, tetanus shot given."));
        history.addRecord(new PatientRecord("2026-04-25", "High Blood Pressure", "Started on low-dose medication, follow up in 1 month."));
        history.addRecord(new PatientRecord("2026-05-10", "Follow-up Checkup", "Blood pressure improving, continue medication."));
        history.addRecord(new PatientRecord("2026-06-01", "Common Cold", "Advised rest and over-the-counter medication."));
        history.addRecord(new PatientRecord("2026-06-20", "Annual Physical", "Overall health good, updated vaccination records."));
    }
}
