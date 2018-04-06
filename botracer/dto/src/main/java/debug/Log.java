package debug;

/**
 * Log.java
 * Simple debug.debug.log formatter
 *
 * @author David Walter
 */
public class Log {

	public static final boolean DEBUG = true;
	public static final boolean VERBOSE = false;

	public static void verbose(String message) {
		if (VERBOSE) {
			System.out.println(message(message));
		}
	}

	public static void debug(String message) {
		if (DEBUG) {
			System.out.println(message(message));
		}
	}

	public static void error(String message) {
		System.err.println(message(message));
	}

	private static String message(String message) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[3];

		String fullClassName = ste.getClassName();
		String fileName = ste.getFileName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = ste.getMethodName();
		int lineNumber = ste.getLineNumber();

		return "[" + fileName + ":" + lineNumber +
				"] " + className + "." + methodName + " - " +
				message;
	}
}
