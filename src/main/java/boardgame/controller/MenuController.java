package boardgame.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;

public class MenuController {

    @FXML
    private void switchScene(ActionEvent event) throws IOException {
        Logger.debug("Start the game button is pressed");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ui.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void switchToHighScores(ActionEvent event) throws IOException {
        Logger.debug("Start the game button is pressed");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/highscores.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

}
