package entity;

import java.io.Serializable;

public class Receptionist implements Serializable {
    private static final long serialVersionUID = 4L;

    public String username;
    public String name;
    public String password;
    public String receptionistId;

    public Receptionist(String username, String name, String password) {
        this.username = username;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Receptionist ID: " + this.receptionistId +
                "\nName: " + this.name +
                "\nUsername: " + this.username;
    }
}
