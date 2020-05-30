package exams;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

/*
this class handles and populate results based on course id
 */

import config.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Results;
import resources.results.ChangeResult;
import result.ExamsResultsDatabase;

import java.net.URL;
import java.util.ResourceBundle;

public class ExamsResults implements Initializable {

    @FXML
    private Button delBtn;

    @FXML
    private TableView<Results> resultsTable;

    @FXML
    private TableColumn<?, ?> serialCol;

    @FXML
    private TableColumn<?, ?> examCodeCol;

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

    ObservableList<Results> examsResultsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        delBtn.setDisable(true);
        tableListener();
    }

    public Button getDelBtn() {
        return delBtn;
    }

    public void tableListener() {

        // listen for table selection to enable action buttons related to table
        resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> delBtn.setDisable(newSelection == null));

    }

    public void setResultsTable(int course_id) {

        examsResultsList.clear();
        resultsTable.refresh();

        examsResultsList = new ExamsResultsDatabase().getResult(course_id);

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        examCodeCol.setCellValueFactory(new PropertyValueFactory<>("examCode"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentRegCol.setCellValueFactory(new PropertyValueFactory<>("studentRegistration"));
        totalMarksCol.setCellValueFactory(new PropertyValueFactory<>("totalMarks"));
        obtainedMarksCol.setCellValueFactory(new PropertyValueFactory<>("obtainedMarks"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentageNode"));

        resultsTable.setItems(examsResultsList);

        tableListener();
    }

    public void setNewResult() {

        Results new_result = new ExamsResultsDatabase().getLatestResult();
        new_result.setSerial(examsResultsList.size() + 1);
        examsResultsList.add(new_result);
        resultsTable.refresh();

        tableListener();
    }

    public void setResultsTable(int course_id, int student_id) {

        examsResultsList.clear();
        resultsTable.refresh();

        examsResultsList = new ExamsResultsDatabase().getResultByStudentID(course_id, student_id);

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        examCodeCol.setCellValueFactory(new PropertyValueFactory<>("examCode"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentRegCol.setCellValueFactory(new PropertyValueFactory<>("studentRegistration"));
        totalMarksCol.setCellValueFactory(new PropertyValueFactory<>("totalMarks"));
        obtainedMarksCol.setCellValueFactory(new PropertyValueFactory<>("obtainedMarks"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentageNode"));

        resultsTable.setItems(examsResultsList);

        tableListener();

    }

    @FXML
    private void deleteResult() {

        int choice = new Dialogs().confirmationAlert("Confirmation", "Do you want to delete " +
                "selected results?", "Once deleted you will not be able to recover and they will not " +
                "be available to students.");

        if (choice == 1) {
            ObservableList<Results> selected_results = resultsTable.getSelectionModel().getSelectedItems();
            new ExamsResultsDatabase().deleteResults(selected_results);
            examsResultsList.removeAll(selected_results);
            resultsTable.refresh();
        }
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
                change_result.setWin(root, result, "exam");

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void refreshTable(){
        resultsTable.refresh();
        tableListener();
    }
}
