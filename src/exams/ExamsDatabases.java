package exams;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import db.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.Exams;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Map;

public class ExamsDatabases {

    DbConnection dbConnection = new DbConnection();

    public void addNewExam(Map<String, Object> exam_details) {

        try {
            Connection conn = dbConnection.connectToDb();
            String insert_exam = "INSERT INTO exams (course_id, code, details, start_date, start_time, " +
                    "time_allotted, content)" +
                    " VALUES (?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(insert_exam);

            String start_time = (String) exam_details.get("start_time");

            preparedStatement.setInt(1, (int) exam_details.get("course_id"));
            preparedStatement.setString(2, (String) exam_details.get("exam_code"));
            preparedStatement.setString(3, (String) exam_details.get("details"));
            preparedStatement.setString(4, (String) exam_details.get("start_date"));
            preparedStatement.setString(5, start_time);
            preparedStatement.setInt(6, (int) exam_details.get("time_allotted"));
            preparedStatement.setString(7, (String) exam_details.get("content"));
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUploadedExam(int course_id, int student_id, int exam_id, int time_taken, String completed_on,
                                String content) {

        try {
            Connection conn = dbConnection.connectToDb();
            String insert_query = "INSERT INTO uploaded_exams (course_id, student_id, exam_id, time_taken" +
                    ", completed_on, content, status) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(insert_query);
            preparedStatement.setInt(1, course_id);
            preparedStatement.setInt(2, student_id);
            preparedStatement.setInt(3, exam_id);
            preparedStatement.setInt(4, time_taken);
            preparedStatement.setString(5, completed_on);
            preparedStatement.setString(6, content);
            preparedStatement.setString(7, "unchecked");

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteExams(ObservableList<Exams> selected_exams) {

        try {
            Connection conn = dbConnection.connectToDb();

            for (Exams exam : selected_exams) {
                String insert_exam = "DELETE FROM exams where id = " + exam.getId();
                PreparedStatement preparedStatement = conn.prepareStatement(insert_exam);
                preparedStatement.executeUpdate();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Exams> getExamsByCourseID(int course_id) {

        ObservableList<Exams> exams_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String get_exams = "SELECT * FROM exams WHERE course_id = " + course_id;
            ResultSet rs = conn.createStatement().executeQuery(get_exams);

            int serial = 0;

            while (rs.next()) {

                serial++;
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String details = rs.getString("details");
                String content = rs.getString("content");
                String start_time = rs.getString("start_time");
                String start_date = rs.getString("start_date");
                int time_allocated = rs.getInt("time_allotted");

                Exams exam = new Exams(serial, id, course_id, code, details, content, start_date, start_time, time_allocated);

                exams_list.add(exam);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exams_list;
    }

    public Exams getLatestExam() {

        Exams exam = null;

        try {
            Connection conn = dbConnection.connectToDb();
            String get_exams = "SELECT * FROM exams ORDER BY ID DESC LIMIT 1";
            ResultSet rs = conn.createStatement().executeQuery(get_exams);

            int serial = 0;

            while (rs.next()) {

                serial++;
                int id = rs.getInt("id");
                int course_id = rs.getInt("course_id");
                String code = rs.getString("code");
                String details = rs.getString("details");
                String content = rs.getString("content");
                String start_time = rs.getString("start_time");
                String start_date = rs.getString("start_date");
                int time_allotted = rs.getInt("time_allotted");

                exam = new Exams(serial, id, course_id, code, details, content,
                        start_date, start_time, time_allotted);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return exam;
    }

    public ObservableList<Exams> getUploadedExamsByCourseId(int course_id) {

        ObservableList<Exams> uploaded_exams_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String select = "SELECT * FROM uploaded_exams u_e " +
                    " JOIN exams e ON  e.id = u_e.exam_id" +
                    " JOIN students s ON s.id = u_e.student_id" +
                    " WHERE u_e.course_id = " + course_id;

            ResultSet rs = conn.createStatement().executeQuery(select);

            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("u_e.id");
                int student_id = rs.getInt("u_e.student_id");
                int exam_id = rs.getInt("u_e.exam_id");
                String exam_code = rs.getString("e.code");
                String details = rs.getString("e.details");
                String student_name = rs.getString("s.name");
                String student_registration = rs.getString("s.reg_no");
                int time_taken = rs.getInt("u_e.time_taken");
                String completed_on = rs.getString("u_e.completed_on");
                String content = rs.getString("u_e.content");
                String status = rs.getString("status");

                Exams uploaded_exam = new Exams(serial, id, course_id, exam_id,
                        student_id, student_name, student_registration,
                        exam_code, details, completed_on, time_taken, content, status);

                uploaded_exams_list.add(uploaded_exam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploaded_exams_list;
    }

    public ObservableList<Exams> getUploadedExamsByStudentId(int course_id, int student_id) {
        ObservableList<Exams> uploaded_exams_list = FXCollections.observableArrayList();

        try {
            Connection conn = dbConnection.connectToDb();
            String select = "SELECT * FROM uploaded_exams u_e " +
                    " JOIN exams e ON  e.id = u_e.exam_id" +
                    " JOIN students s ON s.id = u_e.student_id" +
                    " WHERE exam_id = " + course_id +
                    " AND student_id = " + student_id;

            ResultSet rs = conn.createStatement().executeQuery(select);

            int serial = 0;
            while (rs.next()) {
                serial++;
                int id = rs.getInt("u_e.id");
                int exam_id = rs.getInt("u_e.exam_id");
                String exam_code = rs.getString("e.code");
                String details = rs.getString("e.details");
                String student_name = rs.getString("s.name");
                String student_registration = rs.getString("s.reg_no");
                int time_taken = rs.getInt("u_e.time_taken");
                String completed_on = rs.getString("u_e.completed_on");
                String content = rs.getString("ue_.content");
                String status = rs.getString("status");

                Exams uploaded_exam = new Exams(serial, id, course_id, exam_id,
                        student_id, student_name, student_registration,
                        exam_code, details, completed_on, time_taken, content, status);

                uploaded_exams_list.add(uploaded_exam);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploaded_exams_list;
    }

    /*
    this method takes student id and course id and check whether
    this student has taken exam for this specific course
     */
    public boolean checkTakenExams(int course_id, int exam_id, int student_id) {

        boolean check = false;
        try {
            Connection conn = dbConnection.connectToDb();
            String check_query = "SELECT * FROM uploaded_exams WHERE exam_id = " + exam_id +
                    " AND student_id = " + student_id +
                    " AND course_id = " + course_id;
            ResultSet rs = conn.createStatement().executeQuery(check_query);
            check = rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }

    public void updateExamStatus(int exam_id, String status) {
        try {
            Connection conn = dbConnection.connectToDb();
            String update_status = "UPDATE uploaded_exams set status = '" + status + "'"
                    + " WHERE id = " + exam_id;

            PreparedStatement preparedStatement = conn.prepareStatement(update_status);
            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
