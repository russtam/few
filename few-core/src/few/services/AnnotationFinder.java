package few.services;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 31.01.12
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
public interface AnnotationFinder {
    Map<Class, List<Class>> findAnnotations();

    List<String> findXmlFiles();
}
