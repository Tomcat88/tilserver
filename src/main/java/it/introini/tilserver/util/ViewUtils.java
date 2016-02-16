package it.introini.tilserver.util;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import it.introini.tilserver.route.Routes;
import org.apache.commons.lang3.text.WordUtils;
import spark.ModelAndView;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

/**
 * Created by thomas on 13/02/16.
 */
public class ViewUtils {
    public static ModelAndView v(String template){
        return new ModelAndView(Collections.emptyMap(),template);
    }

    public static ModelAndView mv(ImmutableMap<String,Object> model,String template){
        return new ModelAndView(addHelper(model),template);
    }

    public static ModelAndView e(String error,String backLink){
        return new ModelAndView(ImmutableMap.of("error",error,
                                                "back_link",backLink), Routes.errView);
    }

    private static Map<String ,Object> addHelper(ImmutableMap<String ,Object> model){
        ImmutableMap.Builder<String , Object> builder = ImmutableMap.builder();
        builder.putAll(model)
               .put("format",(Function<String ,Object>)Utils::reformatDateTime)
               .put("capitalize",(Function<String ,Object>) WordUtils::capitalize);
        return builder.build();
    }
}
