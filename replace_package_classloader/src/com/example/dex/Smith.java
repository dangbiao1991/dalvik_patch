package com.example.dex;

import java.lang.reflect.Field;

public class Smith<T> {
	private Object obj;
	private String filed_name;
	private boolean inited;
	private Field field;
	
	public Smith(Object obj, String fieldName) {
		if (obj == null) {
			throw new IllegalArgumentException("obj cannot be null");
		}
		this.obj = obj;
		this.filed_name = fieldName;
	}
	
	private void prepare() {
		if (inited) return;
		inited = true;
		
		Class<?> c = obj.getClass();
		while (c != null) {
			try {
				Field f = c.getDeclaredField(filed_name);
				f.setAccessible(true);
				field = f;
				return ;
			} catch (Exception e) {
			} finally {
				c = c.getSuperclass();
			}
		}
	}
	
	public T get() 
			throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		prepare();
		if (field == null) {
			throw new NoSuchFieldException();
		}
		
		try {
			T r = (T) field.get(obj);
			return r;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("unable to cast object");
		}
	}
	
	public void set(T val) 
			throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		prepare();
		if (field == null) {
			throw new NoSuchFieldException();
		}
		field.set(obj, val);
	}
}
