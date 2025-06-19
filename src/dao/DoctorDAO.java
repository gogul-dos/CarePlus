package dao;

import db.DBConnection;
import entity.Doctor;

import java.sql.*;
import java.util.*;

public class DoctorDAO {

    public Map<String, Doctor> getAllDoctors() {
        Map<String, Doctor> map = new LinkedHashMap<>();
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Doctor")) {

            while (rs.next()) {
                Doctor d = new Doctor(
                        rs.getString("name"),
                        rs.getLong("contact_number"),
                        rs.getInt("start_time"),
                        rs.getInt("end_time"),
                        rs.getString("specialization")
                );
                d.doctorId = rs.getString("doctor_id");
                map.put(d.doctorId, d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public Doctor getDoctorById(String doctorId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Doctor WHERE doctor_id = ?")) {
            ps.setString(1, doctorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Doctor d = new Doctor(
                        rs.getString("name"),
                        rs.getLong("contact_number"),
                        rs.getInt("start_time"),
                        rs.getInt("end_time"),
                        rs.getString("specialization")
                );
                d.doctorId = rs.getString("doctor_id");
                return d;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addDoctor(Doctor d) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO Doctor (doctor_id, name, contact_number, start_time, end_time, specialization) VALUES (?, ?, ?, ?, ?, ?)")) {
            ps.setString(1, d.doctorId);
            ps.setString(2, d.name);
            ps.setLong(3, d.mobileNumber);
            ps.setInt(4, d.startTime);
            ps.setInt(5, d.endTime);
            ps.setString(6, d.specialization);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeDoctor(String doctorId) {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("DELETE FROM Doctor WHERE doctor_id = ?")) {
            ps.setString(1, doctorId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Map<String, Doctor> getDoctorsBySpecialization(String specialization) {
        Map<String, Doctor> map = new LinkedHashMap<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM Doctor WHERE specialization = ?")) {
            ps.setString(1, specialization);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Doctor d = new Doctor(
                        rs.getString("name"),
                        rs.getLong("contact_number"),
                        rs.getInt("start_time"),
                        rs.getInt("end_time"),
                        rs.getString("specialization")
                );
                d.doctorId = rs.getString("doctor_id");
                map.put(d.doctorId, d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String getNextDoctorId() {
        String query = "SELECT doctor_id FROM Doctor ORDER BY doctor_id DESC LIMIT 1";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                String lastId = rs.getString("doctor_id");
                int number = Integer.parseInt(lastId.substring(1));
                return "D" + (number + 1);
            } else {
                return "D1";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "D1";
        }
    }
}
