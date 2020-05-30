package result;

import config.Dialogs;
import faculty.FacultyDashboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Results;
import resources.results.ChangeResult;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignmentsResults implements Initializable {

    @FXML
    private Button changeBtn;

    @FXML
    private Button delBtn;

    @FXML
    private TableView<Results> resultsTable;

    @FXML
    private TableColumn<?, ?> serialCol;

    @FXML
    private TableColumn<?, ?> assignmentTopicCol;

    @FXML
    private TableColumn<?, ?> studentNameCol;

    @FXML
    private TableColumn<?, ?> studentRegCol;

    @FXML
    private TableColumn<?, ?> obtainedMarksCol;

    @FXML
    private TableColumn<?, ?> totalMarksCol;

    @FXML
    private TableColumn<?, ?> percentageCol;

    ObservableList<Results> resultsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resultsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableListener();
    }

    public void tableListener() {

        // listen for table selection to enable action buttons related to table
        resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                delBtn.setDisable(false);
                changeBtn.setDisable(false);
            } else {
                delBtn.setDisable(true);
                changeBtn.setDisable(true);
            }
        });

    }

    public void setAssignmentsResults() {

        int course_id = new FacultyDashboard().getSelectedCourseId();
        resultsList = new AssignmentsResultsDatabase().getResultsByCourseId(course_id);

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        assignmentTopicCol.setCellValueFactory(new PropertyValueFactory<>("assignmentTopic"));
        studentRegCol.setCellValueFactory(new PropertyValueFactory<>("studentRegistration"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        obtainedMarksCol.setCellValueFactory(new PropertyValueFactory<>("obtainedMarks"));
        totalMarksCol.setCellValueFactory(new PropertyValueFactory<>("totalMarks"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentageNode"));

        resultsTable.setItems(resultsList);

        tableListener();
    }

    public void refreshTable() {
        resultsTable.refresh();
        tableListener();
    }

    public void setNewResult() {
        Results result = new AssignmentsResultsDatabase().getLatestResult();
        result.setSerial(resultsList.size() + 1);
        resultsList.add(result);
        resultsTable.refresh();
        tableListener();
    }

    @FXML
    private void changeResult() {

        if (!resultsTable.getSelectionModel().isEmpty()) {

            Results result = resultsTable.getSelectionModel().getSelectedItem();

            try {
                // loading faculty dashboard
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/results/change_result.fxml"));
                Parent root = loader.load();
                ChangeResult change_result = loader.getController();
                change_result.setWin(root, result, "assignment");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void deleteResult() {

        if (!resultsTable.getSelectionModel().isEmpty()) {

            int choice = new Dialogs().confirmationAlert("Confirmation", "Do you want to delete" +
                            "selected results?",
                    "Warning : Once deleted you will not be able to recover this data.");

            if (choice == 1) {
                ObservableList<Results> selected_results = resultsTable.getSelectionModel().getSelectedItems();
                new AssignmentsResultsDatabase().deleteResult(selected_results);

                resultsList.removeAll(selected_results);
                resultsTable.refresh();
                int course_id = new FacultyDashboard().getFacultyDashboard().getSelectedCourseId();
                new FacultyDashboard().getFacultyDashboard().getAssignmentsController().
                        getUploadedAssignmentsController().setAssignmentsTable(course_id);
            }
        }
    }
}
