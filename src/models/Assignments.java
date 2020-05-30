package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;

public class Assignments {

    SimpleStringProperty topic, deadline, content, submittedDate, studentName;
    int courseId, id, serial, studentId, assignmentId;
    long daysRemaining;
    Label statusLabel;

    public Assignments(int serial, int id, int course_id, String topic, String content,
                       String deadline, long days_remaining) {

        this.serial = serial;
        this.id = id;
        this.courseId = course_id;
        this.topic = new SimpleStringProperty(topic);
        this.content = new SimpleStringProperty(content);
        this.deadline = new SimpleStringProperty(deadline);
        this.daysRemaining = days_remaining;
    }

    // this constructor is for uploaded assignments
    public Assignments(int serial, int id, int course_id, int assignment_id, int student_id,
                       String student_name,
                       String uploaded_date,
                       String topic,
                       String content, String status) {

        this.serial = serial;
        this.id = id;
        this.courseId = course_id;
        this.studentId = student_id;
        this.assignmentId = assignment_id;
        this.studentName = new SimpleStringProperty(student_name);
        this.topic = new SimpleStringProperty(topic);
        this.content = new SimpleStringProperty(content);
        this.submittedDate = new SimpleStringProperty(uploaded_date);
        this.statusLabel = new Label(status);

        setStatusLabel(this.statusLabel);

    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(Label statusLabel) {
        this.statusLabel = statusLabel;
        String status = statusLabel.getText();

        if (status.equals("checked")) {
            this.statusLabel.getStyleClass().add("checked-label");
        } else {
            this.statusLabel.getStyleClass().add("unchecked-label");
        }
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getStudentName() {
        return studentName.get();
    }

    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
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

    public long getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int course_id) {
        this.courseId = course_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic.get();
    }

    public void setTopic(String topic) {
        this.topic.set(topic);
    }

    public String getDeadline() {
        return deadline.get();
    }

    public void setDeadline(String deadline) {
        this.deadline.set(deadline);
    }

    public String getSubmittedDate() {
        return submittedDate.get();
    }

    public void setSubmittedDate(String submittedDate) {
        this.submittedDate.set(submittedDate);
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
