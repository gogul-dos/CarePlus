package manage.patientmanage;

import base.Base;
import dao.PatientDAO;
import entity.Patient;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class PatientManagement {

    private final Scanner scan = new Scanner(System.in);
    private final PatientDAO patientDAO = new PatientDAO();

    public void init() {
        try {
            while (true) {
                System.out.println("-------------------- Patient Management -----------------");
                System.out.println("1. Add Patient");
                System.out.println("2. View All Patients");
                System.out.println("3. Search Patient by Name");
                System.out.println("4. Back");
                System.out.print("Enter an option: ");
                try {
                    String input = scan.nextLine();
                    if (!input.matches("\\d+")) throw new NumberFormatException();
                    int option = Integer.parseInt(input);
                    switch (option) {
                        case 1 -> addPatient();
                        case 2 -> viewAllPatient();
                        case 3 -> searchByName();
                        case 4 -> { return; }
                        default -> System.out.println("Invalid Option!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error inside patient management.");
            if (Base.needToContinue()) init();
        }
    }

    private void viewAllPatient() {
        Map<String, Patient> patients = patientDAO.getAllPatients();
        if (patients.isEmpty()) {
            System.out.println("No data available regarding patients.");
            return;
        }

        for (Patient patient : patients.values()) {
            System.out.println(patient);
            System.out.println("-------------------------------------------------------------------------");
        }
    }

    private void searchByName() {
        Map<String, Patient> patients = patientDAO.getAllPatients();

        System.out.print("Enter a name to search: ");
        String searchInput = scan.nextLine();
        boolean found = false;

        for (Patient patient : patients.values()) {
            if (patient.name.toLowerCase().contains(searchInput.toLowerCase())) {
                System.out.println(patient);
                System.out.println("-------------------------------------------------------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No patient found matching the search input.");
        }
    }

    private void addPatient() {
        try {
            String name = getName();
            Long mobileNumber = getMobileNumber();
            Integer age = getAge();
            String gender = getGender();

            Patient patient = new Patient(name, mobileNumber, age, gender);
            patient.patientId = patientDAO.getNextPatientId();
            boolean success = patientDAO.addPatient(patient);

            if (success) {
                System.out.println("The following Patient added successfully!");
                System.out.println(patient);
            } else {
                System.out.println("Failed to add patient.");
            }

        } catch (Exception e) {
            System.out.println("Error occurred while adding patient.");
            if (Base.needToContinue()) addPatient();
        }
    }

    private String getGender() {
        while (true) {
            System.out.print("Enter the gender of the patient (Male/Female/Other): ");
            String gender = scan.nextLine();
            if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female") || gender.equalsIgnoreCase("other")) {
                return gender.substring(0, 1).toUpperCase() + gender.substring(1).toLowerCase();
            } else {
                System.out.println("Invalid gender! Only Male, Female, or Other allowed.");
            }
        }
    }

    private Integer getAge() {
        while (true) {
            System.out.print("Enter the age of the patient: ");
            String input = scan.nextLine();
            if (!input.matches("\\d+")) {
                System.out.println("Age must be a valid number.");
            } else {
                int age = Integer.parseInt(input);
                if (age <= 0) {
                    System.out.println("Age must be greater than zero.");
                } else {
                    return age;
                }
            }
        }
    }

    private Long getMobileNumber() {
        while (true) {
            System.out.print("Enter patient contact number: ");
            String input = scan.nextLine();
            if (!input.matches("\\d{10}")) {
                System.out.println("Contact number must be exactly 10 digits.");
            } else {
                Long number = Long.parseLong(input);
                if (number < 6000000000L || number > 9999999999L) {
                    System.out.println("Invalid number! Must be a 10-digit number starting from 6–9.");
                } else {
                    return number;
                }
            }
        }
    }

    private String getName() {
        while (true) {
            System.out.print("Enter the patient name: ");
            String name = scan.nextLine();
            if (name.length() < 3) {
                System.out.println("Name should have at least 3 characters.");
            } else if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Name must contain only letters and spaces.");
            } else {
                return name;
            }
        }
    }
}
