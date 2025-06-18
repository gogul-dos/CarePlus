import java.util.Scanner;

public class Utils {
    static Scanner scan = new Scanner(System.in);
    public static boolean  needToContinue() {
        System.out.print("Do You Want To Continue? (Y/N)");

        return scan.nextLine().equalsIgnoreCase("y");
    }

    public static void logout(){
        new CarePlus().init();
    }
}
