package base;

import careplus.CarePlus;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;
import entity.Receptionist;
import storage.Data;

import java.io.*;
import java.util.Map;
import java.util.Scanner;

public class Base {
    static Scanner scan = new Scanner(System.in);
    public static boolean  needToContinue() {
        System.out.print("Do You Want To Continue? (Y/N)");
        return scan.nextLine().equalsIgnoreCase("y");
    }
    public static void logout(){
        new CarePlus().init();
    }

//    public static void saveDetails() {
//
//        try{
//            File doctorFile = new File("doctors.dat");
//            File patientFile = new File("patients.dat");
//            File appointmentsFile = new File("appointments.dat");
//            File receptionistsFile = new File("receptionist.dat");
//            if (!doctorFile.exists()) doctorFile.createNewFile();
//            if (!patientFile.exists()) patientFile.createNewFile();
//            if (!appointmentsFile.exists()) appointmentsFile.createNewFile();
//            if (!receptionistsFile.exists()) receptionistsFile.createNewFile();
//            ObjectOutputStream ods = new ObjectOutputStream(new FileOutputStream(doctorFile));
//            ods.writeObject(Data.doctors);
//            ObjectOutputStream ops = new ObjectOutputStream(new FileOutputStream(patientFile));
//            ops.writeObject(Data.patients);
//            ObjectOutputStream oas = new ObjectOutputStream(new FileOutputStream(appointmentsFile));
//            oas.writeObject(Data.appoinments);
//            ObjectOutputStream ors = new ObjectOutputStream(new FileOutputStream(receptionistsFile));
//            ors.writeObject(Data.receptionists);
//            ods.close();
//            ops.close();
//            oas.close();
//            ors.close();
//            System.out.println("Files saved Successfully");
//        } catch (Exception e) {
//            System.out.println("Error occurred while writing  files");
//        }
//    }
//
//    public static void fetchDetails(){
//        try{
//            File doctorFile = new File("doctors.dat");
//            File patientFile = new File("patients.dat");
//            File appointmentsFile = new File("appointments.dat");
//            File receptionistsFile = new File("receptionist.dat");
//            if (!doctorFile.exists()) doctorFile.createNewFile();
//            if (!patientFile.exists()) patientFile.createNewFile();
//            if (!appointmentsFile.exists()) appointmentsFile.createNewFile();
//            if (!receptionistsFile.exists()) receptionistsFile.createNewFile();
//            if(doctorFile.length()>0){
//                ObjectInputStream idr = new ObjectInputStream(new FileInputStream(doctorFile));
//                Data.doctors=(Map<String, Doctor>)idr.readObject();
//                for(Doctor doctor: Data.doctors.values())  doctor.refreshSlots();
//                idr.close();
//                Doctor.doctorCounter = Data.doctors.size();
//            }
//            if(patientFile.length()>0){
//                ObjectInputStream ipr = new ObjectInputStream(new FileInputStream(patientFile));
//                Data.patients =(Map<String, Patient>) ipr.readObject();
//                ipr.close();
//                Patient.counter = Data.patients.size();
//            }
//            if(appointmentsFile.length()>0){
//                ObjectInputStream iar = new ObjectInputStream(new FileInputStream(appointmentsFile));
//                Data.appoinments = (Map<String, Appointment>) iar.readObject();
//                iar.close();
//                Appointment.counter = Data.appoinments.size();
//            }
//
//            if(receptionistsFile.length()>0){
//                ObjectInputStream irr = new ObjectInputStream(new FileInputStream(receptionistsFile));
//                Data.receptionists = (Map<String, Receptionist>) irr.readObject();
//                irr.close();
//                Receptionist.counter = Data.receptionists.size();
//            }
//            System.out.println("Files Loaded SuccessFully");
//        }
//        catch (Exception e){
//            System.out.println("Error occurred while reading files");
//            e.printStackTrace();
//        }
//    }
}
