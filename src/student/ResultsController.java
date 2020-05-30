package student;

import config.ValidateFields;
import exams.ExamsResults;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main_menu.LoginController;
import result.ExamsResultsDatabase;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

/*
this class is used to set total results stats for student
including assignments and exams
 */
public class ResultsController implements Initializable {

    @FXML
    AssignmentsResults assignmentsResultsController;

    @FXML
    ExamsResults examsResultsController;

    @FXML
    private Label assignmentMarksLabel;

    @FXML
    private Label assignmentPercentageLabel;

    @FXML
    private Label examMarksLabel;

    @FXML
    private Label examPercentageLabel;

    @FXML
    private Label totalAggregateLabel;

    int studentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        studentId = LoginController.loginID;

        // removing del-btn because only faculty can delete results
        Button del_btn = examsResultsController.getDelBtn();

        HBox parent = (HBox) del_btn.getParent();
        VBox container = (VBox) parent.getParent();
        container.getChildren().remove(parent);
    }

    public void setResultsStats(int course_id) {

        Map<String, Object> assignments_marks_stats =
                new ExamsResultsDatabase().getTotalMarksForAssignment(course_id, studentId);

        double assignment_total_marks = (double) assignments_marks_stats.get("total_marks");
        double assignment_obtained_marks = (double) assignments_marks_stats.get("obtained_marks");
        double assignment_percentage =  ValidateFields.round((double) assignments_marks_stats.get("percentage"), 2);

        assignmentMarksLabel.setText(assignment_obtained_marks +
                " / " + assignment_total_marks);

        assignmentPercentageLabel.setText(Double.toString(assignment_percentage));

        Map<String, Object> exams_marks_stats =
                new ExamsResultsDatabase().getTotalMarksForExams(course_id, studentId);

        double exams_total_marks = (double) exams_marks_stats.get("total_marks");
        double exams_obtained_marks = (double) exams_marks_stats.get("obtained_marks");
        double exams_percentage = ValidateFields.round((double) exams_marks_stats.get("percentage"), 2);

        examMarksLabel.setText((exams_obtained_marks) + " / " + exams_total_marks);

        examPercentageLabel.setText(Double.toString(exams_percentage));

        double total_aggregate = (exams_obtained_marks + assignment_obtained_marks) /
                (exams_total_marks + assignment_total_marks) * (100);

        totalAggregateLabel.setText(Double.toString(ValidateFields.round(total_aggregate, 2)));
    }

}
