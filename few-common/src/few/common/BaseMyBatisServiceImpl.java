package few.common;

import few.core.ServiceRegistry;
import few.services.AnnotationFinder;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

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

        Properties props = new Properties();
        props.setProperty("driver", driver);
        props.setProperty("url", dbUrl);
        props.setProperty("username", conf.user);
        props.setProperty("password", conf.password);

        Configuration iConf = new Configuration();
        iConf.setEnvironment(
                new Environment(
                        "development", 
                        new JdbcTransactionFactory(), 
                        new PooledDataSource(driver, dbUrl, conf.user, conf.password))
        );


        for( String r : getBatisFiles() ) {
                InputStream is = null;
                try {
                    is = new FileInputStream(r);
                    XMLMapperBuilder b = new XMLMapperBuilder(
                            is, iConf, r, Collections.<String, XNode>emptyMap()
                    );
                    b.parse();
                } catch (FileNotFoundException e) {
                } finally {
                    if( is != null )
                        try {
                            is.close();
                        } catch (IOException e) {
                        }
                }

        }

        sqlMapper = new SqlSessionFactoryBuilder().build(iConf);

        SqlSession session = sqlMapper.openSession();

        session.close();
    }

    static List<String> batisFiles;
    private static List<String> getBatisFiles() {
        if( batisFiles != null )
            return batisFiles;

        long start = System.currentTimeMillis();
        try {
            batisFiles = new LinkedList<String>();
            AnnotationFinder af = ServiceRegistry.get(AnnotationFinder.class);

            List<String> files = af.findXmlFiles();
            for( String f : files ) {
                if( checkIsMyBatisXML(f) )
                    batisFiles.add(f);
            }
        } finally {
            log.info("getBatisFiles procedure took " + (System.currentTimeMillis() - start) + " msec");
        }

        return batisFiles;
    }

    private static boolean checkIsMyBatisXML(String file) {
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            byte buf[] = new byte[1024];
            int size = is.read(buf);
            String startString = new String(buf);

            return startString.contains("<mapper");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if( is != null )
                try {
                    is.close();
                } catch (IOException e) {
                }
        }
        return false;
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

    private static Logger log = Logger.getLogger(BaseMyBatisServiceImpl.class.getName());
}
