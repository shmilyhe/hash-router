package io.github.shmilyhe.router;

import java.util.ArrayList;

public class RConfing<T> {

	private T config;
	ArrayList<Integer> plot= new ArrayList<Integer>();
	
	public T getConfig() {
		return config;
	}
	public void setConfig(T config) {
		this.config = config;
	}
	public ArrayList<Integer> getPlot() {
		return plot;
	}
	public void setPlot(ArrayList<Integer> plot) {
		this.plot = plot;
	}
	public void addPlot(int i) {
		plot.add(i);
	}
	
	public ArrayList<Integer> fetch(int avg){
		ArrayList<Integer> p = new ArrayList<Integer>();
		ArrayList<Integer> r = new ArrayList<Integer>();
		if(avg<plot.size()) {
			int c=0;
			for(Integer i:plot) {
				if(c++<avg) {
					p.add(i);
				}else {
					r.add(i);
				}
			}
			plot=p;
		}
		return r;
	}
	public int getCount() {
		if(plot==null)return 0;
		return plot.size();
	}
	
	@Override
	public String toString() {
		if(plot==null)return ";";
		StringBuilder sb = new StringBuilder();
		sb.append(config).append("(").append(plot.size()).append(")")
		.append(":[");
		boolean isFirst =true;
		for(int i:plot) {
			if(isFirst) {isFirst=false;}
			else {
				sb.append(",");
			}
			sb.append(i);
		}
		sb.append("]");
		return sb.toString();
	}
	
	
	
}
