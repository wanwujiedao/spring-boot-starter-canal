package com.wwjd.starter.canal.client.interfaces;

import com.alibaba.otter.canal.protocol.CanalEntry;

/**
 * 表结构操作接口层
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 14:10:00
 * @Modified_By 阿导 2018/5/29 14:10
 */
public interface IDBTableOption {
	
	/**
	 * 操作
	 *
	 * @param rowChange
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 08:59
	 * @CopyRight 万物皆导
	 */
	void doOption(CanalEntry.RowChange rowChange);
	
}
