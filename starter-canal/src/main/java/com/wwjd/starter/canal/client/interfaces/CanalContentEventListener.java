package com.wwjd.starter.canal.client.interfaces;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * canal 的事件接口层（表数据改变）
 *
 * @author 阿导
 * @CopyRight 杭州弧途科技有限公司(万物皆导)
 * @created 2018/5/28 16:37
 * @Modified_By 阿导 2018/5/28 16:37
 */
public interface CanalContentEventListener {
	
	
	/**
	 * 处理事件
	 *
	 * @param destination 指令
	 * @param schemaName  库实例
	 * @param tableName   表名
	 * @param eventType   参数类型
	 * @param rowData     操作数据信息
	 * @return
	 * @author 阿导
	 * @time 2018/5/28 16:37
	 * @CopyRight 万物皆导
	 */
	void onEvent(String destination, String schemaName, String tableName, CanalEntry.EventType eventType, CanalEntry.RowData rowData);
	
}
