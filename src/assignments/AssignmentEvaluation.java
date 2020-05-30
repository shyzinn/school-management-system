package assignments;

/*
    This class allows faculty to evaluate assignment submitted by student
 */
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import config.ValidateFields;
import faculty.FacultyDashboard;
import faculty.UploadedAssignments;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import models.Assignments;
import models.Results;
import result.AssignmentsResultsDatabase;

import java.net.URL;
import java.util.*;

public class AssignmentEvaluation implements Initializable {
    @FXML
    private TextArea assignmentContainer;

    @FXML
    private TextField totalMarksField;

    @FXML
    private TextField obtainedMarksField;

    @FXML
    private TextArea commentsField;

    @FXML
    VBox evaluationWindow;

    Assignments assignment;

    UploadedAssignments parent;

    FacultyDashboard facultyDashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // using reg ex in class validate field to make marks field only accept decimals

        List<TextField> marks_fields = Arrays.asList(obtainedMarksField, totalMarksField);
        ValidateFields.makeFieldsDecimal(marks_fields);

        evaluationWindow.setVisible(false);
    }

    public void injectAssignment(UploadedAssignments obj) {

        parent = obj;
    }

    public void setAssignment(Assignments assignment) {
        facultyDashboard = new FacultyDashboard().getFacultyDashboard();
        this.assignment = assignment;
        assignmentContainer.setText(assignment.getContent());
        evaluationWindow.setVisible(true);
    }

    public void hideContents() {

        evaluationWindow.setVisible(false);
    }

    // in this method we will take total marks and uploaded marks and evaluate the assignment
    @FXML
    private void evaluateAssignment() {

        if (!totalMarksField.getText().isEmpty() && !obtainedMarksField.getText().isEmpty()) {

            double obtained_marks = Double.parseDouble(obtainedMarksField.getText());
            double total_marks = Double.parseDouble(totalMarksField.getText());
            double percentage = (obtained_marks / total_marks) * 100;
            String comments = commentsField.getText();

            // rounding percentage

            if (obtained_marks <= total_marks) {

                Map<String, Object> result = new HashMap<>();

                result.put("obtained_marks", obtained_marks);
                result.put("total_marks", total_marks);
                result.put("percentage", ValidateFields.round(percentage, 2));
                result.put("comments", comments);

                // true if added successfully
                boolean status = new AssignmentsResultsDatabase().addResults(result, assignment);
                System.out.println(status);
                // if status true then add this result to result table;
                if (status) {

                    Label status_label = new Label("checked");
                    assignment.setStatusLabel(status_label);
                    facultyDashboard.getAssignmentsController().setUploadedAssignmentStatus();
                    facultyDashboard.getResultsController().getAssignmentsResultsController().setNewResult();

                    System.out.println(assignment.getStatusLabel().getText());

                }

            } else {
                new Dialogs().errorAlert("Error", "Obtained marks must be less than or equal to total marks",
                        "Enter the valid values and make sure that obtained marks are lees " +
                                "or equal to total marks.");
            }
        } else {
            new Dialogs().errorAlert("Error", "Please enter values",
                    "You have to enter total marks and marks obtained.");
        }
    }

}
