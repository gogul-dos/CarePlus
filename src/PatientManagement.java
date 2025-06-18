import java.sql.SQLOutput;
import java.util.List;
import java.util.Scanner;

public class PatientManagement {
    Scanner scan = new Scanner(System.in);
    public void init(){
        try{
            while(true){
                System.out.println("--------------------Patient Management-----------------");
                System.out.println("1.Add Patient");
                System.out.println("2.view All patients");
                System.out.println("3.Search patient by name");
                System.out.println("4.Back");
                System.out.print("Enter an option: ");
                Integer option = Integer.parseInt(scan.nextLine());
                switch (option){
                    case 1:
                        addPatient();
                        break;
                    case 2:
                        viewAllPatient();
                        break;
                    case 3:
                        searchByName();
                    case 4:
                        return;
                }
            }
        }catch (Exception e){
            System.out.println("Error inside patient management ");
            if(Utils.needToContinue()) init();
        }
    }

    private void searchByName() {
        System.out.print("Enter a name to search: ");
        String searchInput = scan.nextLine();
        for(Patient patient: Data.patients.values()){
            if(patient.name.contains(searchInput)) System.out.println(patient);
            System.out.println("-------------------------------------------------------------------------");
        }
    }

    private void viewAllPatient() throws Exception{
        for(Patient patient : Data.patients.values()){
            System.out.println(patient);
            System.out.println("-------------------------------------------------------------------------");
        }
    }

    private void addPatient() {
        try{
            String name = getname();
            Long mobileNumber = getMobileNumber();
            Integer age = getAge();
            String genger = getGender();
            Patient patient = new Patient(name,mobileNumber,age,genger);
            Data.patients.put(patient.patientId,patient);
            System.out.println("Patient added successfully!!");
            return;
        }
        catch (Exception e){
            System.out.println("Error occurred while adding patient");
            if(Utils.needToContinue()) addPatient();
            else return;
        }
    }

    private String getGender() {
        System.out.print("Enter the gender of the patient: ");
        return scan.nextLine();
    }

    private Integer getAge() {
        System.out.print("Enter the age of the patient: ");
        return Integer.parseInt(scan.nextLine());
    }


    private Long getMobileNumber() {
        Long number;
        while(true){
            System.out.print("Enter Patient contact number: ");
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
            System.out.print("Enter the Patient name: ");
            name = scan.nextLine();
            if(name.length()<3){
                System.out.println("name should have least 3 characters ");
            }else break;
        }
        return name;
    }
}
