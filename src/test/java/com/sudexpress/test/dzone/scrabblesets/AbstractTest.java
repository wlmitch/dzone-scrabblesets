package com.sudexpress.test.dzone.scrabblesets;

import static org.mockito.Mockito.spy;

import java.lang.reflect.ParameterizedType;

import org.junit.Before;

public abstract class AbstractTest<T> {

	private final Class<T> testClass;
	protected T test;

	@SuppressWarnings("unchecked")
	public AbstractTest() {
		this.testClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Before
	public void initTest() {
		this.test = spy(this.newTestInstance());
	}

	protected T newTestInstance() {
		try {
			return this.testClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Unable to build test instance", e);
		}
	}
}
