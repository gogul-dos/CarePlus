package manage.receptionistmanage;

import base.Base;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import manage.appointmentmanage.AppointmentManagement;
import manage.doctormange.DoctorManagement;
import manage.patientmanage.PatientManagement;
import storage.Data;

import java.util.List;
import java.util.Scanner;

public class ReceptionistManage {
    private final Scanner scan = new Scanner(System.in);

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
        if (Data.doctors.isEmpty()) {
            System.out.println("No data available regarding doctors.");
            return;
        }
        for (Doctor doctor : Data.doctors.values()) {
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
            if (!Data.patients.containsKey(patientId)) {
                System.out.println("Invalid patient ID");
                return;
            }
            Patient patient = Data.patients.get(patientId);

            System.out.print("Enter required specialization (e.g. heart): ");
            String specialization = scan.nextLine();
            boolean found = false;
            for (Doctor doc : Data.doctors.values()) {
                if (doc.specialization.equalsIgnoreCase(specialization)) {
                    System.out.println(doc);
                    System.out.println("------------------------------------------------------------------------");
                    found = true;
                }
            }
            if (!found) {
                System.out.println("No doctors found for specialization: " + specialization);
                return;
            }

            System.out.print("Enter doctor ID: ");
            String doctorId = scan.nextLine();
            if (!doctorId.matches("D\\d+")) {
                System.out.println("Invalid Doctor ID format. Use format like D1, D2.");
                return;
            }
            if (!Data.doctors.containsKey(doctorId)) {
                System.out.println("Invalid doctor ID");
                return;
            }

            Doctor doctor = Data.doctors.get(doctorId);
            System.out.print("Enter appointment date (dd-MM-yyyy): ");
            String date = scan.nextLine();

            if (!doctor.availableSlots.containsKey(date) || doctor.availableSlots.get(date).isEmpty()) {
                System.out.println("Doctor not available on that date.");
                return;
            }

            List<Integer> slots = doctor.availableSlots.get(date);
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

            int time = slots.remove(slotChoice - 1);
            doctor.availableSlots.put(date, slots);

            Appointment appointment = new Appointment(doctor, patient, time, date);
            patient.bookedAppoinments.add(appointment);
            Data.appoinments.put(appointment.appointmentId, appointment);

            System.out.println("Appointment booked successfully:");
            System.out.println(appointment);
        } catch (Exception e) {
            System.out.println("Error while booking.");
            if (Base.needToContinue()) bookAppointment();
        }
    }
}
