public class Appoinment {
    Doctor doctor;
    Patient patient;
    Integer time;
    String date;
    String appointmentId;
    static Integer counter = 0;

    public Appoinment(Doctor doctor, Patient patient, Integer time, String date) {
        this.doctor = doctor;
        this.patient = patient;
        this.time = time;
        this.date = date;
        this.appointmentId = "A"+ ++counter;
    }

    public String toString(){
        return "AppointmentID: "+this.appointmentId+"\nDoctor Name: "
                + this.doctor.name + "\npatient Name: "+this.patient.name
                +"\nAppointmentDate: "+this.date+ "\nAppointmentTime in 24hrs format: "+this.time;
    }
}
