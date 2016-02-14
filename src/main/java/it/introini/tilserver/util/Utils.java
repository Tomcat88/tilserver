package it.introini.tilserver.util;

import javaslang.control.Try;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Created by thomas on 13/02/16.
 */
public class Utils {

    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    static Logger log = Logger.getLogger(Utils.class.getName());

    public static String stackTraceToString(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

    public static String reformatDateTime(String datetime){
        return Try.of(() -> Instant.parse(datetime.trim()))
                  .mapTry(instant -> dateTimeFormatter.format(LocalDateTime.ofInstant(instant,ZoneId.systemDefault())))
                  .getOrElse("Invalid date.");
    }

}
