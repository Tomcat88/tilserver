package it.introini.tilserver.db.manager.user;

import it.introini.tilserver.db.DatabaseManager;
import it.introini.tilserver.db.RowMapper;
import javaslang.control.Try;

import javax.inject.Inject;
import java.sql.PreparedStatement;

/**
 * Created by thomas on 15/02/16.
 */
public class UserManager {

    @Inject DatabaseManager databaseManager;

    private final RowMapper<User> mapper = rs ->
            Try.of(() ->
                User.of(rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3))
    );

    public Try<User> authenticate(String login, String password){
        PreparedStatement sql = databaseManager.sql("select * from user where login = ? and password = ?");
        return Try.of(() -> {
            sql.setString(1,login);
            sql.setString(2,password);
            return sql.executeQuery();
        }).mapTry(resultSet ->  databaseManager.map(resultSet,mapper));
    }
}
