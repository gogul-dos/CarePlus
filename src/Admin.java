import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Admin {
    private final String username = "Gogul";
    private final String password = "123";
    private final Scanner scan = new Scanner(System.in);
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void init() {
        try{
            showAdminMenu();

        } catch (Exception e) {
            System.out.println("invalid input !!");
            init();
        }
    }

    private void showAdminMenu() throws Exception{
        while(true){
            System.out.println("-----------Admin Menu-------------");
            System.out.println("1.Add Receptionist");
            System.out.println("2.Manage CarePlus");
            System.out.println("3.Logout");
            Integer option = Integer.parseInt(scan.nextLine());
            switch (option){
                case 1:
                    addReceptionist();
                    break;
                case 2:
                    new Manage().init();
                case 3:
                    new CarePlus().init();
                default:
                    System.out.println("Invalid Option !!");
            }
        }
    }

    private void addReceptionist() throws Exception{
        String name = enterName();
        String username = enterUsername();
        String password = enterPassword();
        Receptionist receptionist = new Receptionist(name,username,password);
        data.receptionists.put(username,receptionist);
        System.out.println("The following Receptionist Added Successfully");
        System.out.println(receptionist);
        return;
    }

    private String enterPassword() throws Exception{
        String password;
        while(true){
            System.out.print("Enter Your password: ");
            password = scan.nextLine();
            if(password.length()<2 ){
                System.out.println("password should be least 3 characters...");
            }
            else break;
        }
        return password;
    }

    private String enterUsername() throws Exception{
        String name;
        while(true){
            System.out.print("Enter Your username: ");
            name = scan.nextLine();
            if(name.length()<2 ){
                System.out.println("Username should be least 3 characters...");
            }
            else break;
        }
        return name;
    }

    private String enterName() throws Exception{
        String name;
        while(true){
            System.out.print("Enter Receptionist name: ");
            name = scan.nextLine();
            if(name.length()<2 ){
                System.out.println("name should be least 3 characters...");
            }
            else break;
        }
        return name;
    }



}
