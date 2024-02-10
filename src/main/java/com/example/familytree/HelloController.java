package com.example.familytree;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class HelloController {

    @FXML
    AnchorPane page;

    // to be use the draggable methods and line connecting methods
    static DraggableMaker draggableMaker = new DraggableMaker();
    LineConnecting lineConnecting = new LineConnecting();

    // variable for if you want to stop the view data from updating
    static Boolean isViewEnabled = true;

    // Last Clicked Rectangle
    Label choice;

    // Data of TextBox
    @FXML
    TextField textBox;

    @FXML
    DatePicker dateBox;


    @FXML
    Label dataLabel;


    // Radio buttons Handling
    @FXML
    RadioButton radioMale;
    @FXML
    RadioButton radioFemale;

    @FXML
    private void handleRadioButton1Action() {
        if (radioMale.isSelected()) {
            radioFemale.setSelected(false);
        }
    }
    @FXML
    private void handleRadioButton2Action() {
        if (radioFemale.isSelected()) {
            radioMale.setSelected(false);
        }
    }

    //first method to start  putting the first Person
    @FXML
    public void initialize() {
        dateBox.setEditable(false);
        try {
            DatabaseConnector.connect(); //connect to database
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void load() throws SQLException {
        reBuilder(null);
    }

    public void restart() throws SQLException{
        DatabaseConnector.restartSQL();
        page.getChildren().clear();
        Person.numberOfPerson = 0;
        Person.List = new ArrayList<>();
    }

    public Label addRoot() throws SQLException {
        Person person = new Person(DatabaseConnector.getName(0),DatabaseConnector.getBirthDay(0),DatabaseConnector.getGender(0));
        Label label = person.createRectangle(200,200);

        page.getChildren().add(label);
        draggableMaker.makeDraggable(label);
        dateBox.setEditable(false);

        person.setFatherID(-1);
        choice = label;

        // Add a click event handler to allow adding child on click
        label.setOnMouseClicked(event -> {
            if (isViewEnabled) {
                ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                ((Rectangle) choice.getUserData()).setStrokeWidth(0);
                choice = label;
                ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                ((Rectangle) choice.getUserData()).setStrokeWidth(3);
                dataLabel.setText(dataViewer(choice));

                addChildButton.setDisable(((Person) choice.getLabelFor()).gender.equals("Female") );
                if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male") && ((Person) choice.getLabelFor()).partner == null) {
                    addWifeButton.setText("Add Wife");
                    addWifeButton.setDisable(false);
                    addChildButton.setDisable(true);
                    radioMale.setSelected(false);
                    radioFemale.setSelected(false);
                    radioMale.setDisable(true);
                    radioFemale.setDisable(true);
                } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")) {
                    addWifeButton.setText("Married");
                    addWifeButton.setDisable(true);
                    addChildButton.setDisable(false);
                    radioMale.setDisable(false);
                    radioFemale.setDisable(false);
                }
            }
        });
        return label;
    }

    public Label addChildRebilder(Label parent ,Person child , int index) {

            Label label = child.createRectangle(parent.getLayoutX() + 150 * index, parent.getLayoutY()+200);
            page.getChildren().add(label);
            draggableMaker.makeDraggableChildren(parent , label);

            Line connectingLine = lineConnecting.createConnectingLineChild(parent, label);
            page.getChildren().add(connectingLine);

            Person father = (Person) parent.getLabelFor();
            father.addChild(child);
            child.setFatherID(father.getPersonId());

            textBox.clear();
            dateBox.setValue(null);
            // Add a click event handler to allow adding child on click
            label.setOnMouseClicked(event -> {
                if (isViewEnabled){
                    ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                    ((Rectangle) choice.getUserData()).setStrokeWidth(0);
                    choice = label;
                    ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                    ((Rectangle) choice.getUserData()).setStrokeWidth(3);
                    dataLabel.setText(dataViewer(choice));
                    if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male") && ((Person) choice.getLabelFor()).partner == null) {
                        addWifeButton.setText("Add Wife");
                        addWifeButton.setDisable(false);
                        addChildButton.setDisable(true);
                        radioMale.setSelected(false);
                        radioFemale.setSelected(false);
                        radioMale.setDisable(true);
                        radioFemale.setDisable(true);
                    } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")) {
                        addWifeButton.setText("Married");
                        addChildButton.setDisable(false);
                        radioMale.setDisable(false);
                        radioFemale.setDisable(false);
                    }
                    if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female") && ((Person) choice.getLabelFor()).partner == null) {
                        addWifeButton.setText("Add Husband");
                        addWifeButton.setDisable(false);
                        addChildButton.setDisable(true);
                        radioMale.setSelected(false);
                        radioFemale.setSelected(false);
                        radioMale.setDisable(true);
                        radioFemale.setDisable(true);
                    } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female")) {
                        addWifeButton.setText("Married");
                        addWifeButton.setDisable(true);
                        addChildButton.setDisable(true);
                    }
                }
            });
            return label;
    }
    public Label addWifeRebulider(Label label ,Person wife) {
        Label rec = wife.createRectangle(label.getLayoutX() + 200, label.getLayoutY());
        page.getChildren().add(rec);
        draggableMaker.makeDraggableWife(label , rec);

        Line connectingLine = lineConnecting.createConnectingLineWife(label, rec);
        page.getChildren().add(connectingLine);

        wife.setFatherID(-1);
        Person partner = (Person) label.getLabelFor();
        wife.setPartner(partner);

        if (Objects.equals(((Person) label.getLabelFor()).gender, "Female")){
            ((Rectangle) rec.getUserData()).setFill(Color.CORNFLOWERBLUE);
            ((Rectangle) label.getUserData()).setFill(Color.DEEPPINK);
        } else if (Objects.equals(((Person) label.getLabelFor()).gender, "Male")) {
            ((Rectangle) label.getUserData()).setFill(Color.CORNFLOWERBLUE);
            ((Rectangle) rec.getUserData()).setFill(Color.DEEPPINK

            );
        }
        textBox.clear();
        dateBox.setValue(null);
        // Add a click event handler to allow adding child on click
        rec.setOnMouseClicked(event -> {
            if (isViewEnabled) {
                ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                ((Rectangle) choice.getUserData()).setStrokeWidth(0);
                choice = rec;
                ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                ((Rectangle) choice.getUserData()).setStrokeWidth(3);

                dataLabel.setText(dataViewer(choice));
                if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")) {
                    addChildButton.setDisable(false);
                    addWifeButton.setText("Married");
                    addWifeButton.setDisable(true);
                    radioMale.setDisable(false);
                    radioFemale.setDisable(false);
                }
                if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female")) {
                    addChildButton.setDisable(true);
                    addWifeButton.setText("Married");
                    addWifeButton.setDisable(true);
                    radioMale.setSelected(false);
                    radioFemale.setSelected(false);
                    radioMale.setDisable(true);
                    radioFemale.setDisable(true);
                }
            }
        });
        return rec;
    }


    //adding Children
    public void addChild() throws SQLException {
        String gender = genderReader();
        LocalDate birthDate = dateBox.getValue();
        if (choice != null) {
            if (!Objects.equals(gender, "") && birthDate != null) {
                Person child = new Person(textBox.getText(), birthDate, gender);

                //insert child into person table
                DatabaseConnector.connect();
                DatabaseConnector.insertPerson(child);

                //insert a father and child into relationships tables
                DatabaseConnector.insertRelationship(((Person) choice.getLabelFor()).getPersonId(), child.getPersonId());

                Label label = child.createRectangle(choice.getLayoutX(), choice.getLayoutY() + 200);
                page.getChildren().add(label);
                draggableMaker.makeDraggableChildren(choice, label);

                Line connectingLine = lineConnecting.createConnectingLineChild(choice, label);
                page.getChildren().add(connectingLine);

                Person father = (Person) choice.getLabelFor();
                father.addChild(child);
                child.setFatherID(father.getPersonId());

                textBox.clear();
                dateBox.setValue(null);
                // Add a click event handler to allow adding child on click
                label.setOnMouseClicked(event -> {
                    if (isViewEnabled) {
                        ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                        ((Rectangle) choice.getUserData()).setStrokeWidth(0);
                        choice = label;
                        ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                        ((Rectangle) choice.getUserData()).setStrokeWidth(3);
                        dataLabel.setText(dataViewer(choice));
                        if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male") && ((Person) choice.getLabelFor()).partner == null) {
                            addWifeButton.setText("Add Wife");
                            addWifeButton.setDisable(false);
                            addChildButton.setDisable(true);
                            radioMale.setSelected(false);
                            radioFemale.setSelected(false);
                            radioMale.setDisable(true);
                            radioFemale.setDisable(true);
                        } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")) {
                            addWifeButton.setText("Married");
                            addChildButton.setDisable(false);
                            radioMale.setDisable(false);
                            radioFemale.setDisable(false);
                        }
                        if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female") && ((Person) choice.getLabelFor()).partner == null) {
                            addWifeButton.setText("Add Husband");
                            addWifeButton.setDisable(false);
                            addChildButton.setDisable(true);
                            radioMale.setSelected(false);
                            radioFemale.setSelected(false);
                            radioMale.setDisable(true);
                            radioFemale.setDisable(true);
                        } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female")) {
                            addWifeButton.setText("Married");
                            addWifeButton.setDisable(true);
                            addChildButton.setDisable(true);
                        }
                    }
                });
            }
        }else if (!Objects.equals(gender, "") && birthDate != null){
            Person child = new Person(textBox.getText(), birthDate, gender);

            //insert child into person table
            DatabaseConnector.connect();
            DatabaseConnector.insertPerson(child);

            Label label = child.createRectangle(150, 150);
            draggableMaker.makeDraggable(label);
            page.getChildren().add(label);

            textBox.clear();
            dateBox.setValue(null);
            choice = label;
            // Add a click event handler to allow adding child on click
            label.setOnMouseClicked(event -> {
                if (isViewEnabled) {
                    ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                    ((Rectangle) choice.getUserData()).setStrokeWidth(0);
                    choice = label;
                    ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                    ((Rectangle) choice.getUserData()).setStrokeWidth(3);
                    dataLabel.setText(dataViewer(choice));
                    if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male") && ((Person) choice.getLabelFor()).partner == null) {
                        addWifeButton.setText("Add Wife");
                        addWifeButton.setDisable(false);
                        addChildButton.setDisable(true);
                        radioMale.setSelected(false);
                        radioFemale.setSelected(false);
                        radioMale.setDisable(true);
                        radioFemale.setDisable(true);
                    } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")) {
                        addWifeButton.setText("Married");
                        addChildButton.setDisable(false);
                        radioMale.setDisable(false);
                        radioFemale.setDisable(false);
                    }
                    if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female") && ((Person) choice.getLabelFor()).partner == null) {
                        addWifeButton.setText("Add Husband");
                        addWifeButton.setDisable(false);
                        addChildButton.setDisable(true);
                        radioMale.setSelected(false);
                        radioFemale.setSelected(false);
                        radioMale.setDisable(true);
                        radioFemale.setDisable(true);
                    } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female")) {
                        addWifeButton.setText("Married");
                        addWifeButton.setDisable(true);
                        addChildButton.setDisable(true);
                    }
                }
            });
        }



    }


    //adding Wife
    public void addWife() throws SQLException {
        String gender;
        if (choice != null && ((Person) choice.getLabelFor()).partner == null) {
            if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")){
                gender = "Female";
            } else {
                gender = "Male";
            }

            LocalDate birthDate = dateBox.getValue();
            Person wife = new Person(textBox.getText(),birthDate,gender);

            DatabaseConnector.connect();
            //insert wife into person table
            DatabaseConnector.insertPerson(wife);

            if (gender.equals("Male")){
                DatabaseConnector.insertMarriage(wife.getPersonId() ,((Person) choice.getLabelFor()).getPersonId());
            } else {
                DatabaseConnector.insertMarriage(((Person) choice.getLabelFor()).getPersonId(), wife.getPersonId());
            }
            //insert wife into marriage table

            Label rec = wife.createRectangle(choice.getLayoutX() + 200, choice.getLayoutY());
            page.getChildren().add(rec);
            draggableMaker.makeDraggableWife(choice , rec);

            Line connectingLine = lineConnecting.createConnectingLineWife(choice, rec);
            page.getChildren().add(connectingLine);

            wife.setFatherID(-1);
            Person partner = (Person) choice.getLabelFor();
            wife.setPartner(partner);

            if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female")){
                ((Rectangle) rec.getUserData()).setFill(Color.CORNFLOWERBLUE);
                ((Rectangle) choice.getUserData()).setFill(Color.DEEPPINK);
            } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")) {
                ((Rectangle) choice.getUserData()).setFill(Color.CORNFLOWERBLUE);
                ((Rectangle) rec.getUserData()).setFill(Color.DEEPPINK

                );
            }
            textBox.clear();
            dateBox.setValue(null);
            // Add a click event handler to allow adding child on click
            rec.setOnMouseClicked(event -> {
                if (isViewEnabled) {
                    ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                    ((Rectangle) choice.getUserData()).setStrokeWidth(0);
                    choice = rec;
                    ((Rectangle) choice.getUserData()).setStroke(Color.BLACK);
                    ((Rectangle) choice.getUserData()).setStrokeWidth(3);

                    dataLabel.setText(dataViewer(choice));
                    if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")) {
                        addChildButton.setDisable(false);
                        addWifeButton.setText("Married");
                        addWifeButton.setDisable(true);
                        radioMale.setDisable(false);
                        radioFemale.setDisable(false);
                    }
                    if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female")) {
                        addChildButton.setDisable(true);
                        addWifeButton.setText("Married");
                        addWifeButton.setDisable(true);
                        radioMale.setSelected(false);
                        radioFemale.setSelected(false);
                        radioMale.setDisable(true);
                        radioFemale.setDisable(true);
                    }
                }
            });
        }
    }


    // data view window
    public static String dataViewer(Label choice){
        Person personChoice = (Person) choice.getLabelFor();
        String gender = personChoice.getGender();
        String sb;
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
        if (Objects.equals(personChoice.gender, "Male") && personChoice.children.size() >= 1){
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
    static public boolean waitingForConfirmation;
    static public Label updatingChoice;

    public void updateName() throws SQLException {
        if (!waitingForConfirmation) {
            updatingChoice = choice;
            // First click action
            Person person = (Person) updatingChoice.getLabelFor();

            waitingForConfirmation = true;

            isViewEnabled = false;
            textBox.setText(person.getName());
            dateBox.setValue(person.localdateBirth);
            editButton.setText("confirm?");
            addChildButton.setDisable(true);
            addWifeButton.setDisable(true);
            radioMale.setDisable(true);
            radioFemale.setDisable(true);
        } else {
            // Second click action (confirmation)
            Person person = (Person) updatingChoice.getLabelFor();
            waitingForConfirmation = false;
            isViewEnabled = true;

            person.setName(textBox.getText());
            person.setBirthday(dateBox.getValue());
            DatabaseConnector.updateDataFromID(person.personId , textBox.getText() , dateBox.getValue());
            editButton.setText("Edit");


            choice = updatingChoice;
            radioMale.setSelected(false);
            radioFemale.setSelected(false);
            radioMale.setDisable(true);
            radioFemale.setDisable(true);
            if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male") && ((Person) choice.getLabelFor()).partner == null) {
                addWifeButton.setText("Add Wife");
                addWifeButton.setDisable(false);
                addChildButton.setDisable(true);
            } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Male")) {
                addWifeButton.setText("Married");
                addChildButton.setDisable(false);
            }
            if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female") && ((Person) choice.getLabelFor()).partner == null) {
                addWifeButton.setText("Add Husband");
                addWifeButton.setDisable(false);
                addChildButton.setDisable(true);
            } else if (Objects.equals(((Person) choice.getLabelFor()).gender, "Female")) {
                addWifeButton.setText("Married");
                addWifeButton.setDisable(true);
                addChildButton.setDisable(true);
            }
            textBox.clear();
            dateBox.setValue(null);
        }

    }

    public void reBuilder(Label caller) throws SQLException {
        Label root = new Label();
        if (caller == null) {
            root = addRoot();

            int partnerID = isMarried(root);

            if (partnerID >= 0){
                Person wife = new Person(DatabaseConnector.getName(partnerID),DatabaseConnector.getBirthDay(partnerID),DatabaseConnector.getGender(partnerID));
                addWifeRebulider(root ,wife);
                    ArrayList<Integer> list = DatabaseConnector.getChildren(((Person) root.getLabelFor()).personId);
                    if (list.size() > 0){
                        for (int i = 0; i < list.size(); i++) {
                            Person child = new Person(DatabaseConnector.getName(list.get(i)),DatabaseConnector.getBirthDay(list.get(i)),DatabaseConnector.getGender(list.get(i)));
                            Label labelChild = addChildRebilder(root, child , i);
                            if (isMarried(labelChild) >= 0){
                                reBuilder(labelChild);
                            }
                        }

                }
            }

        } else {
            int partnerID = isMarried(caller);

            if (partnerID >= 0) {
                Person partner = new Person(DatabaseConnector.getName(partnerID), DatabaseConnector.getBirthDay(partnerID), DatabaseConnector.getGender(partnerID));
                Label labelPartner = addWifeRebulider(caller, partner);
                if (Objects.equals(partner.gender, "Male")) {
                    ArrayList<Integer> listPartner = DatabaseConnector.getChildren(partner.personId);
                    System.out.println(listPartner.size());
                    if (listPartner.size() > 0) {
                        for (int i = 0; i < listPartner.size(); i++) {
                            Person child = new Person(DatabaseConnector.getName(listPartner.get(i)), DatabaseConnector.getBirthDay(listPartner.get(i)), DatabaseConnector.getGender(listPartner.get(i)));
                            Label labelChild = addChildRebilder(labelPartner, child, i);
                            if (isMarried(labelChild) >= 0) {
                                reBuilder(labelChild);
                            }
                        }
                    }
                }
                    ArrayList<Integer> list = DatabaseConnector.getChildren(((Person) caller.getLabelFor()).personId);
                    if (list.size() > 0) {
                        for (int i = 0; i < list.size(); i++) {
                            Person child = new Person(DatabaseConnector.getName(list.get(i)), DatabaseConnector.getBirthDay(list.get(i)), DatabaseConnector.getGender(list.get(i)));
                            Label labelChild = addChildRebilder(caller, child , i);
                            if (isMarried(labelChild) >= 0) {
                                reBuilder(labelChild);
                            }
                        }
                    }

                }
            }


        }


    public int isMarried(Label root) throws SQLException {
        int partnerID = -1;
        if (Objects.equals(((Person) root.getLabelFor()).gender, "Male")){
            partnerID = DatabaseConnector.getWife(((Person) root.getLabelFor()).personId);
        } else {
            partnerID = DatabaseConnector.getHusband(((Person) root.getLabelFor()).personId);
        }
        return partnerID;
    }

}
