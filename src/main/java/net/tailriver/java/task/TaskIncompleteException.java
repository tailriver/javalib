package net.tailriver.java.task;

@SuppressWarnings("serial")
public class TaskIncompleteException extends Exception {
	public TaskIncompleteException() {
		super();
	}

	public TaskIncompleteException(String message) {
		super(message);
	}
}
