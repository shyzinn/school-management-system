package result;

import assignments.AssignmentsDatabases;
import config.Dialogs;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Assignments;
import models.Results;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class AssignmentsResultsDatabase {

    DbConnection dbConnection = new DbConnection();

    public boolean addResults(Map<String, Object> result, Assignments assignment) {

        boolean status = false;

        if (!check(assignment.getId())) {

            try {
                Connection conn = dbConnection.connectToDb();
                String insert_result = "INSERT INTO assignments_results (assignment_id, course_id, student_id, total_marks, " +
                        "obtained_marks, percentage, comments) " +
                        "VALUES(?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(insert_result);

                preparedStatement.setInt(1, assignment.getAssignmentId());
                preparedStatement.setInt(2, assignment.getCourseId());
                preparedStatement.setInt(3, assignment.getStudentId());
                preparedStatement.setDouble(4, (Double) result.get("total_marks"));
                preparedStatement.setDouble(5, (Double) result.get("obtained_marks"));
                preparedStatement.setDouble(6, (Double) result.get("percentage"));
                preparedStatement.setString(7, (String) result.get("comments"));
                preparedStatement.executeUpdate();

                //updating assignment status

                new AssignmentsDatabases().updateAssignmentStatus(assignment.getId(), "checked");


                new Dialogs().infoAlert("Success", "Marks are uploaded successfully",
                        "You can check, update and remove in results section.");

                status = true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            new Dialogs().warningAlert("Warning", "This assignment has already evaluated",
                    "This assignment has already marked, go to results tab," +
                            " you will find the marked assignment there.");
        }
        return  status;
    }

    // this method will check already uploaded result for the assignment
    public boolean check(int assignment_id) {
        boolean check = false;
        try {
            Connection conn = dbConnection.connectToDb();
            String check_record = "SELECT * from assignments_results where assignment_id = " + assignment_id;
            ResultSet rs = conn.createStatement().executeQuery(check_record);

            check = rs.next();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public ObservableList<Results> getResultsByCourseId(int course_id) {

        ObservableList<Results> result_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String get_results = "SELECT * FROM assignments_results r "
                    + "JOIN students s on r.student_id = s.id "
                    + "JOIN uploaded_assignments u ON r.assignment_id = u.id "
                    + "JOIN assignments a on u.assignment_id = a.id "
                    + "WHERE r.course_id = " + course_id;

            ResultSet rs = conn.createStatement().executeQuery(get_results);

            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("id");
                int uploaded_assignment_id = rs.getInt("assignment_id");
                String student_name = rs.getString("name");
                String student_registration = rs.getString("reg_no");
                String assignment_topic = rs.getString("topic");
                double total_marks = rs.getDouble("total_marks");
                double obtained_marks = rs.getDouble("obtained_marks");
                double percentage = rs.getDouble("percentage");
                String comments = rs.getString("comments");

                Results result = new Results(serial, id, uploaded_assignment_id,
                        student_name, student_registration,
                        assignment_topic, total_marks, obtained_marks, percentage, comments);
                result.setProgress(percentage);
                result_list.add(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result_list;
    }

    //this method returns the result for specific student and of specific course
    public ObservableList<Results> getResultsByCourseAndStudentID(int course_id, int student_id) {

        ObservableList<Results> result_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String get_results = "SELECT * FROM assignments_results r "
                    + "JOIN students s on r.student_id = s.id "
                    + "JOIN uploaded_assignments u ON r.assignment_id = u.id "
                    + "JOIN assignments a on u.assignment_id = a.id "
                    + "WHERE r.course_id = " + course_id
                    + " AND r.student_id = " + student_id;

            ResultSet rs = conn.createStatement().executeQuery(get_results);

            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("id");
                int uploaded_assignment_id = rs.getInt("assignment_id");
                String student_name = rs.getString("name");
                String student_registration = rs.getString("reg_no");
                String assignment_topic = rs.getString("topic");
                double total_marks = rs.getDouble("total_marks");
                double obtained_marks = rs.getDouble("obtained_marks");
                double percentage = rs.getDouble("percentage");
                String comments = rs.getString("comments");

                Results result = new Results(serial, id, uploaded_assignment_id,
                        student_name, student_registration,
                        assignment_topic, total_marks, obtained_marks, percentage, comments);
                result.setProgress(percentage);
                result_list.add(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result_list;
    }

    // this method takes uploaded result id and change it
    public void changeResult(Results result, double total_marks, double obtained_marks, double percentage,
                             String comments) {

        try {
            Connection conn = dbConnection.connectToDb();
            String update_results = "UPDATE assignments_results set obtained_marks = "
                    + obtained_marks
                    + ",total_marks = "
                    + total_marks
                    + ",percentage = "
                    + percentage
                    + ",comments = '"
                    + comments + "'"
                    + " WHERE id = " + result.getId();

            PreparedStatement preparedStatement = conn.prepareStatement(update_results);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Results getLatestResult() {

        Results result = null;

        try {
            Connection conn = dbConnection.connectToDb();
            String get_results = "SELECT * FROM assignments_results r "
                    + "JOIN students s on r.student_id = s.id "
                    + "JOIN uploaded_assignments u ON r.assignment_id = u.id "
                    + "JOIN assignments a on u.assignment_id = a.id "
                    + "ORDER BY r.id DESC LIMIT 1";

            ResultSet rs = conn.createStatement().executeQuery(get_results);

            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("id");
                int uploaded_assignment_id = rs.getInt("assignment_id");
                String student_name = rs.getString("name");
                String student_registration = rs.getString("reg_no");
                String assignment_topic = rs.getString("topic");
                double total_marks = rs.getDouble("total_marks");
                double obtained_marks = rs.getDouble("obtained_marks");
                double percentage = rs.getDouble("percentage");
                String comments = rs.getString("comments");

                result = new Results(serial, id, uploaded_assignment_id, student_name, student_registration,
                        assignment_topic, total_marks, obtained_marks, percentage, comments);
                result.setProgress(percentage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteResult(ObservableList<Results> result_list) {
        try {
            Connection conn = dbConnection.connectToDb();

            for (Results result : result_list) {
                String delete_query = "DELETE FROM assignments_results WHERE id = " + result.getId();
                PreparedStatement preparedStatement1 = conn.prepareStatement(delete_query);
                preparedStatement1.executeUpdate();

                // after deleting result making assignments status uncheck

                String update_assignment_status = "UPDATE uploaded_assignments SET " +
                        "status = 'unchecked' WHERE id = " + result.getAssignmentId();

                PreparedStatement preparedStatement2 = conn.prepareStatement(update_assignment_status);
                preparedStatement2.executeUpdate();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}