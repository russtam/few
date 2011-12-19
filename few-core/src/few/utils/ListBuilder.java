package few.utils;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 17.12.11
 * Time: 3:17
 * To change this template use File | Settings | File Templates.
 */
public class ListBuilder<T> extends LinkedList<T> {

    public ListBuilder build(T t) {
        add(t);
        return this;
    }

}
