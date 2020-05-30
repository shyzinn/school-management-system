package admin;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import config.GetDate;
import config.HashPassword;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import student.StudentDatabases;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class RegisterStudent implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private DatePicker regDateField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //setting current day date in startDateField
        LocalDate date_value = java.time.LocalDate.now();
        regDateField.setValue(date_value);
    }

    public void setRegisterWin(Parent root) {

        // this function will open new window for student registration form
        Stage win = new Stage();
        Scene scene = new Scene(root, 500, 500);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.showAndWait();
    }

    @FXML
    private void registerStudent() {

        String name = nameField.getText();
        String department = departmentField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String date_joined = regDateField.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String username = usernameField.getText();
        String password = passwordField.getText();

        if(!name.isEmpty() && !department.isEmpty() && !date_joined.isEmpty()
                && !username.isEmpty() && !password.isEmpty()) {

            // reg_no is registration number of student
            String reg_no = generateRegNo(department);

            Map<String, Object> student_details = new HashMap<>();
            student_details.put("name", name);
            student_details.put("department", department);
            student_details.put("email", email);
            student_details.put("phone", phone);
            student_details.put("date_joined", date_joined);
            student_details.put("reg_no", reg_no);
            student_details.put("username", username);
            student_details.put("password", new HashPassword().encrypt(password));

            // initially fee for student is 0 when he/she will register course then we will add fee
            student_details.put("fee", 0.0);


            StudentDatabases studentDatabases = new StudentDatabases();
            studentDatabases.registerStudent(student_details);
            AdminDashboard.admin.studentController.setNewStudent();
        }else{
            new Dialogs().warningAlert("Warning" ,"Please fill out the required fields",
                    "You must complete the required information of student.");
        }
    }

    // this method takes current year and department and generate registration number
    public String generateRegNo(String department) {

        String year = Integer.toString(new GetDate().getYear());
        String number = Integer.toString(new StudentDatabases().getLastestStudentID() + 1);

        return department + "-" + year + "-" + number;
    }

    @FXML
    private void clearForm(){
        nameField.clear();
        departmentField.clear();
        emailField.clear();
        phoneField.clear();
    }
}
