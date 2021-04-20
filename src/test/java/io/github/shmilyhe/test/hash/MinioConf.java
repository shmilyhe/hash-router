package io.github.shmilyhe.test.hash;

public class MinioConf {
	private String name;
	private int count;
	
	public MinioConf(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	public void invoke() {
		count++;
	}
	public String toString() {
		return name;
	}
	
	public String printInvoke() {
		return name+" 调用:"+count+"次";
	}
}
