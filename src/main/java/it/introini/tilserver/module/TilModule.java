package it.introini.tilserver.module;

import dagger.Module;
import dagger.Provides;
import it.introini.tilserver.app.Application;
import it.introini.tilserver.db.DatabaseManager;
import it.introini.tilserver.db.manager.til.TilManager;
import it.introini.tilserver.handler.RootHandler;

import javax.inject.Singleton;

/**
 * Created by thomas on 12/02/16.
 */
@Module(injects = {
        Application.class,
        RootHandler.class,
        TilManager.class
})
public class TilModule {

    @Provides
    @Singleton
    public DatabaseManager databaseManager(){
        return new DatabaseManager();
    }

}
