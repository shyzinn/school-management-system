package models;

import javafx.beans.property.SimpleStringProperty;

public class CoursePlan {
    int id, courseId;
    SimpleStringProperty date, plan;

    public CoursePlan(int id, int course_id, String date, String plan) {

        this.id = id;
        this.courseId = course_id;
        this.date = new SimpleStringProperty(date);
        this.plan = new SimpleStringProperty(plan);
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getPlan() {
        return plan.get();
    }

    public void setPlan(String plan) {
        this.plan.set(plan);
    }
}
