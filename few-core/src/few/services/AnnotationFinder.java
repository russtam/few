package few.services;

import java.util.List;
import java.util.Map;

/**
 * User: igor
 * Date: 31.01.12
 */
public interface AnnotationFinder {
    Map<Class, List<Class>> findAnnotations();

    List<String> findXmlFiles();
}
