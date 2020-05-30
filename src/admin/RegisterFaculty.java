package admin;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import config.HashPassword;
import config.ValidateFields;
import courses.CourseDatabases;
import faculty.FacultyDatabases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Courses;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RegisterFaculty implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField officeNoField;

    @FXML
    private TextField salaryField;

    @FXML
    private DatePicker dateField;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TableColumn<?, ?> checkBoxCol;

    @FXML
    private TableColumn<?, ?> courseCodeCol;

    @FXML
    private TableColumn<?, ?> courseNameCol;

    @FXML
    private TableColumn<?, ?> courseDepartmentCol;

    @FXML
    private TableView<Courses> courseSelectionTable;

    @FXML
    private CheckBox courseSelectionCheckBox;

    ObservableList<Courses> courses = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // making salary field decimal

        List<TextField> decimal_field = Collections.singletonList(salaryField);
        ValidateFields.makeFieldsDecimal(decimal_field);

        // setting current date to date filed
        LocalDate date_value = java.time.LocalDate.now();
        dateField.setValue(date_value);

        setCoursesOnTable();
    }

    public void setRegisterWin(Parent root) {

        Stage win = new Stage();
        Scene scene = new Scene(root, 500, 650);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.showAndWait();

    }

    // this function is used to select all the courses available
    @FXML
    private void coursesSelection() {
        for (Courses course : courses) {
            if (courseSelectionCheckBox.isSelected()) {
                course.getCourseSelection().setSelected(true);
            } else if (!courseSelectionCheckBox.isSelected()) {
                course.getCourseSelection().setSelected(false);
            }
        }
    }

    public void setCoursesOnTable() {

        courses = new CourseDatabases().getCourses();

        checkBoxCol.setCellValueFactory(new PropertyValueFactory<>("courseSelection"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseDepartmentCol.setCellValueFactory(new PropertyValueFactory<>("courseDepartment"));

        courseSelectionTable.setSelectionModel(null);
        courseSelectionTable.setItems(courses);
    }

    @FXML
    private void register() {

        String name = nameField.getText();
        String department = departmentField.getText();
        String office_no = officeNoField.getText();
        String salary = salaryField.getText();
        String date_joined = dateField.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!name.isEmpty() && !department.isEmpty() && !office_no.isEmpty()
                && !salary.isEmpty() && !date_joined.isEmpty()
                && !username.isEmpty() && !password.isEmpty()) {


            // putting these values in hash map and sending to faculty database class

            HashMap<String, Object> faculty_data = new HashMap<>();

            faculty_data.put("name", name);
            faculty_data.put("department", department);
            faculty_data.put("office_no", office_no);
            faculty_data.put("salary", Double.parseDouble(salary));
            faculty_data.put("date_joined", date_joined);
            faculty_data.put("username", username);
            faculty_data.put("password", new HashPassword().encrypt(password));

            FacultyDatabases facultyDatabases = new FacultyDatabases();

            facultyDatabases.registerFaculty(faculty_data);

            // register the selected courses with reference to this faculty
            facultyDatabases.registerCourses(selectedCourses());

            // adding newly added faculty to faculty table

            AdminDashboard.admin.facultyController.setNewFaculty();

        } else {
            new Dialogs().warningAlert("Warning", "Please complete the required fields", "" +
                    "You must complete the required fields to register new faculty");
        }

    }

    public ArrayList<Courses> selectedCourses() {

        // getting the selected courses, which are selected by check box in table
        // making temp list and adding these to it
        ArrayList<Courses> selected_courses_list = new ArrayList<>();

        for (Courses course : courses) {

            if (course.getCourseSelection().isSelected()) {
                selected_courses_list.add(course);
            }
        }
        return selected_courses_list;
    }

    @FXML
    private void clearForm() {
        nameField.clear();
        departmentField.clear();
        officeNoField.clear();
        salaryField.clear();
    }
}


