package io.github.shmilyhe.router;

/**
 * 
 * @author eric
 * Date 2021-04-21
 *
 * @param <T>
 */
public interface IRouter<T> {
	
	T get(int value);
	T get(long value);
	T get(String value);
	
	void setNodes(T[] nodes);
	

	 void setNodes(T[] nodes,int maxNode);
	
	void addNodes(T[] nodes);
	
	RConfing<T>[] geteConfig();
	
	void reflush(RConfing<T>[] config);

}
