package exams;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import config.Dialogs;
import config.GetDate;
import config.Timer;
import config.ValidateFields;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import javafx.util.Duration;
import main_menu.LoginController;
import models.Exams;

import java.net.URL;
import java.util.ResourceBundle;

public class TakeExam implements Initializable {

    @FXML
    private Label examLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label timerLabel;

    @FXML
    private TextArea answerTextBox;

    @FXML
    private TextArea questionsTextBox;

    @FXML
    private Button startBtn;

    @FXML
    private Button submitBtn;

    @FXML
    private Button quitBtn;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private Label progressPercentageLabel;

    private int minutes;
    private int seconds = 0;
    private int timeTaken = 0;
    private Timeline animation;

    Exams exam;

    boolean submitted = false;

    boolean startExam = false;

    Stage win;

    double progressInterval;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void startExam() {

        startExam = true;

        progressInterval = (double) (100) / (minutes * 60);
        progressInterval = (progressInterval) / (100);

        int choice = new Dialogs().confirmationAlert("Confirmation", "Do you want to start the exam?",
                "You have only one chance to take this exam, after this you won't be able to get another chance.");

        if (choice == 1) {

            answerTextBox.setDisable(false);
            answerTextBox.setEditable(true);

            startBtn.setDisable(true);
            submitBtn.setDisable(false);
            quitBtn.setDisable(false);

            questionsTextBox.setText(exam.getContent());

            animation = new Timeline(new KeyFrame(Duration.seconds(1), e -> countDown()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();

        }
    }

    @FXML
    private void submitExam() {

        if (!submitted && startExam) {

            int choice = new Dialogs().confirmationAlert("Confirmation", "Do you want to submit the exam?",
                    "You have only one chance to take this exam, after this you won't be able to get another chance." +
                            "What you have done so far will be saved and consider final.");

            if (choice == 1) {
                int course_id = exam.getCourseId();
                int student_id = LoginController.loginID;
                int exam_id = exam.getId();

                String answers = answerTextBox.getText();
                String date = new GetDate().getDate();

                new ExamsDatabases().setUploadedExam(course_id, student_id, exam_id, timeTaken, date, answers);
                new Dialogs().infoAlert("Success", "Exam submitted successfully",
                        "You have successfully attempted the exam and it will be send to your faculty " +
                                "for evaluation, you can now quit this window. Thanks");
                submitBtn.setDisable(true);
                quitBtn.setDisable(true);
                submitted = true;

                timerLabel.setText("Submitted");
                animation.stop();
            }
        }
    }

    @FXML
    private void quitExam() {

        submitExam();
        win.close();

    }

    public void setTakeExamWin(Parent root, Exams exam) {

        this.exam = exam;

        examLabel.setText(exam.getCode());
        detailsLabel.setText(exam.getDetails());

        timerLabel.setText("Time: " + exam.getTimeAllotted() + " Minutes");

        minutes = exam.getTimeAllotted();

        win = new Stage();
        Scene scene = new Scene(root, 500, 500);
        win.setScene(scene);
        win.setOnCloseRequest(e -> {
            if (!submitted && startExam) {
                submitExam();
            }
        });
        win.setMaximized(true);
        win.showAndWait();
    }

    private void countDown() {
        if (minutes >= 0) {

            Timer defaultTime = new Timer();
            defaultTime.setMinutes(minutes);
            defaultTime.setSeconds(seconds);
            timerLabel.setText(defaultTime.toString());
            setProgressBar();

            if (seconds == 0) {
                minutes--;
                seconds = 59;
                timeTaken++;
            } else {
                seconds--;
            }

        } else {
            timerLabel.setText("Times up!");
            answerTextBox.setDisable(true);
        }
    }

    public void setProgressBar() {
        double progress = progressBar.getProgress() - progressInterval;
        if (progress >= 0) {
            progressBar.setProgress(progress);
            progressPercentageLabel.setText(ValidateFields.round(progress * 100, 2) + " % ");

            if(progress < .50){
                progressPercentageLabel.setStyle("-fx-text-fill: black");
            }
        }
    }
}
