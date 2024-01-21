package com.example.familytree;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class HelloController {

    @FXML
    private AnchorPane page;

    DraggableMaker draggableMaker = new DraggableMaker();

    ArrayList<Rectangle> nodes = new ArrayList<>();

    Rectangle choice;

    public void addFirstNode() {
        Rectangle rec = createRectangle(100, 100);
        page.getChildren().add(rec);
        draggableMaker.makeDraggable(rec);

        // Add a click event handler to allow adding child on click
        rec.setOnMouseClicked(event -> {
            choice = rec;
            choice.setLayoutX(rec.getLayoutX());
            choice.setLayoutY(rec.getLayoutY());
        });

        nodes.add(rec);
    }

    public void addRecChild() {
        if (choice != null) {
            Rectangle rec = createRectangle(choice.getLayoutX(), choice.getLayoutY() + choice.getHeight() + 50);
            page.getChildren().add(rec);
            draggableMaker.makeDraggable(rec);

            Line connectingLine = createConnectingLineChild(choice, rec);
            page.getChildren().add(connectingLine);


            // Add a click event handler to allow adding child on click
            rec.setOnMouseClicked(event -> {
                choice = rec;
                choice.setLayoutX(rec.getLayoutX());
                choice.setLayoutY(rec.getLayoutY());
            });

            nodes.add(rec);
        }
    }

    public void addRecWife() {
        if (choice != null) {
            Rectangle rec = createRectangle(choice.getLayoutX() + choice.getWidth() + 50, choice.getLayoutY());
            page.getChildren().add(rec);
            draggableMaker.makeDraggable(rec);

            Line connectingLine = createConnectingLineWife(choice, rec);
            page.getChildren().add(connectingLine);

            // Add a click event handler to allow adding child on click
            rec.setOnMouseClicked(event -> {
                choice = rec;
                choice.setLayoutX(rec.getLayoutX());
                choice.setLayoutY(rec.getLayoutY());
            });

            nodes.add(rec);
        }
    }

    private Line createConnectingLineChild(Rectangle startRect, Rectangle endRect) {
        Line line = createLine(startRect, endRect);
        line.startYProperty().bind(startRect.layoutYProperty().add(startRect.heightProperty()));
        line.endYProperty().bind(endRect.layoutYProperty());
        return line;
    }

    private Line createConnectingLineWife(Rectangle startRect, Rectangle endRect) {
        Line line = createLine(startRect, endRect);
        line.startXProperty().bind(startRect.layoutXProperty().add(startRect.widthProperty()));
        line.startYProperty().bind(startRect.layoutYProperty().add(startRect.heightProperty().divide(2)));
        line.endXProperty().bind(endRect.layoutXProperty());
        line.endYProperty().bind(endRect.layoutYProperty().add(startRect.heightProperty().divide(2)));
        return line;
    }

    private Line createLine(Rectangle startRect, Rectangle endRect) {
        Line line = new Line();
        line.startXProperty().bind(startRect.layoutXProperty().add(startRect.widthProperty().divide(2)));
        line.startYProperty().bind(startRect.layoutYProperty().add(startRect.heightProperty().divide(2)));
        line.endXProperty().bind(endRect.layoutXProperty().add(endRect.widthProperty().divide(2)));
        line.endYProperty().bind(endRect.layoutYProperty().add(endRect.heightProperty().divide(2)));
        return line;
    }

    private Rectangle createRectangle(double x, double y) {
        Rectangle rec = new Rectangle();
        rec.setHeight(125);
        rec.setWidth(125);
        rec.setFill(Color.RED);
        rec.setLayoutX(x);
        rec.setLayoutY(y);
        return rec;
    }
}
