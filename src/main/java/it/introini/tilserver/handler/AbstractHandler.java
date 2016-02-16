package it.introini.tilserver.handler;

import com.google.common.collect.ImmutableMap;
import it.introini.tilserver.db.manager.SessionManager;
import javaslang.collection.List;
import javaslang.control.Option;
import spark.Request;
import spark.TemplateViewRoute;

import javax.inject.Inject;

/**
 * Created by thomas on 16/02/16.
 */
public abstract class AbstractHandler implements TemplateViewRoute{

    @Inject SessionManager sessionManager;


    protected Option<String> stringParam(Request request,String param){
        return Option.of(request.queryParamsValues(param)).map(List::of).map(List::head).filter(p -> !p.isEmpty());
    }

    protected ImmutableMap.Builder<String ,Object> addTo(ImmutableMap.Builder<String ,Object> builder,String key,Object value){
        return builder.put(key, value);
    }
    protected ImmutableMap<String ,Object> addToAndBuild(ImmutableMap.Builder<String ,Object> builder,String key,Object value){
        return builder.put(key, value).build();
    }

    protected ImmutableMap.Builder<String ,Object> modelBuilder(Request request){
        return sessionManager.sessionMapBuilder(request);
    }
    protected ImmutableMap<String ,Object> build(Request request,String key,Object value){
        return addToAndBuild(modelBuilder(request),key,value);
    }


}
