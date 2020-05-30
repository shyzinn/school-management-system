package student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Results;
import result.AssignmentsResultsDatabase;

import java.net.URL;
import java.util.ResourceBundle;

public class AssignmentsResults implements Initializable {

    @FXML
    private TableColumn<?, ?> serialCol;

    @FXML
    private TableColumn<?, ?> obtainedMarksCol;

    @FXML
    private TableColumn<?, ?> totalMarksCol;

    @FXML
    private TableColumn<?, ?> percentageCol;

    @FXML
    private TableColumn<?, ?> topicCol;

    @FXML
    private TableColumn<?, ?> commentsCol;


    @FXML
    private TableView<Results> assignmentsResultsTable;

    ObservableList<Results> assignmentResults = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAssignmentsResultsTable() {

        assignmentResults = new AssignmentsResultsDatabase().getResultsByCourseAndStudentID(CoursesController.courseId,
                CoursesController.studentId);

        serialCol.setCellValueFactory(new PropertyValueFactory<>("serial"));
        commentsCol.setCellValueFactory(new PropertyValueFactory<>("comments"));
        topicCol.setCellValueFactory(new PropertyValueFactory<>("assignmentTopic"));
        obtainedMarksCol.setCellValueFactory(new PropertyValueFactory<>("obtainedMarks"));
        totalMarksCol.setCellValueFactory(new PropertyValueFactory<>("totalMarks"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentageNode"));

        assignmentsResultsTable.setItems(assignmentResults);
    }
}
