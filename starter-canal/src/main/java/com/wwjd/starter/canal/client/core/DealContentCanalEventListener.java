package com.wwjd.starter.canal.client.core;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.abstracts.option.content.DeleteOption;
import com.wwjd.starter.canal.client.abstracts.option.content.InsertOption;
import com.wwjd.starter.canal.client.abstracts.option.content.UpdateOption;
import com.wwjd.starter.canal.client.interfaces.CanalContentEventListener;

/**
 * 处理 Canal 监听器
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月28日 20:13:00
 * @Modified_By 阿导 2018/5/28 20:13
 */
public class DealContentCanalEventListener implements CanalContentEventListener {
	
	/**
	 * 表内容操作模块
	 */
	/**
	 * 新增操作处理
	 */
	private InsertOption insertOption;
	/**
	 * 删除操作处理
	 */
	private DeleteOption deleteOption;
	
	/**
	 * 更新操作处理
	 */
	private UpdateOption updateOption;
	
	
	public DealContentCanalEventListener(InsertOption insertOption, DeleteOption deleteOption, UpdateOption updateOption) {
		//修改表内容链路
		this.insertOption = insertOption;
		this.deleteOption = deleteOption;
		this.updateOption = updateOption;
		
		//链路设置
		this.deleteOption.setNext(this.updateOption);
		this.insertOption.setNext(this.deleteOption);
		
		
	}
	
	/**
	 * 处理数据库的操作
	 *
	 * @param destination
	 * @param schemaName
	 * @param tableName
	 * @param eventType
	 * @param rowData
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 09:43
	 * @CopyRight 万物皆导
	 */
	@Override
	public void onEvent(String destination, String schemaName, String tableName, CanalEntry.EventType eventType, CanalEntry.RowData rowData) {
		this.insertOption.doChain(destination, schemaName, tableName, eventType, rowData);
	}
	
	
}
