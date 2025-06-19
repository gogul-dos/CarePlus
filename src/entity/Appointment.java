package entity;

import storage.Data;

import java.io.Serializable;

public class Appointment implements Serializable {
    private static final long serialVersionUID = 1L;
    public Doctor doctor;
    public Patient patient;
    public Integer time;
    public String date;
    public String appointmentId;
    public static Integer counter = Data.appoinments.size();

    public Appointment(Doctor doctor, Patient patient, Integer time, String date) {
        this.doctor = doctor;
        this.patient = patient;
        this.time = time;
        this.date = date;
        this.appointmentId = "A"+ ++counter;
    }

    public String toString(){
        return "AppointmentID: "+this.appointmentId+"\nDoctor Name: "
                + this.doctor.name + "\nPatient Name: "+this.patient.name
                +"\nAppointmentDate: "+this.date+ "\nAppointmentTime in 24hrs format: "+this.time;
    }
}
