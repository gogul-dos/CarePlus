package entity;

import java.io.Serializable;

public class Doctor implements Serializable {
    private static final long serialVersionUID = 2L;

    public String name;
    public String doctorId;
    public Long mobileNumber;
    public Integer startTime;
    public Integer endTime;
    public String specialization;

    public Doctor(String name, Long mobileNumber, Integer startTime, Integer endTime, String specialization) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.specialization = specialization;
    }

    @Override
    public String toString() {
        return "Doctor ID: " + doctorId +
                "\nDoctor name: " + name +
                "\nContact Info: " + mobileNumber +
                "\nStarting time: " + startTime +
                "\nEnding time: " + endTime +
                "\nSpecialization: " + specialization;
    }
}
