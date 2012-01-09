package few.common.audit.service;

import few.common.BaseMyBatisServiceImpl;
import few.common.audit.persistence.RequestDump;
import few.core.ServiceRegistry;
import few.services.Credentials;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 07.01.12
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class AuditAndLogFilter implements Filter, ServletContextListener, HttpSessionListener {
    Credentials credentials;
    AuditService auditService;

    public void init(FilterConfig filterConfig) throws ServletException {
        credentials = ServiceRegistry.get(Credentials.class);
        auditService = AuditService.get();
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        ResponseForLogWrapper resp = new ResponseForLogWrapper((HttpServletResponse) response);

        try {
            chain.doFilter(request, resp);
            try {
                log((HttpServletRequest)request, resp, resp.getStatus());
            } catch(Throwable t) {
                log.log(Level.WARNING, "", t);
            }
        } catch(Throwable t) {
            try {
                log((HttpServletRequest)request, resp, 600);
            } catch(Throwable e) {
                log.log(Level.WARNING, "", t);
            }
            try {
                if( t instanceof InvocationTargetException)
                    t = ((InvocationTargetException) t).getTargetException();
                AuditService.get().insertActivity(
                        AuditKeys.CRITICAL, AuditKeys.ERROR_REQUEST_PROCESSING, t.getMessage());
            } catch(Throwable e) {
                log.log(Level.WARNING, "", t);
            }
            throw new ServletException(t);
        }
    }

    static Logger log = Logger.getLogger(AuditAndLogFilter.class.getName());

    private void log(HttpServletRequest request, ResponseForLogWrapper response, int status) {
        RequestDump dump = new RequestDump();
        dump.uri = request.getRequestURI();
        dump.method = request.getMethod();
        dump.response_code = status;
        dump.processing_time = (int) (System.currentTimeMillis() - response.getStart_time());
        dump.timestamp = response.getStart_time();

        if( request.getRemoteAddr().equals("127.0.0.1") && request.getHeader("X-Real-IP") != null )
            dump.remote_address = request.getHeader("X-Real-IP");
        else
            dump.remote_address = request.getRemoteAddr();

        dump.referer = request.getHeader("referer");

        dump.session_id = request.getRequestedSessionId();
        dump.user_id = credentials.getUserID(request);

        auditService.insertRequestDump(dump);
    }

    public void destroy() {
    }

    static class ResponseForLogWrapper extends HttpServletResponseWrapper {

        private long start_time = System.currentTimeMillis();
        private int status = 200;

        public ResponseForLogWrapper(HttpServletResponse response) {
            super(response);
        }

        public long getStart_time() {
            return start_time;
        }
        public int getStatus() {
            return status;
        }

        public void sendError(int sc, String msg) throws IOException {
            status = sc;
            super.sendError(sc, msg);
        }
        public void sendError(int sc) throws IOException {
            status = sc;
            super.sendError(sc);
        }
        public void sendRedirect(String location) throws IOException {
            status = 302;
            super.sendRedirect(location);
        }
        public void setStatus(int sc) {
            status = sc;
            super.setStatus(sc);
        }
        public void setStatus(int sc, String sm) {
            status = sc;
            super.setStatus(sc, sm);
        }
    }

    // a bit of audit...
    public void sessionCreated(HttpSessionEvent se) {
        if( credentials == null ) {
            credentials = ServiceRegistry.get(Credentials.class);
        }
        try {
            String user_id = credentials.getUserID(se.getSession());
            Integer uid = user_id != null ? Integer.valueOf(user_id) : null;
            if( se.getSession().isNew() ) {
                AuditService.get().insertActivity(
                        uid, AuditKeys.NORMAL, AuditKeys.CREATE_SESSION, se.getSession().getId());
            } else {
                AuditService.get().insertActivity(
                        uid, AuditKeys.MINOR, AuditKeys.RESTORE_SESSION, se.getSession().getId());
            }
        } finally {
            AuditService.get().closeSession();
        }
    }

    public void sessionDestroyed(HttpSessionEvent se) {
    }

    public void contextInitialized(ServletContextEvent sce) {
        try {
            AuditService.get().insertActivity(
                    AuditKeys.WARNING, AuditKeys.START, "");
        } finally {
            AuditService.get().closeSession();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            AuditService.get().insertActivity(
                    AuditKeys.WARNING, AuditKeys.SHUTDOWN, "");
        } finally {
            AuditService.get().closeSession();
        }
    }

}
