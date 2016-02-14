package it.introini.tilserver.handler;

import com.google.common.primitives.Longs;
import it.introini.tilserver.db.manager.til.TilManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.ViewUtils;
import javaslang.control.Option;
import javaslang.control.Try;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import javax.inject.Inject;

/**
 * Created by thomas on 14/02/16.
 */
public class DeleteHandler implements TemplateViewRoute {

    @Inject TilManager tilManager;

    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        Option<Long> maybeId = Option.of(request.params(":id")).filter(s -> !s.isEmpty()).flatMap(s -> Option.of(Longs.tryParse(s)));
        if (maybeId.isEmpty()) return ViewUtils.e("id is mandatory.", Routes.rootTuple._1);
        Try<Void> deleteTry = maybeId.map(tilManager::delete).get();
        if (deleteTry.isSuccess()) response.redirect(Routes.rootTuple._1);
        else {
            deleteTry.getCause().printStackTrace();
            return ViewUtils.e("error deleting til.",Routes.rootTuple._1);
        }
        return ViewUtils.v(Routes.rootTuple._2);
    }
}
