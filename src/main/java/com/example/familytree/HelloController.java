package com.example.familytree;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class HelloController {

    @FXML
    private AnchorPane page;

    private Rectangle previousRectangle;

    DraggableMaker draggableMaker = new DraggableMaker();

    public void addRec() {
        Rectangle rec = new Rectangle();
        rec.setHeight(5 * 5 + 100);
        rec.setWidth(5 * 5 + 100);
        rec.setFill(Color.RED);

        rec.setLayoutX(100);
        rec.setLayoutY(100);

        page.getChildren().add(rec);
        draggableMaker.makeDraggable(rec);

        if (previousRectangle != null) {
            // Create a line connecting the bottom end of the first rectangle to the top end of the last rectangle
            Line connectingLine = createConnectingLine(previousRectangle, rec);
            page.getChildren().add(connectingLine);
        }

        previousRectangle = rec;
    }

    private Line createConnectingLine(Rectangle startRect, Rectangle endRect) {
        Line line = new Line();

        line.startXProperty().bind(startRect.layoutXProperty().add(startRect.widthProperty().divide(2)));
        line.startYProperty().bind(startRect.layoutYProperty().add(startRect.heightProperty()));
        line.endXProperty().bind(endRect.layoutXProperty().add(endRect.widthProperty().divide(2)));
        line.endYProperty().bind(endRect.layoutYProperty());

        return line;
    }
}
