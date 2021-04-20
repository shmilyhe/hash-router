package io.github.shmilyhe.router.impl;

import java.util.ArrayList;

import io.github.shmilyhe.hashing.Hashing;
import io.github.shmilyhe.router.IRouter;
import io.github.shmilyhe.router.RConfing;

/**
 * 
 * @author eric
 * Date 2021-04-21
 *
 * @param <T> Config
 */
public class HashRouter<T> implements IRouter<T> {
	T[] nodes;
	int len;
	RConfing<T>[] configs;
	
	public HashRouter() {
	}
	
	public HashRouter(int maxNode) {
		len=maxNode;
	}
	
	@Override
	public T get(int value) {
		if(nodes==null||len==0)return null;
		value = value< 0 ?-value:value;
		return nodes[value%nodes.length];
	}

	@Override
	public T get(long value) {
		return get(getHash(value));
	}

	@Override
	public T get(String value) {
		return get(getHash(value));
	}

	@Override
	public void setNodes(T[] nodes) {
		setNodes(nodes,1024);
	}

	@Override
	public void addNodes(T[] nodes) {
		if(nodes==null||nodes.length==0)return;
		if(configs==null) {
			setNodes(nodes);
		}
		int len =configs.length;
		RConfing<T>[] c=new RConfing[len+nodes.length];
		RConfing<T>[] nc=new RConfing[nodes.length];
		for(int i=0;i<nodes.length;i++) {
			nc[i]=new RConfing();
			nc[i].setConfig(nodes[i]);
		}
		System.arraycopy(configs, 0, c, 0, configs.length);
		System.arraycopy(nc, 0, c, configs.length, nc.length);
		int ua=this.len/(len+nodes.length);
		int fetchCount =ua*nodes.length;
		ArrayList<Integer> fecth= new ArrayList<Integer>();
		for(RConfing<T> rc:configs) {
			if(fetchCount<1)break;
			ArrayList<Integer> f=rc.fetch(ua);
			fecth.addAll(f);
			fetchCount-=f.size();
		}
		int ni=0;
		for(Integer i:fecth) {
			nc[ni++].addPlot(i);
			if(ni>=nodes.length)ni=0;
		}
		init(c);
	}
	
	protected int getHash(long key) {
		int hashCode = (int)key;
		return hashCode;
	}
	protected int getHash(String key) {
		 int hashCode = (int)Hashing.MURMUR_HASH.hash(key ); 
		 return hashCode;
	}

	@Override
	public RConfing<T>[] geteConfig() {
		return configs;
	}

	@Override
	public void reflush(RConfing<T>[] config) {
		init(config);
	}
	
	protected synchronized void init(RConfing<T>[] c) {
		int count =getCount(c);
		if(count==0)return;
		Object[] ns =new Object[count];
		for(RConfing<T> rc:c) {
			T config =rc.getConfig();
			for(Integer i:rc.getPlot()) {
				ns[i]=config;
			}
		}
		nodes=(T[]) ns;
		len=count;
		configs=c;
		
	}
	
	int getCount(RConfing<T>[] c) {
		if(c==null||c.length==0)return 0;
		int count=0;
		for(RConfing rc :c) {
			count+=rc.getCount();
		}
		return count;
	}

	@Override
	public void setNodes(T[] nodes, int maxNode) {
		if(nodes==null||nodes.length==0)throw new RuntimeException("Nodes sould not be empty!");
		int nl=nodes.length;
		RConfing<T>[] conf = new RConfing[nl];
		for(int i=0;i<nl;i++) {
			conf[i]=new RConfing<T>();
			conf[i].setConfig(nodes[i]);
		}
		int c=0;
		for(int i=0;i<maxNode;i++) {
			conf[c++].addPlot(i);
			if(c>=nl)c=0;
		}
		init(conf);
	}



}
