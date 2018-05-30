package com.wwjd.canal.canaltest.optiontest;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.abstracts.option.table.DropTableOption;
import org.springframework.stereotype.Component;

/**
 * 真正的删除表操作
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 15:01:00
 * @Modified_By 阿导 2018/5/29 15:01
 */
@Component
public class RealDropTableOption extends DropTableOption {
	
	/**
	 * 删除表操作
	 *
	 * @param destination 指令
	 * @param schemaName  实例名称
	 * @param tableName   表名称
	 * @param rowChange   数据
	 * @return
	 * @author 阿导
	 * @time 2018/5/29 08:59
	 * @CopyRight 万物皆导
	 */
	@Override
	public void doOption(String destination, String schemaName, String tableName, CanalEntry.RowChange rowChange) {
		System.out.println("======================接口方式（删除表操作）==========================");
		System.out.println("use "+schemaName+";\n"+rowChange.getSql());
		System.out.println("\n======================================================");
		
	}
}
