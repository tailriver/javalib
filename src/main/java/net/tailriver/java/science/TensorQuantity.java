package net.tailriver.java.science;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class TensorQuantity<T extends Enum<T> & Tensor> extends AbstractMap<T, Double> {
	private final EnumMap<T, Double> eMap;
	private final Map<T, Double> map;

	public TensorQuantity(Class<T> keyType) {
		this(new EnumMap<T, Double>(keyType));
	}

	public TensorQuantity(T component, Double value) {
		eMap = null;
		map = Collections.singletonMap(component, value);
	}

	private TensorQuantity(EnumMap<T, Double> map) {
		eMap = map;
		this.map = eMap;
	}

	private final boolean isEnumMap() {
		return eMap != null;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public Double get(Object key) {
		return map.get(key);
	}

	@Override
	public Double put(T key, Double value) {
		return isEnumMap() ? eMap.put(key, value) : super.put(key, value);
	}

	@Override
	public Double remove(Object key) {
		return isEnumMap() ? eMap.remove(key) : super.remove(key);
	}

	@Override
	public void putAll(Map<? extends T, ? extends Double> m) {
		if (isEnumMap())
			eMap.putAll(m);
		else
			super.putAll(m);
	}

	@Override
	public void clear() {
		if (isEnumMap())
			eMap.clear();
		else
			super.clear();
	}

	@Override
	public Set<T> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<Double> values() {
		return map.values();
	}

	@Override
	public Set<Map.Entry<T, Double>> entrySet() {
		return map.entrySet();
	}

	@Override
	public boolean equals(Object o) {
		return isEnumMap() ? eMap.equals(o) : super.equals(o);
	}

	@Override
	public int hashCode() {
		return isEnumMap() ? eMap.hashCode() : super.hashCode();
	}

	@Override
	public TensorQuantity<T> clone() {
		if (isEnumMap())
			return new TensorQuantity<>(eMap.clone());
		else {
			T component = null;
			Double value = null;
			for (Map.Entry<T, Double> e : map.entrySet()) {
				component = e.getKey();
				value = e.getValue();
			}
			return new TensorQuantity<>(component, value);
		}
	}
}
