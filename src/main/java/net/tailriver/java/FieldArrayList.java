package net.tailriver.java;

import java.util.ArrayList;

/** ArrayList w/ one field. */
public class FieldArrayList<E, F> extends ArrayList<E> implements Cloneable {
	private static final long serialVersionUID = -1809717557260599214L;
	private F field;

	public FieldArrayList() {
		super();
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
		field = null;
	}

	@Override
	public Object clone() {
		@SuppressWarnings("unchecked")
		FieldArrayList<E, F> v = (FieldArrayList<E, F>) super.clone();
		v.field = field;
		return v;
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
