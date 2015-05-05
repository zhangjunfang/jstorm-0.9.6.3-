package com.alibaba.jstorm.callback;

/**
 * Callback interface
 * 
 * @author lixin 2012-3-12
 * 
 */
public interface Callback {

	@SuppressWarnings("unchecked")
	public <T> Object execute(T... args);

}
