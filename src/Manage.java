import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Manage {
    private final Scanner scan;
    public Manage() {
        scan = new Scanner(System.in);
    }

    public void init(){
        try{
            showMenu();
        } catch (Exception e) {
            System.out.println("Invalid Option");
            if(needToContinue()) init();
            else return;
        }
    }

    private void showMenu() throws Exception{
        while (true){
            System.out.println("-----------------Main Menu----------------");
            System.out.println("1.Doctor Management");
            System.out.println("2.Patient Management");
            System.out.println("3.logout");
            System.out.print("Enter an option: ");
            Integer option = Integer.parseInt(scan.nextLine());
            switch (option){
                case 1:
                    new DoctorManagement().init();
                    break;
                case 2:
                    new PatientManagement().init();
                    break;
                case 3:
                    Utils.logout();
                    return;
                default:
                    System.out.println("Invalid Option !!");
            }
        }
    }

    private void doctorManagement() {

    }

    private boolean needToContinue() {
        System.out.print("Do You Want To Continue? (Y/N)");
        return scan.nextLine().equalsIgnoreCase("y");
    }

}
