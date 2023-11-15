package br.edu.unifalmg.repository.impl;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.repository.ChoreRepository;
import br.edu.unifalmg.repository.book.ChoreBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLChoreRepository implements ChoreRepository {

    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    @Override
    public List<Chore> load() {
        if(!connectToMySQL()){
            return new ArrayList<>();
        }
        try{
            statement = connection.createStatement();

            resultSet = statement.executeQuery(ChoreBook.FIND_ALL_CHORES);

            List<Chore> chores = new ArrayList<>();
            while(resultSet.next()) {
                Chore chore = Chore.builder()
                                .description(resultSet.getString("description"))
                                .isCompleted(resultSet.getBoolean("isCompleted"))
                                .deadline(resultSet.getDate("deadline").toLocalDate())
                                .id(resultSet.getLong("id"))
                                .build();
                chores.add(chore);
            }
            return chores;
        }catch (SQLException exception){
            System.out.println("Error when connecting to database.");
        }
        return null;
    }

    @Override
    public boolean save(List<Chore> chores) {
        return false;
    }

    private boolean connectToMySQL(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.1.254:3306/db2022108012?" +"user=user2022108012&password=2022108012");
            return Boolean.TRUE;
        }catch (ClassNotFoundException | SQLException exception){
            System.out.println("Error When connecting to database. Try again later");
        }
        return Boolean.FALSE;
    }

    private void closeConnections(){
        try {
            connection.close();
            statement.close();
            preparedStatement.close();
            resultSet.close();
        }catch(SQLException exception){
            System.out.println("Error when closing database connections.");
        } finally {
            closeConnections();
        }
    }

    @Override
    public boolean update(Chore chore) {
        if (!connectToMySQL()) {
            return Boolean.FALSE;
        }

        try {
            preparedStatement = connection.prepareStatement(
                    "UPDATE db.chore SET description = ?, isCompleted = ?, deadline = ? WHERE id = ?"
            );
            preparedStatement.setString(1, chore.getDescription());
            preparedStatement.setBoolean(2, chore.getIsCompleted());
            preparedStatement.setDate(3, Date.valueOf(chore.getDeadline()));
            preparedStatement.setLong(4, chore.getId());
            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows > 0) {
                return Boolean.TRUE;
            }

        } catch (SQLException exception) {
            System.out.println("Error when updating the chore");
        } finally {
            closeConnections();
        }

        return Boolean.FALSE;
    }

}
