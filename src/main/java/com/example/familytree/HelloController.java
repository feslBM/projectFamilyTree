package com.example.familytree;

import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class HelloController {

    @FXML
    private AnchorPane page;

    // to be use the draggable methods
    DraggableMaker draggableMaker = new DraggableMaker();

    // Last Clicked Rectangle
    Label choice;

    // the Size of the  rectangle
    static int width = 100;
    static int height = 100;



    // Radio buttons Handling
    @FXML
    private RadioButton radioButton1;
    @FXML
    private RadioButton radioButton2;;

    @FXML
    private void handleRadioButton1Action(ActionEvent event) {
        if (radioButton1.isSelected()) {
            radioButton2.setSelected(false);
        }
    }
    @FXML
    private void handleRadioButton2Action(ActionEvent event) {
        if (radioButton2.isSelected()) {
            radioButton1.setSelected(false);
        }
    }

    //first method to start  putting the first Person
    @FXML
    public void initialize(){
        Person person = new Person("ME",21,true);

        Label label1 = person.createRectangle(200,200);

        page.getChildren().add(label1);
        draggableMaker.makeDraggable(label1);

        // Add a click event handler to allow adding child on click
        label1.setOnMouseClicked(event -> {
            choice = label1;
        });
    }

    //adding Children
    public void addRecChild() {
        if (choice != null) {
            Person wife = new Person("rfef",15,true);

            Label label = wife.createRectangle(choice.getLayoutX(), choice.getLayoutY()+200);
            page.getChildren().add(label);
            draggableMaker.makeDraggableChildren(choice , label);

            Line connectingLine = createConnectingLineChild(choice, label);
            page.getChildren().add(connectingLine);

            Person father = (Person) choice.getLabelFor();
            father.addChild(wife);

            // Add a click event handler to allow adding child on click
            label.setOnMouseClicked(event -> {
                choice = label;
                choice.setLayoutX(label.getLayoutX());
                choice.setLayoutY(label.getLayoutY());
            });
        }
    }

    //adding Wife
    public void addRecWife() {
        if (choice != null) {
            Person wife = new Person("rfef",15,false);
            Label rec = wife.createRectangle(choice.getLayoutX() + 200, choice.getLayoutY());
            page.getChildren().add(rec);
            draggableMaker.makeDraggableWife(choice , rec);

            Line connectingLine = createConnectingLineWife(choice, rec);
            page.getChildren().add(connectingLine);

            Person husband = (Person) choice.getLabelFor();
            wife.setPartner(husband);

            // Add a click event handler to allow adding child on click
            rec.setOnMouseClicked(event -> {
                choice = rec;
            });
        }
    }


    //************ line connecting method one for child and other for wife ****************//
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


    // testing methods
    public void printData(){
//        Person a = (Person) choice.getLabelFor();
//        System.out.println(a);
//        System.out.println(Person.printRelationship());

        if (radioButton1.isSelected()){
            System.out.println("Male");
        }else if (radioButton2.isSelected()){
            System.out.println("Female");
        }else {
            System.out.println("Other");
        }
    }

    public void updateName(){
        Person person = (Person) choice.getLabelFor();
        System.out.println(Person.List.get(person.ID).name.getValue());
        System.out.println(choice.getText());
        choice.setText("Hani");
        System.out.println(choice.getText());
        System.out.println(Person.List.get(person.ID).name.getValue());

    }
}
