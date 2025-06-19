package manage.appointmentmanage;

import base.Base;
import entity.Appointment;
import storage.Data;

import java.util.Scanner;

public class AppointmentManagement {

    Scanner scan = new Scanner(System.in);

    public void init() {
        try {
            while (true) {
                System.out.println("--------------- Appointment Management ---------------");
                System.out.println("1. View All Appointments");
                System.out.println("2. View Doctor-Specific Appointments");
                System.out.println("3. View Patient-Specific Appointments");
                System.out.println("4. Back");
                System.out.print("Enter an option: ");

                try {
                    String input = scan.nextLine();
                    if (!input.matches("\\d+")) throw new NumberFormatException();
                    int option = Integer.parseInt(input);

                    switch (option) {
                        case 1 -> viewAllAppointments();
                        case 2 -> viewDoctorAppointment();
                        case 3 -> viewPatientAppointment();
                        case 4 -> { return; }
                        default -> System.out.println("Invalid choice.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error inside Appointment Management class!");
            if (Base.needToContinue()) init();
        }
    }

    private void viewPatientAppointment() {
        String patientId = getValidId("Patient");
        if (patientId == null) return;

        boolean found = false;
        for (Appointment appointment : Data.appoinments.values()) {
            if (appointment.patient.patientId.equalsIgnoreCase(patientId)) {
                displayAppointment(appointment);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointments found for the given patient.");
        }
    }

    private void viewDoctorAppointment() {
        String doctorId = getValidId("Doctor");
        if (doctorId == null) return;

        boolean found = false;
        for (Appointment appointment : Data.appoinments.values()) {
            if (appointment.doctor.doctorId.equalsIgnoreCase(doctorId)) {
                displayAppointment(appointment);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No appointments found for the given doctor.");
        }
    }

    private void viewAllAppointments() {
        if (Data.appoinments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        for (Appointment appointment : Data.appoinments.values()) {
            displayAppointment(appointment);
        }
    }

    private void displayAppointment(Appointment appointment) {
        System.out.println("Appointment ID: " + appointment.appointmentId);
        System.out.println("Assigned Doctor: " + appointment.doctor.name);
        System.out.println("Appointed Patient: " + appointment.patient.name);
        System.out.println("Date: " + appointment.date + " | Time: " + appointment.time + " (24-hour format)");
        System.out.println("--------------------------------------------------------------");
    }

    private String getValidId(String type) {
        System.out.print("Enter the " + type + " ID: ");
        String id = scan.nextLine();

        String pattern = type.equals("Doctor") ? "[D]\\d+" : "[P]\\d+";

        if (!id.matches(pattern)) {
            System.out.println("Invalid " + type + " ID format. It must start with " +
                    (type.equals("Doctor") ? "'d' (e.g. d1)" : "'p' (e.g. p1)") + " followed by digits.");
            return null;
        }

        if (type.equals("Doctor") && !Data.doctors.containsKey(id.toLowerCase())) {
            System.out.println("Doctor ID not found.");
            return null;
        }

        if (type.equals("Patient") && !Data.patients.containsKey(id.toLowerCase())) {
            System.out.println("Patient ID not found.");
            return null;
        }

        return id.toLowerCase();
    }
}
