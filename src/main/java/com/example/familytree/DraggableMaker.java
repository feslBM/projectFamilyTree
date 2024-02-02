package com.example.familytree;


import javafx.scene.Node;
import javafx.scene.control.Label;

public class DraggableMaker {
    private double mouseAnchorX;
    private double mouseAnchorY;

    public void makeDraggable(Label node) {
        node.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX() + 200;
            mouseAnchorY = mouseEvent.getY();
        });

        node.setOnMouseDragged(mouseEvent -> {
            double newX = mouseEvent.getSceneX() - mouseAnchorX;
            newX = Math.max(newX, 0);
            node.setLayoutX(newX);
            node.setLayoutY(mouseEvent.getSceneY() - mouseAnchorY);
        });

    }

    public void makeDraggableChildren(Label parent, Label child){
            child.setOnMousePressed(mouseEvent -> {
                mouseAnchorX = mouseEvent.getX() +200 ;
                mouseAnchorY = mouseEvent.getY();
            });

            child.setOnMouseDragged(mouseEvent -> {
                double newY = mouseEvent.getSceneY() - mouseAnchorY;
                double newX = mouseEvent.getSceneX() - mouseAnchorX;
                newY = Math.max(newY, parent.getLayoutY() + parent.getHeight());
                newX = Math.max(newX, 0);

                child.setLayoutX(newX);
                child.setLayoutY(newY);
            });
    }

    public void makeDraggableWife(Label parent, Label wife) {
        wife.setOnMousePressed(mouseEvent -> {
            mouseAnchorX = mouseEvent.getX()+200;
            mouseAnchorY = mouseEvent.getY();
        });

        wife.setOnMouseDragged(mouseEvent -> {
            double newLayoutX = mouseEvent.getSceneX() - mouseAnchorX;
            double newLayoutY = mouseEvent.getSceneY() - mouseAnchorY;
            newLayoutX = Math.max(newLayoutX, parent.getLayoutX() + 150);
            wife.setLayoutX(newLayoutX);
            wife.setLayoutY(newLayoutY);
        });
    }

}
