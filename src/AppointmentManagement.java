import java.util.Map;
import java.util.Scanner;

public class AppointmentManagement {
    Scanner scan = new Scanner(System.in);
    public void init(){
        try{
            while(true){
                System.out.println("---------------------Appointment Management------------------");
                System.out.println("1.View All Appointments");
                System.out.println("2.View Doctor Specific Appointments");
                System.out.println("3.View Patient Specific Appointments");
                System.out.println("4.back");
                Integer option = Integer.parseInt(scan.nextLine());
                switch (option){
                    case 1:
                        viewAllAppointments();
                        break;
                    case 2:
                        viewDoctorAppointment();
                        break;
                    case 3:
                        viewPatientAppointment();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid choice ");
                        break;
                }

            }
        } catch (Exception e) {
            System.out.println("Error inside appointment management class !!");
            if(Utils.needToContinue()) init();
        }
        return;
    }

    private void viewPatientAppointment() {
        System.out.print("Enter the Patient ID: ");
        String patientId = scan.nextLine();
        if(!Data.patients.containsKey(patientId)){
            System.out.println("Invalid patient ID ");
            return;
        }
        Map<String, Appointment> appointments = Data.appoinments;
        for(String key: appointments.keySet()){
            Appointment appoinment = appointments.get(key);
            if(appoinment.patient.patientId.equals(patientId)) displayAppointment(appoinment);
        }
    }

    private void viewDoctorAppointment() {
        System.out.print("Enter the doctor ID: ");
        String doctorId = scan.nextLine();
        if(!Data.doctors.containsKey(doctorId)){
            System.out.println("Invalid Doctor ID ");
            return;
        }
        Map<String, Appointment> appointments = Data.appoinments;
        for(String key: appointments.keySet()){
            Appointment appoinment = appointments.get(key);
            if(appoinment.doctor.doctorId.equals(doctorId)) displayAppointment(appoinment);
        }
    }

    private void viewAllAppointments() {
        Map<String, Appointment> appointments = Data.appoinments;
        for(String key: appointments.keySet()){
            Appointment appoinment = appointments.get(key);
            displayAppointment(appoinment);
        }
    }
    private void displayAppointment(Appointment appoinment){
        System.out.println("Appointment ID: "+appoinment.appointmentId);
        System.out.println("Assigned Doctor: "+appoinment.doctor.name);
        System.out.println("Appointed Patient: "+ appoinment.patient.name);
        System.out.println("Appointed date: "+ appoinment.date + " Time: "+ appoinment.time+" in 24hrs format");
        System.out.println("------------------------------------------------------------------------------------");
    }
}
