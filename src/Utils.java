import java.io.*;
import java.util.Map;
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

    public static void saveDetails() {
        try{
            File doctorFile = new File("doctors.dat");
            File patientFile = new File("patients.dat");
            File appointmentsFile = new File("appointments.dat");
            File receptionistsFile = new File("receptionist.dat");
            if (!doctorFile.exists()) doctorFile.createNewFile();
            if (!patientFile.exists()) patientFile.createNewFile();
            if (!appointmentsFile.exists()) appointmentsFile.createNewFile();
            if (!receptionistsFile.exists()) receptionistsFile.createNewFile();
            ObjectOutputStream ods = new ObjectOutputStream(new FileOutputStream(doctorFile));
            ods.writeObject(Data.doctors);
            ObjectOutputStream ops = new ObjectOutputStream(new FileOutputStream(patientFile));
            ops.writeObject(Data.patients);
            ObjectOutputStream oas = new ObjectOutputStream(new FileOutputStream(appointmentsFile));
            oas.writeObject(Data.appoinments);
            ObjectOutputStream ors = new ObjectOutputStream(new FileOutputStream(receptionistsFile));
            ors.writeObject(Data.appoinments);
            ods.close();
            ops.close();
            oas.close();
            ors.close();
            System.out.println("Files saved Successfully");
        } catch (Exception e) {
            System.out.println("Error occurred while writing  files");
        }
    }

    public static void fetchDetails(){
        try{
            File doctorFile = new File("doctors.dat");
            File patientFile = new File("patients.dat");
            File appointmentsFile = new File("appointments.dat");
            File receptionistsFile = new File("receptionist.dat");
            if (!doctorFile.exists()) doctorFile.createNewFile();
            if (!patientFile.exists()) patientFile.createNewFile();
            if (!appointmentsFile.exists()) appointmentsFile.createNewFile();
            if (!receptionistsFile.exists()) receptionistsFile.createNewFile();
            ObjectInputStream idr = new ObjectInputStream(new FileInputStream(doctorFile));
            Data.doctors=(Map<String, Doctor>)idr.readObject();
            ObjectInputStream ipr = new ObjectInputStream(new FileInputStream(patientFile));
            Data.patients =(Map<String, Patient>) ipr.readObject();
            ObjectInputStream iar = new ObjectInputStream(new FileInputStream(appointmentsFile));
            Data.appoinments = (Map<String, Appointment>) iar.readObject();
            ObjectInputStream irr = new ObjectInputStream(new FileInputStream(receptionistsFile));
            Data.receptionists = (Map<String,Receptionist>) irr.readObject();
            idr.close();
            ipr.close();
            iar.close();
            irr.close();
            System.out.println("Files Loaded SuccessFully");
        }
        catch (Exception e){
            System.out.println("Error occurred while reading files");
        }
    }
}
