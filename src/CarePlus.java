import java.util.Scanner;

public class CarePlus {
    private final Scanner scan = new Scanner(System.in);
    private final Admin admin = new Admin();
    public void init(){
        try{
            showMainMenu();
        } catch (Exception e) {
            System.out.println("Invalid Option !!");
            init();
        }
    }

    private void showMainMenu() throws Exception{
        while(true){
            System.out.println("---------Care Plus -------------");

            System.out.println("Admin username:Gogul");
            System.out.println("Admin password:123");
            System.out.println("1.Login Admin");
            System.out.println("2.Login Receptionist");
            System.out.print("Enter an option:");
            Integer option = Integer.parseInt(scan.nextLine());
            switch (option){
                case 1:
                    loginAdmin();
                    break;
                case 2:
                    loginReceptionist();
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
    }

    private void loginReceptionist() {
        try{
            String username = getUserName();
            if (Data.receptionists.get(username) == null) {
                System.out.println("username not found !!");
                if (needToContinue()) loginReceptionist();
                else return;
            }
            String password = getPassword();
            if (!Data.receptionists.get(username).password.equals(password)) {
                System.out.println("Password Mismatch !!");
                if (needToContinue()) loginReceptionist();
                else return;
            }

            if (Data.receptionists.get(username)!=null && Data.receptionists.get(username).password.equals(password)) {
                new Manage().init();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void loginAdmin() throws Exception{
        String username = getUserName();
        if(!username.equals(admin.getUsername())){
            System.out.println("Invalid username");
            if(needToContinue()) loginAdmin();
            else return;
        }
        String password = getPassword();
        if(!password.equals(admin.getPassword())){
            System.out.println("Password Mismatch!");
            if(needToContinue()) loginAdmin();
            else return;
        }
        if(username.equals(admin.getUsername()) && password.equals(admin.getPassword())){
            admin.init();
        }
    }

    private boolean needToContinue() {
        System.out.print("Do You Want To Continue? (Y/N)");
        return scan.nextLine().equalsIgnoreCase("y");
    }


    private String getPassword() {
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

    private String getUserName() {
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

    private void registerAdmin()throws Exception {
        loginAdmin();
    }


}
