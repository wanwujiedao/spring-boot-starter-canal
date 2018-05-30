package com.wwjd.starter.canal.client.abstracts.option;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.interfaces.IDBTableOption;

/**
 * 数据库操作抽象类
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月28日 21:20:00
 * @Modified_By 阿导 2018/5/28 21:20
 */
public abstract class AbstractDBTableOption extends AbstractDBOption implements IDBTableOption {
	
	/**
	 * 下一个节点
	 */
	protected AbstractDBTableOption next;
	
	
	/**
	 * 设置下一个节点
	 *
	 * @param next
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 09:39
	 * @CopyRight 万物皆导
	 */
	public void setNext(AbstractDBTableOption next) {
		this.next = next;
	}
	
	/**
	 * 责任链处理
	 *
	 * @param eventType
	 * @param rowChange
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 09:20
	 * @CopyRight 万物皆导
	 */
	public void doChain(CanalEntry.EventType eventType, CanalEntry.RowChange rowChange) {
		if (this.eventType.equals(eventType)) {
			this.doOption(rowChange);
		} else {
			next.doChain(eventType, rowChange);
		}
	}
	
	
}
