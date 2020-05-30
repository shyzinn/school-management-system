package student;

import courses.CourseDatabases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main_menu.LoginController;
import models.Courses;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/*in this class we will show up the courses available to student
  so student can register the course according to his/her own choice
 */
public class RegisterCourse implements Initializable {

    @FXML
    private TableView<Courses> courseTable;

    @FXML
    private TableColumn<Courses, ChoiceBox<String>> choiceBoxCol;

    @FXML
    private TableColumn<Courses, String> nameCol;

    @FXML
    private TableColumn<Courses, String> codeCol;

    @FXML
    private TableColumn<Courses, String> feeCol;

    @FXML
    private TableColumn<Courses, String> facultyCol;

    @FXML
    private TableColumn<Courses, String> departmentCol;

    @FXML
    private TableColumn<?, ?> startDateCol;

    @FXML
    private TableColumn<?, ?> endDateCol;

    @FXML
    private CheckBox courseSelectionCheckBox;

    ObservableList<Courses> coursesList = FXCollections.observableArrayList();

    int studentId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        studentId = LoginController.loginID;
        setCourseTable();
    }

    public void setRegistrationWin(Parent root) {
        Stage win = new Stage();
        Scene scene = new Scene(root, 700, 400);
        win.setScene(scene);
        win.setResizable(false);
        win.initModality(Modality.APPLICATION_MODAL);
        win.showAndWait();
    }

    public void setCourseTable() {

        coursesList.clear();
        coursesList = new CourseDatabases().getCourses();

        choiceBoxCol.setCellValueFactory(new PropertyValueFactory<>("courseSelection"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("courseDepartment"));
        facultyCol.setCellValueFactory(new PropertyValueFactory<>("courseFaculty"));
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("courseStartDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("courseEndDate"));

        courseTable.setItems(coursesList);
    }

    @FXML
    private void registerCourse() {

        try {
            ObservableList<Courses> courses_list = new StudentDatabases().registerCourses(selectedCourses(), studentId);
            StudentDashboard.studentDashboard.coursesController.setNewCourse(courses_list);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // this function is used to select all the courses available
    @FXML
    private void coursesSelection() {
        for (Courses course : coursesList) {
            if (courseSelectionCheckBox.isSelected()) {
                course.getCourseSelection().setSelected(true);
            } else if (!courseSelectionCheckBox.isSelected()) {
                course.getCourseSelection().setSelected(false);
            }
        }
    }

    public ArrayList<Courses> selectedCourses() {

        // getting the selected courses, which are selected by check box in table
        // making temp list and adding these to it
        ArrayList<Courses> selected_courses_list = new ArrayList<>();

        for (Courses course : coursesList) {

            if (course.getCourseSelection().isSelected()) {
                selected_courses_list.add(course);
            }
        }
        return selected_courses_list;
    }
}
