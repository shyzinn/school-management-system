package models;

import config.ValidateFields;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;

import java.util.Arrays;
import java.util.List;

public class Results {

    SimpleStringProperty studentName, studentRegistration, assignmentTopic, comments, examCode;
    double obtainedMarks, totalMarks, percentage;
    int id, serial, assignmentId, examId, studentId;
    ProgressBar marksProgress;
    StackPane percentageNode;

    public Results(int serial, int id, int assignment_id, String student_name,
                   String student_registration, String assignment_topic,
                   double total_marks, double obtained_marks, double percentage,
                   String comments) {

        this.serial = serial;
        this.id = id;
        this.assignmentId = assignment_id;
        this.studentName = new SimpleStringProperty(student_name);
        this.studentRegistration = new SimpleStringProperty(student_registration);
        this.assignmentTopic = new SimpleStringProperty(assignment_topic);
        this.obtainedMarks = obtained_marks;
        this.totalMarks = total_marks;
        this.percentage = percentage;
        this.marksProgress = new ProgressBar();
        this.marksProgress.setMaxWidth(Double.MAX_VALUE);
        this.comments = new SimpleStringProperty(comments);
        this.setPercentageNode(percentage);

    }

    /*
    this constructor is for exam result
     */
    public Results(int serial, int id, int exam_id, int student_id, String student_name,
                   String student_registration, String exam_code,
                   double total_marks, double obtained_marks, double percentage, String comments) {

        this.serial = serial;
        this.id = id;
        this.examId = exam_id;
        this.studentId = student_id;
        this.comments = new SimpleStringProperty(comments);
        this.studentName = new SimpleStringProperty(student_name);
        this.studentRegistration = new SimpleStringProperty(student_registration);
        this.examCode = new SimpleStringProperty(exam_code);
        this.obtainedMarks = obtained_marks;
        this.totalMarks = total_marks;
        this.percentage = percentage;
        this.marksProgress = new ProgressBar();
        this.marksProgress.setMaxWidth(Double.MAX_VALUE);
        this.setPercentageNode(percentage);

    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getExamCode() {
        return examCode.get();
    }

    public SimpleStringProperty examCodeProperty() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode.set(examCode);
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public void setPercentageNode(double percentage) {
        this.percentageNode = new StackPane();
        this.percentageNode.getChildren().add(this.marksProgress);
        Label percentage_label = new Label(Double.toString(ValidateFields.round(percentage,2)));
        percentage_label.getStyleClass().add("percentage-label");
        this.percentageNode.getChildren().add(percentage_label);
    }

    public void setProgress(double value) {

        double progress = value / 100;
        this.marksProgress.setProgress(progress);

        List<String> colors_list = Arrays.asList("green-bar", "yellow-bar", "orange-bar", "red-bar");

        this.marksProgress.getStyleClass().removeAll(colors_list);

        if (progress >= 0.80) {
            this.marksProgress.getStyleClass().add("green-bar");

        } else if (progress >= 0.60 && progress < 0.8) {
            this.marksProgress.getStyleClass().add("yellow-bar");

        } else if (progress >= 0.30 && progress < 0.60) {
            this.marksProgress.getStyleClass().add("orange-bar");

        } else if (progress < 0.30) {
            this.marksProgress.getStyleClass().add("red-bar");
        }
    }

    public String getComments() {
        return comments.get();
    }

    public SimpleStringProperty commentsProperty() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments.set(comments);
    }

    public StackPane getPercentageNode() {
        return percentageNode;
    }

    public void setPercentageNode(StackPane percentageNode) {
        this.percentageNode = percentageNode;
    }

    public ProgressBar getMarksProgress() {
        return marksProgress;
    }

    public void setMarksProgress(ProgressBar marksProgress) {
        this.marksProgress = marksProgress;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getStudentName() {
        return studentName.get();
    }


    public void setStudentName(String studentName) {
        this.studentName.set(studentName);
    }

    public String getStudentRegistration() {
        return studentRegistration.get();
    }

    public void setStudentRegistration(String studentRegistration) {
        this.studentRegistration.set(studentRegistration);
    }

    public double getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(double obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssignmentTopic() {
        return assignmentTopic.get();
    }

    public SimpleStringProperty assignmentTopicProperty() {
        return assignmentTopic;
    }

    public void setAssignmentTopic(String assignmentTopic) {
        this.assignmentTopic.set(assignmentTopic);
    }
}
