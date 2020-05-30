package result;

import config.Dialogs;
import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Results;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ExamsResultsDatabase {

    DbConnection dbConnection = new DbConnection();

        /*
    this method will accept course_id, student_id, exam_id and enter these foreign keys in result database and
    generate the result for exams
     */

    public void setResult(int course_id, int exam_id, int uploaded_exam_id, int student_id,
                          double total_marks, double obtained_marks, double percentage, String comments) {

        try {
            Connection conn = dbConnection.connectToDb();
            if (!checkIfAlreadyExists(course_id, exam_id, student_id)) {
                String query = "INSERT INTO exams_results (course_id, student_id, " +
                        "exam_id, uploaded_exam_id, total_marks, " +
                        "obtained_marks, percentage, comments) VALUES(?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, course_id);
                preparedStatement.setInt(2, student_id);
                preparedStatement.setInt(3, exam_id);
                preparedStatement.setInt(4, uploaded_exam_id);
                preparedStatement.setDouble(5, total_marks);
                preparedStatement.setDouble(6, obtained_marks);
                preparedStatement.setDouble(7, percentage);
                preparedStatement.setString(8, comments);
                preparedStatement.executeUpdate();
            } else {
                new Dialogs().warningAlert("Warning", "This exam has already evaluated",
                        "In order to see the evaluated exam, go to results tab and select exams");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    here we will check if exam evaluation has already done for this course
     */
    public boolean checkIfAlreadyExists(int course_id, int exam_id, int student_id) {

        boolean check = false;
        try {
            Connection conn = dbConnection.connectToDb();
            String check_query = "SELECT * FROM exams_results WHERE exam_id = " + exam_id
                    + " and course_id = " + course_id
                    + " and student_id = " + student_id;

            ResultSet rs = conn.createStatement().executeQuery(check_query);
            check = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    /*
    this method returns list containing result of student for specific course
     */
    public ObservableList<Results> getResultByStudentID(int course_id, int student_id) {

        ObservableList<Results> results_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String select_results = "SELECT * FROM exams_results e_r " +
                    " JOIN students s" +
                    " ON e_r.student_id = s.id" +
                    " JOIN exams e " +
                    " ON e_r.exam_id = e.id" +
                    " WHERE e_r.course_id = " + course_id
                    + " and e_r.student_id = " + student_id;
            ResultSet rs = conn.createStatement().executeQuery(select_results);
            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("e_r.id");
                int exam_id = rs.getInt("e_r.exam_id");
                String student_name = rs.getString("s.name");
                String student_reg = rs.getString("s.reg_no");
                String exam_code = rs.getString("e.code");
                double total_marks = rs.getDouble("e_r.total_marks");
                double obtained_marks = rs.getDouble("e_r.obtained_marks");
                double percentage = rs.getDouble("e_r.percentage");
                String comments = rs.getString("comments");

                Results result = new Results(serial, id, exam_id, student_id,
                        student_name, student_reg, exam_code, total_marks,
                        obtained_marks, percentage, comments);

                result.setProgress(percentage);

                results_list.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results_list;
    }

    /*
    this method will return result for specific exam and for specific course
     */

    public ObservableList<Results> getResult(int course_id, int exam_id) {

        ObservableList<Results> results_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String select_results = "SELECT * FROM exams_results e_r " +
                    " JOIN students s" +
                    " ON e_r.student_id = s.id" +
                    " JOIN exams e " +
                    " ON e_r.exam_id = e.id" +
                    " WHERE e_r.course_id = " + course_id
                    + " and e_r.exam_id = " + exam_id;
            ResultSet rs = conn.createStatement().executeQuery(select_results);
            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("e_r.id");
                int student_id = rs.getInt("s.id");
                String student_name = rs.getString("s.name");
                String student_reg = rs.getString("s.reg_no");
                String exam_code = rs.getString("e.code");
                double total_marks = rs.getDouble("e_r.total_marks");
                double obtained_marks = rs.getDouble("e_r.obtained_marks");
                double percentage = rs.getDouble("e_r.percentage");
                String comments = rs.getString("comments");

                Results result = new Results(serial, id, exam_id, student_id,
                        student_name, student_reg, exam_code, total_marks,
                        obtained_marks, percentage, comments);

                result.setProgress(percentage);

                results_list.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results_list;
    }

    /*
    this method returns all the results related to that course id
     */
    public ObservableList<Results> getResult(int course_id) {

        ObservableList<Results> results_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String select_results = "SELECT * FROM exams_results e_r " +
                    " JOIN students s" +
                    " ON e_r.student_id = s.id" +
                    " JOIN exams e " +
                    " ON e_r.exam_id = e.id" +
                    " WHERE e_r.course_id = " + course_id;

            ResultSet rs = conn.createStatement().executeQuery(select_results);
            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("e_r.id");
                int exam_id = rs.getInt("e_r.exam_id");
                int student_id = rs.getInt("s.id");
                String student_name = rs.getString("s.name");
                String student_reg = rs.getString("s.reg_no");
                String exam_code = rs.getString("e.code");
                double total_marks = rs.getDouble("e_r.total_marks");
                double obtained_marks = rs.getDouble("e_r.obtained_marks");
                double percentage = rs.getDouble("e_r.percentage");
                String comments = rs.getString("comments");

                Results result = new Results(serial, id, exam_id, student_id,
                        student_name, student_reg, exam_code, total_marks,
                        obtained_marks, percentage, comments);

                result.setProgress(percentage);

                results_list.add(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results_list;
    }

    /*
    this method accepts the course id and student id and returns the total marks,
    obtained marks, total percentage for that specific course
     */
    public Map<String, Object> getTotalMarksForAssignment(int course_id, int student_id) {

        Map<String, Object> marks_stats = new HashMap<>();

        try {
            Connection conn = dbConnection.connectToDb();
            String select_total_marks = "SELECT sum(total_marks) as total_marks" +
                    ", sum(obtained_marks) as obtained_marks" +
                    " FROM assignments_results WHERE course_id = " + course_id +
                    " AND student_id = " + student_id;
            ResultSet rs = conn.createStatement().executeQuery(select_total_marks);

            while (rs.next()) {
                double total_marks = rs.getDouble("total_marks");
                double obtained_marks = rs.getDouble("obtained_marks");
                double percentage = (obtained_marks / total_marks) * 100;

                marks_stats.put("total_marks", total_marks);
                marks_stats.put("obtained_marks", obtained_marks);
                marks_stats.put("percentage", percentage);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return marks_stats;
    }

    public Map<String, Object> getTotalMarksForExams(int course_id, int student_id) {

        Map<String, Object> marks_stats = new HashMap<>();

        try {
            Connection conn = dbConnection.connectToDb();
            String select_total_marks = "SELECT sum(total_marks) as total_marks" +
                    ", sum(obtained_marks) obtained_marks" +
                    " FROM exams_results WHERE course_id = " + course_id +
                    " AND student_id = " + student_id;
            ResultSet rs = conn.createStatement().executeQuery(select_total_marks);

            while (rs.next()) {
                double total_marks = rs.getDouble("total_marks");
                double obtained_marks = rs.getDouble("obtained_marks");
                double percentage = (obtained_marks / total_marks) * 100;

                marks_stats.put("total_marks", total_marks);
                marks_stats.put("obtained_marks", obtained_marks);
                marks_stats.put("percentage", percentage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return marks_stats;
    }

    public Results getLatestResult() {

        Results result = null;

        try {
            Connection conn = dbConnection.connectToDb();
            String select_total_marks = "SELECT * FROM exams_results e_r " +
                    " JOIN students s" +
                    " ON e_r.student_id = s.id" +
                    " JOIN exams e " +
                    " ON e_r.exam_id = e.id" +
                    " ORDER BY e_r.id DESC LIMIT 1";
            ResultSet rs = conn.createStatement().executeQuery(select_total_marks);

            while (rs.next()) {

                int id = rs.getInt("e_r.id");
                int exam_id = rs.getInt("e_r.exam_id");
                int student_id = rs.getInt("s.id");
                String student_name = rs.getString("s.name");
                String student_reg = rs.getString("s.reg_no");
                String exam_code = rs.getString("e.code");
                double total_marks = rs.getDouble("e_r.total_marks");
                double obtained_marks = rs.getDouble("e_r.obtained_marks");
                double percentage = rs.getDouble("e_r.percentage");
                String comments = rs.getString("comments");

                result = new Results(0, id, exam_id, student_id, student_name, student_reg, exam_code, total_marks,
                        obtained_marks, percentage, comments);

                result.setProgress(percentage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void deleteResults(ObservableList<Results> results_list) {

        try {
            Connection conn = dbConnection.connectToDb();
            for (Results result : results_list) {
                String delete_query = "DELETE FROM exams_results WHERE id = " + result.getId();
                PreparedStatement preparedStatement = conn.prepareStatement(delete_query);
                preparedStatement.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeResult(Results result, double total_marks, double obtained_marks, double percentage,
                             String comments) {

        try {
            Connection conn = dbConnection.connectToDb();
            String update_query = "UPDATE exams_results SET total_marks = ?, " +
                    " obtained_marks=?, percentage=?, comments=? WHERE id = " + result.getId();

            PreparedStatement preparedStatement = conn.prepareStatement(update_query);
            preparedStatement.setDouble(1, total_marks);
            preparedStatement.setDouble(2, obtained_marks);
            preparedStatement.setDouble(3, percentage);
            preparedStatement.setString(4, comments);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
