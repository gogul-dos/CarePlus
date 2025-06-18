import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient {
    String patientId;
    String name;
    Long mobileNumber;
    Integer age;
    String gender;
    List<Appoinment> bookedAppoinments;
    static Integer counter = 0;

    public Patient(String name, Long mobileNumber, Integer age, String gender) {
        this.patientId = "P" + ++counter;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.bookedAppoinments = new ArrayList<>();
    }

    public String toString(){
        StringBuilder result = new StringBuilder("Patient ID: " + this.patientId + "\nName: "
                + this.name + "\nAge: " + this.age + "\nGender: " + this.gender
                + "\nContact Info: " + this.mobileNumber +"\nToday Appointment Id's are:");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        String today = sdf.format(new Date());
        List<Appoinment> todaysAppointments = new ArrayList<>();
        for(Appoinment appoinment: bookedAppoinments){
            if(appoinment.date.equals(today)) todaysAppointments.add(appoinment);
        }

        if(todaysAppointments.isEmpty()){
            result.append("No Appointments");
            return result.toString();
        }
        for(Appoinment appoinment: todaysAppointments){
            result.append(appoinment.appointmentId+"  ");
        }

        return result.toString();
    }
}
