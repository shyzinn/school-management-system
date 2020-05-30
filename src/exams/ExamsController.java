package exams;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

/*
In this class we will handle and show the uploaded exams by the faculty
 */

import config.Dialogs;
import faculty.FacultyDashboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableView;
import models.Exams;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExamsController implements Initializable {

    @FXML
    ExamsTable examsTableController;

    @FXML
    UploadedExams uploadedExamsController;

    TableView<Exams> examsTable;

    ObservableList<Exams> examsList = FXCollections.observableArrayList();

    static ExamsController examsController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        examsController = this;
        examsList = examsTableController.getExamsList();
        examsTable = examsTableController.getExamsTable();
    }

    public ExamsTable getExamsTableController() {
        return examsTableController;
    }

    public ExamsEvaluation getEvaluateExamController() {
        return uploadedExamsController.getExamsEvaluationController();
    }

    public UploadedExams getUploadedExamsController() {
        return uploadedExamsController;
    }

    public ExamsController getExamsController() {
        return examsController;
    }

    @FXML
    private void addExam() {
        if (new FacultyDashboard().getSelectedCourseId() != 0) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/exams/add_exam.fxml"));
                Parent root = loader.load();
                AddExam add_exam = loader.getController();
                add_exam.setAddExamWin(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            new Dialogs().warningAlert("Warning", "No course selected",
                    "Please select the course from side menu.");
        }
    }

    @FXML
    private void deleteExams() {

        if (!examsTable.getSelectionModel().isEmpty()) {

            int choice = new Dialogs().confirmationAlert("Confirmation", "Do you want to delete selected exams",
                    "Once deleted you will not be able to recover the deleted data");

            if (choice == 1) {
                ObservableList<Exams> selected_exams = examsTable.getSelectionModel().getSelectedItems();
                examsTableController.deleteExams(selected_exams);
            }
        }
    }
}
