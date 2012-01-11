package few.common;

import few.common.users.service.ConfirmationService;
import few.core.ServiceRegistry;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * Created by IntelliJ IDEA.
 * User: gerbylev
 * Date: 06.09.11
 * Time: 20:10
 * To change this template use File | Settings | File Templates.
 */
public abstract class BaseMyBatisServiceImpl {

    private DataConfigProvider configProvider = ServiceRegistry.get(DataConfigProvider.class);
    protected SqlSessionFactory sqlMapper;

    public BaseMyBatisServiceImpl() {
        _init();
    }

    private void _init() {
        DataConfigProvider.Conf conf = configProvider.getConfig(this.getClass());

        String driver =  "org.postgresql.Driver";
        String dbUrl = "jdbc:postgresql://" + conf.ip + ":" + conf.port + "/" + conf.dbname;
        String sql_conf = "ibatis-conf.xml";

        Properties props = new Properties();
        props.setProperty("driver", driver);
        props.setProperty("url", dbUrl);
        props.setProperty("username", conf.user);
        props.setProperty("password", conf.password);

        Reader resource = null;
        try {
            resource = Resources.getResourceAsReader(Thread.currentThread().getContextClassLoader(), sql_conf);
        } catch (IOException e) {
            throw new Error("can not read " + sql_conf, e);
        }

        sqlMapper = new SqlSessionFactoryBuilder().build(resource, props);

        SqlSession session = sqlMapper.openSession();

        session.close();
    }

    protected <T> T getMapper(Class<T> clazz) {
        return session().getMapper(clazz);
    }

    protected SqlSession session() {
        SqlSession session = localSession.get();
        if( session == null ) {
            session = sqlMapper.openSession();
            localSession.set(session);
        }
        return session;
    }

    private ThreadLocal<SqlSession> localSession = new ThreadLocal<SqlSession>();

    public void closeSession() {
        SqlSession session = localSession.get();
        if( session != null ) {
            session.close();
            localSession.set(null);
        }
    }

    private static LinkedList<BaseMyBatisServiceImpl> instances = new LinkedList<BaseMyBatisServiceImpl>();
    {
        instances.add(this);
    }
    public static void onThreadStop() {
        for (BaseMyBatisServiceImpl instance : instances) {
            instance.closeSession();
        }
    }

}
