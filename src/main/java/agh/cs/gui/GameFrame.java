package agh.cs.gui;

import agh.cs.board.Board;
import agh.cs.board.utils.Direction;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class GameFrame extends Application {

    private static final int MAP_HEIGHT = 900;
    private static final int MAP_WIDTH = 712;

    private Board board;
    public GameScene gameScene;

    @Override
    public void start(Stage myStage) {
        myStage.setTitle("2048");
        Pane rootNode = new Pane();
        myStage.setResizable(false);
        myStage.setOnCloseRequest(event -> Platform.exit());

        gameScene = new GameScene(rootNode, MAP_WIDTH, MAP_HEIGHT);
        board = new Board(4, 2, this);
        board.addPropertyChangeListener(gameScene);


        gameScene.setOnKeyPressed(event -> {

            if (!board.isLost()) {
                switch (event.getCode()) {
                    case LEFT:
                        board.makeMove(Direction.LEFT);
                        break;
                    case RIGHT:
                        board.makeMove(Direction.RIGHT);
                        break;
                    case DOWN:
                        board.makeMove(Direction.DOWN);
                        break;
                    case UP:
                        board.makeMove(Direction.UP);
                        break;
                }
            } else
                System.out.println("Lost");

            if (event.getCode().equals(KeyCode.R)) {
                gameScene.reset();
                board.reset();
            }
            if (board.isWon()) System.out.println("Won");

        });
        myStage.setScene(gameScene);
        myStage.show();
    }

    public void show(String[] args) {
        launch(args);
    }
}
