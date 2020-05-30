package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.awt.*;

public class Attendance {

    SimpleStringProperty studentName, studentRegistration, status, date;
    int serial, id, courseId, studentId;
    HBox attendanceNode;

    public Attendance(int serial, int id, int course_id, int student_id,
                      String student_name, String student_registration, String status, String date) {

        this.serial = serial;
        this.id = id;
        this.courseId = course_id;
        this.studentId = student_id;
        this.studentName = new SimpleStringProperty(student_name);
        this.studentRegistration = new SimpleStringProperty(student_registration);
        this.attendanceNode = new HBox();
        this.status = new SimpleStringProperty(status);
        this.date = new SimpleStringProperty(date);

        setAttendanceNode(this.attendanceNode, status);
    }


    public void setAttendanceNode(HBox attendance_node, String status) {

        this.attendanceNode = attendance_node;

        Button present_btn = new Button("P");
        Button absent_btn = new Button("A");
        Button leave_btn = new Button("L");

        present_btn.getStyleClass().add("present-btn");
        absent_btn.getStyleClass().add("absent-btn");
        leave_btn.getStyleClass().add("leave-btn");

        present_btn.setMaxWidth(Double.MAX_VALUE);
        leave_btn.setMaxWidth(Double.MAX_VALUE);
        absent_btn.setMaxWidth(Double.MAX_VALUE);

        if (status.equals("P")) {
            present_btn.getStyleClass().add("selected-btn-present");
        } else if (status.equals("A")) {
            absent_btn.getStyleClass().add("selected-btn-absent");
        } else if (status.equals("L")) {
            leave_btn.getStyleClass().add("selected-btn-leave");
        }

        this.attendanceNode.getChildren().addAll(present_btn, leave_btn, absent_btn);
        this.attendanceNode.setSpacing(20);
        this.attendanceNode.setAlignment(Pos.CENTER);

    }

    public String getStudentRegistration() {
        return studentRegistration.get();
    }

    public void setStudentRegistration(String studentRegistration) {
        this.studentRegistration.set(studentRegistration);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public HBox getAttendanceNode() {
        return attendanceNode;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }


}
