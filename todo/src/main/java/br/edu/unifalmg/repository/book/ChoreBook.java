package br.edu.unifalmg.repository.book;

public class ChoreBook {
    public static final  String FIND_ALL_CHORES = "SELECT * FROM db2022108012.chore";

    public static final String INSERT_CHORE = "INSERT INTO db.chore (`description`, `isCompleted`, `deadline`) VALUES (?,?,?)";

    public static final String UPDATE_CHORE = "UPDATE db.chore SET" +
            " `description` = ?, `deadline` = ? WHERE db.chore.choreID = ?";
}
