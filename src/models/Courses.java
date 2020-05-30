package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableArray;
import javafx.scene.control.CheckBox;

public class Courses {

    SimpleStringProperty courseName;
    SimpleStringProperty courseCode;
    SimpleStringProperty courseDepartment;
    SimpleStringProperty courseStartDate;
    SimpleStringProperty courseEndDate;
    SimpleStringProperty courseFaculty;
    int id, serial;
    double fee;

    CheckBox courseSelection;

    public Courses(int serial, int id, String course_name, String course_code, String course_department,
                   String course_start_date, String course_end_date, double fee, String course_faculty) {

        this.id = id;
        this.serial = serial;
        this.courseName = new SimpleStringProperty(course_name);
        this.courseCode = new SimpleStringProperty(course_code);
        this.courseDepartment = new SimpleStringProperty(course_department);
        this.courseFaculty = new SimpleStringProperty(course_faculty);
        this.courseStartDate = new SimpleStringProperty(course_start_date);
        this.courseEndDate = new SimpleStringProperty(course_end_date);
        this.courseSelection = new CheckBox();
        this.fee = fee;

    }

    public String getCourseFaculty() {
        return courseFaculty.get();
    }

    public void setCourseFaculty(String courseFaculty) {
        this.courseFaculty.set(courseFaculty);
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public CheckBox getCourseSelection() {
        return courseSelection;
    }

    public void setCourseSelection(CheckBox courseSelection) {
        this.courseSelection = courseSelection;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }

    public String getCourseName() {
        return courseName.get();
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public String getCourseCode() {
        return courseCode.get();
    }

    public void setCourseCode(String courseCode) {
        this.courseCode.set(courseCode);
    }

    public String getCourseDepartment() {
        return courseDepartment.get();
    }

    public void setCourseDepartment(String courseDepartment) {
        this.courseDepartment.set(courseDepartment);
    }

    public String getCourseStartDate() {
        return courseStartDate.get();
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate.set(courseStartDate);
    }

    public String getCourseEndDate() {
        return courseEndDate.get();
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate.set(courseEndDate);
    }
}
