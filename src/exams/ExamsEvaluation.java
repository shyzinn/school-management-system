package exams;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import config.ValidateFields;
import faculty.FacultyDashboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import models.Exams;
import result.ExamsResultsDatabase;
import java.net.URL;
import java.util.*;

public class ExamsEvaluation implements Initializable {

    @FXML
    private TextField totalMarksField;

    @FXML
    private TextField obtainedMarksField;

    @FXML
    private TextArea commentsField;

    @FXML
    private TextArea contentField;

    @FXML
    private HBox evaluationWindow;

    Exams selectedExam;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<TextField> numeric_fields_list = Arrays.asList(totalMarksField, obtainedMarksField);
        ValidateFields.makeFieldsDecimal(numeric_fields_list);

        evaluationWindow.setVisible(false);
    }

    public void setExam(Exams selected_exam) {
        evaluationWindow.setVisible(true);
        selectedExam = selected_exam;
        contentField.setText(selectedExam.getContent());
    }

    @FXML
    private void evaluateExam() {

        if (selectedExam != null) {
            if (!totalMarksField.getText().isEmpty() && !obtainedMarksField.getText().isEmpty()) {
                double total_marks = Double.parseDouble(totalMarksField.getText());
                double obtained_marks = Double.parseDouble(obtainedMarksField.getText());

                if (obtained_marks <= total_marks) {
                    String comments = commentsField.getText();
                    double percentage = (obtained_marks / total_marks) * 100;

                    new ExamsResultsDatabase().setResult(selectedExam.getCourseId(), selectedExam.getExamId(),
                            selectedExam.getId(),
                            selectedExam.getStudentId(),
                            total_marks, obtained_marks, percentage, comments);

                    // setting newly added exam result to results section

                    selectedExam.setStatusLabel("checked");

                    new ExamsDatabases().updateExamStatus(selectedExam.getId(), "checked");

                    new FacultyDashboard().getFacultyDashboard().setNewResult();

                    new FacultyDashboard().getFacultyDashboard().getExamsController().getUploadedExamsController().refreshTable();
                }
            } else {
                new Dialogs().errorAlert("Invalid Input", "Please make sure that to enter valid marks.",
                        "You need to make sure that obtained marks should be less than or equal to total marks.");
            }
        } else {
            new Dialogs().errorAlert("Selection Error", "Please select uploaded exam from above table.",
                    "You need to make selection from above table, then selected exam can be evaluated from this menu");
        }
    }

    public void hideContents() {
        evaluationWindow.setVisible(false);
    }
}




