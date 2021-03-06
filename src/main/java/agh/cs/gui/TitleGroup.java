package agh.cs.gui;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class TitleGroup extends Group {


    private Text bestNrText;
    private Text scoreNrText;


    TitleGroup() {
        getChildren().add(gen2048Text());
        GridPane scoreFields = genScoreFields();
        getChildren().add(scoreFields);
    }


    private Rectangle genScoreRec() {
        int paddingLR = 30;
        int nrSize = 17;
        Rectangle rec = new Rectangle(paddingLR * 2 + nrSize * 4, 65);
        rec.setFill(Color.rgb(187, 173, 160, 1.0));
        rec.setArcWidth(14);
        rec.setArcHeight(14);
        return rec;
    }

    private StackPane scoreStack(String label, Text nrText) {

        StackPane bestScoreStack = new StackPane();
        GridPane bestScoreGrid = new GridPane();

        Rectangle bestRec = genScoreRec();
        bestScoreStack.getChildren().add(bestRec);

        Text bestText = genScoreBestText(label, 16, Color.rgb(205, 193, 180, 1.0));
        bestScoreGrid.add(bestText, 1, 0);


        bestScoreGrid.add(nrText, 1, 1);
        GridPane.setHalignment(nrText, HPos.CENTER);
        bestScoreGrid.setAlignment(Pos.CENTER);

        bestScoreStack.getChildren().add(bestScoreGrid);
        bestScoreStack.setAlignment(Pos.CENTER_RIGHT);

        return bestScoreStack;
    }

    private GridPane genScoreFields() {

        bestNrText = genScoreBestText("0", 21, Color.WHITE);
        scoreNrText = genScoreBestText("0", 21, Color.WHITE);

        GridPane scoresGrid = new GridPane();
        scoresGrid.add(scoreStack("SCORE", scoreNrText), 0, 0);
        scoresGrid.add(scoreStack("BEST", bestNrText), 1, 0);
        scoresGrid.setHgap(5);
        scoresGrid.setAlignment(Pos.CENTER_RIGHT);
        scoresGrid.relocate(350, 75);
        return scoresGrid;
    }

    private Text genScoreBestText(String inside, int fontSize, Color color) {
        Text t = new Text(inside);
        t.setFont(Font.font("Verdana", FontWeight.BOLD, fontSize));
        t.setFill(color);
        return t;
    }


    private Text gen2048Text() {
        Text text2048 = genScoreBestText("2048", 85, Color.rgb(119, 110, 101, 1.0));
        text2048.relocate(60, 60);
        return text2048;
    }

    void updateBestScore(int newScore) {
        bestNrText.setText(Integer.toString(newScore));
    }

    void updateActualScore(int newScore) {
        scoreNrText.setText(Integer.toString(newScore));
    }

}
