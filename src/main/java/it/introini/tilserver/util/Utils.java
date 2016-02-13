package it.introini.tilserver.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by thomas on 13/02/16.
 */
public class Utils {

    public static String stackTraceToString(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }

}
