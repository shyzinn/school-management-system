// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\





package admin;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main_menu.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboard implements Initializable {

    @FXML
    CourseController courseController;
    @FXML
    FacultyController facultyController;
    @FXML
    StudentController studentController;

    Stage admin_window;

    static AdminDashboard admin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("Admin Controller initiated: ");
        admin = this;
    }

    public void setAdminDashboard(Parent root) {
        try {
            new Main().getLoginWindow().close();
            admin_window = new Stage();
            Scene scene = new Scene(root, 900, 305);
            Image main_icon = new Image(getClass().getResourceAsStream("/resources/icons/school_36px.png"));
            admin_window.getIcons().add(main_icon);
            admin_window.setScene(scene);
            admin_window.setMaximized(true);
            admin_window.show();

            // this will close all the remaining child windows opened related to this main window
            admin_window.setOnCloseRequest(e -> {
                Platform.exit();
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this function will take us to login screen by closing admin menu
    @FXML
    private void logout() {
        admin_window.close();
        new Main().setLoginWindow();
    }

    @FXML
    public void exit(){
        Platform.exit();
    }
}
