package dao;

import db.DBConnection;
import entity.Patient;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class PatientDAO {

    public Map<String, Patient> getAllPatients() {
        Map<String, Patient> map = new LinkedHashMap<>();
        String query = "SELECT * FROM Patient";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getString("name"),
                        rs.getLong("contact_number"),
                        rs.getInt("age"),
                        rs.getString("gender")
                );
                p.patientId = rs.getString("patient_id");
                map.put(p.patientId, p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public boolean addPatient(Patient p) {
        String query = "INSERT INTO Patient(patient_id, name, age, contact_number, gender) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, p.patientId);
            ps.setString(2, p.name);
            ps.setInt(3, p.age);
            ps.setLong(4, p.mobileNumber);
            ps.setString(5, p.gender);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Patient getPatientById(String patientId) {
        String query = "SELECT * FROM Patient WHERE patient_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, patientId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Patient p = new Patient(
                        rs.getString("name"),
                        rs.getLong("contact_number"),
                        rs.getInt("age"),
                        rs.getString("gender")
                );
                p.patientId = rs.getString("patient_id");
                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removePatient(String patientId) {
        String query = "DELETE FROM Patient WHERE patient_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, patientId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getNextPatientId() {
        String query = "SELECT patient_id FROM Patient ORDER BY LENGTH(patient_id) DESC, patient_id DESC LIMIT 1";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastId = rs.getString("patient_id");  // e.g., "P14"
                int num = Integer.parseInt(lastId.substring(1)); // 14
                return "P" + (num + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "P1"; // First patient if no data
    }

}
