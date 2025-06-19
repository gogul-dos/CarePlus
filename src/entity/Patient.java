package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Patient implements Serializable {
    private static final long serialVersionUID = 3L;

    public String patientId;
    public String name;
    public Long mobileNumber;
    public Integer age;
    public String gender;
    public List<Appointment> bookedAppoinments;

    public Patient(String name, Long mobileNumber, Integer age, String gender) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.age = age;
        this.gender = gender;
        this.bookedAppoinments = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Patient ID: " + this.patientId + "\nName: "
                + this.name + "\nAge: " + this.age + "\nGender: " + this.gender
                + "\nContact Info: " + this.mobileNumber + "\nToday Appointment Id's are: ");

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Fixed year format
        String today = sdf.format(new Date());
        List<Appointment> todaysAppointments = new ArrayList<>();

        for (Appointment appointment : bookedAppoinments) {
            if (appointment.date.equals(today)) {
                todaysAppointments.add(appointment);
            }
        }

        if (todaysAppointments.isEmpty()) {
            result.append("No Appointments");
        } else {
            for (Appointment appointment : todaysAppointments) {
                result.append(appointment.appointmentId).append("  ");
            }
        }

        return result.toString();
    }
}
