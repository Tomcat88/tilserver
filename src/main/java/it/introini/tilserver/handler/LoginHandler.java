package it.introini.tilserver.handler;

import it.introini.tilserver.db.manager.user.User;
import it.introini.tilserver.db.manager.user.UserManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.ViewUtils;
import javaslang.control.Option;
import javaslang.control.Try;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

import javax.inject.Inject;

/**
 * Created by thomas on 15/02/16.
 */
public class LoginHandler extends AbstractHandler{

    @Inject UserManager userManager;

    public static final String LOGIN_PARAM    = "login";
    public static final String PASSWORD_PARAM = "password";

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
        Option<String> maybeLogin    = stringParam(request, LOGIN_PARAM);
        Option<String> maybePassword = stringParam(request, PASSWORD_PARAM);

        if (maybeLogin.isEmpty() || maybePassword.isEmpty()) return ViewUtils.e("user and password are mandatory.",Routes.loginTuple._1);

        Try<User> authenticate = userManager.authenticate(maybeLogin.get(), maybePassword.get());
        if (authenticate.isFailure()){
            authenticate.getCause().printStackTrace();
            return ViewUtils.e("login failed",Routes.loginTuple._1);
        }else {
            
        }

        return ViewUtils.v(Routes.loginTuple._2);
    }
}
