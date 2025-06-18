public class Receptionist {
    String username;
    String name;
    String password;
    String receptionistId;
    static Integer counter;

    public Receptionist(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.receptionistId = "R"+ ++counter;
    }

    public String toString(){
        return "Receptionist ID: "+this.receptionistId
                + "\nName: "+this.name+ "\nusername: "+this.username;
    }

}
