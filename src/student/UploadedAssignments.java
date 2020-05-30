package student;

import assignments.AssignmentsDatabases;
import config.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import main_menu.LoginController;
import models.Assignments;

import java.net.URL;
import java.util.ResourceBundle;

public class UploadedAssignments implements Initializable {

    @FXML
    private TableView<Assignments> uploadedAssignmentsTable;

    @FXML
    private TableColumn<?, ?> serialCol;

    @FXML
    private TableColumn<?, ?> topicCol;

    @FXML
    private TableColumn<?, ?> dateCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private Button changeBtn;

    @FXML
    private MenuItem changeItemBtn;

    static CoursesController coursesController;

    ObservableList<Assignments> uploadedAssignments = FXCollections.observableArrayList();

    int studentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        studentId = LoginController.loginID;
        uploadedAssignmentsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableListener();
    }

    public void injectCourseController(CoursesController courses_controller) {

        coursesController = courses_controller;
    }

    public void setUploadedAssignments(int course_id) {
        uploadedAssignments.clear();
        uploadedAssignments = new AssignmentsDatabases().
                getAssignmentsByStudentAndCourseID(studentId, course_id);

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("submittedDate"));
        topicCol.setCellValueFactory(new PropertyValueFactory<>("topic"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusLabel"));

        uploadedAssignmentsTable.setItems(uploadedAssignments);

        tableListener();

    }

    @FXML
    private void changeAssignment() {

        if (!uploadedAssignmentsTable.getSelectionModel().isEmpty()) {

            String status = uploadedAssignmentsTable.getSelectionModel().getSelectedItem().getStatusLabel().getText();

            if (status.equals("checked")) {

                new Dialogs().warningAlert("Already checked", "This assignment is already checked",
                        "The assignment you are trying to alter is already evaluated by your faculty." +
                                "So changing it won't matter anymore, unless your faculty has asked you to do it.");
            }
            Assignments assignment = uploadedAssignmentsTable.getSelectionModel().getSelectedItem();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/student/assignment_editor.fxml"));
                Parent root = loader.load();
                AssignmentEditor assignmentEditor = loader.getController();
                assignmentEditor.setAssignmentEditorWin(root, assignment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void clearTable() {
        uploadedAssignments.clear();
        uploadedAssignmentsTable.getItems().clear();
        uploadedAssignmentsTable.refresh();
    }

    public void tableListener() {

        // listen for table selection to enable action buttons related to table
        uploadedAssignmentsTable.getSelectionModel().selectedItemProperty().
                addListener((obs, oldSelection, newSelection) -> {
                    changeBtn.setDisable(newSelection == null);
                    changeItemBtn.setDisable(newSelection == null);
                });


    }
}
