package assignments;

import config.CalculateDays;
import config.Dialogs;
import config.GetDate;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Assignments;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

public class AssignmentsDatabases {

    DbConnection dbConnection = new DbConnection();

    public void registerAssignment(HashMap<String, Object> assignment_details) {

        try {
            Connection conn = dbConnection.connectToDb();
            String insert_assignment = "INSERT INTO assignments (course_id, topic, content, deadline)" +
                    " VALUES(?,?,?,?)";
            PreparedStatement pstmt = conn.prepareStatement(insert_assignment);

            pstmt.setInt(1, (int) assignment_details.get("course_id"));
            pstmt.setString(2, (String) assignment_details.get("topic"));
            pstmt.setString(3, (String) assignment_details.get("content"));
            pstmt.setString(4, (String) assignment_details.get("deadline"));
            pstmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void uploadAssignment(Map<String, Object> assignment) {
        try {
            Connection conn = dbConnection.connectToDb();
            String insert_assignment = "INSERT INTO uploaded_assignments (student_id, assignment_id, course_id, " +
                    "uploaded_date, content, status)" +
                    " VALUES(?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insert_assignment);

            preparedStatement.setInt(1, (int) assignment.get("student_id"));
            preparedStatement.setInt(2, (int) assignment.get("assignment_id"));
            preparedStatement.setInt(3, (int) assignment.get("course_id"));
            preparedStatement.setString(4, new GetDate().getDate());
            preparedStatement.setString(5, (String) assignment.get("content"));
            preparedStatement.setString(6, (String) assignment.get("status"));
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkForUploadedAssignments(int student_id, int assignment_id, int course_id) {

        boolean check = false;

        try {
            Connection conn = dbConnection.connectToDb();

            String assignment_by_course_id = "SELECT * FROM uploaded_assignments WHERE "
                    + " course_id = " + course_id
                    + " and student_id = " + student_id
                    + " and assignment_id = " + assignment_id;
            ResultSet rs = conn.createStatement().executeQuery(assignment_by_course_id);

            check = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public ObservableList<Assignments> getAssignmentsByCourseID(int course_id) {

        ObservableList<Assignments> assignments_list = FXCollections.observableArrayList();
        int serial = 0;

        try {
            Connection conn = dbConnection.connectToDb();

            String assignment_by_course_id = "SELECT * FROM assignments WHERE course_id = " + course_id;
            ResultSet rs = conn.createStatement().executeQuery(assignment_by_course_id);

            while (rs.next()) {
                serial++;
                int id = rs.getInt("id");
                String topic = rs.getString("topic");
                String deadline = rs.getString("deadline");
                String content = rs.getString("content");
                // calculating days remaining until deadline
                long days_remaining = new CalculateDays().calculateDaysUntil(rs.getString("deadline"));
                Assignments assignment = new Assignments(serial, id, course_id, topic, content, deadline, days_remaining);
                assignments_list.add(assignment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignments_list;
    }

    public ObservableList<Assignments> getAssignments() {

        ObservableList<Assignments> assignments_list = FXCollections.observableArrayList();
        int serial = 0;

        try {
            Connection conn = dbConnection.connectToDb();

            String latest_assignment = "SELECT * FROM assignments";
            ResultSet rs = conn.createStatement().executeQuery(latest_assignment);

            while (rs.next()) {
                serial++;
                int id = rs.getInt("id");
                int course_id = rs.getInt("course_id");
                String topic = rs.getString("topic");
                String deadline = rs.getString("deadline");
                String content = rs.getString("content");
                // calculating days remaining until deadline
                long days_remaining = new CalculateDays().calculateDaysUntil(rs.getString("deadline"));
                Assignments assignment = new Assignments(serial, id, course_id, topic, content, deadline, days_remaining);
                assignments_list.add(assignment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignments_list;
    }

    public Assignments getLatestAssignment() {

        Assignments assignment = null;

        try {
            Connection conn = dbConnection.connectToDb();

            String latest_assignment = "SELECT * FROM assignments ORDER BY id DESC LIMIT 1";
            ResultSet rs = conn.createStatement().executeQuery(latest_assignment);

            while (rs.next()) {

                int id = rs.getInt("id");
                int course_id = rs.getInt("course_id");
                String topic = rs.getString("topic");
                String deadline = rs.getString("deadline");
                String content = rs.getString("content");
                // calculating days remaining until deadline
                long days_remaining = new CalculateDays().calculateDaysUntil(rs.getString("deadline"));
                assignment = new Assignments(getTotalAssignments(), id, course_id, topic, content,
                        deadline, days_remaining);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignment;
    }

    public Assignments getAssignmentByID(int id) {

        Assignments assignment = null;

        try {
            Connection conn = dbConnection.connectToDb();

            String latest_assignment = "SELECT * FROM assignments WHERE id = " + id;
            ResultSet rs = conn.createStatement().executeQuery(latest_assignment);

            while (rs.next()) {
                int course_id = rs.getInt("course_id");
                String topic = rs.getString("topic");
                String deadline = rs.getString("deadline");
                String content = rs.getString("content");
                // calculating days remaining until deadline
                long days_remaining = new CalculateDays().calculateDaysUntil(rs.getString("deadline"));
                assignment = new Assignments(0, id, course_id, topic, content, deadline, days_remaining);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return assignment;
    }

    public int getTotalAssignments() {

        int total_assignments = 0;
        try {
            Connection conn = dbConnection.connectToDb();

            String total_assignment_query = "SELECT count(*) as total FROM assignments";
            ResultSet rs = conn.createStatement().executeQuery(total_assignment_query);

            while (rs.next()) {

                total_assignments = rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return total_assignments;
    }

    public Assignments getAssignmentByCourseID(int course_id) {

        Assignments assignment = null;

        try {
            Connection conn = dbConnection.connectToDb();
            String get_assignments = "SELECT * FROM assignments WHERE course_id = " + course_id
                    + " ORDER BY id DESC LIMIT 1";
            ResultSet rs = conn.createStatement().executeQuery(get_assignments);

            while (rs.next()) {
                int id = rs.getInt("id");
                String topic = rs.getString("topic");
                String deadline = rs.getString("deadline");
                String content = rs.getString("content");
                // calculating days remaining until deadline
                long days_remaining = new CalculateDays().calculateDaysUntil(rs.getString("deadline"));

                assignment = new Assignments(0, id, course_id, topic, content,
                        deadline, days_remaining);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return assignment;
    }

    public ObservableList<Assignments> getAssignmentsByStudentAndCourseID(int student_id, int course_id) {

        ObservableList<Assignments> assignments_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String get_assignments = "SELECT * FROM uploaded_assignments ua" +
                    " JOIN assignments a ON ua.assignment_id = a.id" +
                    " WHERE ua.course_id = " + course_id +
                    " and ua.student_id = " + student_id;

            ResultSet rs = conn.createStatement().executeQuery(get_assignments);

            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("id");
                int assignment_id = rs.getInt("ua.id");
                String uploaded_date = rs.getString("uploaded_date");
                String content = rs.getString("content");
                String student_name = getStudentNameById(student_id);
                String topic = rs.getString("topic");
                String status = rs.getString("status");
                Assignments assignment = new Assignments(serial, id, course_id, assignment_id, student_id,
                        student_name, uploaded_date, topic, content, status);

                assignments_list.add(assignment);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return assignments_list;

    }

    public void updateUploadedAssignment(int id, String content) {

        try {
            Connection conn = new DbConnection().connectToDb();
            String update_assignment = "UPDATE uploaded_assignments SET content = '" + content + "'"
                    + " WHERE id = " + id;
            PreparedStatement preparedStatement = conn.prepareStatement(update_assignment);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // this method will accept array of integers of assignment id's and delete them
    public void deleteAssignments(ObservableList<Assignments> assignment_list) {
        try {
            Connection conn = dbConnection.connectToDb();
            for (Assignments assignment : assignment_list) {
                String delete_assignments = "DELETE FROM assignments WHERE id = " + assignment.getId();
                PreparedStatement preparedStatement = conn.prepareStatement(delete_assignments);
                preparedStatement.executeUpdate();

                deleteUploadedAssignment(assignment_list);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUploadedAssignment(ObservableList<Assignments> assignment_list) {
        try {
            Connection conn = dbConnection.connectToDb();
            for (Assignments assignment : assignment_list) {
                String delete_assignments = "DELETE FROM uploaded_assignments WHERE id = " + assignment.getId();
                PreparedStatement preparedStatement = conn.prepareStatement(delete_assignments);
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method accepts  course id and returns relevant assignments
    public ObservableList<Assignments> getUploadedAssignmentsById(int course_id) {
        ObservableList<Assignments> assignments_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String get_assignments = "SELECT * FROM uploaded_assignments ua" +
                    " JOIN assignments a ON ua.assignment_id = a.id" +
                    " WHERE ua.course_id = " + course_id;

            ResultSet rs = conn.createStatement().executeQuery(get_assignments);

            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("id");
                int student_id = rs.getInt("student_id");
                String student_name = getStudentNameById(student_id);
                int assignment_id = rs.getInt("ua.id");
                String uploaded_date = rs.getString("uploaded_date");
                String content = rs.getString("content");
                String topic = rs.getString("topic");
                String status = rs.getString("status");

                Assignments assignment = new Assignments(serial, id, course_id,
                        assignment_id, student_id, student_name,
                        uploaded_date, topic, content, status);
                assignments_list.add(assignment);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return assignments_list;
    }

    public String getStudentNameById(int student_id) {

        String student_name = null;

        try {
            Connection conn = dbConnection.connectToDb();
            String get_student_name = "SELECT name from students WHERE id = " + student_id;

            ResultSet rs1 = conn.createStatement().executeQuery(get_student_name);

            while (rs1.next()) {
                student_name = rs1.getString("name");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return student_name;
    }

    // changing status of uploaded assignment by accepting uploaded_assignment_id and status as arguments
    public void updateAssignmentStatus(int assignments_id, String status) {

        try {
            Connection conn = dbConnection.connectToDb();
            String update_status = "UPDATE uploaded_assignments set status = '" + status + "'"
                    + " WHERE id = " + assignments_id;

            PreparedStatement preparedStatement = conn.prepareStatement(update_status);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
