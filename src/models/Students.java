package models;

import javafx.beans.property.SimpleStringProperty;

public class Students {

    SimpleStringProperty name;
    SimpleStringProperty username;
    SimpleStringProperty reg_no;
    SimpleStringProperty department;
    SimpleStringProperty email;
    SimpleStringProperty phone;
    SimpleStringProperty dateJoined;
    SimpleStringProperty password;
    double fee;
    int serial, id, coursesRegistered;


    public Students(int serial, int id, String name, String username, String password, String reg_no, String department,
                    String email, String phone, String date_joined,
                    double fee, int courses_registered) {

        this.serial = serial;
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
        this.username = new SimpleStringProperty(username);
        this.reg_no = new SimpleStringProperty(reg_no);
        this.department = new SimpleStringProperty(department);
        this.email = new SimpleStringProperty(email);
        this.phone = new SimpleStringProperty(phone);
        this.dateJoined = new SimpleStringProperty(date_joined);
        this.fee = fee;
        this.coursesRegistered = courses_registered;

    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
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

    public String getDepartment() {
        return department.get();
    }

    public void setDepartment(String department) {
        this.department.set(department);
    }

    public int getCoursesRegistered() {
        return coursesRegistered;
    }

    public void setCoursesRegistered(int coursesRegistered) {
        this.coursesRegistered = coursesRegistered;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getReg_no() {
        return reg_no.get();
    }

    public void setReg_no(String reg_no) {
        this.reg_no.set(reg_no);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhone() {
        return phone.get();
    }

    public String getDateJoined() {
        return dateJoined.get();
    }

    public void setDateJoined(String dateJoined) {
        this.dateJoined.set(dateJoined);
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

}
