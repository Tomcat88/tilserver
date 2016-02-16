package it.introini.tilserver.handler;

import it.introini.tilserver.db.manager.SessionManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.ViewUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.inject.Inject;

/**
 * Created by thomas on 16/02/16.
 */
public class LogoutHandler extends AbstractHandler {

    @Inject SessionManager sessionManager;

    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        sessionManager.logout(request);
        response.redirect(Routes.rootTuple._1);
        return ViewUtils.v(Routes.rootTuple._1); // ignored
    }
}
