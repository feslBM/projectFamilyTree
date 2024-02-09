package com.example.familytree;

import java.sql.*;

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
        String query = "INSERT INTO Person (person_id, name, gender) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, person.getPersonId());
        preparedStatement.setString(2, person.getName());
        preparedStatement.setString(3, person.getGender());
        preparedStatement.executeUpdate();
    }

    // Insert a relationship into the Relationships table
    public static void insertRelationship(int childId, int fatherId) throws SQLException {
        String query = "INSERT INTO Relationships (children_id, father_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, childId);
        preparedStatement.setInt(2, fatherId);
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
    public static void restart() throws SQLException{
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
}
