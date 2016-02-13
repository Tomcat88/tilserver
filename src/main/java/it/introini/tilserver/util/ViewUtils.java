package it.introini.tilserver.util;

import com.google.common.collect.ImmutableMap;
import it.introini.tilserver.route.Routes;
import spark.ModelAndView;

import java.util.Collections;
import java.util.Map;

/**
 * Created by thomas on 13/02/16.
 */
public class ViewUtils {
    public static ModelAndView v(String template){
        return new ModelAndView(Collections.emptyMap(),template);
    }

    public static ModelAndView mv(Map<String,Object> model,String template){
        return new ModelAndView(model,template);
    }

    public static ModelAndView e(String error){
        return new ModelAndView(ImmutableMap.of("error",error), Routes.errView);

    }
}
