package manage.doctormange;

import base.Base;
import entity.Doctor;
import storage.Data;

import java.util.Scanner;

public class DoctorManagement {
    private final Scanner scan = new Scanner(System.in);

    public void init() {
        try {
            while (true) {
                System.out.println("--------------- Doctor Management Menu ---------------");
                System.out.println("1. Add Doctor");
                System.out.println("2. Remove Doctor");
                System.out.println("3. View All Doctors");
                System.out.println("4. View Available Slots of a Doctor");
                System.out.println("5. Back");
                System.out.print("Choose an option: ");
                try {
                    String input = scan.nextLine();
                    if (!input.matches("\\d+")) throw new NumberFormatException();
                    int option = Integer.parseInt(input);
                    switch (option) {
                        case 1 -> addDoctor();
                        case 2 -> removeDoctor();
                        case 3 -> viewAvailableDoctors();
                        case 4 -> availableSlotsOfDoctor();
                        case 5 -> { return; }
                        default -> System.out.println("Invalid Option !!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid number.");
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid input encountered!");
            if (Base.needToContinue()) init();
        }
    }

    private void addDoctor() {
        try {
            String name = getName();
            Long mobileNumber = getMobileNumber();
            Integer[] times = getTime();
            Integer startTime = times[0];
            Integer endTime = times[1];
            String specialization = getSpecialization();
            Doctor doctor = new Doctor(name, mobileNumber, startTime, endTime, specialization);
            Data.doctors.put(doctor.doctorId, doctor);
            System.out.println("The following Doctor added successfully.");
            System.out.println(doctor);
        } catch (Exception e) {
            System.out.println("Error while adding doctor.");
            if (Base.needToContinue()) addDoctor();
        }
    }

    private void removeDoctor() {
        System.out.print("Enter Doctor ID to remove: ");
        String doctorId = scan.nextLine();
        if (!doctorId.matches("D\\d+")) {
            System.out.println("Invalid Doctor ID format. Use format like D1, D2.");
            return;
        }
        if (Data.doctors.containsKey(doctorId)) {
            Data.doctors.remove(doctorId);
            System.out.println("Doctor removed successfully.");
        } else {
            System.out.println("Doctor ID not found.");
        }
    }

    public void viewAvailableDoctors() {
        if (Data.doctors.isEmpty()) {
            System.out.println("No data available regarding doctors.");
            return;
        }
        for (Doctor doctor : Data.doctors.values()) {
            System.out.println(doctor);
            System.out.println("-------------------------------------------------------------------------");
        }
    }

    public void availableSlotsOfDoctor() {
        System.out.print("Enter the doctor ID: ");
        String doctorId = scan.nextLine();
        if (!doctorId.matches("D\\d+")) {
            System.out.println("Invalid Doctor ID format. Use format like D1, D2.");
            return;
        }
        if (!Data.doctors.containsKey(doctorId)) {
            System.out.println("Invalid Doctor ID");
            return;
        }
        Doctor doctor = Data.doctors.get(doctorId);
        if (doctor.availableSlots.isEmpty()) {
            System.out.println("No available slots found for this doctor.");
            return;
        }
        for (String date : doctor.availableSlots.keySet()) {
            System.out.println(date + " -> " + doctor.availableSlots.get(date));
        }
    }

    private String getSpecialization() {
        System.out.print("Enter doctor specialization: ");
        return scan.nextLine();
    }

    private Integer[] getTime() {
        while (true) {
            System.out.println("1. Morning Schedule");
            System.out.println("2. Afternoon Schedule");
            System.out.println("3. Custom Time");
            System.out.print("Choose option: ");
            try {
                String input = scan.nextLine();
                if (!input.matches("\\d+")) throw new NumberFormatException();
                int option = Integer.parseInt(input);
                switch (option) {
                    case 1 -> { return new Integer[]{9, 12}; }
                    case 2 -> { return new Integer[]{12, 15}; }
                    case 3 -> {
                        System.out.print("Start time (e.g., 9 for 9AM, 13 for 1PM): ");
                        int start = Integer.parseInt(scan.nextLine());
                        System.out.print("End time (e.g., 17 for 5PM): ");
                        int end = Integer.parseInt(scan.nextLine());
                        return new Integer[]{start, end};
                    }
                    default -> System.out.println("Invalid option. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Enter valid number.");
            }
        }
    }

    private Long getMobileNumber() {
        while (true) {
            System.out.print("Enter doctor contact number: ");
            try {
                String input = scan.nextLine();
                if (!input.matches("\\d{10}")) throw new NumberFormatException();
                Long number = Long.parseLong(input);
                if (number < 6000000000L || number > 9999999999L) {
                    System.out.println("Invalid number! Must be 10 digits starting from 6-9.");
                } else return number;
            } catch (NumberFormatException e) {
                System.out.println("Enter only 10 digit number.");
            }
        }
    }

    private String getName() {
        while (true) {
            System.out.print("Enter the doctor name: ");
            String name = scan.nextLine();
            if (name.length() < 3) {
                System.out.println("Name should have at least 3 characters.");
            } else if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Name must contain only letters and spaces.");
            } else return name;
        }
    }
}
