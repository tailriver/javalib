package net.tailriver.java;

import java.util.ArrayList;
import java.util.Collection;

/** ArrayList w/ one field. */
public class FieldArrayList<E, F> extends ArrayList<E> implements Cloneable {
	private static final long serialVersionUID = -1809717557260599214L;
	private F field;

	public FieldArrayList() {
		super();
	}

	public FieldArrayList(Collection<? extends E> c) {
		super(c);
	}

	/**
	 * 
	 * @return The field value. null if the field is null;
	 */
	public F get() {
		return field;
	}

	/**
	 * Sets value to the field.
	 * @param field - the value.
	 */
	public void set(F field) {
		this.field = field;
	}


	@Override
	public void clear() {
		super.clear();
		set(null);
	}

	@Override
	public FieldArrayList<E, F> clone() {
		@SuppressWarnings("unchecked")
		FieldArrayList<E, F> c = (FieldArrayList<E, F>) super.clone();
		c.set(field);
		return c;
	}

	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj) && obj instanceof FieldArrayList<?,?>)
			return field.equals(((FieldArrayList<?,?>) obj).field);
		return false;
	}

	@Override
	public int hashCode() {
		return 23 * field.hashCode() + super.hashCode();
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "@{field=" + field.toString() + ";" + super.toString() + "}";
	}
}
