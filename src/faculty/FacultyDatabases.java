package faculty;

import config.Dialogs;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Courses;
import models.Faculty;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class FacultyDatabases {

    public void registerFaculty(Map<String, Object> faculty) {

        if (!checkIfUsernameExits((String) faculty.get("username"))) {
            try {
                Connection conn = new DbConnection().connectToDb();
                String register_faculty = "INSERT INTO faculty (name, department, office_no, " +
                        "salary, date_joined,username, password)" +
                        "VALUES(?,?,?,?,?,?,?)";
                PreparedStatement pstmt = conn.prepareStatement(register_faculty);
                pstmt.setString(1, (String) faculty.get("name"));
                pstmt.setString(2, (String) faculty.get("department"));
                pstmt.setString(3, (String) faculty.get("office_no"));
                pstmt.setDouble(4, (Double) faculty.get("salary"));
                pstmt.setString(5, (String) faculty.get("date_joined"));
                pstmt.setString(6, (String) faculty.get("username"));
                pstmt.setString(7, (String) faculty.get("password"));
                pstmt.executeUpdate();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new Dialogs().warningAlert("Warning", "Username already exists",
                    "Faculty with this username already exists, please choose another username");
        }
    }

    public int getLastFacultyID() {
        int id = 0;
        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT id FROM faculty ORDER BY id DESC LIMIT 1";

            ResultSet rs = conn.createStatement().executeQuery(get_last_id);

            while (rs.next()) {
                id = rs.getInt("id");
            }
            return id;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    // adding courses for this faculty
    // adding these to pivot table which will hold which courses are related to which faculty

    public void registerCourses(ArrayList<Courses> courses) {


        try {
            Connection conn = new DbConnection().connectToDb();
            String register_faculty = "INSERT INTO faculty_pivot_courses (faculty_id, course_id)" +
                    "VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(register_faculty);

            // looping through all the course selected for this faculty

            for (Courses course : courses) {

                if (!checkCourses(course.getId())) {
                    pstmt.setInt(1, getLastFacultyID());
                    pstmt.setInt(2, course.getId());
                    pstmt.executeUpdate();
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this method check for already registered courses to another faculty if any
    public boolean checkCourses(int course_id) {

        boolean check = false;

        try {
            Connection conn = new DbConnection().connectToDb();
            String check_courses = "SELECT * FROM faculty_pivot_courses WHERE course_id = " + course_id;
            ResultSet rs = conn.createStatement().executeQuery(check_courses);

            check = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public Faculty getFacultyByID(int faculty_id) {

        Faculty faculty = null;

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT * FROM faculty WHERE id = " + faculty_id;

            ResultSet rs = conn.createStatement().executeQuery(get_last_id);
            while (rs.next()) {
                int id = rs.getInt("id");
                int courses_count = getTotalCoursesRegistred(id);
                String name = rs.getString("name");
                String department = rs.getString("department");
                String office_no = rs.getString("office_no");
                double salary = rs.getDouble("salary");
                String date_joined = rs.getString("date_joined");

                faculty = new Faculty(0, id, name, department,
                        office_no, salary, courses_count, date_joined);
            }
            return faculty;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculty;
    }


    public ObservableList<Faculty> getFaculty() {

        ObservableList<Faculty> faculty_list = FXCollections.observableArrayList();

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT * FROM faculty";
            String sql_serial = "SELECT ROW_NUMBER() OVER ( ORDER BY id) serial_no FROM faculty";

            ResultSet rs = conn.createStatement().executeQuery(get_last_id);
            ResultSet rs1 = conn.createStatement().executeQuery(sql_serial);

            while (rs.next() && rs1.next()) {
                int serial = rs1.getInt("serial_no");
                int id = rs.getInt("id");
                int courses_count = getTotalCoursesRegistred(id);
                String name = rs.getString("name");
                String department = rs.getString("department");
                String office_no = rs.getString("office_no");
                double salary = rs.getDouble("salary");
                String date_joined = rs.getString("date_joined");

                Faculty faculty = new Faculty(serial, id, name, department,
                        office_no, salary, courses_count, date_joined);

                faculty_list.add(faculty);

            }
            return faculty_list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculty_list;
    }

    public Faculty getLatestFaculty() {

        Faculty faculty = null;

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT * FROM faculty ORDER BY id DESC LIMIT 1";

            ResultSet rs = conn.createStatement().executeQuery(get_last_id);

            while (rs.next()) {
                int id = rs.getInt("id");
                int courses_count = getTotalCoursesRegistred(id);
                String name = rs.getString("name");
                String department = rs.getString("department");
                String office_no = rs.getString("office_no");
                double salary = rs.getDouble("salary");
                String date_joined = rs.getString("date_joined");

                faculty = new Faculty(getTotalFaculty(), id, name, department, office_no, salary, courses_count,
                        date_joined);
            }
            return faculty;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculty;
    }

    public int getTotalFaculty() {

        int faculty_count = 0;

        try {
            Connection conn = new DbConnection().connectToDb();
            String count_courses = "SELECT count(*) as total_faculty FROM faculty";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(count_courses);

            if (rs.next()) {
                faculty_count = rs.getInt("total_faculty");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return faculty_count;
    }

    public int getTotalCoursesRegistred(int faculty_id) {
        int courses_count = 0;

        try {
            Connection conn = new DbConnection().connectToDb();
            String count_courses = "SELECT count(*) as total_courses FROM faculty_pivot_courses WHERE faculty_id = "
                    + faculty_id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(count_courses);

            if (rs.next()) {
                courses_count = rs.getInt("total_courses");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses_count;
    }

    public void deleteFaculty(int id) {
        try {
            Connection conn = new DbConnection().connectToDb();
            String delete_faculty = "DELETE FROM faculty WHERE id = " + id;
            PreparedStatement pstmt = conn.prepareStatement(delete_faculty);

            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfUsernameExits(String username) {

        boolean check = false;

        try {
            Connection conn = new DbConnection().connectToDb();
            String check_username = "SELECT username FROM faculty WHERE username = '" + username + "'";
            ResultSet rs = conn.createStatement().executeQuery(check_username);
            check = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return check;
    }
}
