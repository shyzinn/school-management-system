package student;

import assignments.AssignmentsDatabases;
import config.Dialogs;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main_menu.LoginController;
import models.Assignments;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/*
   this class will allow student to upload assignment answer which will be
   sent to faculty
 */
public class UploadAssignment implements Initializable {

    @FXML
    private Label topicLabel;

    Assignments assignment;

    int studentId;

    @FXML
    private TextArea assignmentEditor;

    @FXML
    private TextArea questionsBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        studentId = LoginController.loginID;
    }

    public void setUploadWin(Parent root, Assignments assignment) {

        this.assignment = assignment;
        topicLabel.setText(assignment.getTopic());
        questionsBox.setText(assignment.getContent());

        Stage win = new Stage();
        Scene scene = new Scene(root, 600, 450);
        win.setScene(scene);
        win.setMaximized(true);
        win.showAndWait();
    }

    @FXML
    public void submitAssignment() {

        boolean assignment_check = new AssignmentsDatabases().
                checkForUploadedAssignments(studentId, assignment.getId(), assignment.getCourseId());

        if (!assignment_check) {
            Map<String, Object> assignment_map = new HashMap<>();

            String content = assignmentEditor.getText();

            assignment_map.put("assignment_id", assignment.getId());
            assignment_map.put("student_id", studentId);
            assignment_map.put("content", content);
            assignment_map.put("course_id", assignment.getCourseId());
            assignment_map.put("status", "unchecked");

            new AssignmentsDatabases().uploadAssignment(assignment_map);
            StudentDashboard.studentDashboard.coursesController.uploadedAssignmentsController.setUploadedAssignments(assignment.getCourseId());

            new Dialogs().infoAlert("Message", "Assignment uploaded successfully", "" +
                    "You can check this assignment in uploaded section.");

        } else {
            new Dialogs().warningAlert("Warning", "Assignment already uploaded"
                    , "Please delete old uploaded assignment and then upload new one.");
        }
    }
}
