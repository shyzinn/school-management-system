package main_menu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class Main extends Application {

    static Stage loginWindow;

    public Main() {
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // loading the application base / main menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/main_menu/login_main_menu.fxml"));
            Parent root = loader.load();
            loginWindow = primaryStage;
            LoginController login = loader.getController();
            login.setLoginWin(root, primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // this method will initiate login window
    public void setLoginWindow(){
        try {
            // loading the application base / main menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/main_menu/login_main_menu.fxml"));
            Parent root = loader.load();
            loginWindow = new Stage();
            LoginController login = loader.getController();
            login.setLoginWin(root, loginWindow);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Stage getLoginWindow() {
        return loginWindow;
    }
}
