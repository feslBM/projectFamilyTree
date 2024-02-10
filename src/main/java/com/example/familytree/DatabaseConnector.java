package com.example.familytree;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class DatabaseConnector {
    // JDBC URL, username, and password of MySQL server
    private static final String URL = "jdbc:mysql://localhost:3306/familytreedb";
    private static final String USER = "root";

    /*
    *
    *
    * Every one must set the password to the one that they use in mysql database
    *
    *
    * */
    private static final String PASSWORD = "1234";

    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    // Connect to the database
    public static void connect() throws SQLException {
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Disconnect from the database
    public static void disconnect() throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }

    // Insert a new person into the Person table
    public static void insertPerson(Person person) throws SQLException {
        String query = "INSERT INTO Person (person_id, name, gender,birthday) VALUES (?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, person.getPersonId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getGender());
        preparedStatement.setDate(4, Date.valueOf(person.localdateBirth));
        preparedStatement.executeUpdate();
    }

    // Insert a relationship into the Relationships table
    public static void insertRelationship(int childId, int fatherId) throws SQLException {
        String query = "INSERT INTO Relationships (children_id, father_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, fatherId);
        preparedStatement.setInt(2, childId);
        preparedStatement.executeUpdate();
    }

    // Insert a marriage into the Marriage table
    public static void insertMarriage(int husbandId, int wifeId) throws SQLException {
        String query = "INSERT INTO Marriage (husband_id, wife_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, husbandId);
        preparedStatement.setInt(2, wifeId);
        preparedStatement.executeUpdate();
    }
    public static void restartSQL() throws SQLException{
        // delete all records from table Relations
        String queryRelationships = "DELETE FROM Relationships";
        PreparedStatement preparedStatementRelations = connection.prepareStatement(queryRelationships);
        preparedStatementRelations.executeUpdate();
        // delete all records from table Marriage
        String queryMarriage = "DELETE FROM marriage";
        PreparedStatement preparedStatementMarriage = connection.prepareStatement(queryMarriage);
        preparedStatementMarriage.executeUpdate();
        // delete all records from table Person
        String queryPerson = "DELETE FROM person";
        PreparedStatement preparedStatementPerson = connection.prepareStatement(queryPerson);
        preparedStatementPerson.executeUpdate();
    }

    // selectors methods SQL
    public static String getName(int ID) throws SQLException {
        String query = "SELECT NAME FROM PERSON WHERE PERSON_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        String name = null;
        if (resultSet.next()) {
            name = resultSet.getString("NAME");
        }
        return name;
    }
    public static String getGender(int ID) throws SQLException {
        String query = "SELECT Gender FROM PERSON WHERE PERSON_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        String gender = null;
        if (resultSet.next()) {
            gender = resultSet.getString("gender");
        }
        return gender;
    }
    public static LocalDate getBirthDay(int ID) throws SQLException {
        String query = "SELECT birthday FROM PERSON WHERE PERSON_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        Date date = null;
        if (resultSet.next()) {
            date = resultSet.getDate("birthday");
        }
        assert date != null;
        return date.toLocalDate();
    }

    public static ArrayList<Integer> getChildren(int ID) throws SQLException {
        ArrayList<Integer> childrenIDs = new ArrayList<>();

        String query = "SELECT children_id FROM relationships WHERE father_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, ID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int childID = resultSet.getInt("children_id");
                childrenIDs.add(childID);
            }
        }

        return childrenIDs;

    }

    public static int getWife(int ID) throws SQLException {
        String query = "SELECT wife_id FROM marriage where husband_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        int wifeID = -1;
        if (resultSet.next()) {
            wifeID = resultSet.getInt("wife_id");
        }
        return wifeID;
    }

    public static int getHusband(int ID) throws SQLException {
        String query = "SELECT husband_id FROM marriage where wife_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, ID);
        ResultSet resultSet = preparedStatement.executeQuery();
        int husband_id = -1;
        if (resultSet.next()) {
            husband_id = resultSet.getInt("husband_id");
        }
        return husband_id;
    }

    public static void updateDataFromID(int ID, String name, LocalDate localDate) throws SQLException {
        // Corrected query placeholders
        String query = "UPDATE person SET name = ?, birthday = ? WHERE person_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set parameters
        preparedStatement.setString(1, name);
        preparedStatement.setDate(2, java.sql.Date.valueOf(localDate)); // Ensure correct import for java.sql.Date
        preparedStatement.setInt(3, ID);

        // Execute update
        preparedStatement.executeUpdate(); // Correct method for update operation
    }

}
