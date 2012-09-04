package net.tailriver.java.task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * Task file parser and utility methods related execution of tasks.
 * @author tailriver
 */
public class Task implements TaskTarget {
	public static final Pattern VARIABLE_DEFINITION =
			Pattern.compile("[!#]\\s*(\\w+)\\s*[:=]\\s*(.*?)\\s*$");
	public static final Pattern VARIABLE_EXPANSION = Pattern.compile("\\$\\((\\w+)\\)");
	public static final Pattern COMMENT_LINE = Pattern.compile("[!#]");

	private static final Map<String, String> variableTable = new HashMap<String, String>();

	private Queue<String> q;
	private String taskFile;

	/**
	 * It consumes just one argument.
	 * <ol>
	 * <li>The filename of Task file.</li>
	 * </ol>
	 */
	@Override
	public void pop(Queue<String> q) {
		this.q = q;
		try {
			taskFile = q.remove();
		} finally {
			TaskUtil.printPopLog("< task:", taskFile);
		}
	}

	@Override
	public void run() throws TaskIncompleteException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(taskFile));

			String line;
			Queue<String> buffer = new LinkedList<String>(q);
			q.clear();
			while ((line = br.readLine()) != null) {
				line.trim();

				// variable definition
				Matcher md = VARIABLE_DEFINITION.matcher(line);
				if (md.lookingAt()) {
					variableTable.put(md.group(1), md.group(2));
					continue;
				}

				// comment skip
				Matcher mc = COMMENT_LINE.matcher(line);
				if (mc.lookingAt())
					continue;

				// variable substitution
				Matcher ms = VARIABLE_EXPANSION.matcher(line);
				StringBuffer sb = new StringBuffer();
				while (ms.find()) {
					String k = ms.group(1);
					String v = variableTable.containsKey(k) ? variableTable.get(k) : "";
					ms.appendReplacement(sb, v);
				}
				ms.appendTail(sb);
				line = sb.toString();

				q.add(line);
			}
			while (!buffer.isEmpty())
				q.add(buffer.poll());
		} catch (IOException e) {
			throw new TaskIncompleteException(e.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				throw new TaskIncompleteException(e.getMessage());
			}
		}
	}
}
