package it.introini.tilserver.app;

/**
 * Created by thomas on 12/02/16.
 */

import dagger.ObjectGraph;
import it.introini.tilserver.db.DatabaseManager;
import it.introini.tilserver.module.TilModule;
import it.introini.tilserver.route.Routes;

import javax.inject.Inject;

public class Application{
    @Inject Routes routes;
    @Inject DatabaseManager databaseManager;


    public static void main(String[] args) {
        ObjectGraph objectGraph = ObjectGraph.create(new TilModule());
        Application application = objectGraph.get(Application.class);
        application.routes.initRoutes();
        application.databaseManager.migrate();
    }

}
