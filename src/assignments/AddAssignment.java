package assignments;

import assignments.AssignmentsDatabases;
import config.Dialogs;
import courses.CourseDatabases;
import faculty.FacultyDashboard;
import faculty.FacultyDatabases;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main_menu.LoginController;
import models.Assignments;
import models.Courses;
import models.Faculty;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

/*
    this class will allow faculty to create new assignment
 */
public class AddAssignment implements Initializable {

    @FXML
    private TextField topicField;

    @FXML
    private DatePicker deadlineDateField;

    @FXML
    private TextArea assignmentContentEditor;

    static int facultyId;

    Faculty faculty;

    Stage win;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        facultyId = LoginController.loginID;

        faculty = new FacultyDatabases().getFacultyByID(facultyId);
        LocalDate date_value = java.time.LocalDate.now();
        deadlineDateField.setValue(date_value);
    }

    public void setNewAssignmentWin(Parent root) {

        win = new Stage();
        Scene scene = new Scene(root, 700, 400);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.show();
    }

    @FXML
    private void uploadAssignment() {

        int course_id = new FacultyDashboard().getFacultyDashboard().getSelectedCourseId();

        String assignment_topic = topicField.getText();
        String assignment_content = assignmentContentEditor.getText();
        String deadline = deadlineDateField.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        if(!assignment_topic.isEmpty() && !deadline.isEmpty()) {
            HashMap<String, Object> assignment_details = new HashMap<>();

            assignment_details.put("course_id", course_id);
            assignment_details.put("topic", assignment_topic);
            assignment_details.put("deadline", deadline);
            assignment_details.put("content", assignment_content);

            new AssignmentsDatabases().registerAssignment(assignment_details);

            Assignments assignment = new AssignmentsDatabases().
                    getAssignmentByCourseID(course_id);

            new FacultyDashboard().getFacultyDashboard().setNewAssignment(assignment);

            new Dialogs().infoAlert("Info", "Assignment uploaded successfully",
                    "This assignment will be shown to student " +
                            "and he/she can upload answer to given assignment, " +
                            "and that answer to be shown to you in uploaded assignment section.");

        }else{
            new Dialogs().warningAlert("Value Warning" ,"Please enter valid values",
                    "You must enter valid values for students to understand this assignment");
        }
    }
}
