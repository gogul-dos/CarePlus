package entity;

import storage.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Doctor implements Serializable {
    private static final long serialVersionUID = 2L;

    public String name;
    public String doctorId;
    Long mobileNumber;
    Integer startTime;
    Integer endTime;
    public String specialization;
    public Map<String, List<Integer>> availableSlots;
    public static Integer doctorCounter = Data.doctors.size();

    public Doctor(String name, Long mobileNumber, Integer startTime, Integer endTime, String specialization) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.specialization = specialization;
        this.doctorId = "D" + ++doctorCounter;
        this.availableSlots = new LinkedHashMap<>();

        generateInitialSlots();
    }

    private void generateInitialSlots() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();

        for (int i = 0; i < 5; i++) {
            String date = sdf.format(cal.getTime());
            List<Integer> timeSlots = new ArrayList<>();
            for (int j = startTime; j < endTime; j++) {
                timeSlots.add(j);
            }
            availableSlots.put(date, timeSlots);
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    public void refreshSlots() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar today = Calendar.getInstance();

        availableSlots.entrySet().removeIf(entry -> {
            try {
                Date slotDate = sdf.parse(entry.getKey());
                return slotDate.before(today.getTime());
            } catch (Exception e) {
                return true;
            }
        });

        while (availableSlots.size() < 5) {
            today.add(Calendar.DAY_OF_MONTH, 1);
            String newDate = sdf.format(today.getTime());

            if (!availableSlots.containsKey(newDate)) {
                List<Integer> timeSlots = new ArrayList<>();
                for (int j = startTime; j < endTime; j++) {
                    timeSlots.add(j);
                }
                availableSlots.put(newDate, timeSlots);
            }
        }
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
