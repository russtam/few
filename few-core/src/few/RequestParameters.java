package few;

import java.lang.annotation.*;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 02.01.12
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.PARAMETER} )
@Inherited
public @interface RequestParameters {
}
