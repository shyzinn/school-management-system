package exams;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
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
import main_menu.LoginController;
import models.Exams;
import models.Results;
import student.StudentDashboard;

import java.net.URL;
import java.util.ResourceBundle;

public class ExamsTable implements Initializable {

    @FXML
    private TableColumn<Exams, Integer> serialCol;

    @FXML
    private TableColumn<Exams, String> examCodeCol;

    @FXML
    private TableColumn<Exams, String> startDateCol;

    @FXML
    private TableColumn<Exams, String> timeAllottedCol;

    @FXML
    private TableColumn<Exams, String> detailsCol;

    @FXML
    private TableColumn<Exams, String> takeExamCol;

    @FXML
    private TableView<Exams> examsTable;

    ObservableList<Exams> examsList = FXCollections.observableArrayList();

    int courseId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listenForTakeExam();
    }

    public void initializeForFaculty() {
        examsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listenForExamSelection();
    }

    public void setExamsTable(int course_id) {

        courseId = course_id;

        examsList = new ExamsDatabases().getExamsByCourseID(course_id);

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        examCodeCol.setCellValueFactory(new PropertyValueFactory<>("code"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        timeAllottedCol.setCellValueFactory(new PropertyValueFactory<>("timeAllotted"));
        detailsCol.setCellValueFactory(new PropertyValueFactory<>("details"));
        takeExamCol.setCellValueFactory(new PropertyValueFactory<>("takeExamBtn"));

        examsTable.setItems(examsList);

    }

    public void deleteExams(ObservableList<Exams> selected_exams) {
        new ExamsDatabases().deleteExams(selected_exams);
        examsList.removeAll(selected_exams);
        examsTable.refresh();
    }

    public void setNewExam() {
        Exams exam = new ExamsDatabases().getLatestExam();
        exam.setSerial(examsList.size() + 1);
        examsList.add(exam);
        examsTable.refresh();
    }

    public void listenForExamSelection() {

        examsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Exams selected_exam = examsTable.getSelectionModel().getSelectedItem();
            }
        });
    }

    public ObservableList<Exams> getExamsList() {
        return examsList;
    }

    public TableView<Exams> getExamsTable() {
        return examsTable;
    }

    public void clearTable() {
        examsList.clear();
        examsTable.refresh();
    }

    public void listenForTakeExam() {
        for (Exams exam : examsList) {

            Button take_exam_btn = exam.getTakeExamBtn();

            take_exam_btn.setOnAction(e -> {
                boolean check = new ExamsDatabases().checkTakenExams(exam.getCourseId(), exam.getId(), LoginController.loginID);
                if (!check) {
                    setTakeExamWin(exam);
                } else {
                    new Dialogs().warningAlert("Warning", "You have already taken this exam",
                            "Contact your faculty for re take of this exam.");
                }
            });
        }
    }

    public void setTakeExamWin(Exams exam) {
        try {
            // loading student dashboard
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/exams/take_exam.fxml"));
            Parent root = loader.load();
            TakeExam take_exam = loader.getController();
            take_exam.setTakeExamWin(root, exam);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
