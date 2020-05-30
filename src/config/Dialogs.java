package config;
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import static javafx.scene.control.ButtonType.*;

public class Dialogs {

    public void infoAlert(String title, String header, String context) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        alert.showAndWait();
    }

    public void warningAlert(String title, String header, String context) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        alert.showAndWait();
    }

    public void errorAlert(String title, String header, String context) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);

        alert.showAndWait();
    }

    public int confirmationAlert(String title, String header, String context) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, context, ButtonType.YES, ButtonType.NO);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            return 1;
        } else if (alert.getResult() == ButtonType.NO) {
            return 0;
        }
        else{
            return 0;
        }
    }
}
