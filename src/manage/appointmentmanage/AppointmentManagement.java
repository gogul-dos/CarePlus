package manage.appointmentmanage;

import base.Base;
import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.PatientDAO;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AppointmentManagement {

    private final Scanner scan = new Scanner(System.in);
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final PatientDAO patientDAO = new PatientDAO();

    private final Map<String, Doctor> doctorMap = doctorDAO.getAllDoctors();
    private final Map<String, Patient> patientMap = patientDAO.getAllPatients();

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
                        case 4 -> {
                            return;
                        }
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

    private void viewAllAppointments() {
        Map<String, Appointment> appointments = appointmentDAO.getAllAppointments(doctorMap, patientMap);
        if (appointments.isEmpty()) {
            System.out.println("No appointments available.");
            return;
        }

        for (Appointment appointment : appointments.values()) {
            displayAppointment(appointment);
        }
    }

    private void viewDoctorAppointment() {
        String doctorId = getValidId("Doctor");
        if (doctorId == null) return;

        List<Appointment> list = appointmentDAO.getAppointmentsByDoctorId(doctorId, doctorMap, patientMap);
        if (list.isEmpty()) {
            System.out.println("No appointments found for the given doctor.");
            return;
        }

        for (Appointment appointment : list) {
            displayAppointment(appointment);
        }
    }

    private void viewPatientAppointment() {
        String patientId = getValidId("Patient");
        if (patientId == null) return;

        List<Appointment> list = appointmentDAO.getAppointmentsByPatientId(patientId, doctorMap, patientMap);
        if (list.isEmpty()) {
            System.out.println("No appointments found for the given patient.");
            return;
        }

        for (Appointment appointment : list) {
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

        String pattern = type.equals("Doctor") ? "D\\d+" : "P\\d+";

        if (!id.matches(pattern)) {
            System.out.println("Invalid " + type + " ID format. It must start with " +
                    (type.equals("Doctor") ? "'D' (e.g. D1)" : "'P' (e.g. P1)") + " followed by digits.");
            return null;
        }

        if (type.equals("Doctor") && !doctorMap.containsKey(id)) {
            System.out.println("Doctor ID not found.");
            return null;
        }

        if (type.equals("Patient") && !patientMap.containsKey(id)) {
            System.out.println("Patient ID not found.");
            return null;
        }

        return id;
    }
}
