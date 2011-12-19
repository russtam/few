package few.embedded;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by IntelliJ IDEA.
 * User: igor
 * Date: 19.12.11
 * Time: 3:07
 * To change this template use File | Settings | File Templates.
 */
public class SimpleFormatter extends Formatter {

    public String format(LogRecord record) {
        StringBuffer sb = new StringBuffer();

        sb.append(record.getLevel().getLocalizedName());
        sb.append(": ");

        sb.append(formatMessage(record));
        if (record.getThrown() != null) {
            sb.append("\n");
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
            sb.append(sw.toString());
            } catch (Exception ex) {
            }
        } else {
            if( sb.length() < 100 ) {
                for(int t = sb.length(); t < 110; t++ )
                    sb.append(" ");
            } else {
                sb.append("\n");
                for(int t = 0; t < 30; t++ )
                    sb.append(" ");
            }
        }

        if (record.getSourceClassName() != null) {
            sb.append(record.getSourceClassName());
        } else {
            sb.append(record.getLoggerName());
        }
        if (record.getSourceMethodName() != null) {
            sb.append(" ");
            sb.append(record.getSourceMethodName());
        }
        sb.append("\n");

        return sb.toString();
    }

}
