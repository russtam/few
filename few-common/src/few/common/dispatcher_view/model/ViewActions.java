package few.common.dispatcher_view.model;

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
@ModelBean(name = "actions", permission = "admin")
public class ViewActions extends ListWrapper<ViewActions.ViewAction>{


    private ViewActions(List<ViewAction> actions) {
        super(actions);
    }

    public static class ViewAction {
        private String name;
        private String clazz;
        private String method_name;
        private String permission;
        private List<ViewRequestParameter> parameters;

        public ViewAction(String name, String clazz, String method_name, String permission, List<ViewRequestParameter> parameters) {
            this.name = name;
            this.clazz = clazz;
            this.method_name = method_name;
            this.permission = permission;
            this.parameters = Collections.unmodifiableList(parameters);
        }

        public String getName() {
            return name;
        }

        public String getClazz() {
            return clazz;
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
        for (Map.Entry<String, DispatcherMap.Controller> e : map.getControllers().entrySet()) {
            if( !e.getKey().equals(e.getValue().getInstance().getClass().getName()))
                continue;

            DispatcherMap.Controller ctrl = e.getValue();
            for (DispatcherMap.Action a : ctrl.getActions().values()) {

                String permission = ctrl.getPermission();
                ViewAction ac = new ViewAction(
                        ctrl.getName(),
                        ctrl.getInstance().getClass().getName(),
                        a.getMethod().getName(), permission,
                        ViewModelBeans.buildParametersFromMethod(a.getMethod())
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
