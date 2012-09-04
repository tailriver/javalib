package net.tailriver.java.task;

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import net.tailriver.java.Util;
import net.tailriver.java.task.TaskTarget;

public class TaskUtil {
	public static final String OUTPUT_FILE_CHECKER_PREFIX = ">";

	private static final LinkedList<Package> taskPackages;

	static {
		taskPackages = new LinkedList<Package>();
		addTaskPackage(TaskUtil.class.getPackage());
	}

	public static void addTaskPackage(Package p) {
		taskPackages.addFirst(p);
	}

	public static TaskTarget getTask(String className) throws ClassNotFoundException {
		try {
			for (Package p : taskPackages) {
				Object o = null;
				try {
					o = Class.forName(p.getName() + "." + className).newInstance();
				} catch (ClassNotFoundException e) {
					continue;
				}
				if (o instanceof TaskTarget)
					return (TaskTarget)o;
				throw new ClassNotFoundException("Tasks must implement TaskTarget interface");
			}
			throw new ClassNotFoundException("requested task package not found");
		} catch (ClassNotFoundException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ClassNotFoundException(e.getMessage());
		}
	}

	/**
	 * It helps you from misplaced arguments.
	 * @param filename - The candidate file name you will output.
	 * @return filename (It already removed {@link TaskUtil#OUTPUT_FILE_CHECKER_PREFIX}).
	 * @throws NoSuchElementException if the candidate failed the test.
	 */
	public static String outputFileCheck(String filename) throws NoSuchElementException {
		if (filename.startsWith(OUTPUT_FILE_CHECKER_PREFIX))
			return filename.substring(OUTPUT_FILE_CHECKER_PREFIX.length());
		throw new NoSuchElementException("output file fail-safe checker detected: " + filename);
	}

	/**
	 * It makes you easy to print something to standard output,
	 * especially in {@link TaskTarget#pop(Queue)}.
	 * When you call this, you will see white-space concatenated messages with caller class name.
	 * @param s string(s)
	 */
	public static void printPopLog(String... s) {
		System.out.println("[" + Util.getCallerClass(3) + "] " + Util.join(" ", s));
	}

}
