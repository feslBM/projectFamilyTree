package com.example.familytree;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;

public class HelloController {

    @FXML
    private AnchorPane page;

    DraggableMaker draggableMaker = new DraggableMaker();

    ArrayList<Node> nodes = new ArrayList<>();

    Node choice;

    public void selectChoice(){


    }


    public void addRecChild() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("node-view.fxml"));
        Node root = fxmlLoader.load();
        if (choice != null) {
            root.setLayoutX(choice.getLayoutX());
            root.setLayoutY(choice.getLayoutY() + 210);

            page.getChildren().add(root);
            draggableMaker.makeDraggable(root);

            Line connectingLine = createConnectingLineChild(choice, root);
            page.getChildren().add(connectingLine);


            // Add a click event handler to allow adding child on click

        } else{
            page.getChildren().add(root);
            draggableMaker.makeDraggable(root);
            root.setOnMouseClicked(event -> {
                choice = root;
                choice.setLayoutX(root.getLayoutX());
                choice.setLayoutY(root.getLayoutY());
            });
        }
        nodes.add(root);
    }

    public void addRecWife() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("node-view.fxml"));
        Node root = fxmlLoader.load();
        root.setLayoutX(100);
        root.setLayoutY(100);
        if (choice != null) {
            root = createRectangle(choice.getLayoutX() + 345 , choice.getLayoutY());
            page.getChildren().add(root);
            draggableMaker.makeDraggable(root);

            Line connectingLine = createConnectingLineChild(choice, root);
            page.getChildren().add(connectingLine);


            // Add a click event handler to allow adding child on click

        }
        nodes.add(root);
    }

    private Line createConnectingLineChild(Node startNode, Node endNode) {
        Line line = createLine(startNode, endNode);
        line.startYProperty().bind(startNode.layoutYProperty().add(160));
        line.endYProperty().bind(endNode.layoutYProperty());
        return line;
    }

    private Line createLine(Node startNode, Node endNode) {
        Line line = new Line();
        line.startXProperty().bind(startNode.layoutXProperty().add(147));
        line.startYProperty().bind(startNode.layoutYProperty().add(80));
        line.endXProperty().bind(endNode.layoutXProperty().add(147));
        line.endYProperty().bind(endNode.layoutYProperty().add(80));
        return line;
    }

    private Line createConnectingLineWife(Node startNode, Node endNode) {
        Line line = createLine(startNode, endNode);
        line.startXProperty().bind(Bindings.createDoubleBinding(() ->
                        startNode.localToScene(startNode.getBoundsInLocal()).getMaxX(),
                startNode.localToSceneTransformProperty()));
        line.startYProperty().bind(Bindings.createDoubleBinding(() ->
                        startNode.localToScene(startNode.getBoundsInLocal()).getMaxY() + startNode.getBoundsInLocal().getHeight() / 2,
                startNode.localToSceneTransformProperty()));
        line.endXProperty().bind(Bindings.createDoubleBinding(() ->
                        endNode.localToScene(endNode.getBoundsInLocal()).getMinX(),
                endNode.localToSceneTransformProperty()));
        line.endYProperty().bind(Bindings.createDoubleBinding(() ->
                        endNode.localToScene(endNode.getBoundsInLocal()).getMinY() + endNode.getBoundsInLocal().getHeight() / 2,
                endNode.localToSceneTransformProperty()));
        return line;
    }


//    private Line createLine(Node startNode, Node endNode) {
//        Line line = new Line();
//        line.startXProperty().bind(Bindings.createDoubleBinding(() ->
//                        startNode.localToScene(startNode.getBoundsInLocal()).getMaxX(),
//                startNode.localToSceneTransformProperty()));
//        line.startYProperty().bind(Bindings.createDoubleBinding(() ->
//                        startNode.localToScene(startNode.getBoundsInLocal()).getMaxY(),
//                startNode.localToSceneTransformProperty()));
//        line.endXProperty().bind(Bindings.createDoubleBinding(() ->
//                        endNode.localToScene(endNode.getBoundsInLocal()).getMinX(),
//                endNode.localToSceneTransformProperty()));
//        line.endYProperty().bind(Bindings.createDoubleBinding(() ->
//                        endNode.localToScene(endNode.getBoundsInLocal()).getMinY(),
//                endNode.localToSceneTransformProperty()));
//        return line;
//    }



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
