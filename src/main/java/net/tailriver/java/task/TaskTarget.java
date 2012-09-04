package net.tailriver.java.task;

import java.util.Queue;

import net.tailriver.java.task.TaskIncompleteException;

/**
 * Interface for the tasks executed by {@link TaskUtil#getTask(String)}.
 * @author tailriver
 *
 */
public interface TaskTarget {
	/**
	 * You have to fetch arguments here to {@link #run()}.
	 * @param args - Arguments passed from task queue.
	 * @see TaskUtil#outputFileCheck(String)
	 */
	public void pop(Queue<String> args);

	/**
	 * 
	 * @throws TaskIncompleteException if you want to interrupt this and following tasks.
	 */
	public void run() throws TaskIncompleteException;
}
