package attendance;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.GetDate;
import config.ValidateFields;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Attendance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class AttendanceDatabase {

    DbConnection dbConnection = new DbConnection();

    public void generateAttendance(int course_id, String str_date) {

        // if attendance for current date and course does not exists, then we generate it
        if (!checkAttendance(course_id, str_date)) {

            try {
                Connection conn = dbConnection.connectToDb();
                String set_attendance = "INSERT INTO attendance_pivot_courses " +
                        "(course_id, date, day, month, year) " +
                        "values(?,?,?,?,?)";

                PreparedStatement preparedStatement = conn.prepareStatement(set_attendance);

                GetDate date_obj = new GetDate();
                Date date = date_obj.stringToDate(str_date, "dd-MM-yyyy");

                preparedStatement.setInt(1, course_id);
                preparedStatement.setString(2, str_date);
                preparedStatement.setInt(3, date_obj.getDay(date));
                preparedStatement.setInt(4, date_obj.getMonth(date));
                preparedStatement.setInt(5, date_obj.getYear());
                preparedStatement.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setAttendance(int course_id, int student_id, String status, String str_date) {

        try {
            Connection conn = dbConnection.connectToDb();
            String set_attendance = "INSERT INTO attendance " +
                    "(course_id, student_id, att_date , status , att_day , att_month , att_year  ) " +
                    "values(?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(set_attendance);

            GetDate date_obj = new GetDate();
            Date date = date_obj.stringToDate(str_date, "dd-MM-yyyy");

            preparedStatement.setInt(1, course_id);
            preparedStatement.setInt(2, student_id);
            preparedStatement.setString(3, str_date);
            preparedStatement.setString(4, status);
            preparedStatement.setInt(5, date_obj.getDay(date));
            preparedStatement.setInt(6, date_obj.getMonth(date));
            preparedStatement.setInt(7, date_obj.getYear());
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Attendance> getAttendanceByCourseID(int course_id) {

        ObservableList<Attendance> attendance_list = FXCollections.observableArrayList();
        try {
            Connection conn = dbConnection.connectToDb();
            String get_attendance = "SELECT * FROM attendance a" +
                    " JOIN students s on s.id = a.student_id" +
                    " WHERE course_id = " + course_id;

            ResultSet rs = conn.createStatement().executeQuery(get_attendance);
            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("a.id");
                int student_id = rs.getInt("student_id");
                String student_name = rs.getString("name");
                String student_registration = rs.getString("reg_no");
                String att_date = rs.getString("att_date");
                String status = rs.getString("status");
//                int att_day = rs.getInt("att_day");
//                int att_month = rs.getInt("att_month");
//                int att_year = rs.getInt("att_year");

                Attendance attendance = new Attendance(serial, id, course_id,
                        student_id, student_name, student_registration, status, att_date);

                attendance_list.add(attendance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendance_list;
    }

    // this method accept date and course id and return attendance for that specific date
    public ObservableList<Attendance> getAttendanceByDate(int course_id, String date) {

        ObservableList<Attendance> attendance_list = FXCollections.observableArrayList();
        try {
            Connection conn = dbConnection.connectToDb();
            String get_attendance = "SELECT * FROM attendance a" +
                    " JOIN students s on s.id = a.student_id" +
                    " WHERE date = '" + date;

            ResultSet rs = conn.createStatement().executeQuery(get_attendance);
            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("id");
                int student_id = rs.getInt("student_id");
                String student_name = rs.getString("name");
                String student_registration = rs.getString("reg_no");
                String att_date = rs.getString("att_date");
                String status = rs.getString("status");

                Attendance attendance = new Attendance(serial, id, course_id,
                        student_id, student_name, student_registration, status, att_date);

                attendance_list.add(attendance);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendance_list;
    }

    // this method check whether attendance for given date , and given course exits or not
    public boolean checkAttendance(int course_id, String date) {

        boolean check = false;

        try {
            Connection conn = new DbConnection().connectToDb();
            String check_query = "SELECT * FROM attendance_pivot_courses WHERE course_id = " + course_id +
                    " and date = '" + date + "'";

            ResultSet rs = conn.createStatement().executeQuery(check_query);
            check = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    // this method will check if attendance for this student, with this course, with this date already exists
    public boolean checkIfAttendanceExits(int course_id, int student_id, String date) {
        boolean check = false;

        try {
            Connection conn = new DbConnection().connectToDb();
            String check_query = "SELECT * FROM attendance WHERE course_id = " + course_id +
                    " and student_id = " + student_id +
                    " and att_date = '" + date + "'"
                    + "ORDER BY ID DESC LIMIT 1";

            ResultSet rs = conn.createStatement().executeQuery(check_query);
            check = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public void updateAttendance(int attendance_id, String status) {

        try {
            Connection conn = new DbConnection().connectToDb();
            String update_attendance = "UPDATE attendance SET status = '" + status + "'" +
                    " WHERE id = " + attendance_id;

            PreparedStatement preparedStatement = conn.prepareStatement(update_attendance);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // this method accepts course id and return attendance percentage of that specific course
    public double getAttendancePercentage(int course_id) {

        double percentage = 0.0;

        try {
            Connection conn = new DbConnection().connectToDb();
            String present_attendance = "SELECT count(*) as present_att FROM attendance WHERE course_id = " + course_id +
                    " AND status = 'P'";
            String total_attendance = "SELECT count(*) as total_att FROM attendance WHERE course_id = " + course_id;

            ResultSet rs1 = conn.createStatement().executeQuery(present_attendance);
            ResultSet rs2 = conn.createStatement().executeQuery(total_attendance);

            while (rs1.next() && rs2.next()) {

                double present_att = rs1.getDouble("present_att");
                double total_att = rs2.getDouble("total_att");

                percentage = (present_att / total_att) * 100;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return ValidateFields.round(percentage, 2);
    }
}

