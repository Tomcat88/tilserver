package it.introini.tilserver.db.manager;

import com.google.common.collect.ImmutableMap;
import it.introini.tilserver.db.manager.user.User;
import javaslang.control.Option;
import spark.Request;
import spark.Session;

/**
 * Created by thomas on 16/02/16.
 */
public class SessionManager {


    public static final String AUTH_ATTR = "auth";
    public static final String USER_ATTR = "user";

    public void login(User user, Request request){
        Session session = request.session(true);
        session.attribute(AUTH_ATTR,"true");
        session.attribute(USER_ATTR,user.getLogin());
    }

    public boolean isAuth(Request request){
        return Option.of(request.session().attribute(AUTH_ATTR))
                     .map(Object::toString)
                     .map(Boolean::parseBoolean)
                     .getOrElse(false);
    }

    public String user(Request request){
        return Option.of(request.session().attribute(USER_ATTR))
                     .map(Object::toString)
                     .getOrElse("");
    }

    public void logout(Request request){
        request.session().removeAttribute(AUTH_ATTR);
        request.session().removeAttribute(USER_ATTR);
    }

    public ImmutableMap.Builder<String,Object> sessionMapBuilder(Request request){
        ImmutableMap.Builder<String ,Object> builder = ImmutableMap.builder();
        builder.put(AUTH_ATTR, isAuth(request))
               .put(USER_ATTR, user(request));
        return builder;
    }

}
