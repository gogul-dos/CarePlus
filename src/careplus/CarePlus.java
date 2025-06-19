package careplus;

import base.Base;
import dao.ReceptionistDAO;
import entity.Receptionist;
import manage.adminmanage.AdminManagement;
import manage.receptionistmanage.ReceptionistManage;

import java.util.Scanner;

public class CarePlus {
    private final Scanner scan = new Scanner(System.in);
    private final AdminManagement adminManagement = new AdminManagement();
    private final ReceptionistDAO receptionistDAO = new ReceptionistDAO();

    public void init() {
        try {
            showMainMenu();
        } catch (Exception e) {
            System.out.println("Invalid Option !!");
            if (Base.needToContinue()) init();
        }
    }

    private void showMainMenu() throws Exception {
        while (true) {
            System.out.println("---------Care Plus -------------");
            System.out.println("Admin username:Gogul");
            System.out.println("Admin password:123");
            System.out.println("1.Admin Login");
            System.out.println("2.Receptionist Login");
            System.out.println("3.Exit");
            System.out.print("Enter an option: ");

            int option;
            try {
                String input = scan.nextLine();
                if (!input.matches("\\d+")) {
                    throw new NumberFormatException();
                }
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input !!");
                continue;
            }

            switch (option) {
                case 1 -> loginAdmin();
                case 2 -> loginReceptionist();
                case 3 -> System.exit(0);
                default -> System.out.println("Invalid Option");
            }
        }
    }

    private void loginReceptionist() {
        try {
            String username = getUserName();
            Receptionist receptionist = receptionistDAO.getReceptionistByUsername(username);
            if (receptionist == null) {
                System.out.println("Username not found!");
                if (Base.needToContinue()) loginReceptionist();
                else return;
            }

            String password = getPassword();
            if (!receptionist.password.equals(password)) {
                System.out.println("Password Mismatch!");
                if (Base.needToContinue()) loginReceptionist();
                else return;
            }

            new ReceptionistManage().init();

        } catch (Exception e) {
            System.out.println("Error during receptionist login.");
            if (Base.needToContinue()) loginReceptionist();
        }
    }

    private void loginAdmin() throws Exception {
        String username = getUserName();
        if (!username.equals(adminManagement.getUsername())) {
            System.out.println("Invalid username");
            if (Base.needToContinue()) loginAdmin();
            else return;
        }
        String password = getPassword();
        if (!password.equals(adminManagement.getPassword())) {
            System.out.println("Password Mismatch!");
            if (Base.needToContinue()) loginAdmin();
            else return;
        }

        adminManagement.init();
    }

    private String getPassword() {
        while (true) {
            System.out.print("Enter Your password: ");
            String password = scan.nextLine();
            if (password.length() < 3) {
                System.out.println("Password should be at least 3 characters...");
            } else if (!password.matches("[a-zA-Z0-9]+")) {
                System.out.println("Password must contain only letters.");
            } else return password;
        }
    }

    private String getUserName() {
        while (true) {
            System.out.print("Enter Your username: ");
            String name = scan.nextLine();
            if (name.length() < 3) {
                System.out.println("Username should be at least 3 characters...");
            } else if (!name.matches("[a-zA-Z]+")) {
                System.out.println("Username must contain only letters.");
            } else return name;
        }
    }
}
