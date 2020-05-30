package faculty;

import animatefx.animation.FadeIn;
import assignments.AssignmentEvaluation;
import assignments.AssignmentsDatabases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import main_menu.LoginController;
import models.Assignments;

import java.net.URL;
import java.util.ResourceBundle;

/*
Here we will control the assignments uploaded/submitted by students
faculty will see his course related submitted assignment here and
assess them and assign grades/numbers
 */

public class UploadedAssignments implements Initializable {

    @FXML
    private TableColumn<?, ?> serialCol;

    @FXML
    private TableColumn<?, ?> studentNameCol;

    @FXML
    private TableColumn<?, ?> assignmentTopicCol;

    @FXML
    private TableColumn<?, ?> submissionDateCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private TableView<Assignments> uploadedAssignmentsTable;

    @FXML
    private SplitPane splitPane;

    @FXML
    private AnchorPane evaluationContainer;

    @FXML
    AssignmentEvaluation assignmentEvaluationController;

    int facultyId;

    ObservableList<Assignments> uploadedAssignments = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        facultyId = LoginController.loginID;
        tableListener();

        assignmentEvaluationController.injectAssignment(this);
    }

    public void tableListener() {

        // listen for table selection to enable action buttons related to table
        uploadedAssignmentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                evaluateAssignment();
            } else {
                assignmentEvaluationController.hideContents();
            }
        });

    }

    public void setAssignmentsTable(int course_id) {

        uploadedAssignments.clear();
        uploadedAssignments = new AssignmentsDatabases().
                getUploadedAssignmentsById(course_id);

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        submissionDateCol.setCellValueFactory(new PropertyValueFactory<>("submittedDate"));
        assignmentTopicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusLabel"));
        uploadedAssignmentsTable.setItems(uploadedAssignments);
    }

    public void evaluateAssignment() {
        Assignments selected_assignment = uploadedAssignmentsTable.getSelectionModel().getSelectedItem();
        assignmentEvaluationController.setAssignment(selected_assignment);
    }

    @FXML
    private void showAndHide() {

        if (splitPane.getItems().contains(evaluationContainer)) {
            splitPane.getItems().remove(evaluationContainer);

        } else {
            splitPane.getItems().add(evaluationContainer);
            new FadeIn(evaluationContainer).play();
        }
    }

    public void refreshTable() {
        uploadedAssignmentsTable.refresh();
    }
}
