package it.introini.tilserver.db.manager.til;

import it.introini.tilserver.db.DatabaseManager;
import javaslang.control.Try;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Created by thomas on 12/02/16.
 */
public class TilManager {

    static Logger log = Logger.getLogger(TilManager.class.getName());

    @Inject
    DatabaseManager databaseManager;

    private final Function<ResultSet,Try<Til>> tilMapper =
            resultSet ->
                Try.of(() ->
                    Til.of( resultSet.getLong(1),
                            resultSet.getTimestamp(2).toInstant(),
                            resultSet.getString(3)));


    public Try<Collection<Til>> all(){
        PreparedStatement statement = databaseManager.sql("select * from til order by datetime desc");
        return Try.of(statement::executeQuery).mapTry(rs -> databaseManager.mapMany(rs, tilMapper));
    }

    public Try<Til> insert(Instant timestamp, String content){
        PreparedStatement statement = databaseManager.sql("insert into til(datetime,content) values(?,?)");
        return Try.of(() -> {
            statement.setTimestamp(1, Timestamp.from(timestamp));
            statement.setString(2,content);
            statement.execute();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            generatedKeys.first();
            return generatedKeys.getLong(1);
        }).mapTry(id -> Til.of(id,timestamp,content));
    }

    public Try<Til> til(long id){
        PreparedStatement statement = databaseManager.sql("select * from til where id = ?");
        return Try.of(() -> {
            statement.setLong(1, id);
            return statement.executeQuery();
        }).mapTry(rs -> databaseManager.map(rs, tilMapper));

    }

    public Try<Void> delete(long id){
        PreparedStatement statement = databaseManager.sql("delete from til where id = ?");
        return Try.run(() -> {
            statement.setLong(1,id);
            statement.execute();
        });
    }

}
