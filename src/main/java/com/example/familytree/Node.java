package com.example.familytree;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Node {

    String name;
    int age;
    Gender gender;

    PartnerShip parentShip;

    public int height = 100;
    public int width = 100;

    Node (String name, int age, Gender gender){
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public Label createRectangle(double x, double y) {
        Rectangle rec = new Rectangle(width, height);
        if (this.gender == Gender.MALE) {
            rec.setFill(Color.BLUE);
        } else {
            rec.setFill(Color.PINK);
        }

        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(rec, new Label(name));
        Label label = new Label("",stackPane);
        label.setLayoutX(x);
        label.setLayoutY(y);
        return label;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setParentShip(PartnerShip parentShip) {
        this.parentShip = parentShip;
    }

    public PartnerShip getParentShip() {
        return parentShip;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }


    public int getHeight() {
        return height;
    }
    public int getWidth() {
        return width;
    }
}
