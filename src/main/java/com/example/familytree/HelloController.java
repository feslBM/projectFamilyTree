package com.example.familytree;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.time.LocalDate;

public class HelloController {

    @FXML
    private AnchorPane page;

    // to be use the draggable methods and line connecting methods
    DraggableMaker draggableMaker = new DraggableMaker();
    LineConnecting lineConnecting = new LineConnecting();

    // variable for if you want to stop the view data from updating
    static Boolean isViewEnabled = true;

    // Last Clicked Rectangle
    Label choice;

    // Data of TextBox
    @FXML
    private TextField textBox;

    @FXML
    private DatePicker dateBox;


    @FXML
    private Label dataLabel;


    // Radio buttons Handling
    @FXML
    private RadioButton radioMale;
    @FXML
    private RadioButton radioFemale;;

    @FXML
    private void handleRadioButton1Action(ActionEvent event) {
        if (radioMale.isSelected()) {
            radioFemale.setSelected(false);
        }
    }
    @FXML
    private void handleRadioButton2Action(ActionEvent event) {
        if (radioFemale.isSelected()) {
            radioMale.setSelected(false);
        }
    }

    //first method to start  putting the first Person
    @FXML
    public void initialize(){
        addChildButton.setDisable(true);
        addWifeButton.setDisable(true);
        LocalDate currentDate = LocalDate.now();

        Person person = new Person("ME",currentDate,"Male");

        Label label = person.createRectangle(200,200);

        page.getChildren().add(label);
        draggableMaker.makeDraggable(label);
        dateBox.setEditable(false);

        person.setFatherID(-1);

        // Add a click event handler to allow adding child on click
        label.setOnMouseClicked(event -> {
            choice = label;

            if (isViewEnabled) {
                dataLabel.setText(dataViewer(choice));

                addChildButton.setDisable(((Person) choice.getLabelFor()).gender == "Female");
                if (((Person) choice.getLabelFor()).gender == "Male" && ((Person) choice.getLabelFor()).partner == null) {
                    addWifeButton.setText("Add Wife");
                    addWifeButton.setDisable(false);
                    addChildButton.setDisable(true);
                } else if (((Person) choice.getLabelFor()).gender == "Male") {
                    addWifeButton.setText("Married");
                    addWifeButton.setDisable(true);
                    addChildButton.setDisable(false);
                }
            }
        });
    }

    //adding Children
    public void addRecChild(ActionEvent e) {
        String gender = genderReader();
        if (choice != null && gender != ""){
            LocalDate birthDate = dateBox.getValue();
            Person child = new Person(textBox.getText(),birthDate,gender);

            Label label = child.createRectangle(choice.getLayoutX(), choice.getLayoutY()+200);
            page.getChildren().add(label);
            draggableMaker.makeDraggableChildren(choice , label);

            Line connectingLine = lineConnecting.createConnectingLineChild(choice, label);
            page.getChildren().add(connectingLine);

            Person father = (Person) choice.getLabelFor();
            father.addChild(child);
            child.setFatherID(father.getID());

            textBox.clear();
            dateBox.setValue(null);
            // Add a click event handler to allow adding child on click
            label.setOnMouseClicked(event -> {
                choice = label;
                choice.setLayoutX(label.getLayoutX());
                choice.setLayoutY(label.getLayoutY());

                if (isViewEnabled){
                    dataLabel.setText(dataViewer(choice));
                    if (((Person) choice.getLabelFor()).gender == "Male" && ((Person) choice.getLabelFor()).partner == null) {
                        addWifeButton.setText("Add Wife");
                        addWifeButton.setDisable(false);
                        addChildButton.setDisable(true);
                    } else if (((Person) choice.getLabelFor()).gender == "Male") {
                        addWifeButton.setText("Married");
                        addChildButton.setDisable(false);
                    }
                    if (((Person) choice.getLabelFor()).gender == "Female" && ((Person) choice.getLabelFor()).partner == null) {
                        addWifeButton.setText("Add Husband");
                        addWifeButton.setDisable(false);
                        addChildButton.setDisable(true);
                    } else if (((Person) choice.getLabelFor()).gender == "Female") {
                        addWifeButton.setText("Married");
                        addWifeButton.setDisable(true);
                        addChildButton.setDisable(true);
                    }
                }
            });
        }
    }

    //adding Wife
    public void addRecPartner() {
        String gender;
        if (choice != null && ((Person) choice.getLabelFor()).partner == null) {
            if (((Person) choice.getLabelFor()).gender == "Male"){
                gender = "Female";
            } else {
                gender = "Male";
            }
            LocalDate birthDate = dateBox.getValue();
            Person wife = new Person(textBox.getText(),birthDate,gender);
            Label rec = wife.createRectangle(choice.getLayoutX() + 200, choice.getLayoutY());
            page.getChildren().add(rec);
            draggableMaker.makeDraggableWife(choice , rec);

            Line connectingLine = lineConnecting.createConnectingLineWife(choice, rec);
            page.getChildren().add(connectingLine);

            wife.setFatherID(-1);
            Person partner = (Person) choice.getLabelFor();
            wife.setPartner(partner);

            if (((Person) choice.getLabelFor()).gender == "Female"){
                ((Rectangle) rec.getUserData()).setFill(Color.CORNFLOWERBLUE);
                ((Rectangle) choice.getUserData()).setFill(Color.DEEPPINK);
            } else if (((Person) choice.getLabelFor()).gender == "Male") {
                ((Rectangle) choice.getUserData()).setFill(Color.CORNFLOWERBLUE);
                ((Rectangle) rec.getUserData()).setFill(Color.DEEPPINK

                );
            }

            textBox.clear();
            dateBox.setValue(null);
            // Add a click event handler to allow adding child on click
            rec.setOnMouseClicked(event -> {
                choice = rec;

                if (isViewEnabled) {
                    dataLabel.setText(dataViewer(choice));
                    if (((Person) choice.getLabelFor()).gender == "Male") {
                        addChildButton.setDisable(false);
                        addWifeButton.setText("Married");
                        addWifeButton.setDisable(true);
                    }
                    if (((Person) choice.getLabelFor()).gender == "Female") {
                        addChildButton.setDisable(true);
                        addWifeButton.setText("Married");
                        addWifeButton.setDisable(true);
                    }
                }
            });
        }
    }


    // data view window
    public static String dataViewer(Label choice){
        Person personChoice = (Person) choice.getLabelFor();
        String gender = personChoice.getGender();
        String sb = "";
        sb = "Name: " + personChoice.name.getValue() + "\n"
                + "age: "  + personChoice.getAge() + "\n"
                + "date Of Birth: " + personChoice.localdateBirth.toString() + "\n"
                + "gender: " + gender + "\n";
        if (personChoice.fatherID != -1){
            sb = sb + "Father Name: " + Person.List.get(personChoice.fatherID).name.getValue() +"\n";
        }
        if (personChoice.partner != null){
            sb = sb + "Partner: " + personChoice.partner.name.getValue() + "\n";
        }
        if (personChoice.gender == "Male" && personChoice.children.size() >= 1){
            sb = sb + "Children: \n" + personChoice.getChildren();
        }
        return sb;
    }

    public String genderReader(){
        String gender = "";
        if (radioMale.isSelected()){
            gender = "Male";
        }else if (radioFemale.isSelected()){
            gender = "Female";
        }
        return gender;
    }


    //************ Updating data for selected node ****************//

    @FXML
    Button editButton;
    @FXML
    Button addChildButton;
    @FXML
    Button addWifeButton;
    public boolean waitingForConfirmation;
    public Person updatingChoice;

    public void updateName(){
        if (!waitingForConfirmation) {
            // First click action
            updatingChoice = (Person) choice.getLabelFor();

            System.out.println("Please click again to confirm");
            waitingForConfirmation = true;

            isViewEnabled = false;
            textBox.setText(updatingChoice.getName());
            dateBox.setValue(updatingChoice.localdateBirth);
            editButton.setText("confirm?");
            addChildButton.setDisable(true);
            addWifeButton.setDisable(true);
            radioMale.setDisable(true);
            radioFemale.setDisable(true);
        } else {
            // Second click action (confirmation)
            System.out.println("Action confirmed");
            waitingForConfirmation = false;
            isViewEnabled = true;

            updatingChoice.setName(textBox.getText());
            updatingChoice.setBirthday(dateBox.getValue());

            editButton.setText("Edit");
            radioMale.setDisable(false);
            radioFemale.setDisable(false);

        }
    }
}
