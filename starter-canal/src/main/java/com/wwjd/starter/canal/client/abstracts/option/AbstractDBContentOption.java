package com.wwjd.starter.canal.client.abstracts.option;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.interfaces.IDBContentOption;

/**
 * 数据库操作抽象类
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月28日 21:20:00
 * @Modified_By 阿导 2018/5/28 21:20
 */
public abstract class AbstractDBContentOption extends AbstractDBOption implements IDBContentOption {
	
	/**
	 * 下一个节点
	 */
	protected AbstractDBContentOption next;
	
	
	
	/**
	 * 设置下一个节点
	 *
	 * @param next
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 09:39
	 * @CopyRight 万物皆导
	 */
	public void setNext(AbstractDBContentOption next) {
		this.next = next;
	}
	
	
	
	/**
	 * 责任链处理
	 *
	 * @param destination
	 * @param schemaName
	 * @param tableName
	 * @param eventType
	 * @param rowData
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 09:20
	 * @CopyRight 万物皆导
	 */
	public void doChain(String destination, String schemaName, String tableName, CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
		if (this.eventType.equals(eventType)) {
			this.doOption(destination, schemaName, tableName, rowData);
		} else {
			next.doChain(destination, schemaName, tableName, eventType, rowData);
		}
	}
	
	
}
