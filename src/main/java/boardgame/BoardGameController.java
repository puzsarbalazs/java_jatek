package boardgame;

import boardgame.json.GameResult;
import boardgame.json.GameResultRepository;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import boardgame.model.BoardGameModel;
import boardgame.model.Square;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Optional;

public class BoardGameController {

    @FXML
    private GridPane board;

    private BoardGameModel model = new BoardGameModel();

    private GameResultRepository gameResultRepository = new GameResultRepository();

    @FXML
    private void initialize() {
        populateGrid();
        registerHandlersAndListeners();
        registerKeyEventHandler();
    }

    private void populateGrid() {
        for (var i = 0; i < board.getRowCount(); i++) {
            for (var j = 0; j < board.getColumnCount(); j++) {
                var square = createSquare(i, j);
                board.add(square, j, i);
            }
        }
    }

    //próba
    private BooleanProperty gameOver = new SimpleBooleanProperty();

    private StackPane createSquare(int i, int j) {
        var square = new StackPane();
        square.getStyleClass().add("square");
        var piece = new Circle(50);
/*
        piece.fillProperty().bind(Bindings.when(model.squareProperty(i, j).isEqualTo(Square.NONE))
                .then(Color.TRANSPARENT)
                .otherwise(Bindings.when(model.squareProperty(i, j).isEqualTo(Square.HEAD))
                        .then(Color.RED)
                        .otherwise(Color.BLUE))
        );
*/
        piece.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.squareProperty(i, j));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.squareProperty(i, j).get()) {
                            case NONE -> Color.TRANSPARENT;
                            case RED -> Color.RED;
                            case BLUE -> Color.BLUE;
                        };
                    }
                }
        );
        square.getChildren().add(piece);
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    //próba
    private void registerHandlersAndListeners() {
        gameOver.addListener(this::handleGameOver);
    }

    //ez is próba
    private void handleGameOver(ObservableValue<? extends Boolean> observableValue, Boolean oldValue, Boolean newValue) {
        if (newValue) {
            var text = model.winner()!=null ? model.winner().toString()+" won the game!" : "It's a draw";
            Logger.info(text);
            var alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Game Over");
            alert.setContentText(text);
            alert.showAndWait();
            GameResult gameResult = GameResult.builder()
                    .created(ZonedDateTime.now())
                    .winner(model.winner().toString())
                    .steps(model.moveCounter()).build();
            gameResultRepository.addOne(gameResult);
            try {
                gameResultRepository.saveToFile(gameResultRepository.getLeaderboardFile());
            } catch (IOException e) {
                e.printStackTrace();
            }
            resetGame();
        }
    }

    //ez is
    private void resetGame() {
        model = new BoardGameModel();
        clearGrid();
        board.getChildren().clear();
        populateGrid();
        //as
        Logger.info("Restarting the the game");
    }

    private static Optional<Node> getGridNodeAtPosition(GridPane gridPane, int row, int col) {
        return gridPane.getChildren().stream()
                .filter(child -> GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == col)
                .findFirst();
    }

    private void clearGrid() {
        for (var row = 0; row < 3; row++) {
            for (var col = 0; col < 3; col++) {
                getGridNodeAtPosition(board, row, col)
                        .ifPresent(node -> ((StackPane) node).getChildren().clear());
            }
        }
    }

    //gombok próba
    private void registerKeyEventHandler() {
        KeyCombination restartKeyCombination = new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN);
        KeyCombination quitKeyCombination = new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN);
        Platform.runLater(() -> board.getScene().setOnKeyPressed(
                keyEvent -> {
                    if (restartKeyCombination.match(keyEvent)) {
                        //Logger.debug("Restarting game...");
                        resetGame();
                    } else if (quitKeyCombination.match(keyEvent)) {
                        //Logger.debug("Exiting...");
                        Platform.exit();
                    }
                }
        ));
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        Logger.debug("Click on square ({}, {})", row, col);
        model.move(row, col);
        Logger.debug(model.toString());
        gameOver.set(model.isEnd());  //próba
    }


    @FXML
    private void BackToMenu(ActionEvent event) throws IOException {
        Logger.debug("Menu button is pressed");
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        stage.setScene(new Scene(root));
        stage.show();
    }

}
