package few;

import java.lang.annotation.*;

/**
 * User: gerbylev
 * Date: 05.11.11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.PARAMETER} )
@Inherited
public @interface RequestParameter {

    public String name();

    public boolean required() default true;

}
