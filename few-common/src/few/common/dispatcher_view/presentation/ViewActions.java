package few.common.dispatcher_view.presentation;

import few.ModelBean;
import few.core.DispatcherMap;
import few.utils.ListWrapper;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 10.01.12
 * Time: 0:18
 * To change this template use File | Settings | File Templates.
 */
@ModelBean(name = "actions")
public class ViewActions extends ListWrapper<ViewActions.ViewAction>{


    private ViewActions(List<ViewAction> actions) {
        super(actions);
    }

    public static class ViewAction {
        private String name;
        private String method_name;
        private String permission;
        private List<ViewRequestParameter> parameters;

        public ViewAction(String name, String method_name, String permission, List<ViewRequestParameter> parameters) {
            this.name = name;
            this.method_name = method_name;
            this.permission = permission;
            this.parameters = Collections.unmodifiableList(parameters);
        }

        public String getName() {
            return name;
        }

        public String getMethod_name() {
            return method_name;
        }

        public String getPermission() {
            return permission;
        }

        public Collection<ViewRequestParameter> getParameters() {
            return parameters;
        }
    }

    public static ViewActions build() {
        List<ViewAction> actions = new LinkedList<ViewAction>();

        DispatcherMap map = DispatcherMap.get();
        for (DispatcherMap.Action ad : map.getActions().values()) {
            for (DispatcherMap.ActionMethodDescription adm : ad.getMethods()) {

                String permission = ad.getAuthorized_roles() != null ? (ad.getAuthorized_roles().length == 1 ? ad.getAuthorized_roles()[0] : "") : "";
                ViewAction ac = new ViewAction(ad.getName(), adm.getMethod().getName(), permission,
                        ViewModelBeans.buildParametersFromMethod(adm.getMethod())
                        );

                actions.add(ac);
            }
        }

        Collections.sort(actions, new Comparator<ViewAction>() {
            public int compare(ViewAction o1, ViewAction o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return new ViewActions(actions);
    }

}
