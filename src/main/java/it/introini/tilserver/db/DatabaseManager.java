package it.introini.tilserver.db;

import javaslang.control.Try;
import org.flywaydb.core.Flyway;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Created by thomas on 12/02/16.
 */
public class DatabaseManager {

    static Logger log = Logger.getLogger(DatabaseManager.class.getName());

    public static final String DB_URL = "jdbc:h2:./resources/db/files/tildb";

    private final Connection connection;

    public DatabaseManager() {
        this.connection = Try.of(() -> Class.forName("org.h2.Driver"))
                             .mapTry(c -> DriverManager.getConnection(DB_URL))
                             .getOrElseThrow((Function<Throwable, RuntimeException>) RuntimeException::new);
    }

    public void migrate(){
        Flyway flyway = new Flyway();
        flyway.setDataSource(DB_URL,null,null);
        flyway.migrate();
    }

    public PreparedStatement sql(String sql){
        return Try.of(() -> connection.prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS)).getOrElseThrow((Function<? super Throwable, RuntimeException>) RuntimeException::new);
    }

    public <T>Collection<T> mapMany(ResultSet resultSet, RowMapper<T> mapper) throws Throwable {
        Collection<T> ret = new ArrayList<>();
        while (resultSet.next()){
            ret.add(mapper.apply(resultSet).getOrElseThrow(Function.identity()));
        }
        return ret;
    }

    public <T>T map(ResultSet resultSet,RowMapper<T> mapper) throws Throwable {
        resultSet.next();
        return mapper.apply(resultSet).getOrElseThrow(Function.identity());
    }

}
