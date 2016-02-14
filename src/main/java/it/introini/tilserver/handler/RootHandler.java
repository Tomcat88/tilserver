package it.introini.tilserver.handler;

import com.google.common.collect.ImmutableMap;
import it.introini.tilserver.db.manager.til.Til;
import it.introini.tilserver.db.manager.til.TilManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.Utils;
import it.introini.tilserver.util.ViewUtils;
import javaslang.control.Try;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import javax.inject.Inject;
import java.util.Collection;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * Created by thomas on 12/02/16.
 */
public class RootHandler implements TemplateViewRoute{
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
            Collection<Til> tils = ttils.get();

            return ViewUtils.mv(ImmutableMap.of("tils",tils,
                                                "format", (Function<String,String>) Utils::reformatDateTime),
                                Routes.rootTuple._2);
        }
    }
}
