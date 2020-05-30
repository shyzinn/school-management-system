package courses;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.CoursePlan;
import models.Courses;
import models.TimeTable;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDatabases {

    public void registerCourse(HashMap<String, Object> course_details, int faculty_id) {

        try {
            // getting conn from mysql server
            Connection conn = new DbConnection().connectToDb();
            // SQL query for inserting the data into database
            String insert_course = "INSERT INTO courses (course_name, course_code, department, faculty_name, " +
                    "start_date, end_date, fee) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement1 = conn.prepareStatement(insert_course);

            // course_details contains the values which are in type object,
            // we have to cast these values to required types

            preparedStatement1.setString(1, (String) course_details.get("name"));
            preparedStatement1.setString(2, (String) course_details.get("code"));
            preparedStatement1.setString(3, (String) course_details.get("department"));
            preparedStatement1.setString(4, (String) course_details.get("faculty_name"));
            preparedStatement1.setString(5, (String) course_details.get("start_date"));
            preparedStatement1.setString(6, (String) course_details.get("end_date"));
            preparedStatement1.setDouble(7, (Double) course_details.get("fee"));

            preparedStatement1.executeUpdate();

            if (faculty_id != 0) {
                // now adding faculty id in faculty_pivot_courses database
                String insert_foreign_key = "INSERT INTO faculty_pivot_courses (course_id, faculty_id)"
                        + "VALUES(?,?)";
                PreparedStatement preparedStatement2 = conn.prepareStatement(insert_foreign_key);
                preparedStatement2.setInt(1, getLastCourseID());
                preparedStatement2.setInt(2, faculty_id);

                preparedStatement2.executeUpdate();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // this method check if course is already assigned to some faculty
    public boolean checkCourseAssigned(int course_id) {

        boolean assigned = false;

        try {
            Connection conn = new DbConnection().connectToDb();

            String check = "SELECT course_id FROM faculty_pivot_courses WHERE course_id = " + course_id;

            ResultSet rs = conn.createStatement().executeQuery(check);

            assigned = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return assigned;
    }

    public int assignCourseToFaculty(ArrayList<Courses> courses, int id) {

        int courses_assigned = 0;
        boolean flag = true;

        try {
            Connection conn = new DbConnection().connectToDb();

            String register_faculty = "INSERT INTO faculty_pivot_courses (faculty_id, course_id)" +
                    "VALUES(?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(register_faculty);
            // looping through all the course selected for this faculty

            for (Courses course : courses) {

                // check if faculty has already registered for this course

                if (!checkCourseAssigned(course.getId())) {
                    preparedStatement.setInt(1, id);
                    preparedStatement.setInt(2, course.getId());
                    preparedStatement.executeUpdate();
                    courses_assigned++;

                } else {
                    flag = false;
                }
            }
            if (!flag) {
                new Dialogs().warningAlert("Warning", "You are trying to assign " +
                                "already assigned course",
                        "Some courses are already assigned to this faculty or" +
                                " any other faculty and you" +
                                " are trying to register them again with this faculty, which can't be possible");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses_assigned;
    }

    public Courses getLatestCourse() {

        Courses course;

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT * from courses ORDER BY ID DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(get_last_id);

            if (rs.next()) {
                int serial = getTotalCourses();
                int course_id = rs.getInt("id");
                String course_name = rs.getString("course_name");
                String course_code = rs.getString("course_code");
                String course_department = rs.getString("department");
                String faculty_name = rs.getString("faculty_name");
                String start_date = rs.getString("start_date");
                String end_date = rs.getString("end_date");
                double fee = rs.getDouble("fee");

                course = new Courses(serial, course_id, course_name, course_code, course_department,
                        start_date, end_date, fee, faculty_name);
                return course;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTotalCourses() {

        int courses_count = 0;

        try {
            Connection conn = new DbConnection().connectToDb();
            String count_courses = "SELECT count(*) as total_courses FROM courses";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(count_courses);

            if (rs.next()) {
                courses_count = rs.getInt("total_courses");
                return courses_count;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses_count;
    }

    public void deleteCourse(int id) {
        // this function accept the id of course then delete the course with that id and its relevant data

        try {
            Connection conn = new DbConnection().connectToDb();
            String delete_course = "DELETE FROM courses where id = " + id;
            PreparedStatement preparedStatement = conn.prepareStatement(delete_course);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int getLastCourseID() {

        int id = 0;
        try {
            Connection conn = new DbConnection().connectToDb();
            String get_last_id = "SELECT id from courses ORDER BY ID DESC LIMIT 1";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(get_last_id);
            if (rs.next()) {
                id = rs.getInt("id");
                return id;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public void setTimeTable(Map<String, Object> time_table, List<String> venue_list) {

        try {
            Connection conn = new DbConnection().connectToDb();
            String insert_time_table = "INSERT INTO time_table (course_id, day, time, class_venue) VALUES(?,?,?,?)";

            int i = 0;
            for (Map.Entry<String, Object> entry : time_table.entrySet()) {

                PreparedStatement preparedStatement = conn.prepareStatement(insert_time_table);

                String day = entry.getKey();
                String time = (String) entry.getValue();

                preparedStatement.setInt(1, getLastCourseID());
                preparedStatement.setString(2, day);
                preparedStatement.setString(3, time);
                preparedStatement.setString(4, venue_list.get(i));
                preparedStatement.executeUpdate();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Courses> getCourses() {
        ObservableList<Courses> course_list = FXCollections.observableArrayList();
        try {
            Connection conn = new DbConnection().connectToDb();

            String select_courses = "SELECT * FROM courses";
            String sql_serial = "SELECT ROW_NUMBER() OVER ( ORDER BY id) serial_no FROM courses";

            Statement stmt1 = conn.createStatement();
            Statement stmt2 = conn.createStatement();

            ResultSet rs1 = stmt1.executeQuery(select_courses);
            ResultSet rs2 = stmt2.executeQuery(sql_serial);

            while (rs1.next() && rs2.next()) {

                int serial = rs2.getInt("serial_no");
                int course_id = rs1.getInt("id");
                String course_name = rs1.getString("course_name");
                String course_code = rs1.getString("course_code");
                String course_department = rs1.getString("department");
                String faculty_name = rs1.getString("faculty_name");
                String start_date = rs1.getString("start_date");
                String end_date = rs1.getString("end_date");
                double fee = rs1.getDouble("fee");

                //calling constructor of courses model class
                Courses course = new Courses(serial, course_id, course_name, course_code,
                        course_department,
                        start_date, end_date, fee, faculty_name);

                // adding object to list
                course_list.add(course);
            }

            return course_list;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return course_list;
    }


    // this method returns the total registered course of the student

    public int getTotalRegisteredCourses(int student_id) {

        int students_enrolled = 0;

        try {
            Connection conn = new DbConnection().connectToDb();
            String get_student_counts = "SELECT count(*) as total_courses FROM student_pivot_courses" +
                    " WHERE student_id = " + student_id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(get_student_counts);

            while (rs.next()) {
                students_enrolled = rs.getInt("total_courses");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return students_enrolled;
    }

    public ObservableList<TimeTable> getTimeTable(int course_id) {

        // getting time table for given course id
        ObservableList<TimeTable> time_table = FXCollections.observableArrayList();

        try {
            Connection conn = new DbConnection().connectToDb();
            String time_table_query = "SELECT * FROM time_table where course_id = " + course_id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(time_table_query);

            while (rs.next()) {
                int id = rs.getInt("id");
                String day = rs.getString("day");
                String time = rs.getString("time");
                String venue = rs.getString("class_venue");

                TimeTable time_table_model = new TimeTable(id, day, time, venue);
                time_table.add(time_table_model);
            }
            return time_table;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time_table;
    }

    // id will tell which day to update
    public void changeTimeTable(String value, String col_name, int id) {
        try {
            Connection conn = new DbConnection().connectToDb();
            String update_time_table = "UPDATE time_table SET " + col_name + " = '" + value + "'"
                    + " WHERE id = " + id;
            PreparedStatement preparedStatement = conn.prepareStatement(update_time_table);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* this method return courses with certain id
        it accept array list of type int, which contains id as argument
        and return the Observable list of Courses objects
     */

    public Courses getCoursesOnID(int courses_id) {

        Courses course = null;

        try {
            Connection conn = new DbConnection().connectToDb();

            String select_courses = "SELECT * FROM courses where id = " + courses_id;
            ResultSet rs1 = conn.createStatement().executeQuery(select_courses);

            while (rs1.next()) {

                String course_name = rs1.getString("course_name");
                String course_code = rs1.getString("course_code");
                String course_department = rs1.getString("department");
                String faculty_name = rs1.getString("faculty_name");
                String start_date = rs1.getString("start_date");
                String end_date = rs1.getString("end_date");
                double fee = rs1.getDouble("fee");

                course = new Courses(0, courses_id, course_name, course_code, course_department,
                        start_date, end_date, fee, faculty_name);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }

    public ObservableList<Courses> getCoursesOnID(ArrayList<Integer> courses_id) {

        ObservableList<Courses> courses_list = FXCollections.observableArrayList();
        int serial = 0;

        try {
            Connection conn = new DbConnection().connectToDb();

            for (Integer id : courses_id) {
                String select_courses = "SELECT * FROM courses where id = " + id;
                ResultSet rs1 = conn.createStatement().executeQuery(select_courses);

                while (rs1.next()) {
                    serial++;
                    String course_name = rs1.getString("course_name");
                    String course_code = rs1.getString("course_code");
                    String course_department = rs1.getString("department");
                    String faculty_name = rs1.getString("faculty_name");
                    String start_date = rs1.getString("start_date");
                    String end_date = rs1.getString("end_date");
                    double fee = rs1.getDouble("fee");

                    Courses course = new Courses(serial, id, course_name, course_code,
                            course_department, start_date, end_date, fee, faculty_name);

                    courses_list.add(course);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses_list;
    }

    public ArrayList<Integer> getCoursesRelatedToFaculty(int faculty_id) {

        ArrayList<Integer> courses_id_list = new ArrayList<>();
        try {
            Connection conn = new DbConnection().connectToDb();
            String select_courses = "SELECT course_id FROM faculty_pivot_courses where faculty_id = " + faculty_id;
            ResultSet rs1 = conn.createStatement().executeQuery(select_courses);

            while (rs1.next()) {
                courses_id_list.add(rs1.getInt("course_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses_id_list;
    }

    public ArrayList<Integer> getCoursesRelatedToStudents(int student_id) {

        ArrayList<Integer> courses_id_list = new ArrayList<>();
        try {
            Connection conn = new DbConnection().connectToDb();
            String select_courses = "SELECT course_id FROM student_pivot_courses where student_id = " + student_id;
            ResultSet rs1 = conn.createStatement().executeQuery(select_courses);

            while (rs1.next()) {
                courses_id_list.add(rs1.getInt("course_id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses_id_list;
    }

    // one course can be offered by multiple faculty so we are giving student choice to register

    public void removeAssignedCourses(int course_id, int faculty_id) {

        try {
            Connection conn = new DbConnection().connectToDb();
            String remove_courses = "DELETE FROM faculty_pivot_courses WHERE faculty_id = " + faculty_id +
                    " and course_id = " + course_id;
            PreparedStatement preparedStatement = conn.prepareStatement(remove_courses);

            preparedStatement.executeUpdate();


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public void setCoursePlan(int course_id, String date, String plan) {

        try {
            Connection conn = new DbConnection().connectToDb();
            String insert_query = "INSERT INTO course_plan (course_id, date, plan) VALUES(?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insert_query);

            preparedStatement.setInt(1, course_id);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, plan);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeCoursePlan(String value, String col_name, int id) {

        try {
            Connection conn = new DbConnection().connectToDb();
            String insert_query = "UPDATE course_plan SET " + col_name + " = '" + value + "'" +
                    " WHERE id =  " + id;
            PreparedStatement preparedStatement = conn.prepareStatement(insert_query);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CoursePlan> getCoursePlan(int course_id) {

        ObservableList<CoursePlan> course_plan_list = FXCollections.observableArrayList();
        try {
            Connection conn = new DbConnection().connectToDb();
            String select_query = "SELECT * FROM course_plan WHERE course_id = " + course_id;
            ResultSet rs = conn.createStatement().executeQuery(select_query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                String plan = rs.getString("plan");

                CoursePlan course_plan = new CoursePlan(id, course_id, date, plan);
                course_plan_list.add(course_plan);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course_plan_list;
    }

    public void deleteCoursePlan(ObservableList<CoursePlan> coursePlansList) {

        try {
            Connection conn = new DbConnection().connectToDb();
            for (CoursePlan course_plan : coursePlansList) {
                String delete_query = "DELETE FROM course_plan WHERE id = " + course_plan.getId();
                PreparedStatement preparedStatement = conn.prepareStatement(delete_query);
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CoursePlan getLatestCoursePlan(int course_id) {
        CoursePlan course_plan = null;
        try {
            Connection conn = new DbConnection().connectToDb();
            String select_query = "SELECT * FROM course_plan WHERE course_id = " + course_id;
            ResultSet rs = conn.createStatement().executeQuery(select_query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String date = rs.getString("date");
                String plan = rs.getString("plan");

                course_plan = new CoursePlan(id, course_id, date, plan);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course_plan;
    }
}
