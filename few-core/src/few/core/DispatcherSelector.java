package few.core;

import java.util.Collection;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 30.11.11
 * Time: 19:11
 * To change this template use File | Settings | File Templates.
 */
public class DispatcherSelector {

    DispatcherMap map;

    public DispatcherSelector(DispatcherMap map) {
        this.map = map;
    }

    public DispatcherMap.ActionMethodDescription selectMethod(String action, Collection parameters) {
        DispatcherMap.ActionDescription ad = map.getActions().get(action);
        if( ad == null )
            return null;

        int count = -1;
        DispatcherMap.ActionMethodDescription m = null;
        for (Iterator<DispatcherMap.ActionMethodDescription> iterator = ad.methods.iterator(); iterator.hasNext(); ) {
            DispatcherMap.ActionMethodDescription md = iterator.next();

            if( parameters.containsAll(md.parameters) ) {
                int c = intersectionCount(parameters, md.parameters);
                if( c > count ) {
                    m = md;
                    count = c;
                }
                if( count == parameters.size() )
                    break;
            }
        }
        if( m != null )
            return m;
        else
            return ad.defaultMethod;
    }

    public DispatcherMap.ActionDescription selectAction(String action) {
        return map.getActions().get(action);
    }


    private int intersectionCount(Collection a, Collection b) {
        int count = 0;
        for (Iterator i = a.iterator(); i.hasNext(); ) {
            Object aa = i.next();
            if( b.contains(aa) )
                count++;
        }
        return count;
    }


}
