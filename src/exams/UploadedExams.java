package exams;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import animatefx.animation.FadeIn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import models.Exams;

import java.net.URL;
import java.util.ResourceBundle;

public class UploadedExams implements Initializable {

    @FXML
    ExamsEvaluation examsEvaluationController;

    @FXML
    private TableView<Exams> uploadedExamsTable;

    @FXML
    private TableColumn<?, ?> serialCol;

    @FXML
    private TableColumn<?, ?> studentNameCol;

    @FXML
    private TableColumn<?, ?> studentRegCol;

    @FXML
    private TableColumn<?, ?> examCodeCol;

    @FXML
    private TableColumn<?, ?> detailsCol;

    @FXML
    private TableColumn<?, ?> completedOnCol;

    @FXML
    private TableColumn<?, ?> completedInCol;

    @FXML
    private TableColumn<?, ?> statusCol;

    @FXML
    private SplitPane splitPane;

    @FXML
    private HBox examsEvaluation;

    ObservableList<Exams> uploadedExamsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableListener();
    }

    public ExamsEvaluation getExamsEvaluationController() {
        return examsEvaluationController;
    }

    public void tableListener() {
        uploadedExamsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        setEvaluationMenu();
                    }
                    else{
                        examsEvaluationController.hideContents();
                    }
                }
        );
    }

    public void setUploadedExams(int course_id) {
        uploadedExamsList = new ExamsDatabases().getUploadedExamsByCourseId(course_id);

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        studentNameCol.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentRegCol.setCellValueFactory(new PropertyValueFactory<>("studentRegistration"));
        examCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
        completedInCol.setCellValueFactory(new PropertyValueFactory<>("timeTaken"));
        completedOnCol.setCellValueFactory(new PropertyValueFactory<>("completedOn"));
        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("statusLabel"));

        uploadedExamsTable.setItems(uploadedExamsList);

        tableListener();

    }

    public void setEvaluationMenu() {
        Exams selected_exam = uploadedExamsTable.getSelectionModel().getSelectedItem();
        examsEvaluationController.setExam(selected_exam);
    }

    @FXML
    private void showAndHide() {

        if (splitPane.getItems().contains(examsEvaluation)) {
            splitPane.getItems().remove(examsEvaluation);

        } else {
            splitPane.getItems().add(examsEvaluation);
            splitPane.setDividerPositions(0.5);
            new FadeIn(examsEvaluation).play();
        }
    }

    public void refreshTable() {
        uploadedExamsTable.refresh();
    }
}
