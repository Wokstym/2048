package agh.cs.gui;

import agh.cs.board.utils.Vector2d;
import agh.cs.tile.Tile;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.util.HashMap;
import java.util.Map;



class BoardGroup extends Group {

    private static final int TILE_SIZE = 128;
    private static final int SPACE_BTW_TILES = 16;
    private static final Paint EMPTY_SQR_COLOR = Color.rgb(205, 193, 180, 1.0);
    private static final Paint BOARD_BACKGROUND = Color.rgb(187, 173, 160, 1.0);

    private Vector2d lowerLeftBoard;
    private Vector2d upperRightBoard;
    private GameScene gm;


    Map<Vector2d, StackPane> tilesMap = new HashMap<>();

    BoardGroup(GameScene gm) {
        this.gm = gm;

        lowerLeftBoard = new Vector2d(60, 25);
        upperRightBoard = new Vector2d(lowerLeftBoard.x + 5 * SPACE_BTW_TILES + 4 * TILE_SIZE, lowerLeftBoard.y + 5 * SPACE_BTW_TILES + 4 * TILE_SIZE);

        getChildren().add(genBackground());
    }

    void reset() {
        for (int y = 0; y < 4; y++)
            for (int x = 0; x < 4; x++)
                if (tilesMap.containsKey(new Vector2d(x, y)))
                    getChildren().remove(tilesMap.get(new Vector2d(x, y)));
        tilesMap.clear();
    }


    private GridPane genBackground() {
        GridPane background = new GridPane();

        background.setBackground(new Background(new BackgroundFill(BOARD_BACKGROUND, new CornerRadii(14), Insets.EMPTY)));
        background.setPadding(new Insets(SPACE_BTW_TILES));
        background.setHgap(SPACE_BTW_TILES);
        background.setVgap(SPACE_BTW_TILES);
        background.relocate(lowerLeftBoard.x, gm.getHeight() - upperRightBoard.y);

        for (int y = 0; y < 4; y++)
            for (int x = 0; x < 4; x++)
                background.add(getEmptyRec(getPosOnCanvas(new Vector2d(x, y))), x, y);

        return background;
    }

    Vector2d getPosOnCanvas(Vector2d relativePos) {
        return new Vector2d(
                lowerLeftBoard.x + SPACE_BTW_TILES + relativePos.x * (TILE_SIZE + SPACE_BTW_TILES),
                (int) (gm.getHeight() - (lowerLeftBoard.y + relativePos.y * (TILE_SIZE + SPACE_BTW_TILES) + TILE_SIZE + SPACE_BTW_TILES)));
    }

    private Rectangle getTileRec(Paint color) {
        Rectangle rectangle = new Rectangle(TILE_SIZE, TILE_SIZE);
        rectangle.setArcWidth(14);
        rectangle.setArcHeight(14);
        rectangle.setFill(color);
        return rectangle;
    }

    private Rectangle getEmptyRec(Vector2d pos) {
        Rectangle rectangle = getTileRec(EMPTY_SQR_COLOR);
        rectangle.setX(pos.x);
        rectangle.setY(pos.y);
        return rectangle;
    }


    void addTile(Tile t) {
        Vector2d posOnCanvas = getPosOnCanvas(t.getPos());

        Rectangle tileRec = getTileRec(getBackground(t.getVal()));
        StackPane tileGroup = new StackPane(tileRec);

        Text text = new Text();
        updateText(text, t);
        text.setTextAlignment(TextAlignment.CENTER);
        tileGroup.getChildren().add(text);

        tileGroup.relocate(posOnCanvas.x, posOnCanvas.y);
        tilesMap.put(t.getPos(), tileGroup);
        getChildren().add(tileGroup);
    }

    void updateTileVal(Tile t) {
        StackPane tile = tilesMap.get(t.getPos());
        ((Rectangle) tile.getChildren().get(0)).setFill(getBackground(t.getVal()));
        updateText((Text) tile.getChildren().get(1), t);
    }

    private void updateText(Text text, Tile t) {
        text.setText(String.valueOf(t.getVal()));
        text.setFont(Font.font("Verdana", FontWeight.BOLD, t.getVal() < 100 ?
                32 :
                t.getVal() < 1000 ?
                        28 :
                        24));
        text.setFill(getForeground(t.getVal()));
    }

    /* Tile Colors */

    private Color getBackground(int val) {
        switch (val) {
            case 2:
                return Color.rgb(238, 228, 218, 1.0);
            case 4:
                return Color.rgb(237, 224, 200, 1.0);
            case 8:
                return Color.rgb(242, 177, 121, 1.0);
            case 16:
                return Color.rgb(245, 149, 99, 1.0);
            case 32:
                return Color.rgb(246, 124, 95, 1.0);
            case 64:
                return Color.rgb(246, 94, 59, 1.0);
            case 128:
                return Color.rgb(237, 207, 114, 1.0);
            case 256:
                return Color.rgb(237, 204, 97, 1.0);
            case 512:
                return Color.rgb(237, 200, 80, 1.0);
            case 1024:
                return Color.rgb(237, 197, 63, 1.0);
            case 2048:
                return Color.rgb(237, 194, 46, 1.0);
        }
        return Color.rgb(205, 193, 180, 1.0); //0xcdc1b4
    }

    private Color getForeground(int val) {
        Color foreground;
        if (val < 16) {
            foreground = Color.rgb(119, 110, 101, 1.0); //0x776e65
        } else {
            foreground = Color.rgb(249, 246, 242, 1.0);    //0xf9f6f2
        }
        return foreground;
    }

}
