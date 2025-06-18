import java.text.SimpleDateFormat;
import java.util.*;

public class Doctor {
    String name;
    String doctorId;
    Long mobileNumber;
    Integer startTime;
    Integer endTime;
    String specialization;
    Map<String,List<Integer>> availableSlots;
    static Integer doctorCounter = 0;

    public Doctor(String name, Long mobileNumber, Integer startTime, Integer endTime, String specialization) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        availableSlots = new HashMap<>();
        this.startTime = startTime;
        this.endTime = endTime;
        this.specialization = specialization;
        this.doctorId = "D" + ++doctorCounter;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        for(int i=0; i<=5; i++){
            StringBuilder dateFormatted = new StringBuilder(sdf.format(new Date()));
            String date = dateFormatted.charAt(0)+""+dateFormatted.charAt(1);
            String newDate = ""+(Integer.parseInt(date)+i);
            if(newDate.length()<2){
                dateFormatted.setCharAt(1,newDate.charAt(0));
            }else{
                dateFormatted.setCharAt(0,newDate.charAt(0));
                dateFormatted.setCharAt(1,newDate.charAt(1));
            }
            List<Integer> timeSlots = new ArrayList<>();
            for(int j=startTime; j<=endTime; j++){
                timeSlots.add(j);
            }
            availableSlots.put(dateFormatted.toString(),timeSlots);
        }
    }

    public String toString(){
        return "Doctor ID: "+ this.doctorId+ " \nDoctor name: "+ this.name +
                " \nContact Info: "+mobileNumber+"\nStarting time: "+this.startTime
                +"\nEnding time: "+this.endTime;
    }
}
