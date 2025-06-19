package entity;

import storage.Data;

import java.io.Serializable;

public class Receptionist implements Serializable {
    private static final long serialVersionUID = 4L;
    String username;
    String name;
    public String password;
    String receptionistId;
    public static Integer counter = Data.receptionists.size();

    public Receptionist(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.receptionistId = "R"+ ++counter;
    }

    public String toString(){
        return "Receptionist ID: "+this.receptionistId
                + "\nName: "+this.name+ "\nusername: "+this.username
                +"\nPassword(Just for testing purpose): "+this.password;
    }
}
