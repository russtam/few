package few;

import java.lang.annotation.*;

/**
 * User: igor
 * Date: 02.01.12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( {ElementType.PARAMETER} )
@Inherited
public @interface RequestParameters {
}
