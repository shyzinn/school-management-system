package resources.results;

import config.Dialogs;
import config.ValidateFields;
import faculty.FacultyDashboard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Results;
import result.AssignmentsResultsDatabase;
import result.ExamsResultsDatabase;
import java.net.URL;
import java.util.ResourceBundle;

/*
    in this class faculty is able to change the already uploaded result
 */
public class ChangeResult implements Initializable {

    @FXML
    private TextField totalMarksField;

    @FXML
    private TextField obtainedMarksField;

    @FXML
    private TextArea commentsField;

    Results result;

    String resultType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setWin(Parent root, Results result, String type) {

        this.result = result;
        this.resultType = type;

        obtainedMarksField.setText(Double.toString(result.getObtainedMarks()));
        totalMarksField.setText(Double.toString(result.getTotalMarks()));
        commentsField.setText(result.getComments());

        Stage win = new Stage();
        Scene scene = new Scene(root, 400, 400);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.showAndWait();
    }

    @FXML
    private void changeResult() {
        if (!totalMarksField.getText().isEmpty() && !obtainedMarksField.getText().isEmpty()) {

            double obtained_marks = Double.parseDouble(obtainedMarksField.getText());
            double total_marks = Double.parseDouble(totalMarksField.getText());
            double percentage = (obtained_marks / total_marks) * 100;
            String comments = commentsField.getText();

            // rounding percentage
            percentage =  ValidateFields.round(percentage, 2);

            if (obtained_marks <= total_marks) {

                if(resultType.equals("assignment")) {
                    new AssignmentsResultsDatabase().changeResult(result, total_marks, obtained_marks,
                            percentage, comments);

                    result.setTotalMarks(total_marks);
                    result.setObtainedMarks(obtained_marks);
                    result.setPercentage(percentage);
                    result.setProgress(percentage);
                    result.setPercentageNode(percentage);

                    new FacultyDashboard().getFacultyDashboard().
                            getResultsController().getAssignmentsResultsController().refreshTable();

                }else if (resultType.equals("exam")){
                    new ExamsResultsDatabase().changeResult(result, total_marks, obtained_marks,
                            percentage,comments);

                    result.setTotalMarks(total_marks);
                    result.setObtainedMarks(obtained_marks);
                    result.setPercentage(percentage);
                    result.setProgress(percentage);
                    result.setPercentageNode(percentage);

                    new FacultyDashboard().getFacultyDashboard().getResultsController().
                            getExamsResultsController().refreshTable();

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
