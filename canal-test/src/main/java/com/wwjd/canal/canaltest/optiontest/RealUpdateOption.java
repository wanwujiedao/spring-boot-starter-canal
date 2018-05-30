package com.wwjd.canal.canaltest.optiontest;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.client.abstracts.option.content.UpdateOption;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 需要自己实现的更新处理机制
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018年05月29日 09:49:00
 * @Modified_By 阿导 2018/5/29 09:49
 */
@Component
public class RealUpdateOption extends UpdateOption {
	
	/**
	 * 更新数据操作
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
		System.out.println("======================接口方式（更新数据操作）==========================");
		
		List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
		for (CanalEntry.RowData rowData : rowDatasList) {
			String sql = "use " + schemaName + ";\n";
			StringBuffer updates = new StringBuffer();
			StringBuffer conditions = new StringBuffer();
			rowData.getAfterColumnsList().forEach((c) -> {
				if (c.getIsKey()) {
					conditions.append(c.getName() + "='" + c.getValue() + "'");
				} else {
					updates.append(c.getName() + "='" + c.getValue() + "',");
				}
			});
			sql += "UPDATE " + tableName + " SET " + updates.substring(0, updates.length() - 1) + " WHERE " + conditions;
			
			System.out.println(sql);
		}
		System.out.println( "\n======================================================");
		
	}
}
