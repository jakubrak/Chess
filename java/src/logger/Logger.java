package logger;

/**
 * @author Jakub Rak
 */
public class Logger {
	public static void print(final Object object, final String message) {
		System.out.println(object + ":\n" + message + "\n");
	}
}
