package student;

import courses.CourseDatabases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.TimeTable;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeTableController implements Initializable {

    CoursesController coursesController;

    @FXML
    private TableColumn<TimeTable, String> dayCol;

    @FXML
    private TableColumn<TimeTable, String> timeCol;

    @FXML
    private TableColumn<TimeTable, String> venueCol;

    @FXML
    private TableView<TimeTable> timeTable;

    ObservableList<TimeTable> timeTableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void injectCourseController(CoursesController courses_controller) {

        coursesController = courses_controller;
    }

    public void setTimeTable(int course_id) {

        timeTableList = new CourseDatabases().getTimeTable(course_id);
        dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
        timeCol.setCellValueFactory(new PropertyValueFactory<>("time"));
        venueCol.setCellValueFactory(new PropertyValueFactory<>("venue"));
        timeTable.setItems(timeTableList);
    }

    public void clearTable() {
        timeTableList.clear();
        timeTable.getItems().clear();
        timeTable.refresh();
    }
}
