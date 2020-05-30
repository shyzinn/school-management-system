package student;

import config.Dialogs;
import config.HashPassword;
import courses.CourseDatabases;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import main_menu.LoginController;
import models.Courses;
import models.Students;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

public class StudentDatabases {

    public void registerStudent(Map<String, Object> student) {
        try {
            Connection conn = new DbConnection().connectToDb();
            String register_students = "INSERT INTO students (name, reg_no, department, phone, email," +
                    " fee, date_joined, username, password) VALUES(?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(register_students);
            preparedStatement.setString(1, (String) student.get("name"));
            preparedStatement.setString(2, (String) student.get("reg_no"));
            preparedStatement.setString(3, (String) student.get("department"));
            preparedStatement.setString(4, (String) student.get("phone"));
            preparedStatement.setString(5, (String) student.get("email"));
            preparedStatement.setDouble(6, (Double) student.get("fee"));
            preparedStatement.setString(7, (String) student.get("date_joined"));
            preparedStatement.setString(8, (String) student.get("username"));
            preparedStatement.setString(9, (String) student.get("password"));
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Students getLatestStudent() {
        Students student = null;

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT * FROM students ORDER BY id DESC LIMIT 1";

            ResultSet rs = conn.createStatement().executeQuery(get_last_id);

            while (rs.next()) {
                int id = rs.getInt("id");
                int serial = getTotalStudents();
                int courses_count = getTotalCoursesRegistered(id);
                String name = rs.getString("name");
                String password = rs.getString("password");
                String username = rs.getString("username");
                String reg_no = rs.getString("reg_no");
                String department = rs.getString("department");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                double fee = rs.getDouble("fee");
                String date_joined = rs.getString("date_joined");

                student = new Students(serial, id, name, username, password,
                        reg_no, department, email, phone, date_joined, fee,
                        courses_count);
            }
            return student;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    public int getLastestStudentID() {
        int id = 0;
        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT id FROM students ORDER BY id DESC LIMIT 1";

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

    public int getTotalStudents() {
        int students_count = 0;

        try {
            Connection conn = new DbConnection().connectToDb();
            String count_courses = "SELECT count(*) as total_students FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(count_courses);

            if (rs.next()) {
                students_count = rs.getInt("total_students");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return students_count;
    }

    public int getTotalCoursesRegistered(int student_id) {
        int courses_count = 0;

        try {
            Connection conn = new DbConnection().connectToDb();
            String count_courses = "SELECT count(*) as total_courses FROM student_pivot_courses WHERE student_id = "
                    + student_id;
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

    public ObservableList<Students> getStudentsList() {

        ObservableList<Students> students_list = FXCollections.observableArrayList();

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT * FROM students" +
                    " ORDER BY name ASC";

            String sql_serial = "SELECT ROW_NUMBER() OVER (ORDER BY id) serial_no FROM students";

            ResultSet rs = conn.createStatement().executeQuery(get_last_id);
            ResultSet rs1 = conn.createStatement().executeQuery(sql_serial);

            while (rs.next() && rs1.next()) {
                int id = rs.getInt("id");
                int serial = rs1.getInt("serial_no");
                String username = rs.getString("username");
                String password = rs.getString("password");
                int courses_count = getTotalCoursesRegistered(id);
                String name = rs.getString("name");
                String reg_no = rs.getString("reg_no");
                String department = rs.getString("department");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                double fee = rs.getDouble("fee");
                String date_joined = rs.getString("date_joined");

                Students student = new Students(serial, id, name, username, password,
                        reg_no, department, email, phone, date_joined, fee,
                        courses_count);

                students_list.add(student);
            }
            return students_list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return students_list;
    }

    public void deleteStudent(int id) {
        try {
            Connection conn = new DbConnection().connectToDb();
            String delete_student = "DELETE FROM students where id = " + id;
            PreparedStatement preparedStatement = conn.prepareStatement(delete_student);

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method will check for already registered courses
    public boolean checkRegisteredCourses(int course_id, int student_id) {

        boolean registered = false;

        try {
            Connection conn = new DbConnection().connectToDb();
            String registered_courses = "SELECT course_id FROM student_pivot_courses WHERE course_id = " + course_id +
                    " and student_id = " + student_id;

            ResultSet rs = conn.createStatement().executeQuery(registered_courses);

            registered = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return registered;
    }

    public ObservableList<Courses> registerCourses(ArrayList<Courses> courses, int student_id) {

        boolean flag = true;

        ObservableList<Courses> courses_list = FXCollections.observableArrayList();

        try {
            Connection conn = new DbConnection().connectToDb();
            String register_faculty = "INSERT INTO student_pivot_courses (student_id, course_id)" +
                    "VALUES(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(register_faculty);

            // looping through all the course selected for this faculty
            for (Courses course : courses) {

                if (!checkRegisteredCourses(course.getId(), student_id)) {

                    preparedStatement.setInt(1, student_id);
                    preparedStatement.setInt(2, course.getId());
                    preparedStatement.executeUpdate();

                    courses_list.add(course);
                } else {
                    flag = false;
                }
            }

            if (!flag) {
                new Dialogs().errorAlert("Error", "Trying to register already registered courses",
                        "Error: You are trying to register already register courses.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses_list;
    }

    public Courses getLatestRegisteredCourse(int student_id) {

        Courses course = null;
        try {
            Connection conn = new DbConnection().connectToDb();
            String get_latest_reg_course = "SELECT course_id FROM student_pivot_courses WHERE student_id = "
                    + student_id +
                    " ORDER BY id DESC LIMIT 1";
            ResultSet rs = conn.createStatement().executeQuery(get_latest_reg_course);
            while (rs.next()) {
                int course_id = rs.getInt("course_id");
                course = new CourseDatabases().getCoursesOnID(course_id);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }

    public void unRegisterCourses(Courses course, int student_id) {
        try {
            Connection conn = new DbConnection().connectToDb();
            String unregister_query = "DELETE FROM student_pivot_courses WHERE course_id =  " + course.getId()
                    + " and student_id = " + student_id;

            PreparedStatement preparedStatement1 = conn.prepareStatement(unregister_query);
            preparedStatement1.executeUpdate();

            // deleting assignments uploaded to this course by student

            String delete_assign = "DELETE FROM uploaded_assignments WHERE course_id = " + course.getId() +
                    " and student_id = " + student_id;

            PreparedStatement preparedStatement2 = conn.prepareStatement(delete_assign);
            preparedStatement2.executeUpdate();

            String delete_exam = "DELETE FROM uploaded_exams WHERE course_id = " + course.getId() +
                    " and student_id = " + student_id;

            PreparedStatement preparedStatement3 = conn.prepareStatement(delete_exam);
            preparedStatement3.executeUpdate();

            String delete_attendance = "DELETE FROM attendance WHERE course_id = " + course.getId() +
                    " and student_id =" + student_id;

            PreparedStatement preparedStatement4 = conn.prepareStatement(delete_attendance);
            preparedStatement4.executeUpdate();

            String delete_exams_results = "DELETE FROM exams_results WHERE course_id = " + course.getId() +
                    " and student_id =" + student_id;

            PreparedStatement preparedStatement5 = conn.prepareStatement(delete_exams_results);
            preparedStatement5.executeUpdate();

            String delete_assignments_results = "DELETE FROM exams_results WHERE course_id = " + course.getId() +
                    " and student_id =" + student_id;

            PreparedStatement preparedStatement6 = conn.prepareStatement(delete_assignments_results);
            preparedStatement6.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Students getStudentByID(int student_id) {

        Students student = null;

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_students = "SELECT * FROM students" +
                    " WHERE id = " + student_id;

            ResultSet rs = conn.createStatement().executeQuery(get_students);
            int serial = 0;
            while (rs.next()) {
                serial++;
                String student_name = rs.getString("name");
                String user_name = rs.getString("username");
                String password = rs.getString("password");
                String student_registration = rs.getString("reg_no");
                int courses_count = getTotalCoursesRegistered(student_id);
                String department = rs.getString("department");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                double fee = rs.getDouble("fee");
                String date_joined = rs.getString("date_joined");
                student = new Students(serial, student_id, student_name, user_name, password,
                        student_registration, department, email, phone, date_joined, fee, courses_count);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    public ObservableList<Students> getStudentsByCourseID(int course_id) {

        ObservableList<Students> students_list = FXCollections.observableArrayList();

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_students = "SELECT * FROM student_pivot_courses s_c " +
                    " JOIN students s on s.id = s_c.student_id" +
                    " WHERE s_c.course_id = " + course_id;

            ResultSet rs = conn.createStatement().executeQuery(get_students);
            int serial = 0;
            while (rs.next()) {
                serial++;
                int student_id = rs.getInt("s.id");
                String student_name = rs.getString("name");
                String user_name = rs.getString("username");
                String password = rs.getString("password");
                String student_registration = rs.getString("reg_no");
                int courses_count = getTotalCoursesRegistered(student_id);
                String department = rs.getString("department");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                double fee = rs.getDouble("fee");
                String date_joined = rs.getString("date_joined");
                Students student = new Students(serial, student_id, student_name, user_name, password,
                        student_registration, department, email, phone, date_joined, fee, courses_count);

                students_list.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students_list;
    }

    public void updateInfo(String email, String phone, int student_id) {
        try {
            Connection conn = new DbConnection().connectToDb();
            String update_info = "UPDATE students SET email = '" + email + "'" +
                    " ,phone = '" + phone + "'"
                    + " WHERE id = " + student_id;
            PreparedStatement preparedStatement = conn.prepareStatement(update_info);
            preparedStatement.executeUpdate();
            new Dialogs().infoAlert("Data Updated Successfully", "All data provided has been updated", "");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean updateCredentials(String username, String old_password, String new_password, int student_id) {

        // decrypting login password to plain string

        String decrypt_old_password = new HashPassword().decrypt(LoginController.loginPassword);

        if (old_password.equals(decrypt_old_password)) {
            try {
                if (!checkIfUsernameExits(username, student_id)) {

                    Connection conn = new DbConnection().connectToDb();
                    String update_credentials = "UPDATE students SET username = '" + username + "'" +
                            ", password = '" + new HashPassword().encrypt(new_password) + "'"
                            + " WHERE id = " + student_id;
                    PreparedStatement preparedStatement = conn.prepareStatement(update_credentials);
                    preparedStatement.executeUpdate();

                    new Dialogs().infoAlert("Data Updated Successfully",
                            "All data provided has been updated",
                            "Make sure to login with new credentials");

                    LoginController.loginPassword = new HashPassword().encrypt(new_password);
                    return true;
                } else {
                    new Dialogs().errorAlert("Username Error", "User with this username already exists",
                            "Please choose another username.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            new Dialogs().errorAlert("Password Error", "Invalid old password",
                    "Please make sure to enter valid old password if you don't remember contact admin");
        }
        return false;
    }

    public boolean checkIfUsernameExits(String username, int id) {

        boolean check = false;

        try {
            Connection conn = new DbConnection().connectToDb();
            String check_username = "SELECT username From students WHERE id != " + id +
                    " AND username = '" + username + "'";
            ResultSet rs = conn.createStatement().executeQuery(check_username);
            check = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return check;
    }
}
