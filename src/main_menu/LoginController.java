package main_menu;

import admin.AdminDashboard;
import config.Dialogs;
import config.HashPassword;
import db.CreateTables;
import db.DbConnection;
import faculty.FacultyDashboard;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import student.StudentDashboard;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public class LoginController implements Initializable {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton adminRadioBtn;

    @FXML
    private RadioButton studentRadioBtn;

    @FXML
    private RadioButton facultyRadioBtn;

    @FXML
    private ProgressIndicator progressSpinner;

    @FXML
    private Label progressLabel;

    ToggleGroup loginGroup;

    public static int loginID;
    public static String loginPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // first making group of radio box, so one can be selected at a time

        loginGroup = new ToggleGroup();
        adminRadioBtn.setToggleGroup(loginGroup);
        studentRadioBtn.setToggleGroup(loginGroup);
        facultyRadioBtn.setToggleGroup(loginGroup);

        facultyRadioBtn.setSelected(true);


    }

    public void setLoginWin(Parent root, Stage win) {

        progressSpinner.setVisible(false);

        Scene scene = new Scene(root, 380, 330);
        Image main_icon = new Image(getClass().getResourceAsStream("/resources/icons/school_36px.png"));
        win.getIcons().add(main_icon);
        win.setResizable(false);
        scene.getStylesheets().add(getClass().getResource("/resources/css_files/style.css").toExternalForm());
        win.setScene(scene);
        usernameField.requestFocus();
        win.show();
    }

    public String checkCredentials() {

        String username = usernameField.getText();
        String password = new HashPassword().encrypt(passwordField.getText());

        String type = ((RadioButton) loginGroup.getSelectedToggle()).getText().toLowerCase();
        String login_type = "";

        try {
            String get_credentials;
            Connection conn = new DbConnection().connectToDb();

            // if checkbox selected equals admin then looking for credentials in admin table

            switch (type) {
                case "admin": {

                    // we will take admin credentials from database, for now we are using default
//                    get_credentials = "SELECT username, password FROM admin_credentials WHERE username = '"
//                            + username + "'" + " and password = '" + password + "'";
//
//                    Statement stmt = conn.createStatement();
//                    ResultSet rs = stmt.executeQuery(get_credentials);

                    // if there is any matching then we will check if result exists by rs.next and return true for login

                    //this is for testing purpose
                    password = passwordField.getText();
                    if (username.equals("admin") && password.equals("admin")) {
                        login_type = "admin";
                    }
                    return login_type;

                }
                case "student": {
                    get_credentials = "SELECT username, password, id FROM students WHERE username = '"
                            + username + "'" + " and password = '" + password + "'";

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(get_credentials);

                    if (rs.next()) {
                        loginPassword = rs.getString("password");
                        login_type = "student";
                        loginID = rs.getInt("id");
                    }
                    return login_type;

                }
                case "faculty": {
                    get_credentials = "SELECT username, password, id FROM faculty WHERE username = '"
                            + username + "'" + " and password = '" + password + "'";

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(get_credentials);

                    if (rs.next()) {
                        loginPassword = rs.getString("password");
                        login_type = "faculty";
                        loginID = rs.getInt("id");
                    }
                    return login_type;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return login_type;
    }

    @FXML
    private void login() {

        String login_type = checkCredentials();

        switch (login_type) {
            case "admin":
                adminDashboard();
                break;
            case "student":
                studentDashboard();
                break;
            case "faculty":
                facultyDashboard();
                break;
            default:
                new Dialogs().errorAlert("Error", "Invalid username or password",
                        "Please make sure to enter valid username and password");
                break;
        }

    }

    public void adminDashboard() {

        try {
            // loading admin dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/admin/admin_dashboard.fxml"));
            Parent root = loader.load();
            AdminDashboard admin = loader.getController();
            admin.setAdminDashboard(root);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void studentDashboard() {
        try {
            // loading student dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/student/student_dashboard.fxml"));
            Parent root = loader.load();
            StudentDashboard studentDashboard = loader.getController();
            studentDashboard.setStudentDashboard(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void facultyDashboard() {
        try {
            // loading faculty dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/faculty/faculty_dashboard.fxml"));
            Parent root = loader.load();
            FacultyDashboard faculty = loader.getController();
            faculty.setFacultyDashboard(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setTablesOnDatabase() {
        progressSpinner.setVisible(true);
        progressLabel.setText("Please Wait");
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        new CreateTables();
                        final CountDownLatch latch = new CountDownLatch(1);
                        Platform.runLater(() -> {
                            try {
                                progressLabel.setText("Tables Generated");
                                progressSpinner.setVisible(false);
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                latch.countDown();
                            }
                        });
                        latch.await();
                        // Keep with the background work
                        return null;
                    }
                };
            }
        };
        service.start();
    }
}
