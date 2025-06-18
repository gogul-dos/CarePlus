import java.util.List;
import java.util.Scanner;


public class DoctorManagement{
    private final Scanner scan = new Scanner(System.in);
    public void init(){
        try{
            while(true){
                System.out.println("--------------- Doctor Menu ---------------");
                System.out.println("1.Add Doctor");
                System.out.println("2.view all doctors");
                System.out.println("3.available slots of a doctor");
                System.out.println("4.Back");
                System.out.print("Choose an option: ");
                Integer option = Integer.parseInt(scan.nextLine());
                switch (option){
                    case 1:
                        addDoctor();
                        break;
                    case 2:
                        viewAvailableDoctors();
                        break;
                    case 3:
                        availableSlotsOfDoctor();
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("Invalid Option !!");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Invalid option !!");
            if (Utils.needToContinue()) init();
        }
    }

    private void availableSlotsOfDoctor() {
        System.out.print("Enter the doctor ID: ");
        String doctorId = scan.nextLine();
        if(!Data.doctors.containsKey(doctorId)){
            System.out.println("Invalid Doctor ID");
            return;
        }
        Doctor doctor = Data.doctors.get(doctorId);
        for(String date: doctor.availableSlots.keySet()){
            System.out.println(date+"->"+doctor.availableSlots.get(date));
        }
        return;
    }

    private void viewAvailableDoctors() {
        for(Doctor doctor: Data.doctors.values()){
            System.out.println(doctor);
            System.out.println("-------------------------------------------------------------------------");
        }
        return;
    }

    private void addDoctor(){
        try{
            String name = getname();
            Long mobileNumber = getMobileNumber();
            Integer[] times = getTime();
            Integer startTime = times[0];
            Integer endTime = times[1];
            String specialization = getSpecialization();
            Doctor doctor = new Doctor(name,mobileNumber,startTime,endTime,specialization);
            Data.doctors.put(doctor.doctorId,doctor);
            System.out.println("Doctor Added successfully");
            return;
        }
        catch (Exception e){
            System.out.println("Invalid Input");
            if(Utils.needToContinue()) addDoctor();
            else return;
        }

    }

    private String getSpecialization(){
        System.out.print("Enter doctor specialization: ");
        return scan.nextLine();
    }

    private Integer[] getTime() throws Exception{
        while(true){
            System.out.println("1.Morning Schedule");
            System.out.println("2.Afternoon Schedule");
            System.out.println("3.Specific Time");
            Integer option = Integer.parseInt(scan.nextLine());
            switch (option){
                case 1:
                    return new Integer[]{9,12};
                case 2:
                    return new Integer[]{12,15};
                case 3:
                    System.out.print("Starting work time in integer format (EX:9 for 9 AM and 13 for 1 PM) : ");
                    Integer start = Integer.parseInt(scan.nextLine());
                    System.out.print("Ending work time in integer format (EX:9 for 9 AM and 13 for 1 PM) : ");
                    Integer end = Integer.parseInt(scan.nextLine());
                    return new Integer[]{start,end};
                default:
                    System.out.println("Invalid Option ");
            }
        }
    }

    private Long getMobileNumber() {
        Long number;
        while(true){
            System.out.print("Enter Doctor contact number: ");
            number = Long.parseLong(scan.nextLine());
            if(number<Long.parseLong("5999999999") || number> Long.parseLong("9999999999")){
                System.out.println("invalid number !!");
            }
            else break;
        }
        return number;
    }

    private String getname() {
        String name;
        while(true){
            System.out.print("Enter the Doctor name: ");
            name = scan.nextLine();
            if(name.length()<3){
                System.out.println("name should have least 3 characters ");
            }else break;
        }
        return name;
    }
}
