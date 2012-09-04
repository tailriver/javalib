package net.tailriver.java.science;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class TensorQuantity<T extends Enum<T> & Tensor> extends AbstractMap<T, Double> {
	private final Map<T, Double> map;
	private final boolean isEnumMap;

	public TensorQuantity(Class<T> keyType) {
		map = new EnumMap<T, Double>(keyType);
		isEnumMap = true;
	}

	public TensorQuantity(T component, Double value) {
		map = Collections.singletonMap(component, value);
		isEnumMap = false;
	}

	private TensorQuantity(EnumMap<T, Double> map) {
		this.map = map;
		isEnumMap = true;
	}

	@Override
	public int size() {
		return isEnumMap ?
				((EnumMap<T, Double>) map).size() : map.size();
	}

	@Override
	public boolean containsValue(Object value) {
		return isEnumMap ?
				((EnumMap<T, Double>) map).containsValue(value) : map.containsValue(value);
	}

	@Override
	public boolean containsKey(Object key) {
		return isEnumMap ?
				((EnumMap<T, Double>) map).containsKey(key) : map.containsKey(key);
	}

	@Override
	public Double get(Object key) {
		return isEnumMap ?
				((EnumMap<T, Double>) map).get(key) : map.get(key);
	}

	@Override
	public Double put(T key, Double value) {
		return isEnumMap ?
				((EnumMap<T, Double>) map).put(key, value) : super.put(key, value);
	}

	@Override
	public Double remove(Object key) {
		return isEnumMap ?
				((EnumMap<T, Double>) map).remove(key) : super.remove(key);
	}

	@Override
	public void putAll(Map<? extends T, ? extends Double> m) {
		if (isEnumMap)
			((EnumMap<T, Double>) map).putAll(m);
		else
			super.putAll(m);
	}

	@Override
	public void clear() {
		if (isEnumMap)
			((EnumMap<T, Double>) map).clear();
		else
			super.clear();
	}

	@Override
	public Set<T> keySet() {
		return isEnumMap ?
				((EnumMap<T, Double>) map).keySet() : map.keySet();
	}

	@Override
	public Collection<Double> values() {
		return isEnumMap ?
				((EnumMap<T, Double>) map).values() : map.values();
	}

	@Override
	public Set<Map.Entry<T, Double>> entrySet() {
		return isEnumMap ?
				((EnumMap<T, Double>) map).entrySet() : map.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return isEnumMap ?
				((EnumMap<T, Double>) map).equals(o) : super.equals(o);
	}

	@Override
	public int hashCode() {
		return isEnumMap ?
				((EnumMap<T, Double>) map).hashCode() : super.hashCode();
	}

	@Override
	public TensorQuantity<T> clone() throws CloneNotSupportedException {
		if (!isEnumMap)
			throw new CloneNotSupportedException();

		TensorQuantity<T> result = new TensorQuantity<T>(((EnumMap<T, Double>) map).clone());
		return result;
	}
}
