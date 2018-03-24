package log;

public class Log {

	public static void debug(String message) {
		System.out.println(message("DEBUG", message));
	}

	public static void error(String message) {
		System.err.println(message("ERROR", message));
	}

	private static String message(String type, String message) {
		String fullClassName = Thread.currentThread().getStackTrace()[3].getClassName();
		String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
		String methodName = Thread.currentThread().getStackTrace()[3].getMethodName();
		int lineNumber = Thread.currentThread().getStackTrace()[3].getLineNumber();

		return type + ": [" + className + "." + methodName + "#" + lineNumber +
				"] " +
				message;
	}
}
