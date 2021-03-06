package it.introini.tilserver.handler;

import it.introini.tilserver.db.manager.til.Til;
import it.introini.tilserver.db.manager.til.TilManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.ViewUtils;
import javaslang.control.Try;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * Created by thomas on 12/02/16.
 */
public class RootHandler extends AbstractHandler{
    static Logger log = Logger.getLogger(RootHandler.class.getName());

    @Inject
    TilManager tilManager;

    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        Try<Collection<Til>> ttils = tilManager.all();
        if (ttils.isFailure()) {
            ttils.getCause().printStackTrace();
            return ViewUtils.e("error while loading tils!",Routes.rootTuple._1);
        }else {
            return ViewUtils.mv(build(request,"tils",ttils.get()), Routes.rootTuple._2);
        }
    }
}
