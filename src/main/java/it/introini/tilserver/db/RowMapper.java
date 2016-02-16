package it.introini.tilserver.db;

import javaslang.control.Try;

import java.sql.ResultSet;
import java.util.function.Function;

/**
 * Created by thomas on 16/02/16.
 */
@FunctionalInterface
public interface RowMapper<T> extends Function<ResultSet,Try<T>> {
}
