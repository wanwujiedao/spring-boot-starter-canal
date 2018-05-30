package com.wwjd.starter.canal.client.core;

import com.alibaba.otter.canal.client.CanalConnector;
import com.wwjd.starter.canal.annotation.ListenPoint;
import com.wwjd.starter.canal.client.abstracts.AbstractCanalClient;
import com.wwjd.starter.canal.client.interfaces.CanalContentEventListener;
import com.wwjd.starter.canal.client.interfaces.CanalTableEventListener;
import com.wwjd.starter.canal.config.CanalConfig;
import com.wwjd.starter.canal.util.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 通过线程池处理
 *
 * @author 阿导
 * @CopyRight 萬物皆導
 * @created 2018/5/28 14:52
 * @Modified_By 阿导 2018/5/28 14:52
 */
public class SimpleCanalClient extends AbstractCanalClient {
	
	/**
	 * 声明一个线程池
	 */
	private ThreadPoolExecutor executor;
	
	/**
	 * 通过实现接口的监听器
	 */
	protected final List<CanalContentEventListener> contentListeners = new ArrayList<>();
	protected final List<CanalTableEventListener> tableListeners = new ArrayList<>();
	
	/**
	 * 通过注解的方式实现的监听器
	 */
	private final List<ListenerPoint> annoListeners = new ArrayList<>();
	
	/**
	 * 日志记录
	 */
	private final static Logger logger = LoggerFactory.getLogger(SimpleCanalClient.class);
	
	/**
	 * 构造方法，进行一些基本信息初始化
	 *
	 * @param canalConfig
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 15:33
	 * @CopyRight 万物皆导
	 */
	public SimpleCanalClient(CanalConfig canalConfig) {
		super(canalConfig);
		//这边上可能需要调整，紧跟德叔脚步走，默认核心线程数5个，最大线程数20个，线程两分钟分钟不执行就。。。
		executor = new ThreadPoolExecutor(5, 20,
				120L, TimeUnit.SECONDS,
				new SynchronousQueue<>(), Executors.defaultThreadFactory());
		//初始化监听器
		initListeners();
	}
	
	/**
	 * @param connector
	 * @param config
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 15:01
	 * @CopyRight 万物皆导
	 */
	@Override
	protected void process(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config) {
		executor.submit(factory.newTransponder(connector, config, contentListeners, tableListeners, annoListeners));
	}
	
	/**
	 * 关闭 canal 客户端
	 *
	 * @param
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 15:00
	 * @CopyRight 万物皆导
	 */
	@Override
	public void stop() {
		//停止 canal 客户端
		super.stop();
		
		//线程池关闭
		executor.shutdown();
	}
	
	/**
	 * 初始化监听器
	 *
	 * @param
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 14:53
	 * @CopyRight 万物皆导
	 */
	private void initListeners() {
		logger.info("{}: 监听器正在初始化....", Thread.currentThread().getName());
		//获取监听器
		List<CanalContentEventListener> listForContent = BeanUtil.getBeansOfType(CanalContentEventListener.class);
		List<CanalTableEventListener> listForTable = BeanUtil.getBeansOfType(CanalTableEventListener.class);
		//若没有任何监听的，我也不知道引入这个 jar 干什么，直接返回吧
		if (listForContent != null) {
			//若存在目标监听，放入 listenerMap
			contentListeners.addAll(listForContent);
		}
		//这里监听表结构的操作
		if (listForTable != null) {
			tableListeners.addAll(listForTable);
		}
		//若是你喜欢通过注解的方式去监听的话。。
		Map<String, Object> listenerMap = BeanUtil.getBeansWithAnnotation(com.wwjd.starter.canal.annotation.CanalEventListener.class);
		//也放入 map
		if (listenerMap != null) {
			for (Object target : listenerMap.values()) {
				//方法获取
				Method[] methods = target.getClass().getDeclaredMethods();
				if (methods != null && methods.length > 0) {
					for (Method method : methods) {
						//获取监听的节点
						ListenPoint l = AnnotationUtils.findAnnotation(method, ListenPoint.class);
						if (l != null) {
							annoListeners.add(new ListenerPoint(target, method, l));
						}
					}
				}
			}
		}
		//初始化监听结束
		logger.info("{}: 监听器初始化完成.", Thread.currentThread().getName());
		//整个项目上下文都没发现监听器。。。
		if (logger.isWarnEnabled() && contentListeners.isEmpty() && tableListeners.isEmpty() && annoListeners.isEmpty()) {
			logger.warn("{}: 该项目中没有任何监听的目标! ", Thread.currentThread().getName());
		}
	}
}
