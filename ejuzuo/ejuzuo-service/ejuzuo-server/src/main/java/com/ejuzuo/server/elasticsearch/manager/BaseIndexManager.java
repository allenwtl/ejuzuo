package com.ejuzuo.server.elasticsearch.manager;


public interface BaseIndexManager<T> {

	public  void createIndex(T obj);
	
	public void deleteById(Integer id);
}
