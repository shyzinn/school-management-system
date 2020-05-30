package student;

import config.Dialogs;
import config.HashPassword;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main_menu.LoginController;
import models.Students;

import java.net.URL;
import java.util.ResourceBundle;

// this class allows students to edit their personal info like phone number and email
public class EditInfo implements Initializable {

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    int studentId;

    Students student;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        studentId = LoginController.loginID;

        student = new StudentDatabases().getStudentByID(studentId);

        emailField.setText(student.getEmail());
        phoneField.setText(student.getPhone());
        usernameField.setText(student.getUsername());
        oldPasswordField.setText(new HashPassword().decrypt(student.getPassword()));

    }

    public void setEditWin(Parent root) {
        Stage win = new Stage();
        Scene scene = new Scene(root, 400, 400);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.showAndWait();
    }

    @FXML
    private void updateInfo() {

        String email = emailField.getText();
        String phone = phoneField.getText();

        if (!email.isEmpty() && !phone.isEmpty()) {

            new StudentDatabases().updateInfo(email, phone, studentId);
        } else {
            new Dialogs().warningAlert("Warning", "Please fill required fields",
                    "Make sure to fill the required fields to update info");
        }

    }

    @FXML
    private void updatePassword() {
        oldPasswordField.setText(new HashPassword().decrypt(student.getPassword()));

        String old_password = oldPasswordField.getText();
        String new_password = newPasswordField.getText();
        String username = usernameField.getText();

        if (!old_password.isEmpty() && !new_password.isEmpty() && !username.isEmpty()) {

            boolean status = new StudentDatabases().updateCredentials(username, old_password,
                    new_password, studentId);

            if (status) {
                student.setPassword(new HashPassword().encrypt(new_password));
                oldPasswordField.setText(new_password);
            }

        } else {
            new Dialogs().warningAlert("Warning", "Please fill required fields",
                    "Make sure to fill the required fields to update info");
        }
    }
}
