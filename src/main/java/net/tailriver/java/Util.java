package net.tailriver.java;

import java.util.List;

public class Util {
	public static String join(String sep, Object[] array) {
		StringBuilder sb = new StringBuilder();
		for (Object s : array)
			sb.append(s).append(sep);
		return sb.substring(0, sb.length() - sep.length());
	}

	public static <E> String join(String sep, List<E> list) {
		return Util.join(sep, list.toArray());
	}

	public static String repeat(String s, int times) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < times; i++)
			sb.append(s);
		return sb.toString();
	}

	public static String getCallerClass(int stackTraceOrder) {
		String fqclass = Thread.currentThread().getStackTrace()[stackTraceOrder].getClassName();
		return fqclass.substring(fqclass.lastIndexOf(".") + 1);
	}
}
