package it.introini.tilserver.handler;

import com.google.common.collect.ImmutableMap;
import com.google.common.primitives.Longs;
import it.introini.tilserver.db.manager.til.TilManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.ViewUtils;
import javaslang.control.Option;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;

import javax.inject.Inject;

/**
 * Created by thomas on 14/02/16.
 */
public class TilHandler implements TemplateViewRoute {

    @Inject TilManager tilManager;

    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        Option<Long> maybeId = Option.of(request.params(":id")).filter(s -> !s.isEmpty()).flatMap(s -> Option.of(Longs.tryParse(s)));
        if(maybeId.isEmpty()){
            return ViewUtils.e("id is mandatory",Routes.rootTuple._1);
        }else {
            return maybeId.map(tilManager::til)
                          .get()
                          .map(t -> ViewUtils.mv(ImmutableMap.of("til",t),Routes.tilTuple._2))
                          .getOrElseGet(throwable -> {
                              throwable.printStackTrace();
                              return ViewUtils.e("til not found.",Routes.rootTuple._1);
                          });
        }
    }
}
