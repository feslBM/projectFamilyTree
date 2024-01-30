package com.example.familytree;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class PartnerShip {

    public Node husband;
    public Node wife;
    public ArrayList<Node> children;




    PartnerShip (Node husband , Node wife){
        this.husband = husband;
        this.wife = wife;
        this.children = new ArrayList<Node>();
    }

    public Label createPartnerShip(double x, double y) {
        Rectangle rec = new Rectangle(HelloController.width / 2.0, HelloController.height / 2.0);


        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rec, new Label(""));
        Label label = new Label("",stackPane);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void addChild(Node child){
        children.add(child);
    }
}
