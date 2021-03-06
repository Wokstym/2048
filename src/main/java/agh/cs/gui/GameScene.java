package agh.cs.gui;

import agh.cs.board.utils.Vector2d;
import agh.cs.tile.Tile;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class GameScene extends Scene implements PropertyChangeListener {


    private static final Paint GAME_BACKGROUND = Color.rgb(250, 248, 239, 1.0);

    private BoardGroup boardGroup;
    private TitleGroup titleGroup;

    GameScene(Pane rootNode, int width, int height) {
        super(rootNode, width, height, GAME_BACKGROUND);

        boardGroup = new BoardGroup(this);
        titleGroup = new TitleGroup();
        rootNode.getChildren().add(boardGroup);
        rootNode.getChildren().add(titleGroup);
    }

    void reset() {
        boardGroup.reset();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("newTile")) {
            boardGroup.addTile((Tile) event.getSource());
        }

        if (event.getPropertyName().equals("positionChanged")) {
            Vector2d oldPos = (Vector2d) event.getOldValue();
            Vector2d newPos = (Vector2d) event.getNewValue();

            StackPane tile = boardGroup.tilesMap.get(oldPos);
            boardGroup.tilesMap.remove(oldPos);
            Vector2d newPosOnCanva = boardGroup.getPosOnCanvas(newPos);
            tile.relocate(newPosOnCanva.x, newPosOnCanva.y);
            boardGroup.tilesMap.put(newPos, tile);
        }
        if (event.getPropertyName().equals("objectMerged")) {
            Vector2d oldPos = (Vector2d) event.getOldValue();
            Tile tile = (Tile) event.getSource();
            boardGroup.tilesMap.forEach((v, t) -> System.out.println(v));
            StackPane oldTile = boardGroup.tilesMap.get(oldPos);
            oldTile.getChildren().clear();
            boardGroup.tilesMap.remove(oldPos);
            boardGroup.updateTileVal(tile);
        }
        if (event.getPropertyName().equals("bestScoreChanged")) {
            titleGroup.updateBestScore((int) event.getNewValue());
        }
        if (event.getPropertyName().equals("actualScoreChanged")) {
            titleGroup.updateActualScore((int) event.getNewValue());
        }

    }
}
