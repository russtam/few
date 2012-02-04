package few;

import java.lang.annotation.*;

/**
 * User: gerbylev
 * Date: 30.11.11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.TYPE} )
@Inherited
public @interface ModelBean {
    public String name();
    public abstract String permission() default "";
}
