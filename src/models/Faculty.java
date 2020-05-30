package models;

import javafx.beans.property.SimpleStringProperty;

public class Faculty {

    SimpleStringProperty name;
    SimpleStringProperty department;
    SimpleStringProperty officeNo;
    SimpleStringProperty dateJoined;
    Double salary;
    int serial, coursesCount,id;

    public Faculty(int serial, int id, String name, String department, String office_no,
                   Double salary, int courses_count, String date_joined) {

        this.serial = serial;
        this.id = id;
        this.coursesCount = courses_count;
        this.name = new SimpleStringProperty(name);
        this.department = new SimpleStringProperty(department);
        this.officeNo = new SimpleStringProperty(office_no);
        this.salary = salary;
        this.dateJoined = new SimpleStringProperty(date_joined);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getCoursesCount() {
        return coursesCount;
    }

    public void setCoursesCount(int coursesCount) {
        this.coursesCount = coursesCount;
    }

    public int getSerial() {
        return serial;
    }

    public void setSerial(int serial) {
        this.serial = serial;
    }


    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public String getOfficeNo() {
        return officeNo.get();
    }

    public void setOfficeNo(String officeNo) {
        this.officeNo.set(officeNo);
    }

    public String getDateJoined() {
        return dateJoined.get();
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined.set(dateJoined);
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
}
