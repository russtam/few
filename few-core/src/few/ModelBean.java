package few;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 30.11.11
 * Time: 13:26
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE} )
@Inherited
public @interface ModelBean {
    public String name();
    public abstract String permission() default "";
}
