package com.example.familytree;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class HelloController {

    @FXML
    private AnchorPane page;

    DraggableMaker draggableMaker = new DraggableMaker();

    ArrayList<Rectangle> nodes = new ArrayList<>();

    Label choice;

    static int width = 100;
    static int height = 100;


    public void addPartner() {
    }

    public void addRecChild() {
        Label label;
        Node wife = new Node("rfef",15,Gender.MALE);
        if (choice != null) {


            label = wife.createRectangle(choice.getLayoutX(), choice.getLayoutY()+200);
            page.getChildren().add(label);
            draggableMaker.makeDraggableChildren(choice , label);

            Line connectingLine = createConnectingLineChild(choice, label);
            page.getChildren().add(connectingLine);


            // Add a click event handler to allow adding child on click
            label.setOnMouseClicked(event -> {
                choice = label;
                choice.setLayoutX(label.getLayoutX());
                choice.setLayoutY(label.getLayoutY());
            });

        }else {
            label = wife.createRectangle(100 , 100);
                page.getChildren().add(label);
                draggableMaker.makeDraggable(label);

                // Add a click event handler to allow adding child on click
                label.setOnMouseClicked(event -> {
                    choice = label;
                });
            }
    }

    public void addRecWife() {

//            Node wife = new Node("rfef",15,Gender.FEMALE);
//            Label rec = wife.createRectangle(choice.getLayoutX() + 200, choice.getLayoutY());
//        page.getChildren().add(rec);
//        draggableMaker.makeDraggableWife(choice , rec);
//
//        PartnerShip partnerShip = new PartnerShip(choice.getLabelFor() , wife)


        if (choice != null) {
            Node wife = new Node("rfef",15,Gender.FEMALE);
            Label rec = wife.createRectangle(choice.getLayoutX() + 200, choice.getLayoutY());
            page.getChildren().add(rec);
            draggableMaker.makeDraggableWife(choice , rec);

            Line connectingLine = createConnectingLineWife(choice, rec);
            page.getChildren().add(connectingLine);

            // Add a click event handler to allow adding child on click
            rec.setOnMouseClicked(event -> {
                choice = rec;
            });
        }
    }

    private Line createConnectingLineChild(Label startRect, Label endRect) {
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


    private Line createConnectingLineWife(Label startRect, Label endRect) {
        Line line = new Line();
        line.startXProperty().bind(startRect.layoutXProperty().add(startRect.widthProperty()));
        line.startYProperty().bind(startRect.layoutYProperty().add(startRect.heightProperty().divide(2)));
        line.endXProperty().bind(endRect.layoutXProperty());
        line.endYProperty().bind(endRect.layoutYProperty().add(startRect.heightProperty().divide(2)));
        return line;
    }


//    private Line createLine(Label startRect, Label endRect) {
//        Line line = new Line();
//        line.startXProperty().bind(startRect.layoutXProperty().add(startRect.widthProperty().divide(2)));
//        line.startYProperty().bind(startRect.layoutYProperty().add(startRect.heightProperty().divide(2)));
//        line.endXProperty().bind(endRect.layoutXProperty().add(endRect.widthProperty().divide(2)));
//        line.endYProperty().bind(endRect.layoutYProperty().add(endRect.heightProperty().divide(2)));
//        return line;
//    }


}
