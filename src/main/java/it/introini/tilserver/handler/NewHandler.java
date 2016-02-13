package it.introini.tilserver.handler;

import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import it.introini.tilserver.db.manager.til.Til;
import it.introini.tilserver.db.manager.til.TilManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.Utils;
import it.introini.tilserver.util.ViewUtils;
import javaslang.collection.List;
import javaslang.control.Option;
import javaslang.control.Try;
import spark.*;
import spark.route.HttpMethod;

import javax.inject.Inject;
import java.time.Instant;
import java.util.logging.Logger;

/**
 * Created by thomas on 12/02/16.
 */

public class NewHandler implements TemplateViewRoute{

    static private Logger log = Logger.getLogger(NewHandler.class.getName());

    public static final String CONTENT_PARAM = "content";

    @Inject TilManager tilManager;


    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        switch (getMethod(request)){
            case get:
                return get(request,response);
            case post:
                return post(request,response);
                default:
                    Spark.halt(401,"method now allowed");
        }
        return null;
    }

    public ModelAndView get(Request request,Response response){
        return ViewUtils.v(Routes.newTuple._2);
    }
    public ModelAndView post(Request request,Response response){
        Option<String> content = List.of(request.queryParamsValues(CONTENT_PARAM))
                             .headOption()
                             .filter(c -> !c.isEmpty());

        if(content.isEmpty()) return ViewUtils.e("content is mandatory.");

        return tilManager.insert(Instant.now(), content.get())
                  .map(til -> ViewUtils.v(Routes.newTuple._2))
                  .getOrElseGet(t ->{
                      t.printStackTrace();
                      return ViewUtils.e(Utils.stackTraceToString(t));
                  });
    }

    public HttpMethod getMethod(Request request){
        return HttpMethod.valueOf(request.requestMethod().toLowerCase());
    }
}
