package tutorialdb_model;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

/**
 * Logger handles all debugging messages. Printing all messages to STDOUT to
 * /var/log/tomcat7/catalina.out
 * @author Jake Armentrout
 */
public class Logger {

	/* different types of logging events */
    public static enum Status {
        INFO("info"), WARNING("warning"), ERROR("error"), SUCCESS("success");
        String status;
        Status(String s) {
            this.status = s;
        }
        String showStatus() {
            return this.status;
        }
    }

    /**
     * Log a debug message
     * @param title Category of message being logged
     * @param message Content of message being logged
     */
    public static String log(Status status, String message) {
        String json = toJSON(status, message);
        System.out.println(json);
        return json;
    }
    /**
     * Log an exception message
     * @param title Category of message being logged
     * @param message Content of message being logged
     * @param e Exception that was thrown
     */
    public static String log(Status status, String message, Exception e) {
        String json = toJSON(status, message + "\n" + ExceptionUtils.getStackTrace(e));
        System.out.println(json);
        return json;
    }

    /**
     * Convert this message to JSON string.
     * @param title Category of message being logged
     * @param message Content of message being logged
     * @return String JSON string with escaped special characters
     */
    public static String toJSON(Status status, String message) {
        return "{\"status\":\"" + StringEscapeUtils.escapeJava(status.showStatus()) + "\"," +
                "\"message\":\"" + StringEscapeUtils.escapeJava(message) + "\"}";
    }
}
