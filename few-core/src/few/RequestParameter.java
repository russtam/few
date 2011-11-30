package few;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 05.11.11
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.PARAMETER} )
@Inherited
public @interface RequestParameter {

    public String name();

    public boolean required() default true;

}
