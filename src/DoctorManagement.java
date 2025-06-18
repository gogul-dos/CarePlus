import java.util.Scanner;


public class DoctorManagement{
    private final Scanner scan = new Scanner(System.in);
    public void init(){
        try{
            showDoctorMenu();
        } catch (Exception e) {
            System.out.println("Invalid option !!");
            if (needToContinue()) init();
            else return;
        }
    }

    public void showDoctorMenu(){
        while(true){
            System.out.println("--------------- Doctor Menu ---------------");
            System.out.println("1.Add Doctor");
            System.out.println("2.view available doctors");
            System.out.print("Choose an option: ");
            Integer option = Integer.parseInt(scan.nextLine());
            switch (option){
                case 1:
                    addDoctor();
                    break;
                case 2:
                    viewAvailableDoctors();
                    break;
                default:
                    System.out.println("Invalid Option !!");

            }
        }
    }

    private void viewAvailableDoctors() {
        for(Doctor doctor: data.doctors.values()){
            System.out.println(doctor);
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
            Doctor doctor = new Doctor(name,mobileNumber,startTime,endTime);
            data.doctors.put(name,doctor);
            return;
        }
        catch (Exception e){
            System.out.println("Invalid Input");
            if(needToContinue()) addDoctor();
            else return;
        }

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
                    return new Integer[]{12,3};
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

    public boolean  needToContinue() {
        System.out.print("Do You Want To Continue? (Y/N)");
        return scan.nextLine().equalsIgnoreCase("y");
    }
}
