public class Doctor {
    String name;
    String doctorId;
    Long mobileNumber;
    Integer[][] bookedSlots;
    Integer startTime;
    Integer endTime;
    static Integer doctorCounter = 0;

    public Doctor(String name, Long mobileNumber, Integer startTime, Integer endTime) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        bookedSlots = new Integer[endTime-startTime][2];
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctorId = "D" + ++doctorCounter;
    }

    public String toString(){
        return "Doctor ID: "+ this.doctorId+ " \nDoctor name: "+ this.name +
                " \nContact Info: "+mobileNumber+"\n starting time: "+this.startTime
                +"\n Ending time: "+this.endTime;
    }
}
