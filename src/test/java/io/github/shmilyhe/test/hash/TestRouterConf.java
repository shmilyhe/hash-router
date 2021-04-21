package io.github.shmilyhe.test.hash;

import java.util.Random;

import org.junit.Test;

import io.github.shmilyhe.router.RConfing;
import io.github.shmilyhe.router.impl.HashRouter;

public class TestRouterConf {

	
	/**
	 * 测试动态添加节点的逻辑
	 */
	@Test
	public void testInit() {
		HashRouter<MinioConf> mr = new HashRouter<MinioConf>();
		MinioConf[] nodes = new MinioConf[3];
		for(int i=0;i<nodes.length;i++) {
			nodes[i]= new MinioConf("minio"+i);
		}
		mr.setNodes(nodes,1024);
		System.out.println("=======初始化配置======");
		for(RConfing<MinioConf> c:mr.geteConfig())
		System.out.println(c);
		
		System.out.println("======动态添加配置=======");
		mr.addNodes(new MinioConf[] {
				new MinioConf("minio"+4),
				new MinioConf("minio"+5)
		});
		for(RConfing<MinioConf> c:mr.geteConfig())
			System.out.println(c);
		
		System.out.println("======动态添加配置=======");
		mr.addNodes(new MinioConf[] {
				new MinioConf("minio"+6)
		});
		for(RConfing<MinioConf> c:mr.geteConfig())
			System.out.println(c);
		System.out.println("======动态添加配置=======");
		mr.addNodes(new MinioConf[] {
				new MinioConf("minio"+7)
		});
		for(RConfing<MinioConf> c:mr.geteConfig())
			System.out.println(c);
	}
	
	
	/**
	 * 测试分发的逻辑
	 */
	@Test
	public void testHash() {
		/**
		 * 创建路由
		 */
		HashRouter<MinioConf> router = new HashRouter<MinioConf>();
		
		/**
		 * 初始化配置，假设有7个minio节点
		 * 
		 */
		System.out.println("=======初始化7个minio节点======");
		MinioConf[] nodes = new MinioConf[7];
		for(int i=0;i<nodes.length;i++) {
			nodes[i]= new MinioConf("minio"+i);
		}
		
		/**
		 * 把7个节点添加到路由表中，创建1024个虚节点
		 * 
		 */
		router.setNodes(nodes,1024);
		
		/**
		 * 打印当前路由表
		 */
		printConfig(router.geteConfig());
		
		
		
		int icount =10000000;
		long bt =System.currentTimeMillis();
		Random random = new Random();
		for(int i=0;i<icount;i++) {
			
			/**
			 * 模拟生成社区ID，并调用路由分发
			 */
			MinioConf conf =router.get(String.valueOf(random.nextInt()));
			
			/**
			 * 模拟调用minio ,只计数
			 */
			conf.invoke();
		}
		
		/**
		 * 
		 * 统计单核单线程效率
		 */
		System.out.println("单核单线程："+(icount*1000/(System.currentTimeMillis()-bt))+"tps");
		
		System.out.println("每个节点负载如下：");
		for(MinioConf c:nodes)
		System.out.println(c.printInvoke());

	}
	
	private void printConfig(RConfing<MinioConf> [] config) {
		System.out.println("=======初始化配置如下：======");
		for(RConfing<MinioConf> c:config)
		System.out.println(c);
	}
	
}
