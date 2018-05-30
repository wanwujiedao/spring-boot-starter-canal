package com.wwjd.canal.canaltest.test;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.wwjd.starter.canal.annotation.*;
import com.wwjd.starter.canal.client.core.CanalMsg;
import org.springframework.util.CollectionUtils;

/**
 * 注解方法测试
 *
 * @author 阿导
 * @CopyRight 万物皆导
 * @created 2018/5/28 17:31
 * @Modified_By 阿导 2018/5/28 17:31
 */
@CanalEventListener
public class MyAnnoEventListener {
	
	@InsertListenPoint
	public void onEventInsertData(CanalMsg canalMsg, CanalEntry.RowData rowData) {
		System.out.println("======================注解方式（新增数据操作）==========================");
		String sql = "use " + canalMsg.getSchemaName() + ";\n";
		StringBuffer colums = new StringBuffer();
		StringBuffer values = new StringBuffer();
		rowData.getAfterColumnsList().forEach((c) -> {
			colums.append(c.getName() + ",");
			values.append("'" + c.getValue() + "',");
		});
		
		
		sql += "INSERT INTO " + canalMsg.getTableName() + "(" + colums.substring(0, colums.length() - 1) + ") VALUES(" + values.substring(0, values.length() - 1) + ");";
		try {
			System.out.println(sql);
			//mapper.doOption(sql);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		System.out.println("\n======================================================");
		
	}
	
	@UpdateListenPoint
	public void onEventUpdateData(CanalMsg canalMsg, CanalEntry.RowData rowData) {
		System.out.println("======================注解方式（更新数据操作）==========================");
		
		String sql = "use " + canalMsg.getSchemaName() + ";\n";
		StringBuffer updates = new StringBuffer();
		StringBuffer conditions = new StringBuffer();
		rowData.getAfterColumnsList().forEach((c) -> {
			if (c.getIsKey()) {
				conditions.append(c.getName() + "='" + c.getValue() + "'");
			} else {
				updates.append(c.getName() + "='" + c.getValue() + "',");
			}
		});
		sql += "UPDATE " + canalMsg.getTableName() + " SET " + updates.substring(0, updates.length() - 1) + " WHERE " + conditions;
		System.out.println(sql + "\n======================================================");
	}
	
	@DeleteListenPoint
	public void onEventDeleteData(CanalEntry.RowData rowData, CanalMsg canalMsg) {
		
		System.out.println("======================注解方式（删除数据操作）==========================");
		if (!CollectionUtils.isEmpty(rowData.getBeforeColumnsList())) {
			String sql = "use " + canalMsg.getSchemaName() + ";\n";
			
			sql += "DELETE FROM " + canalMsg.getTableName() + " WHERE ";
			StringBuffer idKey = new StringBuffer();
			StringBuffer idValue = new StringBuffer();
			for (CanalEntry.Column c : rowData.getBeforeColumnsList()) {
				if (c.getIsKey()) {
					idKey.append(c.getName());
					idValue.append(c.getValue());
					break;
				}
				
				
			}
			
			sql += idKey + " =" + idValue + ";";
			
			System.out.println(sql + "\n======================================================");
			
		}
	}
	
	@CreateTableListenPoint
	public void onEventCreateTable(CanalEntry.RowChange rowChange) {
		System.out.println("======================注解方式（创建表操作）==========================");
		System.out.println("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
		System.out.println("\n======================================================");
	}
	
	@DropTableListenPoint
	public void onEventDropTable(CanalEntry.RowChange rowChange) {
		System.out.println("======================注解方式（删除表操作）==========================");
		System.out.println("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
		System.out.println("\n======================================================");
	}
	
	@AlertTableListenPoint
	public void onEventAlertTable(CanalEntry.RowChange rowChange) {
		System.out.println("======================注解方式（修改表信息操作）==========================");
		System.out.println("use " + rowChange.getDdlSchemaName() + ";\n" + rowChange.getSql());
		System.out.println("\n======================================================");
	}
	
	
}
