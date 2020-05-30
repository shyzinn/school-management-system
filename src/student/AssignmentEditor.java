package student;

import assignments.AssignmentsDatabases;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Assignments;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignmentEditor implements Initializable {

    @FXML
    private TextArea assignmentEditor;

    Assignments assignment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAssignmentEditorWin(Parent root, Assignments assignment) {

        this.assignment = assignment;
        assignmentEditor.setText(assignment.getContent());

        Stage win = new Stage();
        Scene scene = new Scene(root, 600, 450);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.showAndWait();
    }

    @FXML
    private void update() {
        String content = assignmentEditor.getText();
        new AssignmentsDatabases().updateUploadedAssignment(assignment.getId(), content);
        assignment.setContent(content);
    }
}
