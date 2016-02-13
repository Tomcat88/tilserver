package it.introini.tilserver.db;

import javaslang.control.Try;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Created by thomas on 12/02/16.
 */
public class DatabaseManager {

    static Logger log = Logger.getLogger(DatabaseManager.class.getName());

    public static final String DB_URL = "jdbc:h2:./src/main/resources/db/files/tildb";

    private final Connection connection;

    public DatabaseManager() {
        this.connection = Try.of(() -> Class.forName("org.h2.Driver"))
                             .mapTry(c -> DriverManager.getConnection(DB_URL))
                             .getOrElseThrow((Function<Throwable, RuntimeException>) RuntimeException::new);
    }

    public PreparedStatement sql(String sql){
        return Try.of(() -> connection.prepareStatement(sql)).getOrElseThrow((Function<? super Throwable, RuntimeException>) RuntimeException::new);
    }

    public <T>Collection<T> map(ResultSet resultSet,Function<ResultSet,Try<T>> mapper) throws Throwable {
        Collection<T> ret = new ArrayList<>();
        while (resultSet.next()){
            ret.add(mapper.apply(resultSet).getOrElseThrow(Function.identity()));
        }
        return ret;
    }

}
