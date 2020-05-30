package courses;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import faculty.FacultyDashboard;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.CoursePlan;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class CoursePlanController implements Initializable {

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField planField;

    @FXML
    private Button addBtn;

    @FXML
    private Button delBtn;

    @FXML
    CoursesPlanTable coursesPlanTableController;

    ObservableList<CoursePlan> coursePlansList = FXCollections.observableArrayList();
    TableView<CoursePlan> coursePlanTable;

    static CoursePlanController coursePlanController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LocalDate date_value = java.time.LocalDate.now();
        dateField.setValue(date_value);

        planField.textProperty().addListener((observable, oldValue, newValue) ->
                addBtn.setDisable(newValue.isEmpty()));

        coursePlanController = this;
    }

    public void setCoursePlansList(ObservableList<CoursePlan> coursePlansList) {
        this.coursePlansList = coursePlansList;
    }

    public void setCoursePlanTable(TableView<CoursePlan> coursePlanTable) {
        this.coursePlanTable = coursePlanTable;
    }

    public CoursesPlanTable getCoursesPlanTableController() {
        return coursesPlanTableController;
    }

    @FXML
    private void addCoursePlan() {
        int course_id = new FacultyDashboard().getSelectedCourseId();

        if (!planField.getText().isEmpty()) {
            String date = dateField.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            String plan = planField.getText();

            new CourseDatabases().setCoursePlan(course_id, date, plan);

            CoursePlan course_plan = new CourseDatabases().getLatestCoursePlan(course_id);

            coursePlansList.add(course_plan);

            coursePlanTable.refresh();

            tableListener();
        }
    }

    @FXML
    private void deleteCoursePlan() {
        if (!coursePlanTable.getSelectionModel().isEmpty()) {

            int choice = new Dialogs().confirmationAlert("Confirmation", "Do you want to delete selected plans?",
                    "All selected plans from table will be deleted. \n Are you sure to continue?");

            if (choice == 1) {

                ObservableList<CoursePlan> course_plan_list = coursePlanTable.getSelectionModel().
                        getSelectedItems();
                new CourseDatabases().deleteCoursePlan(coursePlansList);
                coursePlansList.removeAll(course_plan_list);
                coursePlanTable.refresh();
                tableListener();
            }
        }
    }

    public void tableListener() {
        coursePlanTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)
                -> delBtn.setDisable(newSelection == null));
    }

}
