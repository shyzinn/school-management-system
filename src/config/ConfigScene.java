package config;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ConfigScene {
// Gustavo Henrique de Souza - 20052 \\
// OOP - Dorset College \\

    private double xOffset = 0.0;
    private double yOffset = 0.0;

    public void config(Button minBtn, Button closeBtn, Parent root, Stage window) {
        closeBtn.setOnAction(e -> {
            window.close();
        });
        minBtn.setOnAction(e -> {
            window.setIconified(true);
        });

        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

        // move around here
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setX(event.getScreenX() - xOffset);
                window.setY(event.getScreenY() - yOffset);
            }
        });
    }
}
