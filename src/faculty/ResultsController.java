package faculty;

import exams.ExamsResults;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import models.Results;
import result.AssignmentsResults;
import result.AssignmentsResultsDatabase;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultsController implements Initializable {

    @FXML
    AssignmentsResults assignmentsResultsController;

    @FXML
    ExamsResults examsResultsController;

    static ResultsController resultsController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resultsController = this;
    }

    public AssignmentsResults getAssignmentsResultsController() {
        return assignmentsResultsController;
    }

    public ExamsResults getExamsResultsController() {
        return examsResultsController;
    }

    // setting assignment marks in result table
    public void setResultTable() {

        assignmentsResultsController.setAssignmentsResults();
    }
}
