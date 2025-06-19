package manage.adminmanage;

import base.Base;
import entity.Admin;
import entity.Receptionist;
import careplus.CarePlus;
import manage.doctormange.DoctorManagement;
import storage.Data;
import java.util.Scanner;

public class AdminManagement {

    private final Admin admin = Data.admin;
    private final Scanner scan = new Scanner(System.in);

    public String getPassword() {
        return admin.password;
    }

    public String getUsername() {
        return admin.username;
    }

    public void init() {
        try {
            showAdminMenu();
        } catch (Exception e) {
            System.out.println("Invalid input!!");
            if (Base.needToContinue()) init();
        }
    }

    private void showAdminMenu() throws Exception {
        while (true) {
            System.out.println("----------- Admin Management Menu -----------");
            System.out.println("1. Add Receptionist");
            System.out.println("2. Remove Receptionist");
            System.out.println("3. View Receptionists");
            System.out.println("4. Manage Doctors");
            System.out.println("5. Export Data");
            System.out.println("6. Import Data");
            System.out.println("7. Logout");
            System.out.print("Enter an option: ");

            try {
                String input = scan.nextLine();
                if (!input.matches("\\d+")) throw new NumberFormatException();
                int option = Integer.parseInt(input);
                switch (option) {
                    case 1 -> addReceptionist();
                    case 2 -> removeReceptionist();
                    case 3 -> viewReceptionists();
                    case 4 -> new DoctorManagement().init();
                    case 5 -> Base.saveDetails();
                    case 6 -> Base.fetchDetails();
                    case 7 -> {
                        new CarePlus().init();
                        return;
                    }
                    default -> System.out.println("Invalid Option!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private void viewReceptionists() {
        if (Data.receptionists.isEmpty()) {
            System.out.println("No data available regarding receptionists.");
            return;
        }
        System.out.println("--------- List of Receptionists ---------");
        for (Receptionist receptionist : Data.receptionists.values()) {
            System.out.println(receptionist);
            System.out.println("-----------------------------------------");
        }
    }

    private void removeReceptionist() {
        System.out.print("Enter receptionist username to remove: ");
        String username = scan.nextLine();
        if (!username.matches("[a-zA-Z]+")) {
            System.out.println("Username must contain only letters.");
            return;
        }
        if (Data.receptionists.containsKey(username)) {
            Data.receptionists.remove(username);
            System.out.println("Receptionist removed successfully.");
        } else {
            System.out.println("Receptionist not found.");
        }
    }

    private void addReceptionist() {
        try {
            String name = enterName();
            String user = enterUsername();
            String pass = enterPassword();
            Receptionist receptionist = new Receptionist(user, name, pass);
            Data.receptionists.put(user, receptionist);
            System.out.println("Receptionist added successfully:");
            System.out.println(receptionist);
        } catch (Exception e) {
            System.out.println("Error while adding receptionist.");
        }
    }

    private String enterPassword() {
        while (true) {
            System.out.print("Enter password: ");
            String pass = scan.nextLine();
            if (pass.length() < 3) {
                System.out.println("Password must be at least 3 characters.");
            } else if (!pass.matches("[a-zA-Z]+")) {
                System.out.println("Password must contain only letters.");
            } else return pass;
        }
    }

    private String enterUsername() {
        while (true) {
            System.out.print("Enter username: ");
            String name = scan.nextLine();
            if (name.length() < 3) {
                System.out.println("Username must be at least 3 characters.");
            } else if (!name.matches("[a-zA-Z]+")) {
                System.out.println("Username must contain only letters.");
            } else return name;
        }
    }

    private String enterName() {
        while (true) {
            System.out.print("Enter receptionist name: ");
            String name = scan.nextLine();
            if (name.length() < 3) {
                System.out.println("Name must be at least 3 characters.");
            } else if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Name must contain only letters and spaces.");
            } else return name;
        }
    }
}
