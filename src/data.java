import java.util.HashMap;
import java.util.Map;

public class data {
    static Map<String, Doctor> doctors;
    static Map<String, Patient> patients;
    static Map<String, Appoinment> appoinments;
    static Map<String,Receptionist> receptionists;

    public data() {
        doctors = new HashMap<>();
        patients = new HashMap<>();
        appoinments = new HashMap<>();
        receptionists = new HashMap<>();
    }
}
