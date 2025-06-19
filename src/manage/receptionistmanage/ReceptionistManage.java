package manage.receptionistmanage;

import base.Base;
import dao.AppointmentDAO;
import dao.DoctorDAO;
import dao.DoctorSlotDAO;
import dao.PatientDAO;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import manage.appointmentmanage.AppointmentManagement;
import manage.doctormange.DoctorManagement;
import manage.patientmanage.PatientManagement;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReceptionistManage {
    private final Scanner scan = new Scanner(System.in);
    private final DoctorDAO doctorDAO = new DoctorDAO();
    private final PatientDAO patientDAO = new PatientDAO();
    private final AppointmentDAO appointmentDAO = new AppointmentDAO();
    private final DoctorSlotDAO doctorSlotDAO = new DoctorSlotDAO();

    public void init() {
        try {
            while (true) {
                System.out.println("-------- Receptionist Menu --------");
                System.out.println("1. Manage Patient");
                System.out.println("2. View Doctors");
                System.out.println("3. Book Appointment");
                System.out.println("4. View Appointments");
                System.out.println("5. View Doctor Available Slots");
                System.out.println("6. Logout");
                System.out.print("Choose an option: ");
                try {
                    String input = scan.nextLine();
                    if (!input.matches("\\d+")) throw new NumberFormatException();
                    int option = Integer.parseInt(input);

                    switch (option) {
                        case 1 -> new PatientManagement().init();
                        case 2 -> viewDoctors();
                        case 3 -> bookAppointment();
                        case 4 -> new AppointmentManagement().init();
                        case 5 -> new DoctorManagement().availableSlotsOfDoctor();
                        case 6 -> {
                            Base.logout();
                            return;
                        }
                        default -> System.out.println("Invalid option.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid number.");
                }
            }
        } catch (Exception e) {
            System.out.println("Receptionist error.");
            if (Base.needToContinue()) init();
        }
    }

    private void viewDoctors() {
        Map<String, Doctor> doctors = doctorDAO.getAllDoctors();
        if (doctors.isEmpty()) {
            System.out.println("No data available regarding doctors.");
            return;
        }
        for (Doctor doctor : doctors.values()) {
            System.out.println(doctor);
            System.out.println("---------------------------------------------------");
        }
    }

    private void bookAppointment() {
        try {
            System.out.println("Booking an appointment");

            System.out.print("Enter patient ID: ");
            String patientId = scan.nextLine();
            if (!patientId.matches("P\\d+")) {
                System.out.println("Invalid Patient ID format. Use format like P1, P2.");
                return;
            }
            Patient patient = patientDAO.getPatientById(patientId);
            if (patient == null) {
                System.out.println("Patient not found.");
                return;
            }

            System.out.print("Enter required specialization (e.g. heart): ");
            String specialization = scan.nextLine();
            Map<String, Doctor> doctorMap = doctorDAO.getDoctorsBySpecialization(specialization);
            if (doctorMap.isEmpty()) {
                System.out.println("No doctors found for specialization: " + specialization);
                return;
            }

            for (Doctor doc : doctorMap.values()) {
                System.out.println(doc);
                System.out.println("------------------------------------------------------------------------");
            }

            System.out.print("Enter doctor ID: ");
            String doctorId = scan.nextLine();
            if (!doctorId.matches("D\\d+")) {
                System.out.println("Invalid Doctor ID format.");
                return;
            }
            Doctor doctor = doctorDAO.getDoctorById(doctorId);
            if (doctor == null) {
                System.out.println("Doctor not found.");
                return;
            }

            System.out.print("Enter appointment date (dd-MM-yyyy): ");
            String date = scan.nextLine();

            List<Integer> slots = doctorSlotDAO.getAvailableSlotsByDoctorAndDate(doctorId, date);
            if (slots == null || slots.isEmpty()) {
                System.out.println("Doctor not available on that date.");
                return;
            }

            System.out.println("Available slots:");
            for (int i = 0; i < slots.size(); i++) {
                System.out.println((i + 1) + ". " + slots.get(i));
            }

            System.out.print("Choose slot number: ");
            String slotInput = scan.nextLine();
            if (!slotInput.matches("\\d+")) {
                System.out.println("Invalid slot input.");
                return;
            }
            int slotChoice = Integer.parseInt(slotInput);
            if (slotChoice < 1 || slotChoice > slots.size()) {
                System.out.println("Invalid slot.");
                return;
            }

            int time = slots.get(slotChoice - 1);
            Appointment appointment = new Appointment(doctor, patient, time, date);
            appointment.appointmentId = appointmentDAO.getNextAppointmentId();
            boolean success = appointmentDAO.bookAppointment(appointment);
            if (success) {
                System.out.println("Appointment booked successfully.");
                doctorSlotDAO.removeSlot(doctorId, date, time);
            } else {
                System.out.println("Failed to book appointment.");
            }

        } catch (Exception e) {
            System.out.println("Error while booking.");
            if (Base.needToContinue()) bookAppointment();
        }
    }
}
