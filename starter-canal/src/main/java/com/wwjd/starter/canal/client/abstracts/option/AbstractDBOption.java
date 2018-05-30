package com.wwjd.starter.canal.client.abstracts.option;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 数据库操作抽象类
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 14:42:00
 * @Modified_By 阿导 2018/5/29 14:42
 */
public abstract class AbstractDBOption {
	
	/**
	 * 操作类型
	 */
	protected CanalEntry.EventType eventType;
	
	/**
	 * 默认构造方法
	 *
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 09:06
	 * @CopyRight 万物皆导
	 */
	public AbstractDBOption() {
		this.setEventType();
	}
	
	/**
	 * 进行类型设置
	 *
	 * @author 阿导
	 * @CopyRight 萬物皆導
	 * @created 2018/5/29 09:21
	 */
	protected abstract void setEventType();
	
	
}
