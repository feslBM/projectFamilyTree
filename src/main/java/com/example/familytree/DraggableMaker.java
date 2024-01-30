package com.example.familytree;


import javafx.scene.Node;
import javafx.scene.control.Label;

public class DraggableMaker {
    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Node node) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            node.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

    }

    public void makeDraggableChildren(Label parent, Label child){
            child.setOnMousePressed(mouseEvent -> {
                mouseAnchorX = mouseEvent.getX();
                mouseAnchorY = mouseEvent.getY();
            });

            child.setOnMouseDragged(mouseEvent -> {
                double newY = mouseEvent.getSceneY() - mouseAnchorY;
                newY = Math.max(newY, parent.getLayoutY() + parent.getHeight());

                child.setLayoutX(mouseEvent.getSceneX() - mouseAnchorX);
                child.setLayoutY(newY);
            });
    }

    public void makeDraggableWife(Label parent, Label wife) {
        wife.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX();
            mouseAnchorY = mouseEvent.getY();
        });

        wife.setOnMouseDragged(mouseEvent -> {
            double newLayoutX = mouseEvent.getSceneX() - mouseAnchorX;
            double newLayoutY = mouseEvent.getSceneY() - mouseAnchorY;
            newLayoutX = Math.max(newLayoutX, parent.getLayoutX() + 200);
            wife.setLayoutX(newLayoutX);
            wife.setLayoutY(newLayoutY);
        });
    }

}
