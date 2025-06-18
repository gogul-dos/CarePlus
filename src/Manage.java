import java.util.List;
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
            System.out.println("3.Book Appointment");
            System.out.println("4.view Appointments");
            System.out.println("5.logout");
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
                    bookAppointment();
                    break;
                case 4:
                    new AppointmentManagement().init();
                case 5:
                    Utils.logout();
                    return;
                default:
                    System.out.println("Invalid Option !!");
            }
        }
    }

    private void bookAppointment() {
        System.out.println("Booking an appointment ");
        System.out.print("Enter the patient ID: ");
        String patientId = scan.nextLine();
        if(!Data.patients.containsKey(patientId)){
            System.out.println("Invalid patient ID");
            return;
        }
        Patient patient = Data.patients.get(patientId);
        System.out.println("Enter the type of  doctors need to be appointed (EX: baby doctor, heart): ");
        String required = scan.nextLine();
        if(!printDoctorsType(required)){
            System.out.println("No doctor available for : "+required );
            return;
        }
        System.out.println("Enter the doctor ID: ");
        String doctorId = scan.nextLine();
        if(Data.doctors.containsKey(doctorId)){
            Doctor doctor = Data.doctors.get(doctorId);
            System.out.println("Enter the date of appointment in Format: dd-MM-YYYY");
            String date = scan.nextLine();
            if(!doctor.availableSlots.containsKey(date)){
                System.out.println("Doctor not available on that date (OR) invalid date!");
                return;
            }
            List<Integer> availableSlots = doctor.availableSlots.get(date);
            if(availableSlots.isEmpty()){
                System.out.println("Selected doctor is busy on the selected day!!");
                return;
            }
            System.out.println("Available time slots are: ");
            for(int i=0; i<availableSlots.size(); i++){
                System.out.println(i+1+". "+availableSlots.get(i));
            }
            System.out.print("Select a time slot :");
            Integer time = Integer.parseInt(scan.nextLine());
            if(time>availableSlots.size() || time<0){
                System.out.println("Invalid time selection ");
                return;
            }
            Integer selectedTime = availableSlots.get(time-1);
            availableSlots.remove(time-1);
            doctor.availableSlots.put(date,availableSlots);
            Appointment appoinment = new Appointment(doctor,patient,selectedTime,date);
            patient.bookedAppoinments.add(appoinment);
            Data.appoinments.put(appoinment.appointmentId,appoinment);
            System.out.println("The Following Appointment is SuccessFull");
            System.out.println(appoinment);
        }else{
            System.out.println("Invalid doctor ID");
            if(needToContinue()) bookAppointment();
        }
    }

    private boolean printDoctorsType(String required) {
        boolean isSeen = false;
        for(Doctor doctor: Data.doctors.values()){
            if(required.equals(doctor.specialization)){
                isSeen = true;
                System.out.println(doctor);
            }
        }
        return isSeen;
    }

    private boolean needToContinue() {
        System.out.print("Do You Want To Continue? (Y/N)");
        return scan.nextLine().equalsIgnoreCase("y");
    }

}
