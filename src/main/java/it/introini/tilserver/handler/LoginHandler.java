package it.introini.tilserver.handler;

import it.introini.tilserver.db.manager.user.UserManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.ViewUtils;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.TemplateViewRoute;
import spark.route.HttpMethod;

import javax.inject.Inject;

/**
 * Created by thomas on 15/02/16.
 */
public class LoginHandler implements TemplateViewRoute {

    @Inject UserManager userManager;

    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        switch (HttpMethod.valueOf(request.requestMethod().toLowerCase())){
            case get:
                return get(request, response);
            case post:
                return post(request, response);
            default:
                return ViewUtils.e("method not allowed", Routes.rootTuple._1);
        }
    }
    public ModelAndView get(Request request,Response response){
        return ViewUtils.v(Routes.loginTuple._2);
    }
    public ModelAndView post(Request request,Response response){
        return ViewUtils.v(Routes.loginTuple._2);
    }
}
