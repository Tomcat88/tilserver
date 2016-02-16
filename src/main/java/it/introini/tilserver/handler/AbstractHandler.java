package it.introini.tilserver.handler;

import javaslang.collection.List;
import javaslang.control.Option;
import spark.Request;
import spark.TemplateViewRoute;

/**
 * Created by thomas on 16/02/16.
 */
public abstract class AbstractHandler implements TemplateViewRoute{

    protected Option<String> stringParam(Request request,String param){
        return List.of(request.queryParamsValues(param)).headOption().filter(p -> !p.isEmpty());
    }
}
