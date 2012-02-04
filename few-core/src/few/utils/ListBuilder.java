package few.utils;

import java.util.LinkedList;

/**
 * User: igor
 * Date: 17.12.11
 */
public class ListBuilder<T> extends LinkedList<T> {

    public ListBuilder build(T t) {
        add(t);
        return this;
    }

}
