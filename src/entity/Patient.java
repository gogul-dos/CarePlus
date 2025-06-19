package entity;

import storage.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient implements Serializable {
    private static final long serialVersionUID = 3L;
    public String patientId;
    public String name;
    Long mobileNumber;
    Integer age;
    String gender;
    public List<Appointment> bookedAppoinments;
    public static Integer counter = Data.patients.size();

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
        List<Appointment> todaysAppointments = new ArrayList<>();
        for(Appointment appoinment: bookedAppoinments){
            if(appoinment.date.equals(today)) todaysAppointments.add(appoinment);
        }

        if(todaysAppointments.isEmpty()){
            result.append("No Appointments");
            return result.toString();
        }
        for(Appointment appointment: todaysAppointments){
            result.append(appointment.appointmentId).append("  ");
        }
        return result.toString();
    }
}
