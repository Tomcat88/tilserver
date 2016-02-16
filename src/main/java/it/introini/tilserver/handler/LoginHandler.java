package it.introini.tilserver.handler;

import it.introini.tilserver.db.manager.SessionManager;
import it.introini.tilserver.db.manager.user.User;
import it.introini.tilserver.db.manager.user.UserManager;
import it.introini.tilserver.route.Routes;
import it.introini.tilserver.util.ViewUtils;
import javaslang.control.Option;
import javaslang.control.Try;
import org.apache.commons.codec.binary.Hex;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.route.HttpMethod;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.util.logging.Logger;

/**
 * Created by thomas on 15/02/16.
 */
public class LoginHandler extends AbstractHandler{

    static private Logger log = Logger.getLogger(LoginHandler.class.getName());

    @Inject UserManager userManager;
    @Inject SessionManager sessionManager;
    public static final String LOGIN_PARAM    = "login";
    public static final String PASSWORD_PARAM = "password";

    @Override
    public ModelAndView handle(Request request, Response response) throws Exception {
        if(sessionManager.isAuth(request)){
            response.redirect(Routes.rootTuple._1);
        }
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
        return ViewUtils.mv(modelBuilder(request).build(),Routes.loginTuple._2);
    }
    public ModelAndView post(Request request,Response response){
        Option<String> maybeLogin    = stringParam(request, LOGIN_PARAM);
        Option<String> maybePassword = stringParam(request, PASSWORD_PARAM);

        if (maybeLogin.isEmpty() || maybePassword.isEmpty()) return ViewUtils.e("user and password are mandatory.",Routes.loginTuple._1);

        Try<User> authenticate = hash(maybePassword.get()).flatMapTry(hash -> userManager.authenticate(maybeLogin.get(), hash));
        if (authenticate.isFailure()){
            authenticate.getCause().printStackTrace();
            return ViewUtils.e("login failed",Routes.loginTuple._1);
        }else {
            User u = authenticate.get();
            log.info("Logged user "+u.getLogin());
            sessionManager.login(u,request);
            response.redirect(Routes.rootTuple._1);
        }
        return ViewUtils.v(Routes.loginTuple._2);
    }

    public Try<String> hash(String password){
        return Try.of(() -> {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            return Hex.encodeHexString(crypt.digest());
        });
    }
}
