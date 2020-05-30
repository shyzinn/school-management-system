package student;

import faculty.FacultyDashboard;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main_menu.LoginController;
import main_menu.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentDashboard implements Initializable {

    @FXML
    CoursesController coursesController;

    Stage studentDashboardStage;

    static StudentDashboard studentDashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Student dashboard initiated");
        studentDashboard = this;

    }

    public void setStudentDashboard(Parent root) {
        try {
            new Main().getLoginWindow().close();
            studentDashboardStage = new Stage();
            Scene scene = new Scene(root, 900, 305);
            Image main_icon = new Image(getClass().getResourceAsStream("/resources/icons/school_36px.png"));
            studentDashboardStage.getIcons().add(main_icon);
            studentDashboardStage.setScene(scene);
            studentDashboardStage.setMaximized(true);
            studentDashboardStage.setOnCloseRequest(e -> Platform.exit());
            studentDashboardStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        studentDashboardStage.close();
        new Main().setLoginWindow();
        CoursesController.courseId = 0;
    }

    @FXML
    private void updateInfo() {
        try {
            // loading the application base / main menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/student/edit_info.fxml"));
            Parent root = loader.load();
            EditInfo edit_info = loader.getController();
            edit_info.setEditWin(root);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void exit() {
        Platform.exit();
    }
}
