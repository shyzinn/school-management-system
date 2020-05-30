package exams;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import config.ValidateFields;
import faculty.FacultyDashboard;
import faculty.FacultyDatabases;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main_menu.LoginController;
import models.Faculty;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class AddExam implements Initializable {
    @FXML
    private TextField examCode;

    @FXML
    private TextArea detailsTextBox;

    @FXML
    private DatePicker startDate;

    @FXML
    private TextField hrField;

    @FXML
    private TextField minField;

    @FXML
    private TextField secField;

    @FXML
    private TextField timeAllottedField;

    @FXML
    private TextArea contentBox;

    int facultyId;

    Faculty faculty;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        facultyId = LoginController.loginID;

        faculty = new FacultyDatabases().getFacultyByID(facultyId);
        LocalDate date_value = java.time.LocalDate.now();
        startDate.setValue(date_value);

        List<TextField> numeric_fields = Arrays.asList(hrField, minField, secField, timeAllottedField);
        ValidateFields.makeFieldsInteger(numeric_fields);
    }

    public void setAddExamWin(Parent root) {

        Stage win = new Stage();
        Scene scene = new Scene(root, 700, 600);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.show();
    }

    @FXML
    private void uploadExam() {

        if(!hrField.getText().isEmpty() && minField.getText().isEmpty() && secField.getText().isEmpty()
        && timeAllottedField.getText().isEmpty()) {
            int course_id = new FacultyDashboard().getSelectedCourseId();

            // getting start time
            int hr = Integer.parseInt(hrField.getText());
            int min = Integer.parseInt(minField.getText());
            int sec = Integer.parseInt(secField.getText());

            int time_allotted = Integer.parseInt(timeAllottedField.getText());


            if ((hr < 24 && min < 60 && sec < 60)) {

                String exam_code = examCode.getText();
                String details = detailsTextBox.getText();
                String content = contentBox.getText();
                String start_date = startDate.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                String start_time = hrField.getText() + ":" + minField.getText() + ":" + secField.getText();

                HashMap<String, Object> exam_details = new HashMap<>();

                exam_details.put("course_id", course_id);
                exam_details.put("exam_code", exam_code);
                exam_details.put("details", details);
                exam_details.put("content", content);
                exam_details.put("start_date", start_date);
                exam_details.put("start_time", start_time);
                exam_details.put("time_allotted", time_allotted);

                new ExamsDatabases().addNewExam(exam_details);

                new ExamsController().getExamsController().getExamsTableController().setNewExam();

                // set this exam on table

            } else {
                new Dialogs().errorAlert("Time Error", "Please enter valid time.",
                        "Time must be in 24 hr format");
            }
        }else{
            new Dialogs().errorAlert("Invalid Input", "Please fill the required fields",
                    "You must enter all the details, so that students can know about the exam well.");
        }
    }
}
