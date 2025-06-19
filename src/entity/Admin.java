package entity;

import java.io.Serializable;

public class Admin implements Serializable {
    public final String username;
    public final String password;

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String toString() {
        return "Admin Username: " + username;
    }
}
