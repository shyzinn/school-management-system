package admin;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import courses.CourseDatabases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import models.Courses;
import models.TimeTable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CourseController implements Initializable {
    @FXML
    private TableView<Courses> coursesTable;

    @FXML
    private TableColumn<Courses, String> serialCol;

    @FXML
    private TableColumn<Courses, String> courseCodeCol;

    @FXML
    private TableColumn<Courses, String> courseNameCol;

    @FXML
    private TableColumn<Courses, String> departmentCol;

    @FXML
    private TableColumn<Courses, Double> feeCol;

    @FXML
    private TableColumn<Courses, String> startDateCol;

    @FXML
    private TableColumn<Courses, String> endDateCol;

    @FXML
    private TableView<TimeTable> timeTable;

    @FXML
    private TableColumn<TimeTable, String> dayCol;

    @FXML
    private TableColumn<TimeTable, String> timeCol;

    @FXML
    private TableColumn<TimeTable, String> venueCol;

    @FXML
    private Button deleteBtn;

    static AdminDashboard adminController;

    ObservableList<Courses> coursesData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // on initializing populating the course table
        setCoursesOnTable();
        tableListener();

        //enabling multiple selection on table
        coursesTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        timeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        venueCol.setCellFactory(TextFieldTableCell.forTableColumn());

        onChangingTimeTable();
    }

    public void tableListener() {

        // listen for table selection to enable action buttons related to table
        coursesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                deleteBtn.setDisable(false);
                setTimeTable();
            } else {
                deleteBtn.setDisable(true);
            }
        });

    }

    public AdminDashboard getAdmin() {
        return adminController;
    }

    @FXML
    private void addNewCourse() {
        // on press of add button, load the add new course window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/admin/add_course.fxml"));
            Parent root = loader.load();
            AddNewCourse course = loader.getController();
            course.setNewCourseWin(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNewCourse() {
        // here we will get newly added course and add it to table

        Courses course = new CourseDatabases().getLatestCourse();
        coursesData.add(course);
        coursesTable.refresh();
    }

    public void deleteCourse() {
        // on deletion of course all data related to deleted course will be deleted
        // like assignments exams etc

        //check whether course is selected from table before pressing button
        if (!coursesTable.getSelectionModel().isEmpty()) {

            //getting user confirmation
            int choice = new Dialogs().confirmationAlert("Confirmation", "Do you want to delete the" +
                    "selected courses?", "All data related to this course will be deleted \n" +
                    "e:g Assignments and Exams etc");

            // getting course id from table object
            if (choice == 1) {
                ObservableList<Courses> selected_courses = coursesTable.getSelectionModel().getSelectedItems();

                for (Courses course : selected_courses) {
                    int id = course.getId();
                    new CourseDatabases().deleteCourse(id);
                }
                // removing the course from list and refreshing table
                coursesData.removeAll(selected_courses);
                coursesTable.refresh();

                //removing timetable from UI

                timeTable.getItems().clear();

                AdminDashboard.admin.facultyController.refresh();

            }
        }
    }

    public void setCoursesOnTable() {

        // getting our list from CourseDatabase class, which will return observable list
        coursesData = new CourseDatabases().getCourses();

        // now populating the courses table

        // string values in quotes must be same as the function defined in Courses model class after get or set,
        // e:g if function : setSerial , then here in serial col we will write serial, which is value after set or get

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("courseDepartment"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("courseStartDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("courseEndDate"));
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));

        /*
          ObservableList: A list that enables listeners to track changes when they occur.
          ListChangeListener: An interface that receives notifications of changes to an
          ObservableList. ObservableMap: A map that enables observers to track changes when they occur
         */
        coursesTable.setItems(coursesData);
    }

    public void setTimeTable() {

        timeTable.getItems().clear();
        timeTable.refresh();

        if (!coursesTable.getSelectionModel().isEmpty()) {

            int selected_course_id = coursesTable.getSelectionModel().getSelectedItem().getId();

            ObservableList<TimeTable> time_table_list = new CourseDatabases().getTimeTable(selected_course_id);

            dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
            timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
            venueCol.setCellValueFactory(new PropertyValueFactory<>("venue"));

            timeTable.setItems(time_table_list);
        }
    }


    public void onChangingTimeTable() {

        timeCol.setOnEditCommit((TableColumn.CellEditEvent<TimeTable, String> t) -> {
            int id = timeTable.getSelectionModel().getSelectedItem().getId();
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setTime(t.getNewValue());
            new CourseDatabases().changeTimeTable(t.getNewValue(), "time", id);
            setTimeTable();
        });
        venueCol.setOnEditCommit((TableColumn.CellEditEvent<TimeTable, String> t) -> {
            int id = timeTable.getSelectionModel().getSelectedItem().getId();
            t.getTableView().getItems().get(t.getTablePosition().getRow()).setTime(t.getNewValue());
            new CourseDatabases().changeTimeTable(t.getNewValue(), "class_venue", id);
            setTimeTable();
        });
    }
}
