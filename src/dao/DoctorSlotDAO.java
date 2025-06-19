package dao;

import db.DBConnection;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class DoctorSlotDAO {

    public Map<String, Map<String, List<Integer>>> getAllSlots() {
        Map<String, Map<String, List<Integer>>> doctorSlots = new HashMap<>();
        String query = "SELECT doctor_id, date, time FROM DoctorSlot ORDER BY doctor_id, date, time";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String doctorId = rs.getString("doctor_id");
                String date = rs.getString("date");
                int time = rs.getInt("time");

                doctorSlots
                        .computeIfAbsent(doctorId, k -> new LinkedHashMap<>())
                        .computeIfAbsent(date, k -> new ArrayList<>())
                        .add(time);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doctorSlots;
    }

    public void addSlots(String doctorId, String date, List<Integer> times) {
        String query = "INSERT INTO DoctorSlot (doctor_id, date, time) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            for (int time : times) {
                ps.setString(1, doctorId);
                ps.setString(2, date);
                ps.setInt(3, time);
                ps.addBatch();
            }

            ps.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean removeSlot(String doctorId, String date, int time) {
        String query = "DELETE FROM DoctorSlot WHERE doctor_id = ? AND date = ? AND time = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, doctorId);
            ps.setString(2, date);
            ps.setInt(3, time);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteAllSlotsByDate(String date) {
        String query = "DELETE FROM DoctorSlot WHERE date = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, date);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteSlotsByDoctor(String doctorId) {
        String query = "DELETE FROM DoctorSlot WHERE doctor_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, doctorId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> getAvailableSlotsByDoctorAndDate(String doctorId, String date) {
        List<Integer> availableSlots = new ArrayList<>();
        String query = "SELECT time FROM DoctorSlot WHERE doctor_id = ? AND date = ? ORDER BY time";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, doctorId);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                availableSlots.add(rs.getInt("time"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return availableSlots;
    }


    public void refreshSlots(String doctorId, int startTime, int endTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar cal = Calendar.getInstance();
        String todayDate = sdf.format(cal.getTime());

        // Step 1: Delete old dates
        String deleteQuery = "DELETE FROM DoctorSlot WHERE doctor_id = ? AND STR_TO_DATE(date, '%d-%m-%Y') < CURDATE()";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(deleteQuery)) {
            ps.setString(1, doctorId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Step 2: Insert new dates to maintain 5 future days
        Set<String> existingDates = new HashSet<>();
        String selectQuery = "SELECT DISTINCT date FROM DoctorSlot WHERE doctor_id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(selectQuery)) {
            ps.setString(1, doctorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                existingDates.add(rs.getString("date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        cal = Calendar.getInstance();
        while (existingDates.size() < 5) {
            String date = sdf.format(cal.getTime());
            if (!existingDates.contains(date)) {
                List<Integer> slots = new ArrayList<>();
                for (int t = startTime; t < endTime; t++) {
                    slots.add(t);
                }
                addSlots(doctorId, date, slots);
                existingDates.add(date);
            }
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}
