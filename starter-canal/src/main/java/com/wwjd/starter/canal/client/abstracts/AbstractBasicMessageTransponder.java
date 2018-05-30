package com.wwjd.starter.canal.client.abstracts;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.wwjd.starter.canal.annotation.ListenPoint;
import com.wwjd.starter.canal.client.core.CanalMsg;
import com.wwjd.starter.canal.client.core.ListenerPoint;
import com.wwjd.starter.canal.client.exception.CanalClientException;
import com.wwjd.starter.canal.client.interfaces.CanalContentEventListener;
import com.wwjd.starter.canal.client.interfaces.CanalTableEventListener;
import com.wwjd.starter.canal.config.CanalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 消息处理转换类
 *
 * @author 阿导
 * @CopyRight 杭州弧途科技有限公司(万物皆导)
 * @created 2018/5/28 16:27
 * @Modified_By 阿导 2018/5/28 16:27
 */
public abstract class AbstractBasicMessageTransponder extends AbstractMessageTransponder {
	/**
	 * 日志记录
	 */
	private final static Logger logger = LoggerFactory.getLogger(AbstractBasicMessageTransponder.class);
	
	/**
	 * @param connector        canal 连接器
	 * @param config           canal 连接配置
	 * @param contentListeners 实现接口层的 canal 监听器（表数据）
	 * @param tableListeners   实现接口层的 canal 监听器(表结构)
	 * @param annoListeners    通过注解方式的 canal 监听器
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:27
	 * @CopyRight 万物皆导
	 */
	public AbstractBasicMessageTransponder(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config, List<CanalContentEventListener> contentListeners, List<CanalTableEventListener> tableListeners, List<ListenerPoint> annoListeners) {
		super(connector, config, contentListeners, tableListeners, annoListeners);
	}
	
	/**
	 * 处理消息
	 *
	 * @param message
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:50
	 * @CopyRight 万物皆导
	 */
	@Override
	protected void distributeEvent(Message message) {
		//获取操作实体
		List<CanalEntry.Entry> entries = message.getEntries();
		//遍历实体
		for (CanalEntry.Entry entry : entries) {
			//忽略实体类的类型
			List<CanalEntry.EntryType> ignoreEntryTypes = getIgnoreEntryTypes();
			if (ignoreEntryTypes != null
					&& ignoreEntryTypes.stream().anyMatch(t -> entry.getEntryType() == t)) {
				continue;
			}
			//canal 改变信息
			CanalEntry.RowChange rowChange;
			try {
				//获取信息改变
				rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
			} catch (Exception e) {
				throw new CanalClientException("错误 ##转换错误 , 数据信息:" + entry.toString(),
						e);
			}
			
			
			//表结构变动处理
			if (rowChange.hasIsDdl() && rowChange.getIsDdl()) {
				
				//处理实现接口的 canal 监听器
				distributeDdlByImpl(rowChange.getEventType(),rowChange);
				//处理通过注解方式的监听器
				distributeDdlByAnnotation(rowChange);
				continue;
			}
			//数据变动处理（这边可能需要改动成批量处理）
			for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
				//处理实现接口的 canal 监听器
				distributeByImpl(destination,
						entry.getHeader().getSchemaName(),
						entry.getHeader().getTableName(),
						rowChange.getEventType(),
						rowData);
				//处理通过注解方式的监听器
				distributeByAnnotation(destination,
						entry.getHeader().getSchemaName(),
						entry.getHeader().getTableName(),
						rowChange.getEventType(),
						rowData);
			}
		}
	}
	
	
	/**
	 * 处理监听信息
	 *
	 * @param destination 指令
	 * @param schemaName  库实例
	 * @param tableName   表名
	 * @param eventType   参数类型
	 * @param rowData     操作数据信息
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:46
	 * @CopyRight 万物皆导
	 */
	protected void distributeByImpl(String destination,
	                                String schemaName,
	                                String tableName,
	                                CanalEntry.EventType eventType,
	                                CanalEntry.RowData rowData) {
		if (contentListeners != null) {
			for (CanalContentEventListener listener : contentListeners) {
				listener.onEvent(destination, schemaName, tableName, eventType, rowData);
			}
		}
	}
	
	protected void distributeDdlByImpl(CanalEntry.EventType eventType,CanalEntry.RowChange rowChange) {
		if (tableListeners != null) {
			for (CanalTableEventListener listener : tableListeners) {
				listener.onEvent(eventType,rowChange);
			}
		}
	}
	
	/**
	 * 处理注解方式的 canal 监听器
	 *
	 * @param destination canal 指令
	 * @param schemaName  实例名称
	 * @param tableName   表名称
	 * @param eventType   操作类型
	 * @param rowData     数据
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:35
	 * @CopyRight 万物皆导
	 */
	protected void distributeByAnnotation(String destination,
	                                      String schemaName,
	                                      String tableName,
	                                      CanalEntry.EventType eventType,
	                                      CanalEntry.RowData rowData) {
		
		//对注解的监听器进行事件委托
		annoListeners.forEach(point -> point
				.getInvokeMap()
				.entrySet()
				.stream()
				.filter(getAnnotationFilter(destination, schemaName, tableName, eventType))
				.forEach(entry -> {
					Method method = entry.getKey();
					method.setAccessible(true);
					try {
						CanalMsg canalMsg = new CanalMsg();
						canalMsg.setDestination(destination);
						canalMsg.setSchemaName(schemaName);
						canalMsg.setTableName(tableName);
						
						Object[] args = getInvokeArgs(method, canalMsg, eventType, rowData, null);
						method.invoke(point.getTarget(), args);
					} catch (Exception e) {
						logger.error("{}: 委托 canal 监听器发生错误! 错误类:{}, 方法名:{}",
								Thread.currentThread().getName(),
								point.getTarget().getClass().getName(), method.getName());
					}
				}));
	}
	
	/**
	 * 处理注解方式的 canal 监听器
	 *
	 * @param rowChange
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:35
	 * @CopyRight 万物皆导
	 */
	protected void distributeDdlByAnnotation(CanalEntry.RowChange rowChange) {
		
		//对注解的监听器进行事件委托
		annoListeners.forEach(point -> point
				.getInvokeMap()
				.entrySet()
				.stream()
				.filter(getAnnotationFilter(destination, rowChange.getDdlSchemaName(), null, null))
				.forEach(entry -> {
					Method method = entry.getKey();
					method.setAccessible(true);
					try {
						CanalMsg canalMsg = new CanalMsg();
						canalMsg.setDestination(destination);
						canalMsg.setSchemaName(rowChange.getDdlSchemaName());
						
						Object[] args = getInvokeArgs(method, canalMsg, null, null, rowChange);
						method.invoke(point.getTarget(), args);
					} catch (Exception e) {
						logger.error("{}: 委托 canal 监听器（表结构监听器）发生错误! 错误类:{}, 方法名:{}",
								Thread.currentThread().getName(),
								point.getTarget().getClass().getName(), method.getName());
					}
				}));
	}
	
	/**
	 * 断言注解方式的监听过滤规则
	 *
	 * @param destination 指定
	 * @param schemaName  数据库实例
	 * @param tableName   表名称
	 * @param eventType   事件类型
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:00
	 * @CopyRight 万物皆导
	 */
	protected abstract Predicate<Map.Entry<Method, ListenPoint>> getAnnotationFilter(String destination, String schemaName, String tableName, CanalEntry.EventType eventType);
	
	
	/**
	 * 获取参数
	 *
	 * @param method    委托处理的方法
	 * @param canalMsg  其他信息
	 * @param eventType 事件类型
	 * @param rowData   处理的数据
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:30
	 * @CopyRight 万物皆导
	 */
	protected abstract Object[] getInvokeArgs(Method method, CanalMsg canalMsg, CanalEntry.EventType eventType, CanalEntry.RowData rowData, CanalEntry.RowChange rowChange);
	
	
	/**
	 * 返回一个空集合
	 *
	 * @param
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:29
	 * @CopyRight 万物皆导
	 */
	protected List<CanalEntry.EntryType> getIgnoreEntryTypes() {
		return Collections.emptyList();
	}
	
}
