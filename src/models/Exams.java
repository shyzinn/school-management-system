package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.SortedMap;

public class Exams {

    int id, courseId, serial, examId, studentId, timeAllotted, timeTaken;
    SimpleStringProperty code, details, startDate, startTime, studentRegistration, studentName;
    SimpleStringProperty completedOn, content;
    Label statusLabel;
    Button takeExamBtn;


    public Exams(int serial, int id, int course_id, String code, String details, String content, String start_date, String start_time,
                 int time_allotted) {

        this.serial = serial;
        this.id = id;
        this.courseId = course_id;
        this.code = new SimpleStringProperty(code);
        this.details = new SimpleStringProperty(details);
        this.content = new SimpleStringProperty(content);
        this.startDate = new SimpleStringProperty(start_date);
        this.startTime = new SimpleStringProperty(start_time);
        this.timeAllotted = time_allotted;
        this.takeExamBtn = new Button("Take Exam");
        this.takeExamBtn.setMaxWidth(Double.MAX_VALUE);
        this.takeExamBtn.getStyleClass().add("secondary-btn");

    }

    // this constructor is used for uploaded exams
    public Exams(int serial, int id, int course_id, int exam_id, int student_id,
                 String student_name, String student_registration, String code, String details,
                 String completed_on, int time_taken, String content, String status) {

        this.serial = serial;
        this.id = id;
        this.courseId = course_id;
        this.examId = exam_id;
        this.code = new SimpleStringProperty(code);
        this.details = new SimpleStringProperty(details);
        this.studentName = new SimpleStringProperty(student_name);
        this.studentRegistration = new SimpleStringProperty(student_registration);
        this.studentId = student_id;
        this.completedOn = new SimpleStringProperty(completed_on);
        this.timeTaken = time_taken;
        this.content = new SimpleStringProperty(content);
        this.statusLabel = new Label(status);
        setStatusLabel(status);
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(String status) {
        this.statusLabel.setText(status);
        if (status.equals("checked")) {
            this.statusLabel.getStyleClass().add("checked-label");
        } else {
            this.statusLabel.getStyleClass().add("unchecked-label");
        }
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public Button getTakeExamBtn() {
        return takeExamBtn;
    }

    public void setTakeExamBtn(Button takeExamBtn) {
        this.takeExamBtn = takeExamBtn;
    }

    public String getContent() {
        return content.get();
    }

    public SimpleStringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public String getStudentRegistration() {
        return studentRegistration.get();
    }

    public void setStudentRegistration(String studentRegistration) {
        this.studentRegistration.set(studentRegistration);
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public String getCompletedOn() {
        return completedOn.get();
    }

    public void setCompletedOn(String completedOn) {
        this.completedOn.set(completedOn);
    }

    public int getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(int timeTaken) {
        this.timeTaken = timeTaken;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getCode() {
        return code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public String getStartDate() {
        return startDate.get();
    }

    public void setStartDate(String startDate) {
        this.startDate.set(startDate);
    }

    public String getStartTime() {
        return startTime.get();
    }


    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public int getTimeAllotted() {
        return timeAllotted;
    }

    public void setTimeAllotted(int timeAllotted) {
        this.timeAllotted = timeAllotted;
    }

    public String getDetails() {
        return details.get();
    }

    public void setDetails(String details) {
        this.details.set(details);
    }
}
