package com.wwjd.starter.canal.client.core;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.abstracts.option.table.AlertTableOption;
import com.wwjd.starter.canal.client.abstracts.option.table.CreateTableOption;
import com.wwjd.starter.canal.client.abstracts.option.table.DropTableOption;
import com.wwjd.starter.canal.client.interfaces.CanalTableEventListener;

/**
 * 处理 Canal 监听器
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月28日 20:13:00
 * @Modified_By 阿导 2018/5/28 20:13
 */
public class DealTableCanalEventListener implements CanalTableEventListener {
	
	/**
	 * 表结构操作模块
	 */
	
	private CreateTableOption createTableOption;
	
	/**
	 * 删除表操作
	 */
	private DropTableOption dropTableOption;
	
	/**
	 * 更改表信息操作
	 */
	private AlertTableOption alertTableOption;
	
	
	public DealTableCanalEventListener(CreateTableOption createTableOption, DropTableOption dropTableOption, AlertTableOption alertTableOption) {
		//修改表结构
		this.createTableOption = createTableOption;
		this.dropTableOption = dropTableOption;
		this.alertTableOption = alertTableOption;
		
		//链路设置
		this.createTableOption.setNext(this.dropTableOption);
		this.dropTableOption.setNext(this.alertTableOption);
	}
	
	
	/**
	 * 若是表结构发生改变
	 *
	 * @param rowChange
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 13:25
	 * @CopyRight 万物皆导
	 */
	@Override
	public void onEvent(CanalEntry.EventType eventType,CanalEntry.RowChange rowChange) {
		this.createTableOption.doChain(eventType,rowChange);
	}
	
	
}
