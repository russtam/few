package few;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 05.11.11
 * Time: 11:13
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ActionMethod {
    public static final String INHERITED = "INHERITED";

    public abstract String action() default INHERITED;
    public abstract boolean _default() default false;

//    public abstract boolean dispatchByParameters() default true;
//
//    public abstract String dispatchParameter() default "";
//    public abstract String dispatchValue() default "";

}
