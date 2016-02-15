package it.introini.tilserver.route;

import it.introini.tilserver.handler.*;
import javaslang.Tuple;
import javaslang.Tuple2;
import spark.TemplateEngine;
import spark.template.mustache.MustacheTemplateEngine;

import javax.inject.Inject;

import static spark.Spark.get;
import static spark.Spark.post;

/**
 * Created by thomas on 13/02/16.
 */
public class Routes {
    public static final Tuple2<String,String> rootTuple   = Tuple.of("/","root.mustache");
    public static final Tuple2<String,String> newTuple    = Tuple.of("/new","new.mustache");
    public static final Tuple2<String,String> tilTuple    = Tuple.of("/til/:id","til.mustache");
    public static final Tuple2<String,String> loginTuple  = Tuple.of("/login","login.mustache");
    public static final String deleteRoute                = "/delete/:id";
    public static final String errView                    = "err.mustache";

    @Inject RootHandler   rootHandler;
    @Inject NewHandler    newHandler;
    @Inject TilHandler    tilHandler;
    @Inject DeleteHandler deleteHandler;
    @Inject LoginHandler  loginHandler;

    private final TemplateEngine templateEngine = new MustacheTemplateEngine();

    public void initRoutes(){
        get(rootTuple._1,rootHandler,templateEngine);

        get(newTuple._1,newHandler,templateEngine);
        post(newTuple._1,newHandler,templateEngine);

        get(tilTuple._1,tilHandler,templateEngine);

        get(deleteRoute,deleteHandler,templateEngine);

        get(loginTuple._1,loginHandler,templateEngine);
        post(loginTuple._1,loginHandler,templateEngine);
    }
}
