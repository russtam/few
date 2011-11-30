package few.support;

import few.Context;
import few.Message;
import few.ModelBean;
import few.utils.MapWrapper;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 29.11.11
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "messages")
public class Messages extends MapWrapper<String, List<Message>> {

    private Messages(Map<String, List<Message>> map) {
        super(map);
    }

    public static Messages build() {
        return new Messages(Context.get().getMessages());
    }
}
