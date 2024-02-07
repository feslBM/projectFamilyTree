package com.example.familytree;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class LineConnecting {
    public Line createConnectingLineChild(Label startRect, Label endRect) {
        Line line = new Line();
        line.endXProperty().bind(endRect.layoutXProperty().add(endRect.widthProperty().divide(2)));
        line.startXProperty().bind(startRect.layoutXProperty().add(startRect.widthProperty().divide(2)));

        line.startYProperty().bind(startRect.layoutYProperty().add(startRect.heightProperty()));

        line.endYProperty().bind(Bindings.createDoubleBinding(() ->
                        Math.max(startRect.layoutYProperty().add(startRect.heightProperty()).get(),
                                endRect.layoutYProperty().get()),
                startRect.layoutYProperty(), startRect.heightProperty(),
                endRect.layoutYProperty()));

        return line;
    }
    public Line createConnectingLineWife(Label startRect, Label endRect) {
        Line line = new Line();
        line.startXProperty().bind(startRect.layoutXProperty().add(startRect.widthProperty()));
        line.startYProperty().bind(startRect.layoutYProperty().add(startRect.heightProperty().divide(2)));
        line.endXProperty().bind(endRect.layoutXProperty());
        line.endYProperty().bind(endRect.layoutYProperty().add(startRect.heightProperty().divide(2)));
        return line;
    }
}
