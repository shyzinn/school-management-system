package faculty;

import attendance.MarkAttendance;
import courses.CourseDatabases;
import courses.CoursePlanController;
import exams.ExamsController;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main_menu.LoginController;
import main_menu.Main;
import models.Assignments;
import models.Courses;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class FacultyDashboard implements Initializable {

    @FXML
    AssignmentsController assignmentsController;

    @FXML
    ExamsController examsController;

    @FXML
    ResultsController resultsController;

    @FXML
    CoursePlanController coursePlanController;

    @FXML
    private VBox sideMenu;

    @FXML
    MarkAttendance attendanceController;

    int facultyId;

    ArrayList<Map<String, Object>> coursesNodeList = new ArrayList<>();

    Stage facultyWindow;

    static int selectedCourseId = 0;

    static FacultyDashboard facultyDashboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("Faculty dashboard initiated");

        facultyDashboard = this;

        facultyId = LoginController.loginID;
        setCoursesListView();
        listenForCourseSelection();

        // removing take exam column in faculty dashboard because it is only for students
        examsController.getExamsTableController().getExamsTable().getColumns().remove(5);

    }

    public FacultyDashboard getFacultyDashboard() {
        return facultyDashboard;
    }

    public void setNewAssignment(Assignments new_assignment) {

        assignmentsController.setNewlyAddedAssignment(new_assignment);
    }

    public CoursePlanController getCoursePlanController() {
        return coursePlanController;
    }

    public ResultsController getResultsController() {
        return resultsController;
    }

    public AssignmentsController getAssignmentsController() {
        return assignmentsController;
    }

    public ExamsController getExamsController() {
        return examsController;
    }

    public void setNewResult() {
        resultsController.getExamsResultsController().setNewResult();
    }

    public void setCoursesListView() {

        ArrayList<Integer> courses_id_list = new CourseDatabases().getCoursesRelatedToFaculty(facultyId);

        ObservableList<Courses> courses_list = new CourseDatabases().getCoursesOnID(courses_id_list);

        for (Courses course : courses_list) {

            Map<String, Object> course_map = new HashMap<>();

            HBox list_node = getNodeForList(course.getCourseName());
            course_map.put("course_id", course.getId());
            course_map.put("list_node", list_node);

            sideMenu.getChildren().add(list_node);
            sideMenu.setPrefHeight(sideMenu.getPrefHeight() + 30);
            coursesNodeList.add(course_map);
        }

    }

    public HBox getNodeForList(String course_name) {

        Button select_btn = new Button(course_name);
        select_btn.getStyleClass().add("course-selection-btn");
        select_btn.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(select_btn, Priority.ALWAYS);
        select_btn.setMaxWidth(Double.MAX_VALUE);

        HBox list_node = new HBox(select_btn);
        list_node.setPadding(new Insets(5, 0, 5, 0));
        list_node.setSpacing(5);
        list_node.setMaxWidth(Double.MAX_VALUE);
        list_node.setPrefHeight(30);
        list_node.setAlignment(Pos.CENTER_LEFT);

        HBox.setHgrow(list_node, Priority.ALWAYS);

        return list_node;

    }

    public void setSelectedBtnStyle(Button selected_btn) {

        // removing the style on other buttons
        for (Map<String, Object> course_map : coursesNodeList) {

            HBox list_node = (HBox) course_map.get("list_node");

            Button course_btn = (Button) list_node.getChildren().get(0);

            if (!selected_btn.equals(course_btn)) {
                course_btn.getStyleClass().remove("selected-btn");
            } else if (!selected_btn.getStyleClass().contains("selected-btn")) {
                selected_btn.getStyleClass().add("selected-btn");
            }
        }
    }

    public void listenForCourseSelection() {

        for (Map<String, Object> course_map : coursesNodeList) {

            HBox list_node = (HBox) course_map.get("list_node");

            Button select_btn = (Button) list_node.getChildren().get(0);

            int course_id = (int) course_map.get("course_id");

            select_btn.setOnAction(e -> {
                assignmentsController.setAssignmentOnTable(course_id);
                selectedCourseId = course_id;
                assignmentsController.getUploadedAssignmentsController().setAssignmentsTable(course_id);
                resultsController.setResultTable();
                attendanceController.setAttendanceTable();
                examsController.getExamsTableController().setExamsTable(course_id);
                examsController.getExamsTableController().initializeForFaculty();
                resultsController.examsResultsController.setResultsTable(course_id);
                examsController.getUploadedExamsController().setUploadedExams(course_id);
                coursePlanController.getCoursesPlanTableController().setCoursePlan(course_id);
                coursePlanController.getCoursesPlanTableController().setForFaculty();
                setSelectedBtnStyle(select_btn);
            });

        }
    }

    public int getSelectedCourseId() {
        return selectedCourseId;
    }

    public void setFacultyDashboard(Parent root) {
        try {
            new Main().getLoginWindow().close();
            facultyWindow = new Stage();
            Scene scene = new Scene(root, 900, 305);
            Image main_icon = new Image(getClass().getResourceAsStream("/resources/icons/school_36px.png"));
            facultyWindow.getIcons().add(main_icon);
            facultyWindow.setScene(scene);
            facultyWindow.setMaximized(true);
            facultyWindow.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        facultyWindow.close();
        new Main().setLoginWindow();
        FacultyDashboard.selectedCourseId = 0;
    }

    public static void e(){

    }

    @FXML
    public void exit() {
        Platform.exit();
    }
}
