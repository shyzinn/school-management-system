// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\


package admin;

import config.ValidateFields;
import courses.CourseDatabases;
import faculty.FacultyDatabases;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Faculty;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AddNewCourse implements Initializable {

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseCodeField;

    @FXML
    private TextField departmentField;

    @FXML
    private DatePicker startDateField;

    @FXML
    private DatePicker endDateField;

    @FXML
    private ChoiceBox<String> facultyChoiceBox;

    @FXML
    private TextField feeField;

    @FXML
    private TextField monHrFiled;

    @FXML
    private TextField monMinField;

    @FXML
    private TextField monVenueField;

    @FXML
    private TextField tueHrField;

    @FXML
    private TextField tueMinField;

    @FXML
    private TextField tueVenueField;

    @FXML
    private TextField wedHrField;

    @FXML
    private TextField wedMinField;

    @FXML
    private TextField wedVenueField;

    @FXML
    private TextField thuHrField;

    @FXML
    private TextField thuMinField;

    @FXML
    private TextField thuVenueField;

    @FXML
    private TextField friHrFiled;

    @FXML
    private TextField friMinField;

    @FXML
    private TextField friVenueField;

    @FXML
    private TextField satHrFiled;

    @FXML
    private TextField satMinField;

    @FXML
    private TextField satVenueField;


    List<TextField> timeTableFiled;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        timeTableFiled = Arrays.asList(monHrFiled, monMinField, tueHrField, tueMinField, wedHrField,
                wedMinField, thuHrField, thuMinField, friHrFiled, friMinField, satHrFiled, satMinField);

        // making required fields integer using reg-ex and by using the class ValidateFields
        ValidateFields.makeFieldsInteger(timeTableFiled);

        // restricting text fields up to 2 numbers
        ValidateFields.restrictTextFields(timeTableFiled, 2);

        //setting current day date in startDateField
        LocalDate date_value = java.time.LocalDate.now();
        startDateField.setValue(date_value);
        endDateField.setValue(date_value);

        setFacultyChoiceBox();

    }

    public void setNewCourseWin(Parent root) {

        // this function will open new window for course registration form

        Stage win = new Stage();
        Scene scene = new Scene(root, 600, 450);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.showAndWait();
    }

    public void setFacultyChoiceBox() {

        ObservableList<Faculty> faculties_list = new FacultyDatabases().getFaculty();

        for (Faculty faculty : faculties_list) {

            facultyChoiceBox.getItems().add(faculty.getName());
        }
    }

    @FXML
    private void registerNewCourse() {
        // here we will take the information from the course registration form
        // and register new course and set its time table

        String course_name = courseNameField.getText();
        String course_code = courseCodeField.getText();
        String course_department = departmentField.getText();
        String start_date = startDateField.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String end_date = endDateField.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        double fee = Double.parseDouble(feeField.getText());

        int faculty_id = 0; // no faculty selected then id = 0
        String faculty_name = "Not Assigned Yet";

        if (!facultyChoiceBox.getSelectionModel().isEmpty()) {
            faculty_name = facultyChoiceBox.getValue();
        }

        // add these values to HashMap and send to CourseDatabases
        // which will save these values in course table

        HashMap<String, Object> course_details = new HashMap<>();

        course_details.put("name", course_name);
        course_details.put("code", course_code);
        course_details.put("department", course_department);
        course_details.put("faculty_name", faculty_name);
        course_details.put("start_date", start_date);
        course_details.put("end_date", end_date);
        course_details.put("fee", fee);

        new CourseDatabases().registerCourse(course_details, faculty_id);
        setTimeTable();

        // adding newly added course to table, in CourseController class

        AdminDashboard.admin.courseController.setNewCourse();
        AdminDashboard.admin.facultyController.refresh();

    }

    public void setTimeTable() {

        String mon_time = monHrFiled.getText() + " : " + monMinField.getText();
        String tue_time = tueHrField.getText() + " : " + tueMinField.getText();
        String wed_time = wedHrField.getText() + " : " + wedMinField.getText();
        String thu_time = thuHrField.getText() + " : " + thuMinField.getText();
        String fri_time = friHrFiled.getText() + " : " + friMinField.getText();
        String sat_time = satHrFiled.getText() + " : " + satMinField.getText();

        String mon_venue = monVenueField.getText();
        String tue_venue = tueVenueField.getText();
        String wed_venue = wedVenueField.getText();
        String thu_venue = thuVenueField.getText();
        String fri_venue = friVenueField.getText();
        String sat_venue = satVenueField.getText();

        // adding these values to hashmap and sending to course database

        Map<String, Object> time_table = new LinkedHashMap<>();

        time_table.put("Monday", mon_time);
        time_table.put("Tuesday", tue_time);
        time_table.put("Wednesday", wed_time);
        time_table.put("Thursday", thu_time);
        time_table.put("Friday", fri_time);
        time_table.put("Saturday", sat_time);


        List<String> venue_list = Arrays.asList(mon_venue, tue_venue, wed_venue, thu_venue, fri_venue, sat_venue);

        // sending to course database to store it

        new CourseDatabases().setTimeTable(time_table, venue_list);
    }

    @FXML
    private void clearForm() {
        courseNameField.clear();
        courseCodeField.clear();
        departmentField.clear();
    }

    @FXML
    private void clearTimeTable() {
        monMinField.clear();
        monHrFiled.clear();
        tueMinField.clear();
        tueHrField.clear();
        wedHrField.clear();
        wedMinField.clear();
        thuHrField.clear();
        thuMinField.clear();
        friHrFiled.clear();
        friMinField.clear();
        satHrFiled.clear();
        satMinField.clear();
        monVenueField.clear();
        tueVenueField.clear();
        wedVenueField.clear();
        thuVenueField.clear();
        friVenueField.clear();
        satVenueField.clear();
    }
}

