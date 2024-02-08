package com.example.familytree;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class Person extends Node {

    //this the String type for JavaFX it used for binding
    public StringProperty name = new SimpleStringProperty(this, "name");
    public int age;
    public LocalDate localdateBirth;
    public String gender;
    public int ID;
    public ArrayList<Person> children;
    public Person partner;

    // not used yet
    public int fatherID;

    // List of all Person objects
    public static ArrayList<Person> List = new ArrayList<Person>();

    //counter for ID
    public static int numberOfPerson = 0;

    //size of the rectangle
    public static int height = 100;
    public static int width = 100;

    // making arrayList inside arrayList to make 2D arrayList
    public static ArrayList<ArrayList<Integer>> relations = Person.intitializeRelations();
    static ArrayList<Integer> relationRowMale = new ArrayList<>();
    static ArrayList<Integer> relationRowFemale = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> intitializeRelations() {
        ArrayList<ArrayList<Integer>> arrayListNew = new ArrayList<>();

        arrayListNew.add(relationRowMale);
        arrayListNew.add(relationRowFemale);

        return arrayListNew;
    }

    // constructor for Person object
    public Person(String name, LocalDate dateOfBirth, String gender) {
        this.name.setValue(name);
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        this.age = period.getYears();
        this.localdateBirth = dateOfBirth;
        this.gender = gender;
        ID = numberOfPerson++;
        if (gender == "Male"){
            children = new ArrayList<Person>();
        }
        this.partner = null;
        List.add(this);
    }

    // the method for making the rectangle and binding the name in the label with the object
    public Label createRectangle(double x, double y) {
        Rectangle rec = new Rectangle(width, height);

        new Label();
        Label label = new Label(this.name.getName(), rec);
        label.setUserData(rec);
        label.textProperty().bindBidirectional(this.name);

        label.setContentDisplay(ContentDisplay.CENTER);
        label.setLabelFor(this);

        if (this.gender == "Male") {
            rec.setFill(Color.LIGHTBLUE);
        } else {
            rec.setFill(Color.PINK);
        }
        label.setLayoutX(x);
        label.setLayoutY(y);

        return label;
    }

    //setting partner for Node  // may be SQL statements
    public void setPartner(Person partner) {
        this.partner = partner;
        partner.partner = this;

        // i want the males to be always in the left
        if (partner.gender == "Male"){
            relationRowMale.add(partner.ID);
            relationRowFemale.add(this.ID);
        }else{
            relationRowMale.add(this.ID);
            relationRowFemale.add(partner.ID);
        }
    }

    // Adding Child   //here may be some SQL statement
    public void addChild(Person child) {
        this.children.add(child);
    }




    //****************************************//setters and getters//****************************************//
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setName(String name) {
        this.name.set(name);
    }
    public void setBirthday(LocalDate date) {
        this.localdateBirth = date;
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(date, currentDate);
        this.age = period.getYears();
    }
    public void setFatherID(int fatherID) {
        this.fatherID = fatherID;
    }

    public String getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }
    public int getID(){
        return this.ID;
    }

    public String getName() {
        return name.get();
    }
    public int getFatherID() {
        return fatherID;
    }

    public String getChildren() {
        StringBuilder result = new StringBuilder();
        for (Person child : children){
            result.append(children.indexOf(child)+1 + "- " + child.name.getValue() + "\n");
        }
        return result.toString();
    }

    //***************************// Text testers methods to test the dataStructure //***************************//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append("Person{");
//        sb.append("name='").append(this.name.getValue()).append("', ");
//        sb.append("age=").append(age).append(", ");
//        sb.append("male=").append(gender).append(", ");
//        sb.append("ID=").append(ID);
//
//        if (this.gender && (this.partner != null) ){
//            sb.append(", children=");
//            sb.append(this.printChildren());
//        }
//
//        sb.append("}");
//        return sb.toString();
//    }


//
//    public static String printFamily(int familyID) {
//        StringBuilder sb = new StringBuilder();
//        for (Person person : List) {
//            if (person.ID == familyID) {
//                for (Person child : person.children) {
//                    sb.append(child.name);
//                }
//            }
//        }
//        return sb.toString();
//    }
//
//    public static String printRelationship(){
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < relationRowMale.size() ; i++) {
//            sb.append(relationRowMale.get(i) + " " + relationRowFemale.get(i) + "\n");
//        }
//        return sb.toString();
//    }



    //*********************************//    اسحب عليها



    // Data structure to save position of every Person
    //************************************************************************************************

//    public static ArrayList<ArrayList<Double>> position = Person.intitializePosition();
//    static ArrayList<Double> positionRowX = new ArrayList<>();
//    static ArrayList<Double> positionRowY = new ArrayList<>();
//
//    public static ArrayList<ArrayList<Double>> intitializePosition() {
//        ArrayList<ArrayList<Double>> arrayListNew = new ArrayList<>();
//
//        arrayListNew.add(positionRowX);
//        arrayListNew.add(positionRowY);
//
//        return arrayListNew;
//    }

//    public double getLayoutX() {
//        return positionRowX.get(this.ID);
//    }
//    public double getLayoutY() {
//        return positionRowY.get(this.ID);
//    }
//
//    public void setLayoutX(double layoutX) {
//        positionRowX.set(this.ID ,layoutX);
//    }
//    public void setLayoutY(double layoutY) {
//        positionRowY.set(this.ID ,layoutY);
//    }
}
