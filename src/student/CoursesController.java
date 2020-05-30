package student;

import courses.CourseDatabases;
import config.Dialogs;
import courses.CoursesPlanTable;
import exams.ExamsTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import main_menu.LoginController;
import models.Courses;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/*
    this class will allow students to manage courses,
    like registering courses, uploading assignment related to courses,
    updating assignments, uploading exams answers
 */

public class CoursesController implements Initializable {

    @FXML
    AssignmentsController assignmentsController;

    @FXML
    TimeTableController timeTableController;

    @FXML
    UploadedAssignments uploadedAssignmentsController;

    @FXML
    ResultsController resultsController;

    @FXML
    ExamsTable examsTableController;

    @FXML
    private VBox sideMenu;

    static int courseId = 0;

    static int studentId = 0;

    CourseDatabases coursesDb;

    ObservableList<Courses> coursesList = FXCollections.observableArrayList();

    ArrayList<Map<String, Object>> coursesNodeList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        assignmentsController.injectCourseController(this);
        timeTableController.injectCourseController(this);
        uploadedAssignmentsController.injectCourseController(this);

        coursesDb = new CourseDatabases();
        studentId = LoginController.loginID;

        setCoursesList();
        listenForCourseSelection();


    }


    @FXML
    private void dropCourse() {

        if (courseId != 0) {
            int choice = new Dialogs().confirmationAlert("Confirmation",
                    "Do you want to unregister the selected courses?",
                    "Warning: All info related to this course will be deleted" +
                            "e:g uploaded assignment, exams and results.");

            if (choice == 1) {

                for (Map<String, Object> course_map : coursesNodeList) {

                    Courses course = (Courses) course_map.get("course");

                    if (course.getId() == courseId) {
                        new StudentDatabases().unRegisterCourses(course, studentId);
                        coursesList.remove(course);

                        sideMenu.getChildren().remove(course_map.get("list_node"));

                        new Dialogs().infoAlert("Courses UnRegistered",
                                "All selected courses are unregistered", "" +
                                        "All uploaded assignments, results, exams related to this course are deleted.");

                        clearTables();
                    }
                }
            }
        } else {
            new Dialogs().warningAlert("Warning", "No course selected",
                    "Please select the course from side menu then click on drop course button.");
        }
    }

    public void clearTables() {
        uploadedAssignmentsController.clearTable();
        timeTableController.clearTable();
        assignmentsController.clearTable();
        examsTableController.clearTable();
    }

    @FXML
    private void registerCourse() {
        // on press of add button, load the add new course window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/student/select_course.fxml"));
            Parent root = loader.load();
            RegisterCourse registration = loader.getController();
            registration.setRegistrationWin(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setCoursesList() {

        sideMenu.getChildren().clear();

        ArrayList<Integer> course_id_list = coursesDb.getCoursesRelatedToStudents(studentId);

        coursesList = coursesDb.getCoursesOnID(course_id_list);

        for (Courses course : coursesList) {

            Map<String, Object> course_map = new HashMap<>();

            HBox list_node = getNodeForList(course.getCourseName());
            course_map.put("course", course);
            course_map.put("list_node", list_node);

            sideMenu.getChildren().add(list_node);
            sideMenu.setPrefHeight(sideMenu.getPrefHeight() + 30);
            coursesNodeList.add(course_map);
        }
        listenForCourseSelection();
    }

    public void setNewCourse(ObservableList<Courses> courses) {

        for (Courses course : courses) {
            Map<String, Object> course_map = new HashMap<>();

            HBox list_node = getNodeForList(course.getCourseName());
            course_map.put("course", course);
            course_map.put("list_node", list_node);

            sideMenu.getChildren().add(list_node);
            sideMenu.setPrefHeight(sideMenu.getPrefHeight() + 30);
            coursesNodeList.add(course_map);

            coursesList.add(course);
        }


        listenForCourseSelection();

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

            Courses course = (Courses) course_map.get("course");

            select_btn.setOnAction(e -> {
                courseId = course.getId();
                assignmentsController.setAssignmentsTable(courseId);
                timeTableController.setTimeTable(courseId);
                uploadedAssignmentsController.setUploadedAssignments(courseId);
                resultsController.assignmentsResultsController.setAssignmentsResultsTable();
                resultsController.examsResultsController.setResultsTable(courseId, studentId);
                examsTableController.setExamsTable(courseId);
                examsTableController.listenForTakeExam();
                resultsController.setResultsStats(courseId);
                CoursesPlanTable.getCoursesPlanTableController().setCoursePlan(courseId);
                setSelectedBtnStyle(select_btn);
            });

        }
    }
}
