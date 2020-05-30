package admin;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Students;
import student.StudentDatabases;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    @FXML
    private Button delBtn;

    @FXML
    private TableColumn<Students, Integer> serialCol;

    @FXML
    private TableColumn<Students, String> nameCol;

    @FXML
    private TableColumn<Students, String> departmentCol;

    @FXML
    private TableColumn<Students, String> totalCoursesCol;

    @FXML
    private TableColumn<Students, String> phoneCol;

    @FXML
    private TableColumn<Students, String> emailCol;

    @FXML
    private TableColumn<Students, String> dateCol;

    @FXML
    private TableView<Students> studentsTable;

    ObservableList<Students> studentsList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setStudentsOnTable();
        studentsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tableListener();

    }

    public void tableListener() {

        // listen for table selection to enable action buttons related to table
        studentsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            delBtn.setDisable(newSelection == null);
        });

    }

    public void setStudentsOnTable() {

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        departmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        totalCoursesCol.setCellValueFactory(new PropertyValueFactory<>("coursesRegistered"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateJoined"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        studentsList = new StudentDatabases().getStudentsList();
        studentsTable.setItems(studentsList);

    }

    @FXML
    private void addNewStudent() {
        // on press of add button, load the add new student window
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/admin/register_student.fxml"));
            Parent root = loader.load();
            RegisterStudent student = loader.getController();
            student.setRegisterWin(root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNewStudent() {
        Students new_student = new StudentDatabases().getLatestStudent();
        studentsList.add(new_student);
        studentsTable.refresh();
        tableListener();
    }

    public void deleteStudents() {

        if (!studentsTable.getSelectionModel().isEmpty()) {
            int choice = new Dialogs().confirmationAlert("Confirmation", "Do you want to delete selected" +
                    "students?", "Warning: All data related to these students will be deleted\n" +
                    "e:g assignments, courses records etc");

            if (choice == 1) {
                ObservableList<Students> selected_student_list = studentsTable.getSelectionModel().getSelectedItems();

                for (Students student : selected_student_list) {
                    int id = student.getId();
                    new StudentDatabases().deleteStudent(id);
                }
                studentsList.removeAll(selected_student_list);
                studentsTable.refresh();
            }

        }

    }
}
