package com.alibaba.jstorm.callback;

import com.alibaba.jstorm.callback.Callback;

public class BaseCallback implements Callback {

	@SuppressWarnings("unchecked")
	@Override
	public <T> Object execute(T... args) {
		return null;
	}

}
