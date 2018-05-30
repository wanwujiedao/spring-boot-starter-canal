package com.wwjd.starter.canal.client.transfer;

import com.alibaba.otter.canal.client.CanalConnector;
import com.wwjd.starter.canal.client.core.ListenerPoint;
import com.wwjd.starter.canal.client.interfaces.CanalContentEventListener;
import com.wwjd.starter.canal.client.interfaces.CanalTableEventListener;
import com.wwjd.starter.canal.client.interfaces.MessageTransponder;
import com.wwjd.starter.canal.client.interfaces.TransponderFactory;
import com.wwjd.starter.canal.config.CanalConfig;

import java.util.List;
import java.util.Map;

/**
 * @author 阿导
 * @CopyRight 杭州弧途科技有限公司(万物皆导)
 * @created 2018/5/28 15:54
 * @Modified_By 阿导 2018/5/28 15:54
 */
public class DefaultTransponderFactory implements TransponderFactory {
	/**
	 *
	 *
	 * @author 阿导
	 * @time 2018/5/28 15:55
	 * @CopyRight 万物皆导
	 * @param connector canal 连接器
	 * @param config canal 连接信息
	 * @param contentListeners 实现监听器接口的 canal 监听器（表内容）
	 * @param tableListeners 实现监听器接口的 canal 监听器(表结构)
	 * @param annoListeners 注解的 canal 监听器
	 * @return
	 */
	@Override
	public MessageTransponder newTransponder(CanalConnector connector, Map.Entry<String, CanalConfig.Instance> config, List<CanalContentEventListener> contentListeners, List<CanalTableEventListener> tableListeners, List<ListenerPoint> annoListeners) {
		return new DefaultMessageTransponder(connector, config, contentListeners,tableListeners, annoListeners);
	}
}
