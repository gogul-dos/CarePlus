package storage;

import entity.*;

import java.util.LinkedHashMap;
import java.util.Map;

public class Data {
    public static final Admin admin = new Admin("Gogul", "123");
    public static Map<String, Doctor> doctors = new LinkedHashMap<>();
    public static Map<String, Patient> patients = new LinkedHashMap<>();
    public static Map<String, Appointment> appoinments = new LinkedHashMap<>();
    public static Map<String, Receptionist> receptionists = new LinkedHashMap<>();

}
