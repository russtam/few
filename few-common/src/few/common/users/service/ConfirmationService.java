package few.common.users.service;

import few.common.BaseMyBatisServiceImpl;
import few.common.users.persistence.Confirmation;
import few.utils.Utils;

import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 04.11.11
 * Time: 16:55
 * To change this template use File | Settings | File Templates.
 */
public class ConfirmationService extends BaseMyBatisServiceImpl {

    public static final int ONE_DAY_TIMEOUT = 24*60*60*1000;
    public static final int ONE_HOUR_TIMEOUT = 60*60*1000;

    private static ConfirmationService service = new ConfirmationService();
    public static ConfirmationService get() {
        return service;
    }
    private ConfirmationService() {
    }


    public void doCleanup() {
        session().delete("few.common.cleanupConfirmations");
        session().commit();
    }

    public String createConfirmationKey(String params[], int timeout) {
        StringBuilder p = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            String param = params[i];
            p.append(Utils.stringToHex(param));
            if( i != params.length - 1)
                p.append(" ");
        }

        Confirmation ac = new Confirmation();
        ac.key = Utils.generateUID();
        ac.parameters = p.toString();
        ac.expire_time = new Date(System.currentTimeMillis() + timeout);

        session().insert("few.common.insertConfirmation",ac);
        session().commit();

        return ac.key;
    }


    public String[] useConfirmationKey(String key) {

        String s = (String) session().selectOne("few.common.selectConfirmation", key);
        if( s == null )
            return null;
        session().delete("few.common.deleteConfirmation", key);
        session().commit();

        StringTokenizer stringTokenizer = new StringTokenizer(s);
        String ret [] = new String[stringTokenizer.countTokens()];
        for ( int c = 0; stringTokenizer.hasMoreTokens(); c++) {
            String s1 = stringTokenizer.nextToken();
            ret[c] = Utils.hexToString(s1);
        }

        return ret;
    }

}
