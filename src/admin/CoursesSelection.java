package admin;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import courses.CourseDatabases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import models.Courses;
import models.Faculty;

import java.net.URL;
import java.util.*;

/*
    In this class admin will be able to assign courses to faculty
    after registration of faculty, however it is also possible to
    assign courses at the time of registration but if admin skip that
    part later he can always assign or un-assign the courses.
 */
public class CoursesSelection implements Initializable {

    @FXML
    private TableView<Courses> courseTable;

    @FXML
    private TableColumn<Courses, ChoiceBox<String>> choiceBoxCol;

    @FXML
    private TableColumn<Courses, String> nameCol;

    @FXML
    private TableColumn<Courses, String> codeCol;

    @FXML
    private TableColumn<Courses, String> departmentCol;

    @FXML
    private CheckBox courseSelectionCheckBox;

    @FXML
    private ListView<HBox> assignCoursesList;

    ObservableList<Courses> courses_list = FXCollections.observableArrayList();

    int facultyID;

    Faculty faculty;

    CourseDatabases courseDb;

    ObservableList<Courses> assignedCourses;

    ArrayList<Map<String, Object>> coursesNodeList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAssignCourses() {

        assignCoursesList.getItems().clear();

        ArrayList<Integer> assigned_courses_id = courseDb.getCoursesRelatedToFaculty(facultyID);
        assignedCourses = courseDb.getCoursesOnID(assigned_courses_id);

        // this hash map contains info related to node in listview

        for (Courses course : assignedCourses) {

            Map<String, Object> assigned_courses_map = new HashMap<>();

            HBox list_node = getNodeForList(course.getCourseName());

            assignCoursesList.getItems().add(list_node);
            assigned_courses_map.put("course_id", course.getId());
            assigned_courses_map.put("list_node", list_node);

            coursesNodeList.add(assigned_courses_map);
        }

        listenForRemoveCoursesBtn();
    }

    public HBox getNodeForList(String course_name) {

        Label course_name_label = new Label(course_name);

        Button remove_btn = new Button();
        remove_btn.getStyleClass().add("remove-btn-list");

        HBox list_node = new HBox(remove_btn, course_name_label);
        list_node.setPadding(new Insets(5, 5, 5, 5));
        list_node.setSpacing(5);
        list_node.setAlignment(Pos.CENTER_LEFT);

        return list_node;
    }

    public void listenForRemoveCoursesBtn() {

        for (Map<String, Object> assigned_courses_map : coursesNodeList) {

            HBox list_node = (HBox) assigned_courses_map.get("list_node");

            Button remove_btn = (Button) list_node.getChildren().get(0);

            int course_id = (int) assigned_courses_map.get("course_id");

            remove_btn.setOnAction(e -> {
                assignCoursesList.getItems().remove(list_node);
                coursesNodeList.remove(assigned_courses_map);
                courseDb.removeAssignedCourses(course_id, facultyID);

                // we also need to refresh faculty table
                // we need to subtract one from course count

                faculty.setCoursesCount(faculty.getCoursesCount() - 1);
                AdminDashboard.admin.facultyController.refreshTable();
            });
        }
    }

    public void setCoursesOnTable(int faculty_id, Faculty faculty) {

        this.facultyID = faculty_id;

        this.faculty = faculty;

        courseDb = new CourseDatabases();

        courseSelectionCheckBox.setSelected(false);

        courses_list = courseDb.getCourses();

        choiceBoxCol.setCellValueFactory(new PropertyValueFactory<>("courseSelection"));
        codeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("courseDepartment"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        courseTable.setItems(courses_list);

        setAssignCourses();
    }

    @FXML
    private void coursesSelection() {
        for (Courses course : courses_list) {
            if (courseSelectionCheckBox.isSelected()) {
                course.getCourseSelection().setSelected(true);
            } else if (!courseSelectionCheckBox.isSelected()) {
                course.getCourseSelection().setSelected(false);
            }
        }
    }

    /*
    this method is used to register more courses with reference
    to this faculty, remember single course can be registered only once
     */
    @FXML
    private void registerCourses() {

        // registering the selected courses with reference to current selected faculty ID

        if (selectedCourses().size() > 0) {

            int courses_assigned = courseDb.assignCourseToFaculty(selectedCourses(), facultyID);
            setAssignCourses();
            faculty.setCoursesCount(faculty.getCoursesCount() + courses_assigned);
            AdminDashboard.admin.facultyController.refreshTable();
        } else {
            new Dialogs().warningAlert("Warning", "No courses selected",
                    "Please select some courses then click on assign button");
        }
    }

    public ArrayList<Courses> selectedCourses() {
        // getting the selected courses, which are selected by check box in table
        // making temp list and adding these to it
        ArrayList<Courses> selected_courses_list = new ArrayList<>();

        for (Courses course : courses_list) {

            if (course.getCourseSelection().isSelected()) {
                selected_courses_list.add(course);
            }
        }
        return selected_courses_list;
    }
}
