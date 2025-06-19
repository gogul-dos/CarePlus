package dao;

import db.DBConnection;
import entity.Receptionist;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReceptionistDAO {

    public Map<String, Receptionist> getAllReceptionists() {
        Map<String, Receptionist> map = new LinkedHashMap<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Receptionist")) {

            while (rs.next()) {
                Receptionist r = new Receptionist(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("password")
                );
                r.receptionistId = rs.getString("receptionist_id");
                map.put(r.username, r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Receptionist getReceptionistByUsername(String username) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "SELECT * FROM Receptionist WHERE username = ?")) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Receptionist r = new Receptionist(
                        rs.getString("username"),
                        rs.getString("name"),
                        rs.getString("password")
                );
                r.receptionistId = rs.getString("receptionist_id");
                return r;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addReceptionist(Receptionist r) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO Receptionist (receptionist_id, name, username, password) VALUES (?, ?, ?, ?)")) {

            ps.setString(1, r.receptionistId);  // Already generated from entity constructor
            ps.setString(2, r.name);
            ps.setString(3, r.username);
            ps.setString(4, r.password);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeReceptionist(String username) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "DELETE FROM Receptionist WHERE username = ?")) {

            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String getNextReceptionistId() {
        String query = "SELECT receptionist_id FROM Receptionist ORDER BY receptionist_id DESC LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                String lastId = rs.getString("receptionist_id");
                int number = Integer.parseInt(lastId.substring(1));
                return "R" + (number + 1);
            } else {
                return "R1";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "R1";
        }
    }

}
