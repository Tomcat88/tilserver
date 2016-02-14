package it.introini.tilserver.app;

import it.introini.tilserver.route.Routes;
import spark.Spark;

import javax.inject.Inject;

/**
 * Created by thomas on 14/02/16.
 */
public class SparkInitializer {

    @Inject Routes routes;

    public static final String STATIC_FOLDER = "/public";

    public void init(){
        Spark.staticFileLocation(STATIC_FOLDER);
        routes.initRoutes();
    }

}
