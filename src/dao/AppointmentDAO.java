package dao;

import db.DBConnection;
import entity.Appointment;
import entity.Doctor;
import entity.Patient;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class AppointmentDAO {

    public Map<String, Appointment> getAllAppointments(Map<String, Doctor> doctorMap, Map<String, Patient> patientMap) {
        Map<String, Appointment> map = new LinkedHashMap<>();
        String query = "SELECT * FROM Appointment";

        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String appointmentId = rs.getString("appointment_id");
                String doctorId = rs.getString("doctor_id");
                String patientId = rs.getString("patient_id");
                java.sql.Date sqlDate = rs.getDate("date");
                int time = rs.getInt("time");

                Doctor doctor = doctorMap.get(doctorId);
                Patient patient = patientMap.get(patientId);

                if (doctor != null && patient != null) {
                    String dateStr = new SimpleDateFormat("dd-MM-yyyy").format(sqlDate);
                    Appointment appointment = new Appointment(doctor, patient, time, dateStr);
                    appointment.appointmentId = appointmentId;

                    // Maintain mapping
                    patient.bookedAppoinments.add(appointment);
                    map.put(appointmentId, appointment);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return map;
    }

    public boolean bookAppointment(Appointment appointment) {
        String query = "INSERT INTO Appointment (appointment_id, doctor_id, patient_id, date, time) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, appointment.appointmentId); // Already generated outside
            ps.setString(2, appointment.doctor.doctorId);
            ps.setString(3, appointment.patient.patientId);
            java.util.Date utilDate = new SimpleDateFormat("dd-MM-yyyy").parse(appointment.date);
            ps.setDate(4, new java.sql.Date(utilDate.getTime()));
            ps.setInt(5, appointment.time);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAppointmentsByDoctorId(String doctorId, Map<String, Doctor> doctorMap, Map<String, Patient> patientMap) {
        List<Appointment> list = new ArrayList<>();
        String query = "SELECT * FROM Appointment WHERE doctor_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String appointmentId = rs.getString("appointment_id");
                String patientId = rs.getString("patient_id");
                java.sql.Date sqlDate = rs.getDate("date");
                int time = rs.getInt("time");

                Doctor doctor = doctorMap.get(doctorId);
                Patient patient = patientMap.get(patientId);

                if (doctor != null && patient != null) {
                    String dateStr = new SimpleDateFormat("dd-MM-yyyy").format(sqlDate);
                    Appointment appointment = new Appointment(doctor, patient, time, dateStr);
                    appointment.appointmentId = appointmentId;
                    list.add(appointment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public String getNextAppointmentId() {
        String query = "SELECT MAX(appointment_id) FROM Appointment";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next() && rs.getString(1) != null) {
                String lastId = rs.getString(1); // e.g., A23
                int num = Integer.parseInt(lastId.substring(1)); // get 23
                return "A" + (num + 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "A1";
    }


    public List<Appointment> getAppointmentsByPatientId(String patientId, Map<String, Doctor> doctorMap, Map<String, Patient> patientMap) {
        List<Appointment> list = new ArrayList<>();
        String query = "SELECT * FROM Appointment WHERE patient_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, patientId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String appointmentId = rs.getString("appointment_id");
                String doctorId = rs.getString("doctor_id");
                java.sql.Date sqlDate = rs.getDate("date");
                int time = rs.getInt("time");

                Doctor doctor = doctorMap.get(doctorId);
                Patient patient = patientMap.get(patientId);

                if (doctor != null && patient != null) {
                    String dateStr = new SimpleDateFormat("dd-MM-yyyy").format(sqlDate);
                    Appointment appointment = new Appointment(doctor, patient, time, dateStr);
                    appointment.appointmentId = appointmentId;
                    list.add(appointment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
